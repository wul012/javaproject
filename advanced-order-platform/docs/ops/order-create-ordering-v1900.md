# v1900 Order Create Side Effect Ordering

## Family design

- Abstraction: the existing `OrderApplicationService` remains the orchestration owner.
- Data boundary: the persisted idempotency key remains the database uniqueness authority.
- Behavior boundary: order construction and uniqueness flush precede inventory mutation.
- Compatibility boundary: HTTP responses, status codes, contracts, and event payloads stay unchanged.
- Failure boundary: a failed order insert must not call inventory, outbox, or history writers.

## Scope

This version hardens the create-order transaction without changing the version mode or public
contract. The service now flushes the new order through `IdempotencyStore` before reserving
inventory. A transaction still rolls the order back if inventory reservation fails; a database
uniqueness failure therefore stops before inventory work begins. The focused unit test freezes this
ordering as a mechanical boundary.

## 起点与范围

本版起点为 master `832fad568db00429f8cf4d3c8689210e5bfc4c49`，对应上次
canonical CI `30022883877` 已成功；最近标签为
`v1899-order-platform-marker-evidence-builders`。工作开始前没有用户未提交变更。
本次只优化 Java 下单事务，不启动跨仓功能计划，不修改其它项目、Maven 版本、接口结构、
历史标签或测试基线。继续使用 `vNNNN-order-platform-*` 标签，本版标签为
`v1900-order-platform-create-ordering`。实现提交为 `6d5bbc0e076194f4b9e0b41ec0f17da4c33853b1`。

## 中文代码讲解

### 为什么调整这一行

`createOrder` 先查询幂等键；不存在时进入 `placeNewOrder`。旧流程先锁定并预占库存，
随后才调用 `saveNewOrder`。两个请求同时查询到键不存在时，最终仍由数据库唯一约束裁决；
但失败的请求可能在被拒绝前已经读取、锁定或修改库存对象。事务回滚能恢复数据，却不能
消除这些多余操作。本版让订单构建与唯一键检查先发生，数据库拒绝插入时不会进入库存逻辑。

代码仍使用已有的 `JpaIdempotencyStore.saveNewOrder`，其实现调用 `saveAndFlush`。
这里的 flush 只是把 SQL 送到数据库，并不是提交事务。服务外层的 `@Transactional`
保持原样，所以后续库存不足仍会回滚刚插入的订单、订单行和此前更新的库存及流水。
Outbox 与状态历史仍在库存成功之后写入，不新增提前发送消息或独立提交事务的路径。

### 测试各自证明什么

`OrderCreateOrderingTests` 用三个单元测试固定调用次序：唯一键插入异常时，库存、
Outbox、历史及支付没有交互；正常创建依次插入、预占、写事件、写历史；库存异常继续向外
传播且没有后续事件。把生产代码恢复到旧顺序而不改断言时，三个测试均失败，说明测试确实
能识别这次改动，而不是只检查返回字段。

`OrderCreateRollbackTests` 不在测试方法外包测试事务，而是通过 Spring 代理调用真实服务。
这样断言读取的是服务自身提交或回滚后的数据库状态。两个场景分别让第一件商品库存不足，
以及第一件已预占、第二件库存不足。检查六张表的行数、两件商品的可用及预占库存、幂等键
查询和版本号；补足库存后以同一请求重试，应该只生成一张订单。再次重放必须返回同一订单，
并且表行数和库存不变。每个场景创建专用商品，不改既有 fixture 数据。

### 本版没有证明什么

这些测试不等于 PostgreSQL 双请求竞态测试，也没有实现并发失败请求的自动重放或 HTTP
错误映射。数据库生成的订单 ID 不保证连续，回滚可能消耗序列值；本版不承诺失败请求保持
后续 ID 不变。唯一约束与库存不足同时发生时，错误优先级可能随检查顺序改变。
正常创建、顺序重放和同键异参的现有 HTTP 测试不变。没有扩大支付、部署或跨仓执行权限。

## 需求与证据矩阵

| 需求 | 实现 | 会失败的机械检查 | 状态 |
| --- | --- | --- | --- |
| 单项目单版本 | 仅 Java 下单事务及两份测试 | Git diff 范围与生产源码增量 | 已核对 |
| 唯一键失败前不碰库存 | 现有 service 调整一次调用顺序 | 3 个顺序测试，旧代码 3 失败 | 通过 |
| 库存失败保持事务原子性 | 保留原事务边界 | 2 个 H2 回滚及重试场景 | 通过 |
| 不降低质量门 | 所有既有测试、fixture、阈值保持原样 | 全量 release gate | clean 重跑通过，2,043 项测试 |
| 不改版本模式 | 保留 Maven 与 canonical tag 模式 | pom 无差异、标签 peel 核验 | 通过 |
| 提交推送 | 一个代码提交及必要发布记录 | 远端 master/tag SHA 与 CI | 通过 |

## Deviations

- 先前概括性的项目质量分数不是完整审计结果，本版只报告实际验证的这一条事务路径。
- 新讲解原计划写入活动归档，但归档精确集合、文件上限与字节上限均被锁定；本次讲解集中
  保存在这个 `docs/ops/` 文件，不扩大归档预算，也不修改已有讲解、manifest 或门禁。
- 初次测试桩用 `when` 覆盖已有 answer 时触发空参数，已改成 `doThrow`；修复后旧代码
  实测为 3 failures / 0 errors，随后才恢复新顺序。没有修改断言来迎合旧实现。
- 文档收尾后执行一次 `clean` 再跑 release gate，避免沿用增量编译或累积覆盖率数据。

## Evidence

`OrderCreateOrderingTests` makes the store insert fail, verifies the write ordering, and checks
that inventory, outbox, and history collaborators receive no calls. `OrderCreateRollbackTests`
uses two real H2 cases (failure before and after the first inventory item) to verify that orders,
lines, movements, Outbox, and history roll back together; the same request then succeeds once
stock is restored and replays without new rows. The focused command ran 28 tests with zero
failures (3 ordering, 2 rollback, 20 application-flow, 3 HTTP idempotency). The clean
`scripts/verify-release.ps1` run took 11:35 and ran 2,043 tests with zero failures, met all JaCoCo floors, and reported
zero SpotBugs findings. No API, migration, route, or cross-repository contract changes.

Reproduce from `D:\javaproj\advanced-order-platform`:

```powershell
.\mvnw.cmd -B '-Dtest=OrderCreateOrderingTests,OrderCreateRollbackTests,OrderApplicationServiceTests,OrderIdempotencyBoundaryIntegrationTests' test
.\mvnw.cmd -B clean
.\scripts\verify-release.ps1
```

Expected release output: `Tests run: 2043, Failures: 0, Errors: 0, Skipped: 0`,
`All coverage checks have been met.`, `BugInstance size is 0`, `Error size is 0`,
`BUILD SUCCESS`. The local clean run completed in 11:35. GitHub Actions run
`34023429770` also passed: headless job `101460079012` in 18m31s and Docker job
`101460079132` in 2m16s. Implementation `6d5bbc0e076194f4b9e0b41ec0f17da4c33853b1` was pushed to `master`;
tag `v1900-order-platform-create-ordering` peels to the same commit. The release script pinned
v1899 to `7c171c6ccae2b7b037a7224cc98a6a2537aad416`.

发布结果记录作为同一 v1900 的纯文档提交，不移动已验证的标签，不改动已通过 CI 的源码
和测试。推送使用单次 `git -c http.sslBackend=openssl push`，保留证书验证，未修改
全局 Git 配置。实现 CI、标签及此发布记录应分别按其提交 SHA 核验，不能互相冒充。

## Failure conditions

- Inventory reservation happens before the idempotency store flush.
- A failed insert produces an outbox event or status history row.
- Public response, route, schema, or idempotency contract changes.
- Focused tests, Spotless, or the release verification gate fails.
