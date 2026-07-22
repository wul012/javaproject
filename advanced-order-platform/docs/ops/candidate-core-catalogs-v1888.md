# CandidateDocument 核心 Catalog 收敛（v1888）

## Family design

- Abstraction: 提交预检、接收包、档案分区各由一个包内短 Catalog 拥有完整证据集合。
- Data boundary: 固定条目、来源投影、字段锁与 gate 留在对应 Catalog，不跨响应泛化。
- Behavior boundary: 每个 `from(source...)` 一次返回类型化、不可变的 `Evidence`。
- Dependency direction：Service -> local Catalog -> upstream read-only Response。
- Rendering boundary：`ProfileRenderer` 继续单独拥有 Markdown 输出行为，不进入数据 Catalog。
- Size boundary：三个新 owner 各自不超过 300 行，不创建字符串键万能映射器。
- Compatibility：公共 route、Response、Controller、Support、列表顺序和只读事务不变。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 三份完整响应不变 | 旧实现先冻结规范 JSON、集合尺寸与 SHA-256 | `CoreCatalogResponseOracleTests` | 旧实现 3/3 通过 |
| 十四个单职责 Catalog 收敛 | `SubmissionCatalog`、`IntakeCatalog`、`ProfileCatalog` | Catalog census + structure gate | `14 -> 3`，结构门通过 |
| Service 只组装一次 | 三个类型化 `Evidence` | source structure assertion | 三个 service 各一次 |
| 所有输出列表归属明确 | compact constructor 防御性复制 | exact copy-count assertion | `4/6/6` 次复制 |
| 不制造巨型文件 | 三个新 owner 均不超过 300 行 | line-count structure gate | `131/190/197` 行 |
| 只读与渲染边界不变 | 原 Support、Response、事务和 `ProfileRenderer` 保留 | service/controller/downstream tests | 聚焦门通过 |

## Scope Reconciliation

只读规划曾把 Profile 家族基类重复计算为两个 owner，因此写成 `15 -> 3`。逐文件 census
确认实际范围为 Submission 3 个、Intake 5 个、Profile 6 个，共 **14 个旧 Catalog -> 3 个
短 Catalog**；仓库事实优先，本版本以后只使用 `14 -> 3`。

## Planned Owners

- Submission precheck -> `SubmissionCatalog`。
- Intake packet -> `IntakeCatalog`。
- Profile section registry -> `ProfileCatalog`；渲染仍由 `ProfileRenderer` 负责。

## Frozen Responses

- Submission precheck：`25/25/8/40/19`，SHA-256
  `920742a06cdbe7f0502abeb4c4b38d2f772088677aabdc5a2eb594f2bc0ce0fa`。
- Intake packet：`5/5/10/10/8/35/23`，SHA-256
  `cb0b888fcc190b1272834cabf7c1bb414471d486da55212cc562cdd6af4c4e95`。
- Profile section registry：`5/5/5/25/5/5/43/21`，SHA-256
  `d3cbe7af21f604737121aa8a5e4d9e05f5dd9ed3e1c7013ec2757b8d60dbc660`。

## Measured Outcome

- `ops` Java `1237 -> 1226`，Catalog `320 -> 309`，生产 Java `1369 -> 1358`。
- 生产长 stem/使用/唯一值 `1140/20178/2699 -> 1126/20107/2685`。
- 测试 Java `909 -> 905`，长 stem/使用/唯一值 `737/9898/3741 -> 725/9866/3719`。
- exact name baseline 删除 58 项、新增 0 项；最大生产文件仍为 738 行，500 行以上仍为 32 个。
- 冻结响应、领域行为、结构、优雅和 staged-change 选择合计 51/51 通过。
- 中文讲解 3,742 Han/10 headings；授权归档 1,699 files / 20,179,335 raw bytes。
- 最终 `scripts/verify-release.ps1` 固定 v1887 commit `de64a97a`，通过 2,005 个测试，
  Maven 耗时 13:18；JaCoCo 分析 2,113 类且全部阈值满足，SpotBugs 0/0，jar 为
  68,010,007 字节。Implementation commit `abb82a98` 的 Actions run `29879782402`
  全绿：Docker 1:42，headless 19:31，包含 prod smoke 与 JaCoCo 上传。closeout CI 与
  annotated tag 待后置执行。

## Failure Conditions

- 任一 JSON 字段值、列表顺序、集合尺寸、gate、check 或摘要变化，版本失败。
- 修改 Response、fixture、公共 route 或旧输出期望来迁就收敛，版本失败。
- 任一新 owner 超过 300 行、旧十四个 Catalog 仍存在或新增第四个同形工具，版本失败。
- `ProfileRenderer` 被并入数据 Catalog、只读事务改变或 Support 状态规则迁移，版本失败。
