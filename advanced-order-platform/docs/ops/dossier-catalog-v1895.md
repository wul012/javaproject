# Operator CI Verification Dossier Catalog 收敛（v1895）

## Family design

- Abstraction: `DossierCatalog` 表示 consumer package 到 verification dossier evidence 的唯一纯投影边界。
- Data boundary: 十组有序列表由 `DossierCatalog.Evidence` 一次持有，并以 `List.copyOf` 建立不可变快照。
- Behavior boundary: Catalog 生成 source、provenance、digest、route、CI、gate、audit、checklist、receipt 与 scorecard，不负责 HTTP、事务、Markdown、status 或 checks。
- Call boundary: service 每次只调用一次 `evidence(source)`，不再逐项协调十个 Catalog。
- Rendering boundary: `ReportRenderer` 只读取完整 evidence 生成十段 Markdown，不拥有或修改证据。
- Decision boundary: `DossierSupport` 只读取 typed evidence，计算计数、status 与三十四条 checks。
- Compatibility boundary: 公共 Response、Controller、route、profile、字段与列表顺序、只读事务保持不变。
- Size boundary: 新 Catalog 必须低于 300 行，十个退休 Catalog 与旧长名 Support 不得复活。

## Shared count design

- Abstraction: `EvidenceCounts` 只表达“按谓词统计证据项”，不认识任何具体 evidence family。
- Ownership: 跨 family 的纯证据算法归 `evidencecore`；Catalog 与 Support 只传入数据和业务谓词。
- Compatibility: 返回精确 `int`，输入列表只读，替换旧 helper 时不得改变计数、状态或响应字节。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 dossier response 不得漂移 | `DossierResponseOracleTests` | 十二段数量向量 + canonical JSON SHA-256 | released v1894 基准与新实现一致 |
| 十个同生命周期 Catalog 收敛 | `DossierCatalog` | 精确当前/退休文件清单 | `10 -> 1` |
| service 只装配一次 | dossier registry service | `DossierCatalog.evidence(...)` occurrence | 恰好 1 次 |
| 十组列表由单一 owner 持有 | `DossierCatalog.Evidence` | 10 次 `List.copyOf` | 已覆盖 |
| decision 与 rendering 只读 typed evidence | `DossierSupport` / `ReportRenderer` | 结构源码断言 | 已覆盖 |
| 重复 predicate count 有唯一 owner | `EvidenceCounts` | 六个调用者 + 禁止本地 `Predicate` helper | 已覆盖 |
| 测试按职责收短 | dossier test package | 精确六文件清单 + 名称预算 | `8 -> 6` |
| 当前家族与全局债务只减不增 | census/name ratchets | 可复现脚本 + exact baseline | 46 项删除，新增 0 项 |

## Scope

v1895 只重构 `ops.maintenance.operatorcidossier` 的内部证据投影、汇总、渲染入参和测试
所有权，并把四个相邻 evidence family 已重复出现的 predicate-count 算法提取到
`ops.maintenance.evidencecore.EvidenceCounts`。公开 Controller、Response record、route、profile、
version、JSON 字段、列表顺序、Markdown 文本、三十四条 checks、最终状态算法和
`@Transactional(readOnly = true)` 均不改变。

上游 consumer-package service 与下游 release-acceptance service 继续通过既有公开类型连接。
本版没有修改 Node、mini-kv、历史 `a/` 到 `f/` archive、fixture 字节、数据库、消息系统、
凭证或任何运行时执行能力。

## Frozen Compatibility Oracle

删除旧生产实现前，新增的 `DossierResponseOracleTests` 已在 released v1894 上先运行。它冻结
十二段数量向量 `1/6/9/4/5/5/8/5/4/10/10/34`，并对排序属性后的完整 response JSON 计算
UTF-8 SHA-256：
`f9ee01616f66f941914558105fbf7fe2652deb82891058fde433a06dcaf92a92`。

生产重构后同一测试保持通过。因此 source snapshot、provenance、section digest、audience、
CI、gate、audit、checklist、receipt、scorecard、Markdown、checks、顶层安全位与 status 的值和
顺序都没有漂移。局部语义断言可以解释各组规则，完整 oracle 则防止局部断言漏掉公开字段。

## Architecture

旧 service 依次调用十个 Catalog，保存十个局部变量，再把同一组变量分别展开传给
scorecard、renderer 和 394 行长名 Support。这个形状让编排层承担了证据内部结构知识；任一
列表发生合法变化，多个签名和调用点都必须同步修改。

新流程只有四步：读取一次公开 consumer-package response，调用一次
`DossierCatalog.evidence(source)`，让 `ReportRenderer.render(evidence)` 生成十段 Markdown，
最后由 `DossierSupport.response(...)` 计算计数、checks、status 并构造原公开 response。
`DossierCatalog` 格式化后 299 行，负责十组同生命周期纯投影；`DossierSupport` 226 行，只负责
决策；service 收紧到 40 行；renderer 收紧到 196 行。HTTP、事务、数据投影、展示和最终判定仍
分别有明确 owner，没有把所有行为堆进一个巨型类。

`DossierCatalog.Evidence` 是包内不可变 ownership boundary。十个组件在 record 紧凑构造器中
逐一 `List.copyOf`；Catalog 不引用 Support，避免数据生产者反向依赖状态判定者。scorecard 只
统计刚生成的 typed lists，不从上游再推导一套影子事实。最终 status 同时核对上游安全位、
Catalog shape constant、实际通过数和 Markdown 数量，缺一项即 blocked。

## Shared Count Engine

调查相邻已收敛 family 后发现，`ArchiveCatalog`、`DigestCatalog`、`PackageCatalog` 与
`PackageSupport` 都各自维护了一个 `stream().filter(predicate).count()` helper。若 Dossier 再
复制一份，就违反第三次规则。v1895 因而新增 12 行 `EvidenceCounts`，公开唯一泛型操作
`matching(List<T>, Predicate<? super T>)`，并用 `Math.toIntExact` 明确返回值转换。

六个当前调用者只共享算法，不共享领域谓词：调用点仍显式写出 `passed`、`ready`、
`readOnly` 或 `locked`。工具不认识任何 response、状态字符串或 expected count，也不拥有 I/O；
因此抽象没有吞掉业务语义。`EvidenceCountsTests` 锁住筛选结果和输入不变性，结构门同时禁止
六个调用者重新导入 `Predicate` 建立本地副本。

## Test Ownership

- `DossierRegistryServiceTests` 验证 route、profile、来源、最终状态和全部只读安全位。
- `DossierCatalogTests` 验证 digest、audience、CI、gate、audit、checklist 与 receipt 的顺序和语义。
- `DossierChecksTests` 验证十二段数量向量、三十四条 checks、关键禁止边界与集合不可变性。
- `DossierMarkdownTests` 保留十段 Markdown 的精确内容和顺序。
- `DossierResponseOracleTests` 冻结完整公开 response，而非只冻结局部字段。
- `DossierExtractionTests` 锁生产/测试 inventory、退休清单、299 行上限、十次 copy、一次装配、单向依赖、共享 count owner 与下游 imports。

测试包从八个文件收紧为六个短职责 owner，六个旧长名综合测试退出，但原断言被按真实责任
重新分配，没有通过删除行为证据换取文件数量。历史 v1847 提取文档与讲解仍由新结构门保护。
`ConsumerPackageExtractionTests` 也同步只检查新的 `DossierCatalog`、`DossierSupport` 与 service
三个真实上游消费者，避免历史门反向要求退休类复活。

## Mechanical Result

- 生产 Java `1324 -> 1316`，ops Java `1192 -> 1184`，Catalog `275 -> 266`，Dossier package
  `14 -> 5`；测试 Java `907 -> 906`，Dossier tests `8 -> 6`。
- `Readiness` 文件 `996 -> 985`；renderer 数量保持 30，总行数 `3219 -> 3209`。
- 生产名称指标 `1084/19785/2643 -> 1073/19646/2632`；测试名称指标
  `701/9807/3672 -> 694/9780/3655`。
- exact-name baseline 删除 46 项、新增 0 项；新 Catalog 299 行，Support 226 行，service 40 行。
- `ops-elegance-census.ps1` 与 `java-maintainability-census.ps1` 已复测上述当前值，所有 ratchet
  已立即收紧，未保留“以后再降”的宽松额度。

## Validation Status

旧实现上的 oracle 先通过，重构后的第一轮 related behavior/oracle selection 也通过；测试重组后，
archive、digest、consumer-package、dossier、共享 count、结构与中央 elegance 的扩展选择再次
通过，零失败、错误或跳过。Spotless 已执行，当前格式化后的 `DossierCatalog` 为 299 行。

第一次 `scripts/verify-release.ps1` 在 2,023 项测试的唯一失败处发现历史 v1866 evidencecore
inventory 仍精确要求两个文件。修复没有删除或放宽该门，而是把 `EvidenceCounts` 加入三文件
inventory，并额外要求它 public、final、少于 20 行；针对性门随后通过。

第二次完整 release gate 固定 predecessor tag
`v1894-order-platform-consumer-package-catalog` 的 peeled commit
`105271fb2a0b7e714e61f2e2a71d6ce5ab3376fe`。Spotless 检查 22 个改动 Java，0 个需要修复；
Maven verify 在 15:15 内通过 2,023/2,023，零失败、错误或跳过；JaCoCo 分析 2,079 类并满足
全部 floor；SpotBugs 0/0；可执行 jar 为 67,966,474 字节。

Implementation commit `f9cc62d99fe4637e8d1dfcf9161c63ed4b0143f1` 通过 canonical
Actions run `29983843663`：Docker-tagged job 2:03，其中 wrapper verify 1:52；headless
job 18:18，其中 wrapper verify 17:36、production-profile smoke 0:10、JaCoCo artifact
上传 0:03。Closeout CI、annotated tag 与本地/远端 peeled SHA 仍属于发布闭环的
后续证据；在这些步骤完成前，本节不把 v1895 自述为 canonical release。

## Failure Conditions

- 完整 response SHA、十二段数量向量、route/profile/version、三十四条 checks 或安全位漂移。
- 十个退休 Catalog、旧长名 Support、六个退休测试 owner 或旧 v1847 结构测试名中任一个复活。
- `DossierCatalog` 达到 300 行、`List.copyOf` 不等于 10、service assembly 不等于 1，或 Catalog 依赖 Support。
- 当前包生产文件不等于 5、测试文件不等于 6，或任一新增/修改名称超过 40 字符。
- 任一调用者复制 predicate-count helper，或 `EvidenceCounts` 开始依赖具体 family/response。
- ops/Catalog/Readiness/renderer/命名 ratchet 被放宽，或 exact baseline 出现新条目。
- 修改 oracle、fixture、历史 archive、只读事务或执行权限来迁就重构。
- 最终 verify、canonical CI、tag push 或本地/远端 peeled SHA 核验失败。
