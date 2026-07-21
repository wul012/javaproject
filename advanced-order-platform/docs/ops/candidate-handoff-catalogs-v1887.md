# CandidateDocument handoff Catalog 收敛（v1887）

## Family design

- Abstraction: 两条 handoff 各由一个短 Catalog bundle 拥有完整证据集合。
- Data boundary: 固定条目、来源投影和 gate 命名留在各自领域 bundle。
- Behavior boundary: `from(source)` 一次返回类型化且不可变的 `Evidence`。
- Dependency direction: Service -> local Catalog bundle -> source Response。
- Size boundary: 两个 bundle 各自不超过 300 行，不创建跨 Response 万能映射器。
- Compatibility: 公共 route、Response、Support、Controller、文本和列表顺序不变。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
|---|---|---|---|
| 两份完整响应兼容 | 旧实现冻结规范 JSON 与集合尺寸 | `HandoffResponseOracleTests` | 旧、新实现各 2/2 |
| 十四个 Catalog 收敛 | 两个短领域 bundle | Catalog census + structure gate | 14 -> 2，Catalog 332 -> 320 |
| Service 只组装一次 | `Evidence` 显式承载七组列表 | source structure assertion | 两个 service 各一次 `from` |
| 不制造巨型文件 | 每个新 owner <= 300 行 | line-count ratchet | 235 / 182 行 |
| 只读边界不变 | 原 Support 和 Response 继续拥有状态判断 | 既有 service/controller tests | 35/35 相关测试通过 |

## Planned Owners

- Candidate document request-package handoff -> `HandoffCatalog`。
- Material submission precheck handoff -> `PrecheckHandoffCatalog`。

## Frozen Responses

- Request handoff 集合尺寸为 `6/5/15/15/8/10/25/20`，规范 JSON 摘要为
  `3c988b527fcf1b53946d9cab7ea91866609b2424ce981c87ad3fef8b849e13c2`。
- Precheck handoff 集合尺寸为 `6/5/10/10/8/10/42/26`，规范 JSON 摘要为
  `91473893363f7062af79e05237e1b43407f73bd14176efcfe844fc0331f21cf5`。

## Measured Outcome

- `ops` Java 文件 `1249 -> 1237`，Catalog 文件 `332 -> 320`，Service 仍为 `375`。
- 生产长 stem/使用/唯一值 `1154/20240/2713 -> 1140/20178/2699`。
- 测试长 stem/使用/唯一值 `746/9916/3763 -> 737/9898/3741`。
- exact name baseline 新增 `0`、删除 `46`；全局 500 行以上文件仍为 `32`，最大文件仍为 `738` 行。
- 退休 Artifact Catalog 的 `DM_CONVERT_CASE` waiver 被删除；slug 显式使用 `Locale.ROOT`，豁免集 `676 -> 675`。
- 相关行为、冻结输出、结构、变更和优雅门合计 `56/56` 通过；全量 release gate 通过 `1,998` 个测试，
  耗时 `15:25`，JaCoCo 分析 `2,121` 类且全部阈值满足，SpotBugs `0/0`，jar 为 `68,017,026` 字节。
- 中文讲解为 `3,060` 个 Han、严格十个标准章节；授权归档为 `1,698` 个文件、`20,160,868` 原始字节。
- 远端 implementation/closeout CI 与 annotated tag 在发布边界补齐。

## Failure Conditions

- 任一 JSON 字段值、列表顺序、集合尺寸、gate 或 check 变化，版本失败。
- 修改 Response、fixture、旧输出期望或公共 route 来迁就收敛，版本失败。
- 新 owner 超过 300 行、旧十四个 Catalog 仍存在或引入第三个相似工具类，版本失败。
