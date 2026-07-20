# v1879 Release-Acceptance Package Renderer 收敛

## Family design

- Abstraction: 主报告、closeout receipt、archive index 各保留一个短 renderer。
- Data boundary: Catalog 与三类 response 继续拥有业务数据和公开契约。
- Behavior boundary: 主报告与 archive index 复用 `MarkdownSections.mapped`。
- Compatibility boundary: 先冻结 9 节 47 行、7 行、5 节 22 行三组旧输出。
- Migration boundary: 删除十二个长名 renderer 与一个 support，新增三个短类型。
- Dependency boundary: 三段只读 service chain、controller 与 route 均保持不变。

## 需求证据矩阵

| 需求 | 实现 | 可复现证据 | 状态 |
| --- | --- | --- | --- |
| 三类 Markdown 输出逐字节兼容 | `ReportRenderer`、`ReceiptRenderer`、`ArchiveIndexRenderer` | `PackageMarkdownTests` 在替换前后运行同一组 3 个精确断言 | 已满足 |
| 长名渲染器不再按章节复制 | 删除 12 个长名 renderer 和 1 个 support | `OpsEleganceCensusTests` 固定全局 renderer `67 -> 58`、行数 `4211 -> 3973` | 已满足 |
| 一个类只负责一种输出 | 报告、收据、索引分别建模，不合并为万能 renderer | 包内只允许三个短名 `*Renderer.java` | 已满足 |
| 公开接口不变化 | 三个 service 与 response 的公开类型、controller 路由原样保留 | 包测试、controller 测试和完整 `mvn verify` | 已满足 |
| 优雅收益只能继续增加 | 更新名称 baseline、ops 文件上限和 renderer ratchet | `JavaEleganceGateTests`、`JavaChangeGateTests`、`OpsEleganceCensusTests` | 已满足 |

## 实现结果

旧实现把一份主报告拆成“archive、CI、decision、lineage、next-change、review、runtime-boundary、scorecard、source”等十二个类，再由一个同样长名的聚合器拼装。类的数量看似体现了单一职责，实际抽象边界却落在 Markdown 标题上：每增加或调整一段展示文本，都要跨多个只服务于这一份报告的类型跳转。v1879 将边界上移到真正独立的三个产品输出：完整验收报告、closeout 收据、归档索引。

`ReportRenderer` 只把响应中已有的九组业务数据映射为九个 Markdown section；`ReceiptRenderer` 只输出七行闭环收据；`ArchiveIndexRenderer` 只映射五个归档索引 section。Catalog 仍决定数据，response 仍决定公开契约，service 仍编排上游证据，renderer 只负责最后一步表现。三个类彼此不共享可变状态，也没有形成一个带模式分支的巨型渲染器。

## 兼容性证明

迁移前先用 `PackageMarkdownTests` 冻结现有行为：主报告恰好九节四十七行，closeout receipt 恰好七行，archive index 恰好五节二十二行。该测试对标题、顺序和每一行文本做完整等值断言，而不是只检查若干关键词。生产实现替换后不修改测试，再次执行同一组断言并保持 3/3 通过，因此结果不是“肉眼看起来相似”，而是由旧实现建立、由新实现满足的可执行 oracle。

## 当前机械普查

- `ops` Java 文件：`1293 -> 1283`。
- 全局 renderer：`67 -> 58`，总行数 `4211 -> 3973`，长文件名 `59 -> 47`。
- 本包生产文件：`36 -> 26`；renderer 从十二个长名类型与一个 support 收敛为三个短名类型，共 227 行。
- 生产源码：1415 个 Java 文件；长文件 stem 1218，长标识符出现 20696 次，唯一长标识符 2777 个。
- 测试源码：898 个 Java 文件；长文件 stem 786，长标识符出现 10116 次，唯一长标识符 3822 个。
- 超过 500 行的生产文件仍为 32 个，最大 738 行；这一版本没有把渲染问题转移成新的热点文件。
- 完整 `mvnw -B verify` 运行 14:02，1953 个测试零失败、零错误、零跳过；JaCoCo 分析 2162 个类且全部 floor 达标，SpotBugs 为 0 bugs / 0 errors，可执行 jar 已生成。
- 实现提交 `b5366eb1` 的 canonical Actions run `29759922474` 通过：Docker-tagged job 1:51，headless job 19:15，并完成生产 profile smoke 与 JaCoCo report 上传。

## 非目标与安全边界

本版本不改变任何 endpoint、HTTP 状态、response record、Catalog 内容或上游 Java/mini-kv 证据语义；不引入写路由、自动执行、部署或回滚能力；不修改 fixture 字节和既有测试期望来迁就新实现。测试工厂改为 `PackageTestData`、`ReceiptTestData`、`ArchiveIndexTestData` 只是缩短测试代码的导航路径，不改变构造图。

## 失败条件

出现以下任一情况即视为版本失败并回退实现：三组精确 Markdown oracle 任一行变化；公开 service/controller 编译或测试失败；包内重新出现第四个 renderer 或任一长名 renderer；全局 renderer、名称或 ops 文件 ratchet 放宽；Spotless、SpotBugs、JaCoCo 或完整 Maven verify 不通过；通过修改 fixture 或降低断言来制造绿色结果。
