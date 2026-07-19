# v1871 SpotBugs 豁免身份治理

## Family design

- Abstraction: `Waiver` 表达一条 SpotBugs pattern 与 class FQN 的完整身份。
- Data boundary: `config/spotbugs-exclude.xml` 是唯一当前集合，Git 上一版 XML 是历史集合。
- Behavior boundary: `SpotBugsWaiverTests` 只负责安全解析、集合比较与 class 可达性检查。
- Parser boundary: 禁止 DTD、外部实体、非 `Bug + Class` 结构、空属性和重复身份。
- Release boundary: 当前集合只能是上一版集合的子集，不能等量偷换或新增。

## 需求证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 豁免按完整身份只减不增 | Git 前一版 XML 与当前 XML 做集合包含比较 | `waiversOnlyShrinkByIdentity` | 正向与负向通过 |
| XML 必须按结构读取 | JAXP DOM 安全解析，不再按字符串计数 | `filterIsStrictAndUnique` | 聚焦通过 |
| 禁止 XML 外部实体入口 | 关闭 DTD、通用实体、参数实体与 XInclude | `rejectsDocumentTypes` | 聚焦通过 |
| 禁止陈旧 class 豁免 | 不初始化地加载每个 class FQN | `waiversReferenceClasses` | 682 条全通过 |
| 真实减少历史债务 | 删除四条已无对应 class 的条目 | XML 集合 686 -> 682 | 已收紧 |

## 已核实事实

- 当前 XML 有 686 个 `Match`，完整身份也是 686 个，尚无重复。
- pattern 分布为 EI_EXPOSE_REP2 340、EI_EXPOSE_REP 331、CT_CONSTRUCTOR_THROW 8、
  DM_CONVERT_CASE 5、VA_FORMAT_STRING_USES_NEWLINE 1、URF_UNREAD_FIELD 1。
- 编译产物检查发现四条 FQN 已不存在，均属于已经移除的旧讲解合规响应模型。
- 原门只断言 `<Match>` 数量不超过 686；删除一条再新增一条可以保持数量不变而逃逸。

## 失败条件

- 为通过测试而提高数量上限、跳过 Git 前一版比较，或建立可同步篡改的第二份手工基线。
- XML 出现新 pattern/class 组合、重复组合、非 `Bug + Class` 子节点或多余属性却仍通过。
- 任一豁免 class 不能由测试 classpath 加载却仍被保留。
- 修改 fixture 字节、降低 SpotBugs effort/threshold，或使用 `SuppressFBWarnings` 转移现有债务。
- 中文讲解、完整 `mvnw -B verify`、canonical CI、提交、tag 或推送任一未闭环。

## 验证计划

先运行新测试与 `JavaEleganceGateTests`，再临时做一次“删一条、换一条”的等量篡改，
确认身份门失败后立即恢复 XML。随后执行文档、归档与讲解门，最后运行完整 wrapper
verify。外部评审结论不在本文件中自授。

## 聚焦验证记录

正向门两次通过 11/11。负向试验把一个 `PagedResponse` 身份等量替换为真实存在的
`BusinessException`，总数不变但 Maven 按预期失败；XML 随即恢复。第一次失败诊断展开完整
集合后，将断言改为只展示新增差集，再次正向运行通过且 DTD 拒绝不再污染标准错误输出。

最终 `mvnw -B verify` 用时 12:40，1,929 项测试全部通过，失败、错误、跳过均为 0；
JaCoCo 插件分析 2,230 个类并通过全部覆盖率阈值，SpotBugs 为 0 个 BugInstance、0 个 Error，
可执行 jar 正常生成。此前 v1870 证据曾用正则统计 `jacoco.xml` 的 `<class>` 标签并误记为
2,319；本轮确认该标签数不是插件报告的 bundle class 口径，已把活文档统一纠正为 Maven
日志中的 2,230。已发布的 v1870 tag 保持不可变，纠偏由本版 deviation 明示承接。

## 远端验证记录

实现提交 `e3e1f019` 的 GitHub Actions 运行 `29692730030` 全部成功：Docker-tagged
integration tests 用时 1:59，Build and headless regression 用时 19:24；后者包含完整
wrapper verify、生产 profile 启动冒烟与 JaCoCo 上传。该运行只验证 canonical remote 上的
实现提交，不以本地工作区结果替代。外部评审状态仍保持未自授。
