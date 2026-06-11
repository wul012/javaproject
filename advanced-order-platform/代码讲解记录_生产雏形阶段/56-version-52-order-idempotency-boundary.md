> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第五十二版代码讲解：order idempotency boundary

本版目标是补强订单创建的幂等边界。之前代码已经要求 `Idempotency-Key`，并且同 key 会返回已有订单；v52 进一步解决一个更真实的问题：如果调用方拿同一个 key 提交了不同的订单请求，Java 不能把它误当成重放。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v159-post-release-evidence-review-roadmap.md
```

计划要求 Java v52 做：

```text
order idempotency boundary
先在 Java 内部补订单创建幂等边界和只读 evidence
不接 mini-kv
不改支付/库存核心事务
```

所以本版不是把 mini-kv 接入 Java，而是先把 Java 自己的订单幂等语义立稳。

## 请求指纹类

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/order/OrderIdempotencyFingerprint.java
```

核心版本号：

```java
static final String VERSION = "order-create-request-sha256.v1";
```

它把请求转换成稳定文本：

```java
Map<Long, Integer> quantities = new TreeMap<>();
request.items().forEach(item -> quantities.merge(item.productId(), item.quantity(), Integer::sum));
```

这里有两个细节：

```text
TreeMap
 -> 按 productId 排序，避免请求 item 顺序影响指纹

merge
 -> 同一个 productId 分多行提交时先聚合，避免拆行方式影响指纹
```

最终参与计算的字段是：

```java
version=order-create-request-sha256.v1
customerId=<customerId>
items=<productId>:<quantity>;<productId>:<quantity>;
```

再用 SHA-256 输出：

```java
return "sha256:" + HexFormat.of().formatHex(hash);
```

这让后续证据能判断：同一个 key 的两次请求到底是不是同一笔订单。

## 订单实体新增字段

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/order/SalesOrder.java
```

新增字段：

```java
@Column(length = 80)
private String idempotencyRequestFingerprint;
```

构造新订单时把指纹和 key 一起保存：

```java
private SalesOrder(UUID customerId, String idempotencyKey, String idempotencyRequestFingerprint) {
    this.customerId = customerId;
    this.idempotencyKey = idempotencyKey;
    this.idempotencyRequestFingerprint = idempotencyRequestFingerprint;
}
```

`SalesOrder.place(...)` 也改成显式接收指纹：

```java
SalesOrder.place(request.customerId(), idempotencyKey, requestFingerprint, drafts);
```

这说明指纹属于订单创建边界的一部分，而不是 controller 或临时变量里的附属信息。

## 服务层冲突边界

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/order/OrderApplicationService.java
```

创建订单入口现在先算请求指纹：

```java
String requestFingerprint = OrderIdempotencyFingerprint.create(request);
```

然后读取同 key 订单：

```java
return orderRepository.findByIdempotencyKey(idempotencyKey)
        .map(existing -> replayExistingOrder(existing, requestFingerprint))
        .orElseGet(() -> placeNewOrder(idempotencyKey, request, requestFingerprint));
```

关键判断在 `replayExistingOrder(...)`：

```java
if (StringUtils.hasText(existingFingerprint) && !existingFingerprint.equals(requestFingerprint)) {
    throw new BusinessException(HttpStatus.CONFLICT, "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
            "Idempotency-Key was already used for a different create order request");
}
```

这段代码把语义分清楚：

```text
key 相同，指纹相同
 -> replay existing order

key 相同，指纹不同
 -> 409 conflict

老数据没有 fingerprint
 -> 保持旧 replay 兼容，不强行破坏历史订单
```

冲突检查发生在 `placeNewOrder(...)` 之前，所以不会执行：

```text
inventoryService.reserve(...)
outboxRepository.save(OutboxEvent.orderCreated(...))
recordHistory(...)
```

这就是“拒绝发生在副作用之前”的核心价值。

## 数据库迁移

新增文件：

```text
src/main/resources/db/migration/h2/V12__order_idempotency_request_fingerprint.sql
src/main/resources/db/migration/postgresql/V12__order_idempotency_request_fingerprint.sql
```

内容保持一致：

```sql
alter table orders
    add column idempotency_request_fingerprint varchar(80);
```

列允许为空，是为了兼容已有订单。新订单会写入 `sha256:` 指纹，旧订单没有指纹时仍按旧逻辑重放。

## ops evidence

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

`OpsEvidenceResponse` 新增：

```java
public record OrderIdempotency(
        String boundaryVersion,
        String createOrderEndpoint,
        String createOrderMethod,
        String requiredHeader,
        int maxKeyLength,
        String requestFingerprintVersion,
        String requestFingerprintScope,
        String sameKeySameRequestOutcome,
        String sameKeyDifferentRequestOutcome,
        String sameKeyDifferentRequestErrorCode,
        String authoritativeStore,
        boolean miniKvConnected,
        boolean externalTokenStoreConnected,
        boolean changesPaymentOrInventoryTransaction
) {
}
```

`OpsEvidenceService.orderIdempotency()` 固定说明：

```java
"java-order-idempotency-boundary.v1"
"IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"
false
false
false
```

三个 `false` 很关键：

```text
miniKvConnected=false
externalTokenStoreConnected=false
changesPaymentOrInventoryTransaction=false
```

它们明确告诉 Node：Java v52 只是把 Java 内部幂等边界说清楚，还没有把 mini-kv 接进订单交易。

## 静态样本

新增文件：

```text
src/main/resources/static/contracts/order-idempotency-boundary.sample.json
```

样本固定说明同 key 两类结果：

```json
"sameKeySameRequest": {
  "httpStatus": 200,
  "outcome": "REPLAY_EXISTING_ORDER"
}
```

```json
"sameKeyDifferentRequest": {
  "httpStatus": 409,
  "errorCode": "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
  "outcome": "REJECT_BEFORE_ORDER_MUTATION"
}
```

样本也说明存储边界：

```json
"storage": {
  "authoritativeStore": "orders table",
  "miniKvConnected": false,
  "orderAuthoritativeStoreRemainsJavaDatabase": true
}
```

这正好对齐计划里的“Java 先不强依赖 mini-kv”。

## 测试覆盖

服务层测试：

```text
src/test/java/com/codexdemo/orderplatform/OrderApplicationServiceTests.java
```

新增同一请求重放测试：

```java
void createOrderReplaysSameCanonicalRequestWhenLinesAreReordered()
```

它证明 item 顺序变化不会导致误判，因为指纹会排序和聚合。

新增不同请求冲突测试：

```java
void createOrderRejectsSameIdempotencyKeyForDifferentRequestBeforeSideEffects()
```

它检查：

```text
抛 BusinessException
code=IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST
库存没有再次变化
Outbox 没有再次增加
```

HTTP 层测试：

```text
src/test/java/com/codexdemo/orderplatform/OrderIdempotencyBoundaryIntegrationTests.java
```

它验证真实接口语义：

```text
第一次 POST /api/v1/orders -> 201 Created
相同 key + 相同 body -> 200 OK
相同 key + 不同 body -> 409 Conflict
```

ops evidence 测试：

```text
src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java
src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java
```

它们锁定：

```text
orderIdempotency.boundaryVersion
sameKeyDifferentRequestErrorCode
miniKvConnected=false
/contracts/order-idempotency-boundary.sample.json
fieldGuide 包含 orderIdempotency
```

PostgreSQL 迁移测试：

```text
src/test/java/com/codexdemo/orderplatform/PostgresMigrationIntegrationTests.java
```

这次顺手修正了迁移数量期望：

```java
assertThat(appliedMigrations).isEqualTo(12);
```

并新增对真实列和写入值的断言：

```java
assertThat(idempotencyFingerprintColumnCount).isEqualTo(1);
assertThat(idempotencyRequestFingerprint).startsWith("sha256:");
```

## 验证、归档和成熟度变化

运行调试归档写入：

```text
b/52/解释/说明.md
b/52/图片/
```

本版验证覆盖：

```text
聚焦测试 28 个通过
非 Docker 回归 70 个通过
打包成功
HTTP smoke 成功，覆盖 201 / 200 / 409
PostgreSQL Testcontainers 迁移测试 1 个通过
Docker 已关闭
```

成熟度变化是：订单创建幂等不再只是“按 key 查到就返回”，而是能判断“这个 key 是否真的对应同一个订单请求”。这让订单核心更接近生产系统对幂等键的要求，也给后续 Node v160 做纵向 readiness review 提供了清晰证据。

## 一句话总结

v52 把 Java 订单创建幂等边界从简单 key replay 推进到 key + 请求指纹校验，并用 live evidence、静态样本、H2/PostgreSQL 测试把这个边界固定下来。
