# Changelog

本项目的版本化证据以 git tag 为权威来源。Maven artifact 当前保持
`0.1.0-SNAPSHOT`，因为仓库仍处于高频工程演进阶段，尚未切换到语义化制品发布。
每个可追溯版本必须有对应 git tag、提交、测试证据和必要的中文代码讲解。

## v1891 - Operator CI handoff Catalog convergence

- 在 v1890 已发布实现上冻结完整 handoff registry：集合向量 `1/4/5/8/5/5/15`，
  sorted-property UTF-8 SHA-256 为
  `4fc6dc6069cff5bc40ee0934bc1ed9133ff50bcfe7c3c5940429e83cf4287ab0`。
- 删除四个单列表 Catalog，把 service 内 scorecard 投影一并移入 181 行包内
  `HandoffCatalog`；其 `Evidence` 对五组列表建立不可变快照，service 只装配一次。
- renderer 与 Support 改为接收同一 typed evidence，继续分别拥有 Markdown 与
  status/checks。公开 route、Response、Controller、顺序和 read-only transaction 不变。
- 新增 Catalog 语义/所有权测试和完整响应 oracle；原 service 测试与 v1844 结构 owner
  收短为当前职责名，并用精确 15/10 文件清单、200 行、五次 copy、一次 assembly 守卫。
- 生产 Java `1345 -> 1342`，ops `1213 -> 1210`，Catalog `296 -> 293`，当前包
  `18 -> 15`，测试 `904 -> 906`。生产名称指标达到 `1107/20002/2666`，测试达到
  `714/9844/3695`；精确 baseline 删除 12 项、新增 0 项。
- 上游、当前家族、下游 archive/digest、结构、change 与 elegance 选择 77/77 通过；
  新增 3,692-Han、十章节中文讲解，授权归档为 1,702 files / 20,228,272 raw bytes。
- 第一次 docs 联合门仅发现讲解章节顺序不符合标准；不改测试、不删内容，移动章节并重建
  manifest 后同一选择 97/97 通过。最终 `scripts/verify-release.ps1` 固定 v1890 closeout
  `9069d54e`，通过 2,015/2,015，Maven 8:34，JaCoCo 2,100 类/全部阈值，SpotBugs
  0/0，jar 67,997,219 字节。Implementation `be7bd5c1` 通过 canonical Actions run
  `29892031685`：Docker 2:19、headless 19:26，其中 wrapper verify 18:38、prod smoke
  0:12、JaCoCo 上传 0:05。Closeout CI 与 annotated tag 仍是完成门。

## v1890 - Archive registry Catalog convergence

- 在 v1889 已发布实现上先冻结完整 archive registry：集合向量
  `1/6/5/20/10/4/5/7/6/20`，sorted-property UTF-8 SHA-256 为
  `d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859`。
- 删除八个单列表 archive Catalog，新增一个 183 行包内 `ArchiveCatalog`；其类型化
  `Evidence` 对八组列表执行不可变快照，service 每次请求只装配一次。
- renderer 与 Support 继续分别拥有 Markdown 和状态/checks，只把八参数扇出替换为一个
  evidence 聚合值。公开 route、Response、Controller、顺序和只读事务保持不变。
- 两份旧 Catalog 测试合并为 `ArchiveCatalogTests`，新增完整响应 oracle，并把三个被触及的
  archive 测试收短为当前职责名。不可变性测试覆盖全部八组列表。
- 生产 Java `1352 -> 1345`，ops `1220 -> 1213`，Catalog `303 -> 296`，execution 包
  `17 -> 10`，测试 Java 保持 904。生产名称指标达到 `1111/20032/2670`，测试达到
  `716/9846/3697`；精确 baseline 删除 29 项、新增 0 项。
- 新增 3,273-Han、十章节中文讲解；授权归档精确为 1,701 files / 20,209,891 raw bytes。
- 第一次完整门运行 2,009 个测试，仅精确设计说明协议失败；按标准标签修复且 11/11
  通过后，第二次 `scripts/verify-release.ps1` 固定 v1889 commit `99e1afd2` 并通过
  2,009/2,009，Maven 10:33，JaCoCo 2,102 类/全部阈值，SpotBugs 0/0，jar
  67,998,687 字节。Implementation commit `d79bd028` 通过 canonical Actions run
  `29888181626`：Docker 2:02、headless 19:23，其中 wrapper verify 18:43，prod smoke
  与 JaCoCo artifact 上传均成功。Closeout `9069d54e` 通过 run `29889326585`：Docker
  1:45、headless 19:49，其中 wrapper verify 19:00。Annotated tag
  `v1890-order-platform-archive-registry-catalog` 在本地与 `javaproject` 均 peel 到 closeout。

## v1889 - Execution registry Catalog convergence

- Freezes the complete minimal-read-only gate execution Registry on the v1888
  implementation before deletion: collection vector `5/5/20/10/4/6/5/6/20` and
  sorted-property UTF-8 SHA-256
  `8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36`.
- Replaces seven one-list Catalog owners with one package-local `RegistryCatalog`. Its
  typed `Evidence` owns all seven list snapshots; the Service calls `evidence()` once.
- Keeps public route, Response, Controller, Support status/check logic, `ExecutionRenderer`,
  list order, and read-only transaction unchanged. The same full oracle passes after replacement.
- Consolidates three long Catalog-shaped test shells into `RegistryCatalogTests`, proves
  snapshot ownership behavior, and renames the touched v1843 structure owner to
  `ExecutionExtractionTests` instead of retaining a long forwarding shell.
- Tightens production Java `1358 -> 1352`, ops `1226 -> 1220`, Catalogs `309 -> 303`,
  execution package `23 -> 17`, and tests `905 -> 904`. Production name metrics reach
  `1119/20072/2678`; tests reach `721/9856/3710`; exact baseline removals are 23 with
  no additions. The new owner is 284 lines; core gates pass 39/39 and the expanded
  execution/archive/controller/docs selection passes 70/70.
- Adds a 3,247-Han walkthrough with exactly ten standard headings. The authorized archive
  becomes exactly 1,700 files / 20,194,403 raw bytes.
- Final `scripts/verify-release.ps1` pins v1888 commit `15ad48bd` and passes all 2,007
  tests in 7:23. JaCoCo analyzes 2,108 classes with every floor met, SpotBugs reports
  0/0, and the 68,005,806-byte executable jar is packaged.
- Implementation commit `dc73b52c` passes Actions run `29883341547`: Docker-tagged
  integration tests in 2:04 and headless regression in 19:10, including production smoke
  and JaCoCo upload. Closeout `99e1afd2` passes run `29884385641`: Docker 2:19 and
  headless 19:14. Annotated tag `v1889-order-platform-execution-registry-catalog` peels
  to the closeout locally and on `javaproject`.

## v1888 - Candidate core Catalog convergence

- Freezes submission precheck, intake packet, and profile registry against the v1887
  implementation before deletion. Their complete collection vectors remain
  `25/25/8/40/19`, `5/5/10/10/8/35/23`, and `5/5/5/25/5/5/43/21`; sorted-property
  canonical JSON SHA-256 values remain `920742...e0fa`, `cb0b88...4e95`, and
  `d3cbe7...c660` after replacement.
- Replaces fourteen single-responsibility Catalog owners with three package-local bundles:
  `SubmissionCatalog`, `IntakeCatalog`, and `ProfileCatalog`. Each service performs one
  typed `from(...)` assembly; each `Evidence` owns immutable list snapshots.
- Keeps public routes, Response records, controllers, source services, Support status/check
  rules, `ProfileRenderer`, and read-only transaction boundaries unchanged.
- Consolidates seven Profile Catalog-shaped test shells into one semantic owner and shortens
  five touched test responsibilities without dropping their assertions.
- Adds a five-test structure gate requiring all fourteen retired production files to stay
  absent, one assembly call per service, exact `4/6/6` defensive copies, a 300-line ceiling,
  and Profile rendering outside the data Catalog. Actual owner sizes are 131/190/197 lines.
- Tightens ops Java `1237 -> 1226`, Catalogs `320 -> 309`, and test Java `909 -> 905`.
  Production name metrics reach `1126/20107/2685`; tests reach `725/9866/3719`; the exact
  baseline has 58 removals and no additions. Focused behavior/oracle/structure/elegance/
  change gates pass 51/51.
- Adds a 3,742-Han walkthrough with exactly ten standard headings in the new
  `v1888-v1892` segment. The authorized archive becomes exactly 1,699 files /
  20,179,335 raw bytes.
- Final `scripts/verify-release.ps1` pins v1887 commit `de64a97a` and passes all
  2,005 tests in 13:18. JaCoCo analyzes 2,113 classes with every floor met, SpotBugs
  reports 0/0, and the 68,010,007-byte executable jar is packaged.
- Implementation commit `abb82a98` passes Actions run `29879782402`: Docker-tagged
  integration tests in 1:42 and headless regression in 19:31, including the production
  profile smoke and JaCoCo upload. Closeout `15ad48bd` passes run `29880876879`: Docker
  2:09 and headless 18:37. Annotated tag
  `v1888-order-platform-candidate-core-catalogs` peels to that closeout locally and on
  `javaproject`.

## v1887 - Candidate handoff Catalog convergence

- Freezes both complete handoff responses on the v1886 implementation with sorted-property
  canonical JSON. Request handoff keeps `6/5/15/15/8/10/25/20` and SHA-256
  `3c988b...13c2`; material-precheck handoff keeps `6/5/10/10/8/10/42/26` and
  SHA-256 `914738...1cf5`. The same two assertions pass unchanged after replacement.
- Replaces fourteen single-list Catalog owners with two package-local bundles:
  `HandoffCatalog` and `PrecheckHandoffCatalog`. Each service now performs one
  `from(source)` assembly and passes one typed immutable `Evidence` to the unchanged
  Support response builder.
- Keeps public routes, Response records, controllers, source fixtures, status/check rules,
  and read-only transaction boundaries byte-for-byte or behaviorally unchanged.
- Renames nine touched test owners to short role names while preserving every semantic
  assertion; no test expectation or frozen digest is relaxed.
- Adds a five-test structure gate requiring exactly two handoff Catalog owners, absence of
  all fourteen retired files, one assembly call per service, seven defensive list copies,
  and a 300-line ceiling per owner. Actual sizes are 235 and 182 lines.
- Deletes the retired Artifact Catalog's locale-sensitive SpotBugs waiver and uses
  `Locale.ROOT` for stable ASCII slug normalization; the exact waiver set shrinks
  `676 -> 675` without changing either frozen response.
- Tightens ops Java `1249 -> 1237` and Catalogs `332 -> 320`. Production name metrics
  reach `1140 / 20178 / 2699`; tests reach `737 / 9898 / 3741`; the exact baseline has
  46 removals and no additions. The focused behavior/oracle/structure/elegance/change
  selection passes 56/56.
- Adds a 3,060-Han walkthrough with exactly ten standard headings; the authorized archive
  becomes exactly 1,698 files / 20,160,868 raw bytes.
- Final `scripts/verify-release.ps1` pins v1886 commit `b5c8df42` and passes all
  1,998 tests in 15:25. JaCoCo analyzes 2,121 classes with every floor met, SpotBugs
  reports 0/0, and the 68,017,026-byte executable jar is packaged.
- Implementation commit `a1bae7a4` passes Actions run `29833966170`: Docker-tagged
  integration tests in 1:58 and headless regression in 18:57, including the production
  profile smoke and JaCoCo upload. Closeout commit `de64a97a` passes run `29835681926`:
  Docker in 2:06 and headless in 19:05. Annotated tag
  `v1887-order-platform-candidate-handoff-catalogs` peels to that commit locally and on
  `javaproject`.

## v1886 - Renderer long-name debt closeout

- Freezes five complete reports against the v1885 implementation: 33 output blocks,
  202 body lines, every heading, every per-block line count, and five canonical full
  UTF-8 SHA-256 values. The unchanged oracle set passes after replacement.
- Replaces the final five long owners with `ArchiveRenderer`, `DossierRenderer`,
  `ManifestRenderer`, `ReportRenderer`, and `HandoffRenderer` while retaining public
  routes, Response records, controllers, catalogs, fixtures, and read-only transactions.
- Reuses `MarkdownSections.mapped/counted` for four section reports. Handoff keeps its
  smaller direct mapping because forcing it through a section abstraction would erase
  rather than clarify its one-to-one domain model.
- Keeps renderer count at 30, tightens renderer lines `3289 -> 3246`, and reaches the
  renderer naming target `5 -> 0` long filenames.
- Tightens production name metrics to `1154 / 20240 / 2713` and test metrics to
  `746 / 9916 / 3763`; the exact baseline records 28 removals and no additions.
- Renames five test-data owners, merges duplicate Handoff markdown responsibility, and
  upgrades v1801/v1802/v1803/v1829 history gates to require current owners and reject
  retired names in both narrow packages and the root.
- Formatted exact-output, behavior, history, structure, elegance, and baseline gates pass
  53/53. Adds a 5,079-Han walkthrough with exactly ten standard headings; the authorized
  archive becomes exactly 1,697 files / 20,146,559 raw bytes.
- Final `scripts/verify-release.ps1` pins v1885 commit `acab0cdc` and passes all
  1,990 tests in 9:07. JaCoCo analyzes 2,131 classes with every floor met, SpotBugs
  reports 0/0, and the 68,026,314-byte executable jar is packaged.
- Implementation commit `cfbafc52` passes Actions run `29827360947`: Docker-tagged
  integration tests in 2:14 and headless regression in 19:11, including the production
  profile smoke and JaCoCo upload. Closeout `b5c8df42` passes run `29828862484`: Docker
  2:18 and headless 19:43. Annotated tag `v1886-order-platform-renderer-debt-closeout`
  peels to that closeout locally and on `javaproject`.

## v1885 - Code Walkthrough report renderer convergence

- Freezes all four existing reports before replacement: Compliance has six sections /
  63 content lines, Depth five / 27, Quality Gate five / 39, and Quality Audit six /
  39. The unchanged four-oracle set passes against both implementations.
- Reuses the established `MarkdownSections.counted/mapped` engine. Four package-local
  `ReportRenderer` adapters retain domain headings and line formats; no route, Response,
  controller, service transaction, or Catalog record changes.
- Pins every heading, per-section line count, and a canonical full-report UTF-8 SHA-256.
  Any character, order, count-prefix, or line-boundary drift now blocks the version.
- The four target renderers shrink `541 -> 458` lines. Global renderer count stays 30,
  renderer lines tighten `3372 -> 3289`, and long renderer filenames fall `9 -> 5`.
- Renames four pure test factories to `WalkthroughTestData` while retaining historical
  test identities that are themselves emitted as compliance evidence.
- Tightens production name metrics to `1159 / 20277 / 2718` and test metrics to
  `754 / 9970 / 3773`; the exact baseline records 16 removals and no additions.
- Focused behavior, exact-output, structure, elegance, and staged-change gates pass
  66/66 after final formatting.
- The first full run executes all 1,981 tests and exposes four v1797-v1800 extraction
  checks that still required retired renderer filenames. Their package-boundary purpose
  is preserved and tightened: current `ReportRenderer.java` is required in each narrow
  package and every retired long filename is forbidden there and at root. Repair-focused
  history, oracle, structure, and elegance gates pass 41/41.
- Adds a 4,102-Han walkthrough with exactly ten standard headings. The authorized
  archive set becomes exactly 1,696 files / 20,125,898 raw bytes.
- Final `scripts/verify-release.ps1` pins v1884 commit `9d3ff03d` and passes all
  1,981 tests in 9:11. JaCoCo analyzes 2,131 classes with every floor met,
  SpotBugs reports 0/0, and the 68,027,947-byte executable jar is packaged.
- Implementation commit `311c5c91` passes Actions run `29822027690`: Docker-tagged
  integration tests in 2:13 and headless regression in 20:12, including the production
  profile smoke and JaCoCo upload. Closeout `acab0cdc` passes run `29823485427`: Docker
  2:14 and headless 19:08. Annotated tag
  `v1885-order-platform-walkthrough-report-renderers` is canonical.

## v1884 - Profile Section shared rendering engine

- Freezes nineteen complete rendered-section records against the v1883 implementation:
  five Candidate Document sections, five Signed Approval Draft sections, and nine Draft
  Text Package sections. The same six exact-output tests pass unchanged after replacement.
- Adds the domain-neutral immutable `ProfileSections` engine, which indexes fields once
  by section code and preserves section/field order without depending on public response
  models or approval-domain policy.
- Keeps one package-private `ProfileRenderer` adapter in each family. Text Package retains
  its `submission` / `compared-evidence` allowlist and order sort locally; public response,
  route, controller, transaction, and status contracts remain unchanged.
- Deletes the two one-group Text Package renderers and their support shell. Ops Java shrinks
  `1251 -> 1249`; renderers reach `32 -> 30`, renderer lines `3448 -> 3372`, and long
  renderer filenames `14 -> 9`.
- Tightens production name metrics to `1163 / 20334 / 2722` and test metrics to
  `758 / 9995 / 3778`; the exact baseline records 24 removals and no additions.
- Adds three engine boundary tests and five source-structure checks. The expanded behavior,
  history, elegance, and staged-change selection passes 181/181.
- Adds a 3,551-Han walkthrough with exactly ten standard headings. The authorized archive
  set becomes exactly 1,695 files / 20,107,763 raw bytes.
- The first full run reaches all 1,976 tests and exposes two stale v1825/v1826 historical
  assertions that still required deleted renderer names. Their extraction intent is
  preserved and tightened: the short adapter must exist and the long renderer must not.
- Final `scripts/verify-release.ps1` pins v1883 commit `4b4193b0` and passes 1,976 tests
  in 11:46. JaCoCo analyzes 2,131 classes with every floor met, SpotBugs reports 0/0,
  and the executable jar is packaged.
- Implementation commit `512d4804` passes Actions run `29815077843`: Docker-tagged
  integration tests in 2:21 and headless regression in 20:30, including the production
  smoke and JaCoCo upload. Closeout `9d3ff03d` passes run `29816576937`: Docker in
  2:13 and headless in 19:34. Annotated tag
  `v1884-order-platform-profile-section-rendering-engine` peels to that commit locally
  and on `javaproject`.

## v1883 - Route Split internal model convergence

- Preserves all five public Route Split compatibility types, route values, response
  fields, transactions, profiles, and downstream service boundaries.
- Replaces nineteen long package-private implementation shells with twelve short
  domain owners and deletes seven forwarding-only section renderers; the family
  shrinks `24 -> 17` files.
- Freezes the old implementation before replacement and preserves it exactly:
  the registry report remains six sections / 43 lines and closeout remains three
  sections / 15 lines. The same focused set passes 19/19 before and after.
- Repairs three ineffective route assertions so the stable barrel is compared with
  the narrow owner and the closeout suffix is pinned independently of its endpoint.
- Shrinks ops Java `1258 -> 1251`, renderers `38 -> 32`, renderer lines
  `3521 -> 3448`, and long renderer filenames `22 -> 14`.
- Production long-name metrics improve to `1169 / 20376 / 2728`; test metrics improve
  to `764 / 9999 / 3783`. The exact baseline has 66 removals and no additions.
- Adds `scripts/verify-release.ps1`: release Spotless now compares with the peeled
  commit of the previous canonical tag instead of a moving remote branch. Native
  stderr is merged at the command boundary and Maven success is judged by exit code.
- The expanded behavior, history, structure, elegance, walkthrough, archive, and
  closeout selection passes 119/119 after correcting one file-list sort assumption.
- The walkthrough has 3,492 Han characters and exactly ten standard headings; the
  exact archive set is 1,694 files / 20,092,216 raw bytes.
- Final `scripts/verify-release.ps1` pins v1882 closeout commit `5ebe1c06` and passes
  1,968 tests in 8:29 with zero failures/errors/skips. JaCoCo analyzes 2,130 classes
  with every floor met, SpotBugs reports 0/0, and the executable jar is packaged.
- Implementation commit `b5cae273` passes Actions run `29807996922`: Docker-tagged
  tests in 2:02 and headless regression in 19:46, including prod smoke and JaCoCo
  upload. Closeout Actions and the annotated tag remain binding gates.
- A pre-tag closeout audit catches the renderer-line ratchet still at 3,451 after the
  final census reached 3,448; the cap is tightened to the measured value before tagging.
  The repair release gate repeats all 1,968 tests in 8:49, with JaCoCo 2,130/all
  floors, SpotBugs 0/0, and jar packaging green.
- Repair closeout commit `4b4193b0` passes Actions run `29810094538`: Docker in 2:12 and
  headless in 20:11. Annotated tag `v1883-order-platform-route-split-internals` peels to
  that commit and is canonical.

## v1882 - Release-acceptance sustainment renderer convergence

- Freezes the real legacy sustainment output before replacement and preserves it
  exactly: seven Markdown sections / 38 lines, 30 checks, and five ordered CI gates.
- Replaces one aggregate renderer, seven section renderers, and one support shell with
  one 118-line package-private `ReportRenderer` backed by `MarkdownSections.mapped`.
- Replaces six long test/factory/structure names with short responsibility names while
  retaining the real v1840 closeout graph and downstream acceptance-package coverage.
- Repairs a tautological route assertion so the root Controller test now pins the exact
  route suffix and final endpoint.
- Shrinks the family `19 -> 11`, ops Java `1266 -> 1258`, renderers `45 -> 38`,
  renderer lines `3616 -> 3521`, and long renderer filenames `30 -> 22`.
- Production long-name metrics improve to `1188 / 20495 / 2747`; test metrics improve
  to `776 / 10039 / 3801`. The exact baseline has 35 removals and no additions.
- Targeted behavior, Controller, downstream, and structure gates pass 27/27; the
  naming, change, v1866, current-structure, and exact-output set passes 28/28.
- The expanded history, elegance, change, walkthrough, archive, closeout, and README
  evidence selection passes 111/111.
- The walkthrough has 3,391 Han characters and exactly ten standard headings; the
  exact archive set is 1,693 files / 20,076,290 raw bytes.
- Full `mvnw -B verify` passes 1,963 tests in 12:10 with zero failures, errors,
  or skips; JaCoCo analyzes 2,137 classes with every floor met, SpotBugs reports
  0 bugs / 0 errors, and the executable jar is packaged. Implementation/closeout
  Actions and the annotated tag remain binding completion gates.
- Initial implementation commit `4ced994e` produced successful Docker evidence in
  Actions run `29799487464`, but headless stopped at the exact prior-commit Spotless
  ratchet because three edited test files contained mixed line endings and one pending
  Google Java Format fold. Repair commit `d525524b` normalizes that fold; the exact
  local ratchet and 30/30 release gates pass.
- Canonical implementation run `29799705965` passes Docker-tagged verification in
  2:03 and headless regression in 19:50, including Spotless against the v1881 peeled
  commit, full wrapper verify, production-profile smoke, and JaCoCo upload. Closeout
  commit `5ebe1c06` passes run `29800790309`: Docker in 1:54 and headless in 18:12.
  Annotated tag `v1882-order-platform-sustainment-renderer` is canonical.

## v1881 - Minimal read-only gate execution renderer convergence

- Adds `MarkdownSections.groupedCounted`, preserving group encounter order, item order,
  count prefixes, and immutable output with a dedicated engine test.
- Freezes the real legacy service output before replacement and preserves it exactly:
  the execution report has six sections / 40 lines and archive verification has six
  sections / 41 lines.
- Replaces eight long renderers and two support shells with package-private
  `ExecutionRenderer` and `ArchiveRenderer`; services, responses, routes, catalogs,
  transactions, and downstream operator-CI dependencies stay unchanged.
- Replaces two long test supports with `ExecutionTestData` and `ArchiveTestData`; the
  archive factory reuses the execution service graph.
- Shrinks the family `31 -> 23`, ops Java `1274 -> 1266`, renderers `51 -> 45`,
  renderer lines `3816 -> 3616`, and long renderer filenames `38 -> 30`.
- Production long-name metrics improve to `1197 / 20544 / 2756`; the exact baseline
  has no additions. The walkthrough has 3,418 Han characters and ten standard
  headings; the exact archive set is 1,692 files / 20,059,203 raw bytes.
- The expanded behavior, controller, downstream, historical structure, elegance,
  change, walkthrough, archive, and closeout selection passes 179/179.
- Full `mvnw -B verify` passes 1,960 tests in 9:25 with zero failures, errors,
  or skips; JaCoCo analyzes 2,145 classes with every floor met, SpotBugs reports
  0/0, and the executable jar is packaged.
- Implementation commit `7ec4f2ba` passes canonical Actions run `29795818326`:
  Docker-tagged verification in 2:06 and headless regression in 19:28, including
  the production-profile smoke and JaCoCo upload. Closeout Actions and the annotated
  tag remain binding completion gates.

## v1880 - Operator CI handoff renderer convergence

- Replaces nine long-named handoff/archive renderers and two support shells with
  `HandoffRenderer` and `ArchiveRenderer`, both backed by
  `MarkdownSections.counted`.
- Freezes the real legacy service output before replacement and preserves it exactly:
  the handoff report has five sections / 33 lines and the archive report has six
  sections / 36 lines.
- Replaces two long test-support names with `HandoffTestData` and `ArchiveTestData`;
  the archive factory now reuses the handoff factory instead of duplicating its graph.
- Shrinks the family `27 -> 18`, ops Java `1283 -> 1274`, renderers `58 -> 51`,
  renderer lines `3973 -> 3816`, and long renderer filenames `47 -> 38` without
  changing routes, response records, Catalog data, transactions, or authority.
- Core behavior, consumer, historical structure, exact-name, change, and census gates
  pass 87/87. The walkthrough has 3,273 Han characters and exactly ten headings;
  the exact archive set is 1,691 files / 20,041,344 raw bytes.
- Full `mvnw -B verify` passes 1,956 tests in 10:26 with zero failures, errors,
  or skips; JaCoCo analyzes 2,153 classes with every floor met, SpotBugs reports
  0/0, and the executable jar is packaged.
- Implementation commit `179e6609` passes canonical Actions run `29792136907`:
  Docker-tagged verification in 2:09 and headless regression in 19:17, including
  the production-profile smoke and JaCoCo upload. Closeout commit `d9fc4c84` passes
  run `29793217972`: Docker in 2:21 and headless in 18:56. Annotated tag
  `v1880-order-platform-operator-ci-handoff-renderers` is canonical.

## v1879 - Acceptance-package renderer convergence

- Replaces twelve long-named acceptance-package renderers and one support shell with
  three output-owned types: `ReportRenderer`, `ReceiptRenderer`, and
  `ArchiveIndexRenderer`.
- Freezes the legacy output before replacement and preserves it unchanged: the main
  report has nine sections / 47 lines, the receipt has seven lines, and the archive
  index has five sections / 22 lines.
- Replaces three long test-support names with `PackageTestData`, `ReceiptTestData`, and
  `ArchiveIndexTestData`, while retaining the real three-service construction chain.
- Shrinks the family `36 -> 26`, ops Java `1293 -> 1283`, renderers `67 -> 58`,
  renderer lines `4211 -> 3973`, and long renderer filenames `59 -> 47` without
  changing routes, response records, Catalog data, transactions, or authority.
- Behavior, controller, historical structure, elegance, change, and census gates pass
  71/71. The walkthrough has 4,430 Han characters and exactly ten standard headings.
- Full `mvnw -B verify` passes 1,953 tests in 14:02 with zero failures, errors, or
  skips; JaCoCo analyzes 2,162 classes with every floor met, SpotBugs reports 0/0,
  and the executable jar is packaged.
- Implementation commit `b5366eb1` passes canonical Actions run `29759922474`:
  Docker-tagged verification in 1:51 and headless regression in 19:15, including
  the production-profile smoke and JaCoCo upload. Closeout commit `5205246d`
  passes run `29761487591` (Docker 2:03, headless 19:22); annotated tag
  `v1879-order-platform-acceptance-package-renderers` is canonical.

## v1878 - Release-archive handoff renderer convergence

- Reuses `MarkdownSections.counted` and one 230-line typed `ReportRenderer`, deleting
  ten section renderers, their aggregate renderer, and the support shell.
- Freezes all ten headings and 67 content lines with an oracle that passed against the
  legacy implementation before deletion and then passed unchanged after replacement.
- Replaces the long test factory with `HandoffTestData`; the upstream archive registry
  and downstream route-path-split continue through the same public service boundaries.
- Shrinks the family `25 -> 14`, ops Java `1304 -> 1293`, renderers `77 -> 67`,
  renderer lines `4376 -> 4211`, and long renderer filenames `70 -> 59` without
  changing routes, response records, Catalog data, transactions, or authority.
- Core behavior, downstream, historical structure, elegance, change, and census gates
  pass 68/68; the walkthrough has 4,541 Han characters and ten standard headings.
- Full `mvnw -B verify` passes 1,949 tests in 9:16 with zero failures, errors, or
  skips; JaCoCo analyzes 2,172 classes with every floor met, SpotBugs reports 0/0,
  and the executable jar is packaged.
- Implementation commit `57ba6fd2` passes canonical Actions run `29753510453`:
  Docker-tagged verification in 1:59 and headless wrapper verify in 17:42, followed
  by the production-profile smoke and JaCoCo upload. Closeout commit `fd3c0cc1`
  passes run `29755253175` (Docker 2:27, headless 17:48); annotated tag
  `v1878-order-platform-archive-handoff-renderer-engine` is canonical.

## v1877 - Archive-registry renderer convergence

- Reuses `MarkdownSections.counted` and one 203-line typed `ReportRenderer`, deleting
  nine archive section renderers, their aggregate renderer, and the support shell.
- Freezes all nine headings and 57 content lines with an oracle that passed against the
  legacy implementation before deletion and then passed unchanged after replacement.
- Replaces the long test factory with `ArchiveTestData`; the upstream release-acceptance
  service and downstream archive-handoff service keep the same public boundaries.
- Shrinks the family `23 -> 13`, ops Java `1314 -> 1304`, renderers `86 -> 77`,
  renderer lines `4586 -> 4376`, and long renderer filenames `80 -> 70` without
  changing routes, responses, Catalog data, transactions, or authority.
- Core behavior, downstream, historical structure, elegance, and change gates pass
  65/65; the walkthrough has 3,281 Han characters and exactly ten standard headings.
- Full `mvnw -B verify` passes 1,947 tests in 17:10 with zero failures, errors,
  or skips; JaCoCo analyzes 2,183 classes with every floor met, SpotBugs reports
  0 bugs / 0 errors, and the executable jar is packaged.

## v1876 - Release-acceptance renderer convergence

- Reuses `MarkdownSections.mapped` and one 208-line typed `ReportRenderer`, deleting
  eleven release-acceptance renderers plus their support shell.
- Freezes all ten headings and 56 content lines with an oracle that passed against the
  legacy implementation before deletion and then passed unchanged after replacement.
- Replaces the long test factory with `ReleaseAcceptanceTestData` and keeps the upstream
  dossier service plus downstream archive service on the same public boundaries.
- Shrinks the family `25 -> 14`, ops Java `1325 -> 1314`, renderers `96 -> 86`,
  renderer lines `4809 -> 4586`, and long renderer filenames `91 -> 80` without
  changing routes, responses, Catalog data, transactions, or authority.
- Full `mvnw -B verify` passes 1,945 tests in 20:39 with zero failures, errors, or
  skips, 2,193 JaCoCo classes with every floor met, SpotBugs 0/0, and a packaged jar.
- Implementation commit `52e4c7c9` passes canonical Actions run `29739016977`:
  Docker-tagged tests in 2:06 and headless regression in 18:15.
- Closeout commit `e82edaa7` passes run `29740214540`: Docker in 2:09 and headless
  in 14:08. The annotated canonical tag is
  `v1876-order-platform-release-acceptance-renderer-engine`.

## v1875 - Verification-dossier renderer convergence

- Extends the shared engine with `MarkdownSections.mapped` for sections that must not
  receive a count prefix, preserving the dossier's existing format exactly.
- Replaces eleven dossier renderers and one support shell with one 206-line typed
  `ReportRenderer`; a pre-change oracle freezes all ten sections and 51 content lines.
- Shrinks the family `25 -> 14`, ops Java `1336 -> 1325`, renderers `106 -> 96`,
  renderer lines `5032 -> 4809`, and long renderer filenames `102 -> 91` without
  changing routes, response records, catalog data, transactions, or authority.
- Renames the legacy test factory to `DossierTestData` and tightens historical,
  global census, long-name, walkthrough, and archive gates to the new state.
- Full `mvnw -B verify` passes 1,943 tests, all JaCoCo floors, SpotBugs 0/0,
  and executable-jar packaging after the Chinese walkthrough is complete.

## v1874 - Consumer-package renderer convergence

- Replaces ten one-shot consumer-package renderers and their support shell with one
  176-line typed `ReportRenderer` backed by the shared `MarkdownSections` engine.
- Freezes all nine headings and every emitted Markdown line with an oracle that passed
  against the legacy implementation before deletion.
- Shrinks the family from 23 to 13 production files, ops Java `1346 -> 1336`, renderer
  files `115 -> 106`, renderer lines `5236 -> 5032`, and long renderer filenames
  `112 -> 102`, without changing routes, response records, catalog data, or authority.
- Replaces the legacy long-named test support with short typed test data and tightens
  family, global census, long-name, walkthrough, and archive gates to the new state.

## v1873 - Declarative Markdown renderer engine

- Replaces seven one-shot archive-digest renderers and one support shell with a shared
  immutable `MarkdownSections` algorithm plus one short, typed `ReportRenderer`.
- Freezes every existing Markdown heading and line against the old implementation,
  tightens the v1845 structural gate, and adds a reproducible ops elegance census.
- Shrinks ops Java `1352 -> 1346`, renderers `121 -> 115`, renderer lines
  `5355 -> 5236`, and production long-name uses `21167 -> 21124` without changing
  routes, response records, catalog data, or runtime authority.
- Splits change-gate responsibilities so deleted debt is not scanned as live source:
  new filenames stay short, exact long-name identities cannot grow, and aggregate
  occurrences can only shrink.

## v1872 - Immutable DTO collection boundaries

- Gives `PagedResponse`, `CreateOrderRequest`, and `OrderResponse` owned immutable list
  snapshots without changing their record components, JSON shape, or validation annotations.
- Centralizes null-preserving defensive copies in `ImmutableLists` and verifies input-alias
  isolation plus unmodifiable access through one generic test scenario.
- Removes six real `EI_EXPOSE_REP`/`EI_EXPOSE_REP2` waivers after SpotBugs returns 0/0,
  shrinking the exact waiver set from 682 to 676.

## v1871 - Exact SpotBugs waiver identities

- Replaces the aggregate-only 686-entry cap with secure XML parsing and Git-aware
  pattern/class identity comparison, so equal-count waiver swaps fail mechanically.
- Rejects DTDs, malformed or duplicate Match nodes, and exclusions whose class no longer
  loads from the compiled test classpath.
- Removes four stale exclusions for deleted walkthrough-registry types, shrinking the
  exact waiver set from 686 to 682 without changing production code or SpotBugs scope.

## v1870 - Framework-independent business errors

- Moves HTTP status selection out of business exceptions, order entities, and inventory
  services; the Web exception handler now owns one exhaustive business-kind mapping.
- Replaces a long derived-query name with an explicit locked, ordered, 50-row expiry
  query while preserving the existing database behavior.
- Collapses repeated inventory iteration, quantity guards, inventory lookup failures, and
  order state transitions; the production diff is net shrinking after Spotless normalization.
- Adds a source-scanning HTTP boundary test and shrinks the exact long-name baseline.

## v1869 - Diff-aware elegance gates

- Replaces aggregate-only long-name protection with an exact, reproducible baseline of
  legacy file paths and lexical identifiers; every later baseline may only shrink.
- Adds one Git change model for dirty local trees and clean CI commits, enforcing short
  names on changed Java, a 400-line production growth cap, and design notes for new
  three-file families.
- Proves the gate with a temporary 56-character filename that fails mechanically, while
  leaving production code, routes, fixtures, schemas, and runtime authority unchanged.

## v1868 - README evidence exhibition

- Adds the repository-root GitHub landing page with a bilingual project summary,
  mechanically sourced badges, a system-and-authority diagram, and direct evidence links.
- Keeps the project README as the deep technical guide while adding the same CI badge and
  one self-verification block for the three committed censuses plus full Maven verification.
- Adds a README consistency gate, reconciles the completed v1867 closeout run and tag,
  and preserves the exact maturity and no-execution boundary language.

## v1867 - Java production-excellence Phase 2 closeout candidate

- Repoints 735 route reads in 160 files to their actual leaf owners and removes 239
  pure forwarding aliases. The root keeps 15 owned literals plus 12 compatibility
  aliases used by the ReleaseAcceptance root-versus-leaf route proof.
- Splits the last two oversized test classes around shared scenario fixtures while
  preserving every response, digest, list-order, and no-write assertion.
- Adds shrink-only elegance, source-size, SpotBugs, archive-retention, workflow,
  coverage, and E1-E10 mechanical gates; refreshes the honest production boundary.
- Upgrades official GitHub actions to checkout v7, setup-java v5, and upload-artifact
  v7. This is a local/CI candidate until external Java-track review grants final status.

## v1866 - Ops root extraction endgame closure

- Reached the binding Phase 1 end state: 104 direct-root files, 100 controllers,
  four retained shared roots, zero movable files, and zero unassigned files.
- Moved the final overview/static-release support inward and split the static release
  table into exact 225/476-line responsibilities without changing public contracts.
- Passed 1,901 tests, all JaCoCo floors, SpotBugs zero, both GitHub Actions jobs, and
  fixed the end state with the v1866 closeout tag.
## v1837 - Release-approval verification composition split

- Replaced repeated 34-builder and multi-dozen receipt argument lists with a
  package-private verification context carrying the canonical receipt chain.
- Extracted no-ledger-write evaluation while preserving every response field,
  warning-digest input, proof claim, Node verification action, and list order.
- Reduced the verification hint, warning digest, and response files from
  874/675/564 to 70/421/421 lines; consolidated verification support is 412
  lines and keeps direct-root/total ops file counts unchanged.
- Tightened production hotspot counts above 500/750 lines from 38/4 to 35/3
  and added reflection plus source-structure gates against parameter fan-out.

## v1836 - Failed-event command responsibility split

- Kept `FailedEventMessageService` as the only public transactional facade while
  moving dead-letter recording, management, approval, and replay execution into
  package-private components.
- Replaced parallel replay strings with an immutable effective-event value and
  kept RabbitMQ headers, attempt persistence, validation errors, and operator
  authorization behavior unchanged.
- Reduced the facade from 662 to 199 lines; the five new collaborators are 71,
  89, 129, 222, and 33 lines, with no new public API.
- Tightened the production count above 500 lines from 39 to 38 and added a
  structural dependency gate preventing repository or messaging infrastructure
  from leaking back into the facade.

## v1835 - Failed-event query responsibility split

- Preserved `FailedEventMessageService` as the public transactional facade while
  moving query orchestration into a package-private collaborator.
- Split JPA specifications and page/sort validation into focused helpers without
  changing filters, defaults, stable tie-break sorting, CSV output, or errors.
- Reduced the former 1,126-line service to 662 lines; the three new collaborators
  are 310, 159, and 103 lines, with no replacement giant class.
- Tightened production hotspot counts above 750/1,000 lines from 5/3 to 4/2 and
  added direct behavior plus architecture regression tests.
- Promoted the exact line-count rule after discovering that PowerShell
  `Measure-Object -Line` omits blank lines.

## v1834 - Java maintainability hotspot budget

- Added a reproducible production/test Java source census with Windows
  long-path support and structured JSON output.
- Added shrink-only aggregate budgets plus named caps for the five highest-risk
  production files, making future giant-file growth a test failure.
- Published the behavior-preserving v1834-v1837 optimization sequence and its
  explicit failure conditions; no runtime route, schema, message, or storage
  behavior changed.
- Closed the stale v1833 progress row with commit, tag, and green CI evidence,
  and retained the promoted same-session ledger-closeout rule.

## v1833 - Compared evidence candidate intake preflight extraction

- Moved the `ComparedEvidenceCandidateIntakePreflight` implementation into
  `ops.maintenance.comparedevidencecandidateintakepreflight` while retaining the
  Spring controller in the root `ops` package.
- Added a public candidate-intake-preflight route owner and delegated the root
  route aggregator to it without changing endpoint suffix bytes. The historical
  CandidateDocument catalog-suffix exposure now delegates to the same owner.
- Folded the standalone GateCatalog into GuardCatalog to offset the new route
  owner and keep total `ops` Java files at 1,352.
- Updated ProfileSection readers to import the moved intake-preflight service
  and response, kept candidate-blueprint endpoint reads explicit through the
  v1832 route owner, and relocated the SpotBugs response FQN.
- Tightened the live root census from 819 to 805 and the remaining movable
  backlog from 714 to 700, then recorded the fifth-batch checkpoint.
- Added the v1833 extraction note, census update, readability guard coverage,
  progress evidence, and Chinese walkthrough.

## v1832 - Compared evidence candidate blueprint extraction

- Moved the `ComparedEvidenceCandidateBlueprint` implementation into
  `ops.maintenance.comparedevidencecandidateblueprint` while retaining the
  Spring controller in the root `ops` package.
- Added a public candidate-blueprint route owner and delegated the root route
  aggregator to it without changing endpoint suffix bytes. The route owner also
  carries the five full endpoint constants formerly held by the old EndpointRefs
  helper.
- Updated CandidateIntakePreflight and ProfileSection endpoint readers to import
  the moved boundary, publicized only the still-root
  `ComparedEvidenceEvaluationPreflightEndpointRefs` immutable constants needed
  by the moved section catalogs, and relocated the SpotBugs response FQN.
- Tightened the live root census from 833 to 819 and the remaining movable
  backlog from 728 to 714 while total `ops` Java files stay at 1,352.
- Added the v1832 extraction note, census update, readability guard coverage,
  progress evidence, and Chinese walkthrough.

## v1831 - Operator evidence value supply base extraction

- Moved the `OperatorEvidenceValueSupply` base implementation into
  `ops.maintenance.operatorevidencevaluesupply` while retaining the two Spring
  controllers in the root `ops` package.
- Added a public ValueSupply route owner and delegated the root route aggregator
  to it without changing endpoint suffix bytes. The moved services compute
  their immutable `ENDPOINT` constants from that owner.
- Updated AdapterPreflight and ApprovalPreflight endpoint readers to import the
  moved base services, relocated the SpotBugs base response FQN, and moved the
  service/support tests with the package.
- Tightened the live root census from 848 to 833 and the remaining movable
  backlog from 743 to 728 while total `ops` Java files stay at 1,352.
- Added the v1831 extraction note, census update, readability guard coverage,
  progress evidence, and Chinese walkthrough.

## v1830 - Operator evidence value supply adapter preflight extraction

- Moved the `OperatorEvidenceValueSupplyAdapterPreflight` implementation into
  `ops.maintenance.operatorevidencevaluesupplyadapterpreflight` while retaining
  the two Spring controllers in the root `ops` package.
- Added a public AdapterPreflight route owner and delegated the root route
  aggregator to it without changing endpoint suffix bytes. The moved services
  now compute their public immutable `ENDPOINT` constants from that owner.
- Folded the old AdapterPreflight `RuleCatalog` into `SlotCatalog` to offset
  the new route owner, keeping total `ops` Java files at 1,352 while direct root
  files fall from 864 to 848 and remaining movable root files fall from 759 to
  743.
- Added the v1830 extraction note, census update, readability guard coverage,
  SpotBugs FQN relocation, progress evidence, and Chinese walkthrough.

## v1829 - Signed approval draft profile section handoff extraction

- Closed the third ProfileSection extraction by moving ten
  `SignedApprovalDraftProfileSectionHandoff` implementation files into
  `ops.maintenance.signedapprovaldraftprofilesectionhandoff`.
- Kept the handoff and registry Spring controllers in root, moved the handoff
  route suffix to the signed-approval ProfileSection route owner, and preserved
  the byte-identical handoff HTTP path through root and candidate-document
  delegation. Direct root `ops` Java files fall from 874 to 864 while total
  `ops` Java files stay at 1,352.
- Added `scripts/ops-root-census.ps1` so the final-push endgame census can be
  reproduced with one command, then recorded v1829 progress as remaining
  movable root files fall from 769 to 759.
- Added the v1829 extraction note, readability guard coverage, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1828 - Ops extraction endgame census

- Added the Java final-push Phase 1 endgame census for the remaining direct-root
  `ops` package. The census fixes the current root count at 874, the final root
  target at 105, and the remaining move/collapse backlog at 769 files.
- Added `docs/ops/extraction-waivers.md` so non-controller root retention has a
  narrow committed list instead of informal exceptions.
- Added v1828 readability guard coverage for the census buckets and waiver list.
  No route, response, write boundary, runtime profile, deployment, rollback, or
  archive path changed.

## v1827 - Java final push step 0 reconciliation

- Reconciled the Java final-push Step 0 facts: v1826 commit `dd3e1db0`, tag
  `v1826-order-platform-production-excellence-ops-signed-approval-draft-text-package-profile-section-extraction`,
  push, and GitHub Actions run `27874073004` are recorded as complete.
- Promoted `docs/project-explanation/project-value-and-flow.md` from a floating
  untracked explanation into a committed project artifact, with a local README
  and documentation guard coverage.
- Added the v1827 reconciliation note and progress evidence. No route, response,
  write boundary, runtime profile, deployment, rollback, or archive path changed.

## v1826 - Signed approval draft text package profile section extraction

- Continued the three-version ProfileSection split by moving the text-package
  profile section implementation into
  `ops.maintenance.signedapprovaldrafttextpackageprofilesection`.
- Kept the Spring controller in root, folded the gate generator into registry
  support, and added a public signed-approval route owner. Direct root `ops`
  Java files fall from 887 to 874 while total `ops` Java files stay at 1,352.
- Preserved the nine read-only source contracts and made the mixed boundary
  explicit: six upstream text-package routes are already extracted while three
  compared-evidence candidate sources still live in the root package. No route,
  response, approval, runtime, write, deployment, rollback, or archive
  contract changed.
- Added the v1826 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1825 - Signed approval draft profile section extraction

- Started the three-version extraction of the 36-file ProfileSection cluster by
  moving the 11-file base family into
  `ops.maintenance.signedapprovaldraftprofilesection`.
- Kept the Spring controller in root, folded the gate generator into registry
  support, and added a public route owner. Direct root `ops` Java files fall
  from 897 to 887 while total `ops` Java files stay at 1,352.
- Preserved the five upstream read-only source contracts and repaired the
  retained-root controller and ProfileSectionHandoff imports. No route,
  response, approval, runtime, write, deployment, rollback, or archive
  contract changed.
- Added the v1825 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1824 - Signed approval artifact draft text package compared package evidence intake extraction

- Moved thirteen compared-package-evidence-intake implementation and endpoint
  reference files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake`.
  The guard catalog is collocated with the slot catalog, direct root `ops`
  Java files fall from 911 to 897, and total `ops` Java files stay at 1,352.
- Added a public route owner for the five compared-package-evidence-intake
  suffixes. Root route aggregation delegates to it, preserving byte-identical
  paths.
- Kept the controller in root and repaired explicit public imports for
  ComparedPackageReview, ProfileSection, controller tests, route tests, and
  test support. No package acceptance, text parsing, detached-signature parsing,
  approval, write, credential, deployment, rollback, runtime, or archive
  contract changed.
- Added the v1824 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1823 - Signed approval artifact draft text package comparison acceptance precheck extraction

- Moved seven comparison-acceptance-precheck implementation files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck`.
  The guard catalog is collocated with the checkpoint catalog, direct root
  `ops` Java files fall from 919 to 911, and total `ops` Java files stay at
  1,352.
- Added a public route owner for the four comparison-acceptance-precheck
  suffixes. Root route aggregation delegates to it, preserving byte-identical
  paths.
- Kept the controller in root and repaired explicit public imports for
  ComparedPackageEvidenceIntake, ProfileSection, controller tests, route tests,
  and test support. No package acceptance, comparison execution, parsing,
  approval, write, credential, deployment, rollback, or archive contract
  changed.
- Added the v1823 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1822 - Signed approval artifact draft text package comparison preflight extraction

- Moved twelve physical comparison-preflight implementation files into
  `ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight`.
  The gate catalog is collocated with the acceptance-control catalog, direct
  root `ops` Java files fall from 932 to 919, and total `ops` Java files stay
  at 1,352.
- Added a public route owner for the five comparison-preflight suffixes. Root
  route aggregation delegates to it, preserving byte-identical paths.
- Kept the controller in root and repaired explicit public imports for
  ComparisonAcceptancePrecheck, ComparedPackageEvidenceIntake, ProfileSection,
  controller tests, route tests, and test support. No response component,
  comparison execution, package acceptance, write, credential, deployment,
  rollback, or archive contract changed.
- Added the v1822 extraction note, readability ratchet, SpotBugs FQN
  relocation, progress evidence, and Chinese walkthrough.

## v1821 - Signed approval artifact draft text package submission preflight extraction

- Moved the primary submission-preflight and its Closeout family together into
  `ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight`.
  Twenty-eight physical files move, the package-private gate catalog is
  collocated with the comparison-control catalog, direct root `ops` Java files
  fall from 961 to 932, and total `ops` Java files stay at 1,352.
- Added a public route owner for five primary and six Closeout HTTP suffixes.
  Root route aggregation delegates to it, preserving all route strings. Three
  Closeout detail views remain catalog URI fragments rather than new routes.
- Kept both controllers in root and repaired explicit public imports for
  TextPackageComparisonPreflight, ComparedPackageEvidenceIntake, ProfileSection,
  controller tests, route tests, and test support. No response component,
  write, credential, deployment, rollback, or archive contract changed.
- Added the v1821 extraction note, readability ratchet, four SpotBugs FQN
  relocations for the primary/Closeout responses, progress evidence, and
  Chinese walkthrough.

## v1820 - Signed approval artifact draft text package review preflight registry package extraction

- Moved the signed-approval artifact-draft-text-package-review-preflight family
  into `ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight`:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the rejection-control catalog. Direct root `ops`
  Java files fall from 977 to 961 and total `ops` Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftTextPackageReviewPreflightRoutePaths`
  owner. Root route aggregation delegates to it, preserving all nine paths.
- Moved services keep reading v1819 TextPackageIntake endpoints. Retained root
  `TextPackageSubmissionPreflight` and ProfileSection readers import the new
  public endpoint/response boundary. No route, response, write, credential,
  deployment, rollback, or archive contract changed.
- Added the v1820 extraction note, readability test, SpotBugs FQN relocation,
  count-ratchet updates, progress evidence, and Chinese walkthrough.
## v1819 - Signed approval artifact draft text package intake registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-text-package-intake registry family into the new
  `ops.maintenance.signedapprovalartifactdrafttextpackageintake` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the guard catalog to offset the new route owner.
  Direct root `ops` Java files fall from 993 to 977 and total `ops` Java files
  stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftTextPackageIntakeRoutePaths`
  owner for the artifact-draft-text-package-intake suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved field catalogs keep reading
  already-public v1818 `ArtifactDraftInstructionPreflight` endpoint constants,
  and retained root readers (`TextPackageReviewPreflight`,
  `SignedApprovalDraftTextPackageProfileSection`) import this family's public
  immutable endpoint strings. No route, response, write boundary, credential
  boundary, deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-text-package-intake-extraction-v1819.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1819Tests`, relocated the
  two `ArtifactDraftTextPackageIntakeResponse` EI_EXPOSE SpotBugs exclude FQNs
  to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 993 to 977.
## v1818 - Signed approval artifact draft instruction preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-instruction-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftinstructionpreflight` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the guard catalog to offset the new route owner.
  Direct root `ops` Java files fall from 1,009 to 993 and total `ops` Java
  files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths`
  owner for the artifact-draft-instruction-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved slot catalogs keep reading
  already-public v1817 `ArtifactDraftAuthoringReadiness` endpoint constants,
  and retained root readers (`TextPackageIntake`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-instruction-preflight-extraction-v1818.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1818Tests`, relocated the
  two `ArtifactDraftInstructionPreflightResponse` EI_EXPOSE spotbugs-exclude
  FQNs to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1009 to 993.
## v1817 - Signed approval artifact draft authoring readiness registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-authoring-readiness registry family into the new
  `ops.maintenance.signedapprovalartifactdraftauthoringreadiness` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the blocker catalog to offset the new route
  owner. Direct root `ops` Java files fall from 1,025 to 1,009 and total `ops`
  Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths`
  owner for the artifact-draft-authoring-readiness suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved requirement catalogs keep reading
  already-public v1816 `ArtifactDraftReviewPackagePreflight` endpoint constants,
  and retained root readers (`InstructionPreflight`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-authoring-readiness-extraction-v1817.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1817Tests`, relocated the
  two `ArtifactDraftAuthoringReadinessResponse` EI_EXPOSE spotbugs-exclude FQNs
  to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1025 to 1009.

## v1816 - Signed approval artifact draft review package preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-review-package-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight`
  subpackage: fifteen physical implementation files moved, while the
  package-private gate catalog was collocated with the guard catalog to offset
  the new route owner. Direct root `ops` Java files fall from 1,041 to 1,025
  and total `ops` Java files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths`
  owner for the artifact-draft-review-package-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved slot catalogs keep reading
  already-public v1815 `ArtifactDraftReadinessLane` endpoint constants, and
  retained root readers (`AuthoringReadiness`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-review-package-preflight-extraction-v1816.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1816Tests`, relocated the
  two `ArtifactDraftReviewPackagePreflightResponse` EI_EXPOSE spotbugs-exclude
  FQNs to the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1041 to 1025.

## v1815 - Signed approval artifact draft readiness lane registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-readiness-lane registry family into the new
  `ops.maintenance.signedapprovalartifactdraftreadinesslane` subpackage:
  fifteen physical implementation files moved, while the package-private gate
  catalog was collocated with the blocker catalog to offset the new route owner.
  Direct root `ops` Java files fall from 1,057 to 1,041 and total `ops` Java
  files stay at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths` owner
  for the artifact-draft-readiness-lane suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved lane catalogs keep reading
  already-public v1814 `ArtifactDraftPreflight` endpoint constants, and retained
  root `ReviewPackagePreflight` slot catalogs import this family's public
  immutable endpoint strings. No route, response, write boundary, credential
  boundary, deployment, rollback, or archive layout changed.
- Added
  `docs/ops/signed-approval-artifact-draft-readiness-lane-extraction-v1815.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1815Tests`, relocated the
  two `ArtifactDraftReadinessLaneResponse` EI_EXPOSE spotbugs-exclude FQNs to
  the new package, and lowered the governance ratchet
  `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package guard
  from 1057 to 1041.

## v1814 - Signed approval artifact draft preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-preflight registry family into the new
  `ops.maintenance.signedapprovalartifactdraftpreflight` subpackage: fifteen
  physical implementation files moved, while the package-private gate catalog
  was collocated with the guard catalog to offset the new route owner. Direct
  root `ops` Java files fall from 1,073 to 1,057 and total `ops` Java files stay
  at 1,352.
- Added the public
  `OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths` owner for
  the artifact-draft-preflight suffixes. The root `OpsShardReadinessRoutePaths`
  aggregator delegates to that owner, so route strings remain byte-identical
  while implementation ownership leaves root.
- Cross-family endpoint sub-recipe: the moved field catalogs keep reading
  already-public v1813 `ArtifactDraftReadiness` endpoint constants, and retained
  root readers (`ArtifactDraftReadinessLane`,
  `SignedApprovalDraftProfileSection`) import this family's public immutable
  endpoint strings. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-artifact-draft-preflight-extraction-v1814.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1814Tests`, relocated the
  two `ArtifactDraftPreflightResponse` EI_EXPOSE spotbugs-exclude FQNs to the
  new package, and lowered the governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`,
  the mirrored `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured
  root-package guard from 1073 to 1057.

## v1813 - Signed approval artifact draft readiness registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  artifact-draft-readiness registry family - sixteen non-controller
  implementation files - into the new
  `ops.maintenance.signedapprovalartifactdraftreadiness` subpackage, reducing
  direct root `ops` Java files from 1,089 to 1,073. The two public
  `@RestController` classes and the global `OpsShardReadinessRoutePaths`
  aggregator stay in root.
- The moved services were repointed from the package-private root aggregator to
  the public family route owner
  `OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths`, which already
  lived in `ops.maintenance.signedapproval` from the v1804 route-path
  consolidation; v1813 only adds a public `BASE_PATH` to that owner. The root
  aggregator still delegates to the same owner, keeping every endpoint string
  byte-identical.
- Cross-family endpoint sub-recipe: the moved item catalogs read ten
  `CaptureArtifactPreflight` endpoint constants already publicized in v1810 (no
  new outbound change); this family's own service endpoint constants are
  publicized as immutable read-only strings for three retained-root sibling
  readers (`ArtifactDraftPreflight`, `ArtifactDraftReviewPackagePreflight`,
  `SignedApprovalDraftProfileSection`). No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-artifact-draft-readiness-extraction-v1813.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1813Tests`, relocated the two
  `ArtifactDraftReadinessResponse` EI_EXPOSE spotbugs-exclude FQNs to the new
  package, and lowered the governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the
  mirrored `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root guard
  from 1089 to 1073.

## v1812 - Approval preflight registry package extraction

- Moved the operator-evidence-value-supply approval-preflight registry family
  into the new `ops.maintenance.approvalpreflight` subpackage: fifteen physical
  implementation files moved, while the package-private policy catalog was
  collocated with the item catalog to offset the new route owner. Direct root
  `ops` Java files fall from 1105 to 1089 and total `ops` Java files stay at
  1352.
- Added the public
  `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths`
  owner for the approval-preflight suffixes. The root
  `OpsShardReadinessRoutePaths` aggregator delegates to that owner, so route
  strings remain byte-identical while implementation ownership leaves root.
- Applied the endpoint-only cross-family recipe: the moved `ItemCatalog` imports
  seven upstream value-supply or adapter-preflight endpoint constants, now
  public immutable strings, and the v1811
  `SignedApprovalCapturePreflightInputCatalog` imports approval-preflight
  endpoint constants from the new package. No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/approval-preflight-extraction-v1812.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1812Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 1105 to 1089.

## v1811 - Signed approval capture preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval capture-preflight
  registry family - sixteen non-controller implementation files - into the new
  `ops.maintenance.signedapprovalcapturepreflight` subpackage, reducing direct
  root `ops` Java files from 1121 to 1105.
- Left the two public controllers and the root `OpsShardReadinessRoutePaths`
  aggregator in the root package. Services now build `ENDPOINT` values from the
  public `OpsShardReadinessSignedApprovalCapturePreflightRoutePaths` owner,
  which already lived in `ops.maintenance.signedapproval` from the v1804
  route-path consolidation; v1811 only adds its public `BASE_PATH`.
- Applied the endpoint-only cross-family recipe in both directions:
  `InputCatalog` imports eleven upstream `ApprovalPreflight` endpoint constants
  that are now public immutable strings, and the v1810
  `CaptureArtifactPreflightFragmentCatalog` imports ten sibling
  `CapturePreflight` endpoint constants from the new package. No route,
  response, write boundary, credential boundary, deployment, rollback, or
  archive layout changed.
- Added `docs/ops/signed-approval-capture-preflight-extraction-v1811.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1811Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES`, the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES`, and the exact measured root-package
  guard from 1121 to 1105.

## v1810 - Signed approval capture artifact preflight registry package extraction

- Moved the operator-evidence-value-supply signed-approval
  capture-artifact-preflight registry family - sixteen non-controller
  implementation files - into the new
  `ops.maintenance.signedapprovalcaptureartifactpreflight` subpackage, reducing
  direct root `ops` Java files from 1,137 to 1,121. The two public
  `@RestController` classes and the global `OpsShardReadinessRoutePaths`
  aggregator stay in root.
- The moved services were repointed from the package-private root aggregator to
  the public family route owner
  `OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths`, which
  already lived in `ops.maintenance.signedapproval` from the v1804 route-path
  consolidation; v1810 only adds a public `BASE_PATH` to that owner. The root
  aggregator still delegates to the same owner, keeping every endpoint string
  byte-identical.
- Applied the cross-family endpoint sub-recipe: the family `FragmentCatalog`
  reads ten sibling `CapturePreflight` endpoint constants, which are now
  publicized as immutable read-only strings and imported into the moved file;
  the retained-root `ArtifactDraftReadiness` item catalogs read this family's
  endpoint constants, which are likewise publicized. No route, response, write
  boundary, credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/signed-approval-capture-artifact-preflight-extraction-v1810.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1810Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` and the mirrored
  `EXPECTED_ROOT_OPS_MAIN_JAVA_FILES` from 1137 to 1121.

## v1809 - Manual evidence worksheet registry package extraction

- Moved the manual-evidence-worksheet registry family - fifteen non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessManualEvidenceWorksheetRoutePaths` - into the new
  `ops.maintenance.manualevidenceworksheet` subpackage, reducing direct root
  `ops` Java files from 1,152 to 1,137. The two public `@RestController`
  classes and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. The root aggregator
  imports and delegates to the moved owner, keeping every endpoint string
  byte-identical.
- Continued the cross-family endpoint sub-recipe upstream of
  `OperatorEvidenceImportPreflight`: downstream import-preflight services now
  import worksheet endpoint constants from the new package, and the worksheet
  services publicize only immutable `RuntimeExecution` endpoint strings they
  already referenced. No route, response, write boundary, credential boundary,
  deployment, rollback, or archive layout changed.
- Added `docs/ops/manual-evidence-worksheet-extraction-v1809.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1809Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1152 to 1137.

## v1808 - Operator evidence import preflight registry package extraction

- Moved the operator-evidence-import-preflight registry family - fifteen
  non-controller implementation files plus the family route-path class
  `OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths` - into the new
  `ops.maintenance.operatorevidenceimportpreflight` subpackage, reducing direct
  root `ops` Java files from 1,167 to 1,152. The two public `@RestController`
  classes and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. The root aggregator
  now imports and delegates to the moved owner, keeping every endpoint string
  byte-identical.
- Continued the cross-family endpoint sub-recipe: the moved ImportPreflight
  services import immutable `ManualEvidenceWorksheet` and
  `RuntimeExecutionLiveReadGate` endpoint constants, so those upstream
  `ENDPOINT` constants are now public. No route, response, write boundary,
  credential boundary, deployment, rollback, or archive layout changed.
- Added `docs/ops/operator-evidence-import-preflight-extraction-v1808.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1808Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1167 to 1152.

## v1806 - Java extraction quality closeout

- Added `docs/ops/java-extraction-quality-closeout-v1806.md` to record the
  current v1805 green baseline, the historical v1798 tag exception, the v1799
  remediation boundary, and the policy that historical tags must not be
  rewritten or force-moved.
- Added `ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests` so the
  closeout evidence, current root-package count (`1,183`), progress table, ops
  index, and changelog stay discoverable.
- No business code, route string, response schema, runtime profile, archive
  layout, deployment, rollback, or evidence contract changed.

## v1805 - Candidate document registry package extraction

- Moved the entire candidate-document registry family 鈥?57 non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessCandidateDocumentRoutePaths` 鈥?into the new
  `ops.maintenance.candidatedocument` subpackage, reducing direct root `ops`
  Java files from 1,240 to 1,183 (the largest single reduction in the
  consolidation program). The eight public `@RestController` classes and the
  global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; the relocated services were repointed from the
  package-private aggregator to the family route-path class. Dependency injection
  is intra-family, so the family moved as one unit with no cross-package wiring.
- Handled the one genuine cross-family edge: two candidate-document catalogs
  reference the compared-evidence candidate-intake-preflight catalog route, which
  the aggregator previously defined inline. That constant now lives in the
  candidate-document route-path class and the aggregator delegates to it, so the
  compared-evidence family keeps the same value through the aggregator.
- Relocated 19 SpotBugs EI_EXPOSE_REP/REP2 exclusions across 9 candidate-document
  response classes to the new fully-qualified names. Two shared test-support
  classes used by retained root tests were made public.
- Added `docs/ops/candidate-document-extraction-v1805.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1805Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1240 to 1183.

## v1807 - Operator evidence value draft registry package extraction

- Moved the operator-evidence-value-draft registry family 鈥?16 non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessOperatorEvidenceValueDraftRoutePaths` 鈥?into the new
  `ops.maintenance.operatorevidencevaluedraft` subpackage, reducing direct root
  `ops` Java files from 1,183 to 1,167. The two public `@RestController` classes
  and the global `OpsShardReadinessRoutePaths` aggregator stay in root.
- First application of the cross-family endpoint sub-recipe (visibility only, no
  route change): made seven `OperatorEvidenceImportPreflight` service `ENDPOINT`
  constants public (read outbound by the relocated value-draft files) and the
  value-draft service `ENDPOINT` constants public (read inbound by the root
  `OperatorEvidenceValueSupplySlotCatalog` and a value-draft route guard test),
  adding imports across the new package boundary.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; relocated services were repointed from the
  package-private aggregator to the family route-path class. Relocated 2 SpotBugs
  EI_EXPOSE exclusions to the new fully-qualified names.
- Added `docs/ops/operator-evidence-value-draft-extraction-v1807.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1807Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1183 to 1167.

## v1804 - Signed approval route-path consolidation

- Moved three signed-approval route-path classes
  (`OpsShardReadinessSignedApproval{ArtifactDraftReadiness,CaptureArtifactPreflight,CapturePreflight}RoutePaths`)
  into the new `ops.maintenance.signedapproval` subpackage, reducing direct root
  `ops` Java files from 1,243 to 1,240 while keeping the total ops file count
  stable. This is the first pure route-path leaf consolidation (no service,
  controller, or response moves) and stands up the signedapproval subpackage for
  later migration of the operator-evidence-value-supply signed-approval registry
  families.
- Made the three route-path classes and their
  `OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_*` suffix constants public
  (behaviour-neutral; values unchanged). Each class is referenced directly only
  by the root `OpsShardReadinessRoutePaths` aggregator (which still delegates the
  matching public suffix) and its single `...RoutePathsTests` guard; both were
  repointed by import only. The registry services/controllers that own those
  routes stay in root and continue to read the suffixes through the aggregator,
  so every endpoint string is byte-identical.
- Added `docs/ops/signed-approval-route-path-consolidation-v1804.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1804Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1243 to 1240.

## v1803 - Sandbox connection registry package extraction

- Moved twenty-six sandbox connection implementation files (two sibling registry
  sub-clusters 鈥?the blocked-execution-context dossier and the precheck
  upstream-receipt verification manifest 鈥?that share one route-path class) into
  `ops.maintenance.sandboxconnection`, reducing direct root `ops` Java files from
  1,269 to 1,243 while keeping the total ops file count stable. This is the
  second dependency-injected "evidence" registry family extracted and the largest
  single root-pressure reduction so far.
- Made the family route-path class public with its public `BASE_PATH`/suffixes;
  both root controllers and the aggregator import it; the moved
  services/catalogs/support import the public `OpsEvidenceService` and
  `ReleaseApprovalRehearsalResponse` types they previously referenced same-package.
  The routes
  `/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier`
  and
  `/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest`,
  both response shapes, and read-only flags are byte-identical.
- Made the single shared schema-version constant
  `RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION`
  on `OpsEvidenceService` public (immutable string, behaviour-neutral) so the
  moved support can read it; the moved test supports reuse the already-public
  `OpsEvidenceServiceTestFixtures`, and both route/controller tests stay in root
  and construct their service directly through that fixture.
- Relocated the moved Responses' accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` (eleven entries) to the new FQN (same accepted
  findings, none new); the `ReleaseApproval*SandboxConnection*Records` exclusions
  stay in root because those records do not move.
- Added `docs/ops/sandbox-connection-extraction-v1803.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1803Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1269 to 1243.

## v1802 - Credential resolver disabled fake harness evidence archive package extraction

- Moved eleven credential resolver disabled fake harness evidence archive
  implementation files into `ops.maintenance.credentialresolver`, reducing direct
  root `ops` Java files from 1,280 to 1,269 while keeping the total ops file
  count stable. First dependency-injected "evidence" registry extracted (second
  family outside CodeWalkthrough).
- Made the family route-path class public with its public `BASE_PATH`/suffix;
  the root controller and aggregator import it; the moved service/catalogs import
  the public `OpsEvidenceService` and `ReleaseApprovalRehearsalResponse` types
  they previously referenced same-package. The route
  `/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive`,
  response shape, and read-only flags are byte-identical.
- Made the shared test helper `OpsEvidenceServiceTestFixtures` public so the
  moved package-local test support can reuse it; the root service/controller test
  now constructs the service directly through that fixture.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1802Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1280 to 1269.

## v1801 - Screenshot explanation archive registry package extraction

- Moved ten screenshot explanation archive registry implementation files into
  `ops.maintenance.screenshotexplanationarchive`, reducing direct root `ops`
  Java files from 1,290 to 1,280 while keeping the total ops file count stable.
  This is the first extraction outside the CodeWalkthrough family.
- Mirrored the v1797鈥搗1800 recipe: made the screenshot explanation archive
  route-path class public (with its own `BASE_PATH`), repointed the moved service
  to it, made `ENDPOINT` public, moved the package-local service/renderer/
  boundary/immutability/closeout/f-root-policy/test-support tests into the
  subpackage; the segmentation docs, controller, and route-path tests stay in
  root (controller/route-path tests construct the service directly). The route
  `/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/screenshot-explanation-archive-extraction-v1801.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1801Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1290 to 1280.

## v1800 - Code walkthrough depth registry package extraction

- Moved eight code walkthrough depth registry implementation files into
  `ops.maintenance.walkthrough.depth`, reducing direct root `ops` Java files
  from 1,298 to 1,290 while keeping the total ops file count stable. This
  completes moving all four CodeWalkthrough registry families (compliance,
  quality gate, quality audit, depth) out of the root package.
- Mirrored the v1797鈥搗1799 recipe: made the depth route-path class public (with
  its own `BASE_PATH`), repointed the moved service to it, made `ENDPOINT`
  public, moved the package-local service/renderer/boundary/test-support tests
  into the subpackage; the root controller and route-path tests construct the
  service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-depth-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/depth-registry-extraction-v1800.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1800Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1298 to 1290.

## v1799 - Code walkthrough quality audit registry package extraction

- Moved eleven code walkthrough quality audit registry implementation files into
  `ops.maintenance.walkthrough.qualityaudit`, reducing direct root `ops` Java
  files from 1,309 to 1,298 while keeping the total ops file count stable.
- Mirrored the v1797/v1798 recipe: made the quality audit route-path class public
  (with its own `BASE_PATH`) and repointed the moved service to it; made
  `ENDPOINT` public; moved the package-local service/renderer/boundary/
  immutability/closeout/test-support tests into the subpackage; the root
  controller and route-path tests construct the service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/quality-audit-registry-extraction-v1799.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1799Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1309 to 1298.
- Fixed a latent gate failure inherited from v1798: the v1798 Chinese walkthrough
  had been written after that version's verify and committed without re-running,
  leaving it below the 3000-CJK / Chinese-majority threshold enforced by
  `OpsCodeWalkthroughArchiveComplianceTests`. This version's full verify caught
  it; the v1798 walkthrough was expanded to satisfy the gate and a full
  `mvnw verify` now passes (1495 tests, JaCoCo floors met, SpotBugs/Spotless
  clean).

## v1798 - Code walkthrough quality gate registry package extraction

- Moved ten code walkthrough quality gate registry implementation files into
  `ops.maintenance.walkthrough.qualitygate`, reducing direct root `ops` Java
  files from 1,319 to 1,309 while keeping the total ops file count stable.
- Made the quality gate route-path class public (with its own `BASE_PATH`) so
  the moved service builds the endpoint from its own subpackage route-path
  class; the root `OpsShardReadinessRoutePaths` table still delegates the public
  suffix, keeping the
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry` route,
  response version, read-only runtime flags, and root controller entry point
  byte-identical.
- Moved the package-local service/renderer/boundary/immutability/test-support
  tests into the subpackage; the root controller and route-path tests construct
  the service directly and import the public route-path class (mirroring v1797).
- Added `docs/ops/quality-gate-registry-extraction-v1798.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1798Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1319 to 1309.

## v1797 - Code walkthrough compliance package extraction

- Moved eleven code walkthrough compliance implementation files into
  `ops.maintenance.walkthrough.compliance`, reducing direct root `ops` Java
  files from 1,330 to 1,319 while keeping the total ops file count stable.
- Preserved the existing
  `/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry` route,
  response version, read-only runtime flags, and root controller entry point.
- Added `docs/ops/code-walkthrough-compliance-extraction-v1797.md` plus
  extraction guard tests so future consolidation batches cannot silently move
  archives, reopen runtime boundaries, or grow the root package again.

## v1796 - Ops consolidation inventory baseline

- 鏂板 `docs/ops/ops-consolidation-inventory-v1796.md`锛岃褰?ops 鍖呭綋鍓?  1,352 涓富婧愮爜鏂囦欢銆?,330 涓牴鍖呯洿鏀炬枃浠躲€?,210 涓?Readiness 鍛藉悕鏂囦欢銆?- 鍥哄寲 route family銆乴oad-bearing archive 鍜?reduction candidate 娓呭崟锛屼负鍚庣画
  contract-preserving 鎷嗗垎鎻愪緵杈圭晫銆?- 鏂板鏂囨。瀹堝崼娴嬭瘯锛岀‘淇?J6 鐩樼偣銆佸巻鍙插綊妗ｄ笉鎼縼瑙勫垯鍜?v1796 涓嶆惉绫诲仠绾垮彲鍙戠幇銆?- 淇鏈湴 Spotless ratchet 榛樿鍩哄噯涓?`javaproject/master`锛屼笌 Java canonical
  remote 瑙勮寖涓€鑷达紱GitHub Actions 浠嶆寜 workflow 鏄惧紡鍙傛暟閫夋嫨 CI 鍩哄噯銆?
## v1795 - Production readiness documentation discipline

- 鏂板 `PRODUCTION_READINESS.md`锛岄泦涓褰曠敓浜ц竟鐣屻€佽繍琛?profile銆佹秷鎭€佹敮浠樸€?  failed-event replay銆乺elease approval rehearsal銆乧redential銆丼QL銆侀儴缃插拰鍥炴粴闄愬埗銆?- 鏂板 changelog 鐗堟湰绛栫暐锛屾槑纭?git tag `vNNNN-*` 鏄綋鍓嶆潈濞佺増鏈彿锛宲om 浠嶄繚鎸?  `0.1.0-SNAPSHOT`銆?- 鏂板鏂囨。瀹堝崼娴嬭瘯锛岄槻姝?CHANGELOG銆丳RODUCTION_READINESS 鍜?README 鎸囬拡婕傜Щ銆?
## v1794 - Production observability tracing

- 澧炲姞 Micrometer Tracing Brave bridge銆乼race/span 鏃ュ織 pattern 鍜屽紓甯稿鐞嗗櫒鏃ュ織鐩稿叧鎬с€?- 鏄庣‘ actuator 鍙毚闇?health銆乮nfo銆乵etrics锛屽苟琛ョ湡瀹?HTTP trace/span 鏃ュ織娴嬭瘯銆?
## v1793 - Production profile and request validation hardening

- 鏂板 `application-prod.yml`锛屽叧闂?H2 console 鍜?SQL debug 杈撳嚭锛屽惎鐢?graceful shutdown銆?- compose 鍑嵁鏀逛负鐜鍙橀噺瑕嗙洊锛屾柊澧?`.env.example`銆?- 璁㈠崟涓?failed-event 鍐欒姹傝ˉ鍏?Bean Validation 杈圭晫鍜?ProblemDetail 鏄犲皠銆?
## v1792 - Coverage ratchet

- 鏂板 JaCoCo 鍩虹嚎鍜?package-level coverage floors銆?- CI 涓婁紶 JaCoCo artifact锛宒ocker profile 涓嶅啀浠ｈ〃瑕嗙洊鐜囬棬銆?
## v1791 - Static analysis ratchets

- 鏂板 Maven Enforcer銆丼potless ratchet 鍜?SpotBugs baseline銆?- CI 寮€濮嬮樆鏂柊澧炴牸寮忓拰闈欐€佸垎鏋愰棶棰樸€?
## v1790 - CI bootstrap

- 鏂板 Maven wrapper銆?- Docker/Testcontainers 娴嬭瘯涓庨粯璁?headless suite 鍒嗙銆?- GitHub Actions 宸ヤ綔娴佸紑濮嬭繍琛岄粯璁?verify 鍜?docker profile verify銆?
## v1789 - Java ops governance consolidation roadmap

- 鏂板 Java ops package 鏁村悎璺嚎鍥惧拰 ratchet 鏂瑰悜銆?- 鏄庣‘涓嶅緱绉诲姩 `a/` 鍒?`f/` 鍘嗗彶褰掓。鍙?evidence JSON銆?
## v1788 - Readability upkeep audit closeout

- 瀹屾垚 readability upkeep audit closeout 璇佹嵁銆?- 璁板綍 v1784-v1788 鍙鎬т繚鍏诲懆鏈熺粨鏋溿€?
## v1787 - Readability docs guard

- 澧炲姞鍙鎬ф枃妗ｅ畧鍗紝纭繚缁存姢鍦板浘銆佸綊妗ｅ竷灞€鍜岃瑙ｈ鍒欏彲杩借釜銆?
## v1786 - Readability audit registry

- 澧炲姞 readability upkeep audit registry锛岃鍚庢湡缁存姢鍏ュ彛銆佽竟鐣屽拰娴嬭瘯璇佹嵁闆嗕腑鍙煡銆?
