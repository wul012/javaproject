# v1873 声明式 Renderer 引擎

## Family design

- Abstraction: `MarkdownSections` 负责“计数行 + 条目映射 + 不可变 section”算法。
- Data boundary: 家族 `ReportRenderer` 只描述标题、计数名和条目到文本的映射。
- Behavior boundary: Registry service 仍按原顺序提供相同六组数据，响应 record 不变。
- Test boundary: 现有 aggregate/source/catalog 测试冻结输出，新 census 冻结结构收缩。
- Migration boundary: 旧七个 Renderer 与一个 Support 删除，不保留转发壳。
- Reuse boundary: 下一批 consumer/dossier/CI 家族必须复用同一 engine，禁止复制。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 六个 Markdown section 内容与顺序不变 | `ReportRenderer` | 旧实现先通过的逐行 oracle + 五个既有行为入口 | 聚焦验证通过 |
| 建立可复用声明式算法 | `MarkdownSections` | `MarkdownSectionsTests` 两个场景 | 聚焦验证通过 |
| 删除一次性 Renderer 文件 | archive-digest package | `OpsEleganceCensusTests` | 17 -> 10，旧八文件为零 |
| 历史抽取门只收紧不失效 | v1845 readability test | 当前文件集合 + 旧文件缺失断言 | 六个断言通过 |
| 全局 Renderer 债务下降 | census 脚本与 Java gate | 121 -> 115；5355 -> 5236 行；长名 119 -> 112 | 已机械收紧 |
| 公共路由、响应、权限不变 | service/controller/response | controller 与家族聚焦测试 + 全量 verify | 全量通过 |

## 当前实测

- 生产 Java `1484 -> 1478`，`ops 1352 -> 1346`，家族生产文件 `17 -> 10`。
- 生产长 stem `1297 -> 1289`，长标识符出现 `21167 -> 21124`。
- 测试长 stem `795 -> 794`，长标识符出现 `10225 -> 10216`。
- 聚焦家族与结构测试 `21/21`；变更、优雅与 census 门 `10/10`。
- 首次全量运行执行 `1938` 个测试，仅 v1866 的历史总量精确 pin 因合法删债
  `1352 -> 1346` 失败；该 pin 已收紧为 `<=1346`，18 个修复门通过。
- 讲解 `4292` 个汉字、恰好十个标准章节；归档精确值
  `1684 files / 19908542 raw bytes`。修复历史 pin 后的完整 `verify` 先以 `1938`
  个测试在 `13:52` 通过；随后将长名 aggregate 收紧到本版精确值，最终完整复验通过
  `1939` 个测试、零失败/错误/跳过，JaCoCo `2225` classes / 全部 floor，SpotBugs
  `0/0`，并成功产出 jar。实现提交 `ccd1ca8a` 的 canonical Actions run
  `29723306983` 全绿：Docker `2:09`，headless `18:35`，包含 prod profile smoke
  与 JaCoCo 上传。

## 明确不做

- 本版不改 Catalog 数据、不改 response record、不改 Controller、RoutePaths 或 JSON 字段。
- 不移动任何历史归档，不修改 Node、mini-kv、aiproj，也不打开执行、credential 或写边界。
- 不为减少文件数制造超过 300 行的组合器；若声明式引擎不能降低阅读成本，本版回退。

## 失败条件

- 任一现有 Markdown 行、标题、顺序或不可变语义变化，回退。
- 通过删除行为断言、修改 fixture 或放宽 census 上限完成迁移，回退。
- 新文件名或新标识符超过 40 字符，回退。
- 最终 verify 早于中文讲解完成，重新执行 verify。
- canonical CI、tag 或 push 未闭环，不开始下一版写入。
