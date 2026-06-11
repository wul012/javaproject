> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第五十三版代码讲解：idempotency store abstraction

本版目标是把订单创建幂等的“存储边界”从服务层里抽出来。v52 已经解决同一个 `Idempotency-Key` 被不同请求复用时的 409 冲突问题；v53 进一步为后续 Redis / mini-kv TTL token 实验预留接口，但默认实现仍然是 Java 自己的数据库路径。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v159-post-release-evidence-review-roadmap.md
```

计划要求 Java v53 做：

```text
idempotency store abstraction
抽象 IdempotencyStore
默认仍用 Java 本地/DB 实现
mini-kv adapter 只作为 disabled candidate
不改变订单权威存储
不让 Node 直接触发真实写操作
```

所以本版不是接入 mini-kv，而是先把 Java 内部边界做成可解释、可测试、可由 Node 只读引用的形态。

## 抽象接口

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/order/IdempotencyStore.java
```

核心接口很小：

```java
public interface IdempotencyStore {
    Optional<IdempotencyStoreEntry> findByKey(String idempotencyKey);

    SalesOrder saveNewOrder(SalesOrder order);

    IdempotencyStoreDescriptor descriptor();
}
```

这三个方法对应三件事：

```text
findByKey
 -> 用 Idempotency-Key 查已有订单

saveNewOrder
 -> 保存首次创建的订单

descriptor
 -> 给 ops evidence 解释当前活动存储和候选适配器
```

这里没有设计成一个庞大的缓存接口，是因为当前计划只需要隔离订单创建幂等边界。接口越小，后续接 mini-kv candidate 时越不容易误把它扩展成订单权威存储。

## 存储返回值

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/order/IdempotencyStoreEntry.java
```

内容是：

```java
public record IdempotencyStoreEntry(
        SalesOrder order,
        String requestFingerprint
) {
}
```

这不是直接返回 `SalesOrder` 的原因是：幂等判断真正需要的是“订单 + 创建请求指纹”。把它包装成 `IdempotencyStoreEntry` 后，服务层不需要关心指纹到底来自 JPA 列、缓存 token，还是未来某个候选 adapter。

## 默认 JPA 实现

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/order/JpaIdempotencyStore.java
```

查找仍然走原来的 repository：

```java
return orderRepository.findByIdempotencyKey(idempotencyKey)
        .map(order -> new IdempotencyStoreEntry(order, order.getIdempotencyRequestFingerprint()));
```

保存仍然是数据库事务里的 flush：

```java
public SalesOrder saveNewOrder(SalesOrder order) {
    return orderRepository.saveAndFlush(order);
}
```

也就是说，v53 没有绕开数据库，没有改变事务提交顺序，也没有把 token 写到外部系统。它只是把“幂等存储”这个角色从 `OrderApplicationService` 中拆出来。

## 描述符

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/order/IdempotencyStoreDescriptor.java
```

描述符固定表达当前边界：

```java
new IdempotencyStoreDescriptor(
        ABSTRACTION_VERSION,
        ACTIVE_STORE,
        JpaIdempotencyStore.class.getSimpleName(),
        "JPA_DATABASE",
        "orders table",
        "orders.idempotency_key",
        "orders.idempotency_request_fingerprint",
        true,
        false,
        false,
        true,
        false,
        "DISABLED_CANDIDATE_ONLY",
        MINI_KV_CANDIDATE + " is documented for later TTL-token experiments, not wired into create-order.",
        false
)
```

这段里最关键的是几组布尔值：

```text
javaDatabaseBacked=true
miniKvConnected=false
externalTokenStoreConnected=false
miniKvAdapterCandidateDeclared=true
miniKvAdapterEnabled=false
changesPaymentOrInventoryTransaction=false
```

它们让 evidence 能直接说明：mini-kv 已被规划为候选方向，但当前没有连接，也不影响订单交易。

## 服务层接入

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/order/OrderApplicationService.java
```

构造器新增依赖：

```java
private final IdempotencyStore idempotencyStore;
```

创建订单入口从直接查 repository 改成：

```java
return idempotencyStore.findByKey(idempotencyKey)
        .map(existing -> replayExistingOrder(existing, requestFingerprint))
        .orElseGet(() -> placeNewOrder(idempotencyKey, request, requestFingerprint));
```

新订单保存也改成：

```java
SalesOrder saved = idempotencyStore.saveNewOrder(order);
```

但原有的副作用顺序没有变化：

```text
aggregateQuantities(...)
loadProducts(...)
inventoryService.reserve(...)
SalesOrder.place(...)
idempotencyStore.saveNewOrder(...)
outboxRepository.save(OutboxEvent.orderCreated(...))
recordHistory(...)
```

所以 v53 是一版“边界抽象”，不是“事务重写”。

## 重放判断

`replayExistingOrder(...)` 的参数从 `SalesOrder` 改为 `IdempotencyStoreEntry`：

```java
private CreateOrderResult replayExistingOrder(IdempotencyStoreEntry existing, String requestFingerprint) {
    String existingFingerprint = existing.requestFingerprint();
    if (StringUtils.hasText(existingFingerprint) && !existingFingerprint.equals(requestFingerprint)) {
        throw new BusinessException(HttpStatus.CONFLICT, "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
                "Idempotency-Key was already used for a different create order request");
    }
    return new CreateOrderResult(OrderResponse.from(existing.order()), true);
}
```

这里保留了 v52 的兼容策略：

```text
已有 fingerprint 且不同
 -> 409

已有 fingerprint 且相同
 -> replay

历史数据没有 fingerprint
 -> 继续按旧订单重放
```

抽象层没有改变这个语义，只是把取数方式换成了接口。

## ops evidence

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

`OrderIdempotency` 新增字段：

```java
String storeAbstractionVersion,
String activeStore,
String activeStoreImplementation,
String activeStoreMode,
String authoritativeStore,
List<IdempotencyStoreCandidate> storeCandidates,
```

`OpsEvidenceService.orderIdempotency()` 从 `idempotencyStore.descriptor()` 生成证据：

```java
IdempotencyStoreDescriptor descriptor = idempotencyStore.descriptor();
```

活动存储写成：

```java
descriptor.activeStore()
descriptor.activeImplementation()
descriptor.activeMode()
descriptor.authoritativeStore() + " via " + descriptor.keyColumn()
        + " and " + descriptor.fingerprintColumn()
```

候选存储列表里第一项是活动 JPA store：

```java
new OpsEvidenceResponse.IdempotencyStoreCandidate(
        descriptor.activeStore(),
        "ORDER_CREATE_IDEMPOTENCY_AUTHORITY",
        true,
        true,
        descriptor.activeMode(),
        "Default Java database-backed idempotency store"
)
```

第二项是 disabled mini-kv candidate：

```java
new OpsEvidenceResponse.IdempotencyStoreCandidate(
        JpaIdempotencyStore.MINI_KV_CANDIDATE,
        "TTL_TOKEN_CANDIDATE",
        descriptor.miniKvAdapterEnabled(),
        descriptor.miniKvConnected(),
        descriptor.miniKvCandidateMode(),
        descriptor.disabledCandidateReason()
)
```

这让 Node 后续读取 `/api/v1/ops/evidence` 时能区分：

```text
当前真正生效的是 JPA_DATABASE
mini-kv 只是 DISABLED_CANDIDATE_ONLY
```

## 静态契约样本

新增文件：

```text
src/main/resources/static/contracts/order-idempotency-store-abstraction.sample.json
```

核心字段：

```json
"activeStore": {
  "name": "jpa-order-idempotency-store",
  "implementation": "JpaIdempotencyStore",
  "mode": "JPA_DATABASE",
  "orderAuthoritative": true
}
```

候选适配器：

```json
"disabledCandidates": [
  {
    "name": "mini-kv-ttl-token-adapter",
    "enabled": false,
    "connected": false,
    "mode": "DISABLED_CANDIDATE_ONLY"
  }
]
```

边界声明：

```json
"boundaries": {
  "orderAuthoritativeStoreRemainsJavaDatabase": true,
  "miniKvConnected": false,
  "changesPaymentOrInventoryTransaction": false,
  "nodeMayTriggerWrites": false
}
```

这份样本服务于后续 Node v161 drill runbook：它能引用 Java v53 的 store abstraction evidence，但不能把它当成写操作授权。

## 测试覆盖

新增单测：

```text
src/test/java/com/codexdemo/orderplatform/order/JpaIdempotencyStoreTests.java
```

它覆盖三件事：

```text
findsExistingOrderByIdempotencyKeyWithStoredFingerprint
 -> 查到订单后包装成 IdempotencyStoreEntry

savesNewOrderThroughRepositoryFlush
 -> 保存仍调用 saveAndFlush

descriptorKeepsMiniKvAsDisabledCandidate
 -> miniKvConnected=false，miniKvAdapterEnabled=false
```

`OpsEvidenceServiceTests` 也补充了 store abstraction 断言：

```java
assertThat(evidence.orderIdempotency().storeAbstractionVersion()).isEqualTo("java-idempotency-store.v1");
assertThat(evidence.orderIdempotency().activeStore()).isEqualTo("jpa-order-idempotency-store");
assertThat(evidence.orderIdempotency().storeCandidates())
        .extracting(OpsEvidenceResponse.IdempotencyStoreCandidate::name)
        .containsExactly("jpa-order-idempotency-store", "mini-kv-ttl-token-adapter");
```

`OpsOverviewIntegrationTests` 锁定新的静态样本 endpoint：

```java
mockMvc.perform(get("/contracts/order-idempotency-store-abstraction.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.activeStore.name").value("jpa-order-idempotency-store"))
        .andExpect(jsonPath("$.disabledCandidates[0].enabled").value(false))
        .andExpect(jsonPath("$.boundaries.nodeMayTriggerWrites").value(false));
```

## 验证、归档和成熟度变化

运行调试归档写入：

```text
b/53/解释/说明.md
b/53/图片/
```

本版验证覆盖：

```text
静态 JSON 校验通过
聚焦测试 32 个通过
非 Docker 回归 74 个通过
打包成功
HTTP smoke 成功，覆盖 health、ops evidence、静态样本、201 / 200 / 409
Docker 未启动
临时 Java 进程已停止
```

成熟度变化是：订单幂等从“硬编码 JPA repository 路径”升级成“有稳定接口、有活动实现、有候选适配器说明、有只读证据”的生产雏形结构。后续要接 mini-kv，也应先通过这个接口做短 TTL token 实验，而不是让 mini-kv 直接变成订单权威存储。

## 一句话总结

v53 把 Java 订单幂等存储做成可替换但默认安全的 `IdempotencyStore` 抽象，为后续 mini-kv TTL token 实验和 Node controlled drill runbook 留出边界清晰的接口位。
