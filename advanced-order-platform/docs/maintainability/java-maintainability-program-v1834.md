# Java v1834 可维护性深度优化计划

## 目标与边界

本轮暂停新增功能，集中降低维护风险。所有优化必须保持现有 HTTP 路由、响应字段、数据库事务、RabbitMQ 消息、只读证据字节、凭据边界以及部署和回滚边界不变。这里的“完成”不是主观判断，而是由源码热点普查、JUnit 预算门、原有回归测试、JaCoCo、SpotBugs、Spotless 和远端 CI 共同证明。

## 需求—实现—证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 热点数据可复现 | `scripts/java-maintainability-census.ps1` 同时扫描生产与测试 Java 文件 | `-Json` 输出固定字段及热点列表 | 已实现 |
| Windows 长路径可读取 | 使用 `StreamReader` 和 `\\?\` 前缀逐行计数 | 当前 1475 个生产 Java 文件均能完成扫描 | 已实现 |
| 巨型文件不能继续膨胀 | `JavaMaintainabilityBudgetTests` 固定四档聚合预算 | 任一预算上升即测试失败 | 已实现 |
| 重点单点不能藏在聚合数后增长 | 对前五个重点文件设置单文件行数上限 | 单文件超过 v1834 上限即失败 | 已实现 |
| 后续拆分必须产生净改善 | v1835–v1837 每版降低对应单文件上限，并尽量降低聚合档位 | 维护预算只能收紧 | 进行中 |
| 评审账本与现实一致 | v1833 行收口到提交、tag、push 与 CI run | `docs/production-excellence-progress.md` | 已修正 |

## v1834 基线

使用下面的唯一复现命令：

```powershell
.\scripts\java-maintainability-census.ps1 -Json
```

生产源码基线为 1475 个 Java 文件，最大文件 1530 行；超过 500、750、1000 行的文件数分别不高于 39、5、3。测试源码最大文件 854 行；超过 500、750、1000 行的文件数分别不高于 8、2、0。

首批单文件预算为：`OpsEvidenceService` 1530 行、`FailedEventMessageService` 1126 行、`OpsShardReadinessRoutePaths` 1111 行、`ReleaseApprovalVerificationHintBuilder` 874 行、`ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder` 793 行。这些数字是历史债务上限，不是推荐尺寸。

## 四版执行顺序

1. v1834 建立普查与预算门，落实 v1833 检查点纠偏。
2. v1835 把失败事件查询、分页、过滤和 CSV 导出从命令服务中拆出。
3. v1836 把失败事件管理、审批、重放和消息持久化按职责拆开，使原服务回到薄门面。
4. v1837 拆分 ReleaseApproval 证据构建链中最大的单点，并收紧对应预算。

## v1835 进展

失败事件查询职责已经从 1126 行门面中拆出，门面降到 662 行。三个包内协作者分别为 310、159、103 行；生产源码超过 750 行和 1000 行的文件数分别从 5、3 收紧到 4、2。详细证据见 `failed-event-query-split-v1835.md`。

## 失败条件

- 为让测试通过而提高任何预算，视为失败。
- 修改已有路由字符串、响应字段顺序、CSV 列、摘要输入顺序、事务边界或消息头，视为失败。
- 新助手类只是复制旧逻辑、原巨型类没有实质缩小，视为失败。
- 讲解在最终 verify 之后补写，视为失败并重新执行 verify。
- 本地 verify、远端 CI、账本收口或清理任一缺失，版本不算完成。

## 本地验证结果

- 聚焦维护性与讲解门：9 个测试，0 失败、0 错误、0 跳过，Spotless 通过。
- 全量 `mvnw verify`：1676 个测试，0 失败、0 错误、0 跳过；JaCoCo 全部覆盖率门达标；SpotBugs `BugInstance=0`、`Error=0`；耗时 12 分 26 秒。
- 远端 GitHub Actions：run `29060123279` 成功，普通 verify、生产配置启动冒烟、JaCoCo 报告上传和 Docker-tagged 集成测试全部通过。
