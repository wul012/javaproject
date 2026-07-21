# Renderer 长名债务收官（v1886）

## Family design

- Abstraction: 四份 section 报告复用 `MarkdownSections.mapped/counted`；Handoff 保留一对一映射。
- Data boundary: 五个 Response 的嵌套 record 只在各自包内短 renderer 中出现。
- Behavior boundary: 共享引擎拥有不可变 lines 与 section factory；领域 owner 拥有标题和行格式。
- Output boundary: 五份旧输出先冻结 heading、逐节行数与全量 UTF-8 SHA-256，再替换实现。
- Dependency direction: Service -> output-owned short renderer -> optional domain-neutral engine。
- Compatibility: 公共 route、Response、Controller、Catalog、只读事务和 fixture 字节不变。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
|---|---|---|---|
| 五份输出字节兼容 | 旧实现建立完整 oracle | 五组 heading/line/digest 测试 | 新旧实现 5/5 |
| 长 renderer 文件名归零 | 五个 owner 改为短职责名 | 全局 renderer census | 5 -> 0 |
| 不复制 section 算法 | 四个报告复用既有引擎 | `RendererCloseoutStructureTests` | 6/6 |
| Handoff 不被过度抽象 | 只缩短 owner，不引入错误公共模型 | exact handoff oracle | 4/4 |
| 历史提取边界保留 | v1801/v1802/v1803/v1829 门升级 | 当前 owner 存在且旧名禁止 | 17/17 |

## Planned Owners

- Credential Resolver archive -> `ArchiveRenderer`。
- Sandbox blocked dossier -> `DossierRenderer`。
- Sandbox precheck manifest -> `ManifestRenderer`。
- Screenshot explanation archive -> `ReportRenderer`。
- Signed approval profile handoff -> `HandoffRenderer`。

## Frozen Reports

- 五份旧输出合计 33 个块 / 202 行，均冻结 heading、逐块行数和全量 UTF-8 SHA-256。
- Archive：`1/4/4/1/10/8`，摘要 `f9f498cb1e6cb70f21eabe5b6d5b9c2459df84193c57d48df328fece62ac6165`。
- Dossier：`1/3/6/5/12/4/5/10/4`，摘要 `6343820c1f3bda7b2574e17515fa949713cd8ebfe819797e727ca320fce57aff`。
- Manifest：`1/12/5/7/17/6/10/4`，摘要 `bfe109f24df2475a13c61621fd81a4732b68241cf27edfe6563299c7902976fd`。
- Screenshot：`4/6/7/9/6`，摘要 `205b7c2d1d84604b31f35a1ec6d3993c9e702a99ed122dbc58edf287f16a58f8`。
- Handoff：`6/6/6/6/6`，摘要 `2cfaf4917eaecff8e5d09dc9f787c785d3067f56f2fa16baa3699f9ccc508d9a`。

## Measured Result

- Renderer 保持 30 个，总行数 `3,289 -> 3,246`，长文件名 `5 -> 0`。
- 五个 owner 分别为 73、92、89、109、34 行；没有新增共享引擎或公共领域类型。
- 生产长 stem/使用/唯一值从 `1159/20277/2718` 收紧到 `1154/20240/2713`。
- 测试长 stem/使用/唯一值从 `754/9970/3773` 收紧到 `746/9916/3763`。
- exact name baseline 新增 0、删除 28；聚焦输出、历史、结构、优雅门共 53/53。
- 完整发布门通过 1,990 项测试，JaCoCo 分析 2,131 个类且全部门槛满足，SpotBugs 为 0/0。

## Failure Conditions

- 任一 heading、正文行、顺序、计数行、不可变性或全文摘要变化，版本失败。
- 修改 fixture、公共 Response、route 或旧输出期望来迁就实现，版本失败。
- 新建第二套 Markdown engine、合并领域模型或让长 renderer 文件名不为零，版本失败。
