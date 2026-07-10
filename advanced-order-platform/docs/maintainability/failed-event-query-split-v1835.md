# Java v1835 失败事件查询职责拆分

## 目标

把 `FailedEventMessageService` 中与只读查询有关的职责从命令、审批、重放和消息发布逻辑中分离，同时保持所有控制器调用、事务注解、分页默认值、排序表达式、JPA 条件、CSV 列、异常状态与异常文本不变。本版不增加功能，不改数据库迁移，不改 RabbitMQ 消息。

## 需求—实现—证据矩阵

| 需求 | 实现 | 证据 | 状态 |
| --- | --- | --- | --- |
| 公共入口不变 | `FailedEventMessageService` 保留原 public 方法与 `@Transactional(readOnly = true)` | 现有控制器和集成测试不改调用点 | 通过 |
| 查询编排独立 | 新增包内 `FailedEventQueryService` | 四组查询、列表和 CSV 导出统一委托 | 通过 |
| JPA 条件独立 | 新增 `FailedEventSearchSpecifications` | 四种实体 Specification 由结构门锁定 | 通过 |
| 分页排序独立 | 新增 `FailedEventSearchPageSupport` | 直接单测默认值、上限、稳定排序与错误文本 | 通过 |
| 巨型类实质缩小 | 原服务 1126 行降到 662 行 | 正式 StreamReader/JUnit 行数口径 | 通过 |
| 新类不形成新巨型类 | 新类分别为 310、159、103 行 | 单文件 ratchet | 通过 |
| 聚合债务下降 | 超过 750 行从 5 降到 4，超过 1000 行从 3 降到 2 | `scripts/java-maintainability-census.ps1 -Json` | 通过 |

## 保持不变的查询契约

- 搜索默认 page 为 0、默认 size 为 50，size 或兼容 limit 的合法范围仍为 1 至 200。
- CSV 导出默认 limit 为 1000，最大值仍为 5000。
- 默认排序仍分别为 `failedAt,desc`、`attemptedAt,desc` 与 `changedAt,desc`。
- 非 `id` 排序仍追加 `id desc` 作为稳定分页的并列键；对外返回的 sort 文本不包含该内部并列键。
- 事件类型、聚合类型、聚合标识、操作员和角色仍先去除首尾空白后做精确相等查询；角色仍经过 `FailedEventReplayProperties.normalize`。
- 时间区间仍为闭区间，起点晚于终点时保持原 400 状态和字段化错误文本。
- 管理历史与审批历史列表仍先校验正数 id，再确认失败事件存在；重放尝试列表保留原先只做存在性检查的行为。
- CSV 仍调用既有 `FailedEventCsvExporter`，列名、顺序、转义和换行均未改动。

## 结构结果

`FailedEventMessageService` 继续是控制器、监听器和集成测试看到的唯一公共门面。它只把九个只读方法交给 `FailedEventQueryService`，因此事务代理边界没有移动。查询编排器拥有四个仓储和角色规范化属性；Specification 工具只负责把条件对象翻译成 JPA predicate；分页支持只负责输入规范化、排序白名单和 `PageRequest`。三个协作者均为包可见，不能成为跨包的新公共 API。

本版还纠正了一次行数测量偏差：`Measure-Object -Line` 会忽略空白行，不能用于 Java ratchet。正式数字只允许由维护性普查脚本、JUnit 的 `Files.lines().count()` 或显式 `StreamReader.ReadLine()` 循环产生；该规则已写入 `AGENTS.md`，避免第三次发生。

## 验证

聚焦命令覆盖 `FailedEventManagementSearchIntegrationTests`、`FailedEventReplayApprovalSearchIntegrationTests`、`FailedEventSearchIntegrationTests`、`FailedEventSearchValidationIntegrationTests`、分页支持单测、查询结构门和维护预算门，共 17 个测试，0 失败、0 错误、0 跳过，Spotless 通过。最终全量结果与远端 CI 在版本收口时写回进度账本。

第一次全量 verify 的 1682 个测试与 JaCoCo 全部通过，SpotBugs 随后发现搬出的排序表达式调用了未指定 Locale 的 `toLowerCase()`。本版没有加入排除项，而是改为 `Locale.ROOT`，使 `asc`/`desc` 输出不依赖运行机器默认地区；修复后重新执行完整 verify。

修复后的最终全量 `mvnw verify` 在 10 分 58 秒内通过：1682 个测试，0 失败、0 错误、0 跳过；JaCoCo 全部覆盖率门达标；SpotBugs `BugInstance=0`、`Error=0`；Spotless 保持清洁。

远端 GitHub Actions run `29063754021` 对实现提交 `db373a41` 独立复现成功：`Build and headless regression` 用时 16 分 59 秒，完成 Spotless ratchet、无 Docker 全量 verify、生产配置启动冒烟与 JaCoCo 报告上传；`Docker-tagged integration tests` 用时 2 分 7 秒并通过。本版由 tag `v1835-order-platform-production-excellence-failed-event-query-responsibility-split` 固定证据边界。
