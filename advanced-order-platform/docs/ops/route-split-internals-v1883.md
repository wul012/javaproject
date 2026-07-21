# Route Split 内部模型收束（v1883）

## Family design

- Abstraction: Route Split Registry（路由拆分注册表）。
- Data boundary: 五个公共 RoutePaths、Service 与 Response 兼容类型保持原名、原包和原值。
- Behavior boundary: Catalog 只构造不可变数据，Assembler 只校验并组装响应。
- Output boundary: ReportRenderer 与 CloseoutRenderer 分别拥有两份稳定 Markdown 契约。
- Dependency direction: Service -> Catalog/Assembler/Renderer，包外不得依赖内部类型。
- Failure semantics: 数量、兼容性或只读边界任一失配，响应状态必须为 blocked。
- Compatibility: 冻结主报告 6 节 43 行、closeout 3 节 15 行以及现有 checks。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
|---|---|---|---|
| 公共 API 不变 | 保留五个公共兼容类型及全部路由值 | 控制器测试、路由兼容测试 | focused 已通过 |
| 输出逐行不变 | 两个短渲染器替代八个长渲染器 | 两份精确 Markdown oracle | 旧实现与新实现各通过 19 项 |
| 内部命名清晰 | 目录、装配器和渲染器使用领域短名 | RouteSplitStructureTests | full verify 已通过 |
| 重复结构消失 | 六段主报告在 ReportRenderer 内集中编排 | 文件清单与 renderer census | 已实现 |
| 发布比较稳定 | Spotless 固定比较上一正式 tag 的 peeled commit | verify-release.ps1 与 closeout gate | 本地入口已通过 |
| 全量行为稳定 | focused、expanded、verify、远端双 CI | Maven 与 GitHub Actions 证据 | 本地完成，远端待执行 |

## Implementation Evidence

- Route Split 生产文件：24 -> 17；其中五个公共兼容边界保持原名。
- ops 生产 Java 文件：1258 -> 1251；全项目生产 Java 文件：1390 -> 1383。
- renderer：38 -> 32；renderer 总行数：3521 -> 3448。
- 超过 40 字符的 renderer 文件名：22 -> 14；本族新增内部文件名全部不超过 40 字符。
- 生产长文件名：1188 -> 1169；生产长标识符出现次数：20495 -> 20376。
- 测试长文件名：776 -> 764；测试长标识符出现次数：10039 -> 9999。
- 精确名称 baseline 删除 66 项、新增 0 项；ratchet 未放宽。
- 新 oracle 在旧实现通过 19 项，再在替换后的实现通过同一组 19 项。

## Validation Evidence

- 第一次扩展门执行 119 项，唯一失败是结构清单中两个合法文件的字典序写反；修正清单顺序后整组 119/119 通过，没有修改集合、行为或 fixture。
- `verify-release.ps1` 首次正确拦截一处未格式化的换行；第二次暴露 Windows PowerShell 会把退出码为 0 的 Mockito stderr 警告包装成 terminating error。
- 发布脚本最终让 `cmd` 在 native 边界合并 stderr，再显式读取 `$LASTEXITCODE`；最小负向探针、固定 v1882 SHA 的 Spotless probe 与仓库测试共同约束该语义。
- 最终 `scripts/verify-release.ps1` 解析 tag `v1882-order-platform-sustainment-renderer`，固定基准 commit `5ebe1c06bb6ac279091046a8a982de6dfe382c93`。
- 第一次最终 verify 如实发现设计短注使用了错误的标题大小写和中文标签；没有修改门，改为协议要求的七行 `Family design` 后，定向门 3/3 通过。
- 完整 Maven verify 随后在 8:29 内通过 1,968 项测试，零失败、零错误、零跳过；JaCoCo 分析 2,130 个类且全部 floor 达标，SpotBugs 为 0 bugs / 0 errors，jar 成功生成。

## Failure Conditions

- 任一公共类型、路由常量、响应字段、事务边界或输出行发生变化，版本不得提交。
- 任一旧长内部实现文件仍存在，或出现新的包外内部依赖，结构门必须失败。
- 为通过重构而修改历史 fixture、放宽 ratchet、改低覆盖率门槛，视为版本失败。
