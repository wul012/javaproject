# Profile Section 共享渲染引擎（v1884）

## Family design

- Abstraction: Profile Sections（配置节渲染管线）。
- Data boundary: 共享层只接收不可变 Section/Field view，不依赖三个公共 Response 类型。
- Behavior boundary: 引擎按 section code 一次索引字段，并保持 section 与字段输入顺序。
- Output boundary: 领域适配器把共享 Rendered view 转回各自原有响应 record。
- Dependency direction: 领域 record -> 包内适配器 -> rendering engine，公共 API 不反向依赖引擎。
- Compatibility: 冻结 19 个完整 section、全部 Markdown 行、group、顺序与 passed 状态。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
|---|---|---|---|
| 公共契约不变 | 不修改三组 Response record、route 与 controller；service 只改包内委派点 | 编译、原 controller/service 测试 | 通过 |
| 完整输出不变 | 改前冻结 5 + 5 + 9 个完整 RenderedSection | 三组 exact oracle | 旧实现 6/6、新实现 6/6 |
| 三次规则收束 | 一个共享引擎替代三份字段聚合算法 | `ProfileRenderingStructureTests` | 通过 |
| renderer 达标 | 五个长 renderer 收束为三个短适配器 | `ops-elegance-census.ps1` | 32 -> 30 |
| 长名只减不增 | 生产与测试 renderer 使用角色短名 | exact name baseline | 新增 0、删除 24 |
| Text Package 语义不变 | 仍只接受 submission/compared-evidence 并按 order 排序 | group/order oracle | 通过 |

## Legacy Oracle

- Candidate Document：5 个 section，每个完整冻结 order、code、heading、五行 body 与 status。
- Signed Approval Draft：5 个 section，每个完整冻结 order、code、heading、六行 body 与 status。
- Draft Text Package：9 个 section，每个完整冻结 order、code、rendererGroup、七行 body 与 status。
- 三组 oracle 已在 v1883 tag 指向的旧生产实现上通过 6/6，之后不允许通过改期望迁就实现。

## Engine Shape

- `ProfileSections.Section` 表达 order、code、可选 group 与 heading。
- `ProfileSections.Field` 表达 sectionCode、name 与 value。
- `ProfileSections.Rendered` 表达完成渲染但尚未进入领域响应的中立结果。
- 引擎用保持插入顺序的索引把字段归到 section，避免每个 section 重扫全部字段。
- Candidate 与 Draft 保持输入 section 顺序；Text Package 的适配器保留旧分组白名单和排序语义。

## Measured Result

- `ops` Java 文件从 1,251 降到 1,249；共享引擎新增一个文件，三个家族净删除三个旧文件。
- renderer 从 32 个降到计划目标 30 个，总行数从 3,448 降到 3,372，长 renderer 文件名从 14 降到 9。
- 生产长文件 stem / 长标识符出现 / 唯一长标识符收紧到 1,163 / 20,334 / 2,722。
- 测试长文件 stem / 长标识符出现 / 唯一长标识符收紧到 758 / 9,995 / 3,778。
- 共享索引把字段归组从每个 section 重扫字段集合，收敛为一次分组后逐 section 读取；外部输出仍由精确 oracle 冻结。

## Verification

- 扩展定向组通过 181/181；讲解、归档、closeout 与文档组通过 47/47。
- 第一轮完整门执行到 1,976 项时发现 v1825/v1826 历史测试仍要求已删除的旧 renderer；修复保留抽取目的，并新增旧长实现必须不存在的断言。
- 修复定向组通过 47/47；最终 `verify-release.ps1` 固定 v1883 peeled commit `4b4193b0`，通过 1,976 项测试，耗时 11:46。
- JaCoCo 分析 2,131 个类且全部 floor 达标，SpotBugs 为 0 bugs / 0 errors，可执行 jar 完成打包。

## Failure Conditions

- 任一旧 exact oracle 的字段、顺序、换行、group 或状态变化，版本失败。
- 任一公共响应 record、路由字符串、service/controller 边界变化，版本失败。
- 共享引擎知道 Candidate、Draft 或 Text Package 的领域类型，抽象方向失败。
- renderer 数量未降到 30，或长 renderer 名未按预期减少，版本失败。
- 为通过迁移而放宽 ratchet、修改 fixture 或删减断言，版本失败。
