# Code Walkthrough 报告渲染收束（v1885）

## Family design

- Abstraction: 复用 `MarkdownSections.counted/mapped`，不新增第二套 section engine。
- Data boundary: 四个领域 Response record 只在本包 `ReportRenderer` 内映射。
- Behavior boundary: 共享引擎拥有计数行、不可变 lines 与 section factory 调用；领域适配器拥有标题和行格式。
- Output boundary: Compliance、Depth、Quality Gate、Quality Audit 的完整 section/line 快照字节兼容。
- Dependency direction: Registry Service -> package-local ReportRenderer -> domain-neutral MarkdownSections。
- Compatibility: 先在 v1884 旧实现冻结四份完整报告，再允许替换生产 renderer。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
|---|---|---|---|
| 四份完整输出不变 | 改前冻结所有 heading 与 line | 四组 exact oracle | 旧实现 4/4、新实现 4/4 |
| 公共边界不变 | Response、route、controller 不改 | 原 service/controller 测试 | 通过 |
| 复用现有引擎 | counted/mapped 承担公共 section 机制 | `WalkthroughRenderingStructureTests` | 通过 |
| 领域政策不泄漏 | line mapper 与 record 只留在本包 | 源码结构门 | 通过 |
| 长名债务下降 | 四个长 renderer 与四个 TestSupport 改为短名 | census/name baseline | 通过；新增 0、删除 16 |

## Frozen Reports

- 四份旧报告合计 22 sections / 168 lines；每份冻结 heading、逐节行数和全量 UTF-8 SHA-256。
- Compliance 为 6 节 63 行，Depth 为 5 节 27 行，Quality Gate 为 5 节 39 行，Quality Audit 为 6 节 39 行。
- 新实现不修改 oracle：同一组四项摘要在 `ReportRenderer` 替换后再次通过。

## Measured Result

- renderer 保持 30 -> 30，总行数 3,372 -> 3,289，长 renderer 文件名 9 -> 5。
- 生产长 stem / 长标识符出现 / 唯一长标识符收紧到 1,159 / 20,277 / 2,718。
- 测试长 stem / 长标识符出现 / 唯一长标识符收紧到 754 / 9,970 / 3,773。
- 四个 `WalkthroughTestData` 替代长 TestSupport；测试文件增加两个机械门，但名称债务仍净下降。
- 最终 `verify-release.ps1` 通过 1,981 项测试、JaCoCo 2,131 个类全部门槛、SpotBugs 0/0 与 jar 打包。
- 实现提交 `311c5c91` 的 Actions run `29822027690` 通过 Docker 2:13 与 headless 20:12。

## Failure Conditions

- 任一旧报告 heading、计数行、正文行、顺序或不可变性变化，版本失败。
- 为迁就新实现而修改冻结 oracle、公共 Response、route 或 fixture，版本失败。
- 新建另一套 Markdown engine、跨包暴露领域 record 或放宽任何 ratchet，版本失败。
