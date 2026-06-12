# 第一千七百八十九版代码讲解：Java ops 治理收敛路线与文件数门禁

本版是根据 Claude 对四项目的调查意见做出的 Java 侧响应。Claude 指出 Node 正在做 v2114 governance consolidation，Java 和 mini-kv 可以并行做内部保养，但必须保护历史 archive 和 evidence 路径，因为 Node 已经硬编码了大量 Java、mini-kv 归档引用和内容摘要。结合本项目自己的现状，Java 最需要先做的不是立刻搬动一千多个旧类，而是建立一个可执行、可测试、可回滚的 ops governance consolidation roadmap 和 Codex playbook，并把当前文件数量锁成 ratchet，防止根包继续膨胀。

本版只做 Java 本项目。没有修改 Node，没有修改 mini-kv，没有移动 `a` 到 `f` 目录，没有移动 `e/<version>`，没有重命名 evidence JSON，没有新增运行时路由，没有改业务接口。实际产物是两个计划文档、一个 ops README 指针、一个 Java 测试门禁、一个新讲解批次目录和本篇讲解。它的价值是把“Java ops 根包太大”从口头判断变成项目内可执行规则：先测量，先立边界，先锁增长，再分批处理。

## 入口路由

v1789 不新增 HTTP 入口。当前可读性保养相关入口仍然是上一批已经收口的 `/api/v1/ops/readability/upkeep-registry` 和 `/api/v1/ops/readability/upkeep-audit`。本版新增的是文档入口：`docs/ops/README.md` 增加 active consolidation plan 段落，指向 `docs/plans/v1789-java-ops-governance-consolidation-roadmap.md` 和 `docs/plans/v1789-codex-ops-migration-playbook.md`。维护者从 ops 文档入口就能看到当前 Java 侧对 Node v2114 调查结果的响应。

不新增路由是有意的。Claude 的建议不是要 Java 立刻再开一个 readiness endpoint，而是提醒 Java 自己也有治理递归和根包膨胀问题。如果此时再新增一个运行时入口，反而会继续扩大 ops 表面。v1789 选择把入口放在文档和测试层，说明这是 consolidation baseline，不是新功能链。

`docs/ops/README.md` 里的新段落还写明 contract-preserving 和 archive-preserving。它明确禁止移动 `a/` 到 `f/`、`e/<version>/` 和 evidence JSON。这个入口非常重要，因为 Java 的本地 AGENTS 规则要求从 Node-driven roadmap 推进时读取 Node plan；现在 Java 自己也有了对应指针，后续不会只靠聊天记录记忆。

## 响应模型

本版不新增响应模型，也不修改上一批的 audit response。原因很简单：当前任务是建立收敛计划和门禁，而不是向外提供新的 JSON 契约。`ReadabilityUpkeepAuditResponse` 已经能表达可读性保养审计状态，v1789 不需要把 roadmap 文档塞进运行时响应。

这个选择能避免 schema 漂移。Java ops 包已经有大量历史 response，如果每次计划或文档变化都新增一个 response，根包会继续变重。v1789 的响应策略是“不为计划新增运行时模型”。计划用 Markdown 承载，门禁用测试承载，运行时接口保持稳定。

不过，本版确实新增了测试模型意义上的门禁：`ReadabilityUpkeepGovernanceConsolidationPlanTests` 读取计划文档、playbook、README 和 ops 源码目录，断言计划可发现、archive 禁令存在、文件数没有超过当前基线。这个测试不是 API response，但它是工程响应。它把 Claude 的判断变成本项目 CI 可以执行的检查。

## 上游证据配置

本版上游证据有三类。第一类是 Node v2114 指针和 playbook。指针明确说 Java 和 mini-kv 可以并行推进，但不能移动归档目录和 evidence JSON，因为 Node 硬编码了大量绝对路径和摘要。第二类是四项目协调规则，它要求区分 contract/evidence chain 和 live runtime integration，也强调重构要保守、要保护证据链。第三类是 Java 本项目测量结果：主源码 ops 目录共有一千三百五十二个 Java 文件，根包直接一千三百三十个，Readiness 命名一千二百一十个。

这些证据被写进 `v1789-java-ops-governance-consolidation-roadmap.md`。路线文档记录测量时间、当前 HEAD、问题陈述、不可移动归档规则、必要性证明、范围、版本切片、跨项目并行声明和 enforcement。它明确说旧 ops evidence 并非无效，问题是新增维护工作不能继续向根包堆 readiness 类。

`v1789-codex-ops-migration-playbook.md` 则把路线转成操作规则：不要移动归档，不改路由，不改响应字段，不新增 echo、verification、readiness 或 closure 链，不使用 `git fetch --all --tags`，需要 fetch 时使用 Java canonical remote。它还规定先跑 focused gate，再做 inventory，再做 contract-preserving batches，最后 closeout。这样后续 Codex 不会一上来就大规模移动旧类。

## 服务层核心流程

本版没有新增服务层流程。它新增的是服务层之外的治理流程：先验证基线，再盘点旧根包，再挑选安全小批次，再保持合同不变，再跑 focused tests 和 full tests。这个流程写在 playbook 中，并由测试检查 playbook 存在和关键规则存在。

新增的测试可以看成“治理服务”的核心逻辑。它先检查计划和 playbook 可发现，确认 README 指向它们，确认文档包含 Node v2114、不可移动归档、Java 并行推进等关键字。然后它统计 ops 主源码文件数、根包直下文件数和 Readiness 命名文件数，要求分别不超过一千三百五十二、一千三百三十、一千二百一十。最后它检查 `a` 到 `f` 目录仍然存在，并且路线和 playbook 都写明不可移动归档和 evidence JSON。

这个流程不是为了永久冻结 Java 项目，而是阻止无意识增长。以后如果确实需要超过基线，必须有新的路线和用户明确同意；默认修复方式应该是收敛、迁移或减少，而不是调高上限。Node v2114 的 file-count ratchet 给了模板，v1789 把同样思路移植到 Java。

## Java 证据检查

Java 证据第一层是新增 `docs/plans`。本项目之前只有 `docs/ops`，没有 plans 目录。v1789 新增计划目录，是为了把路线和执行手册与普通 ops map 分开。`docs/ops` 仍然是阅读入口，`docs/plans` 承载活动计划。这个拆分避免 README 越写越长，也让后续计划文档有固定位置。

第二层是 `ReadabilityUpkeepGovernanceConsolidationPlanTests`。它比纯文档更硬：如果有人删掉 roadmap、删掉 playbook、删掉 README 指针、删掉 archive 禁令，测试会失败。如果有人继续往主源码 ops 包加文件，导致总数、根包数或 Readiness 数超过基线，测试会失败。这就是本版最关键的可执行证据。

第三层是当前讲解批次目录。`代码讲解记录_生产雏形阶段4/v1789-v1793/README.md` 说明本批承接 Claude 指出的 Java ops 根包膨胀问题，只做 Java 本项目，优先建立 roadmap、playbook、ratchet、inventory 和 archive-safe consolidation 规则。这样讲解归档不会和上一批混在一起，也符合用户要求的分目录归档习惯。

## mini-kv 证据检查

mini-kv 在本版只作为跨项目边界被提及，不作为输入被读取或修改。Node v2114 指针同时警告 Java 和 mini-kv 的 archive 路径不能移动，但 v1789 不对 mini-kv 做任何实际操作。Java 侧文档只是把这一点写成 archive-preserving hard rule，避免未来 Java 整理时误伤 Node 对 mini-kv 或 Java 的引用关系。

这种处理符合四项目协调规则。当前任务是 Java 自己的 ops consolidation baseline，不是四项目联合迁移。mini-kv 如果要拆两个大文件或清理一千多兆归档，需要 mini-kv 自己的 roadmap 和 playbook。Java 这边不能越界替它做。

本版也没有启动 mini-kv，没有读取 mini-kv fixture，没有改变 C++ 项目。测试只在 Java 仓库内部统计 Java 源码和 Java archive 目录。这样 CI 仍然可以独立运行，不依赖用户本机其他项目。

## 阻断与安全边界

v1789 阻断的第一类风险是 archive 移动。路线、playbook、README 和测试都反复写明不要移动 `a/` 到 `f/`、`e/<version>/` 或 evidence JSON。这不是保守过头，而是因为 Node 已经消费这些路径和内容摘要。任何移动都可能让 Node 测试和历史证据解析失败。

第二类风险是 contract 改动。Playbook 明确不改 route paths、response fields、public endpoint behavior 或 existing test expectations。未来 consolidation batches 如果要改这些，必须另起明确计划。当前阶段只允许 contract-preserving 重构和文档/测试加固。

第三类风险是继续新增治理链。Playbook 禁止在 plan active 时新增 echo、verification、readiness 或 closure 链。Java ops 根包已经太大，继续添加新链只会让问题更糟。后续如果需要新能力，先证明必要性，再考虑是否关闭当前 consolidation plan。

## 测试覆盖

本版新增的测试类覆盖三个方面。第一个测试检查 Java ops consolidation plan 和 Codex playbook 可发现，并检查 README 指针、Node v2114、archive 禁令、canonical fetch 命令和 CI closeout 字样。第二个测试统计 ops 主源码文件数、根包文件数和 Readiness 文件数，建立不增长的 ratchet。第三个测试检查 `a` 到 `f` 目录存在，并检查 roadmap/playbook 都保留 archive 和 evidence JSON 禁令。

定向验证命令是 `mvn -q "-Dtest=ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`。这条命令同时覆盖新计划门禁、上一批 readability upkeep 测试和中文讲解合规。由于本版只新增文档和测试，不改运行时控制器，focused gate 足以证明主要行为；最终推送前仍应根据批次范围决定是否跑全量。

本篇讲解也受 `OpsCodeWalkthroughArchiveComplianceTests` 约束。它必须包含标准标题、中文为主、至少三千汉字、实际工作量说明、本项目和禁止硬凑。这个门禁继续保证讲解不是随手短记，而是和版本产物一起可审查。

## 实际工作量说明

v1789 的实际工作量包括读取 Node v2114 指针、读取 Node roadmap 和 playbook、读取四项目协调规则、测量 Java ops 主源码规模、新增 Java ops consolidation roadmap、新增 Codex migration playbook、更新 docs/ops README、添加文件数 ratchet 测试、添加 archive-preservation 测试、新增 v1789-v1793 讲解批次目录，并编写本版中文讲解。它没有做运行时功能，但它给后续安全重构建立了门禁。

这不是硬凑。用户要求以后每版中文讲解至少三千字，字数不够就加大真实工作量，禁止硬凑。本版的真实工作量在于把 Claude 的调查结果转成 Java 项目内部可执行规则。没有这个版本，后续任何“整理 ops 包”的动作都容易变成大规模冒险移动；有了这个版本，后续必须先过 ratchet、先保护 archive、先保持 contract。

本项目最大的收益是从“知道 ops 很大”变成“有测试阻止它继续变大”。单纯说 ops 根包有一千多个类没有用，因为下一版仍然可能继续添加。Ratchet 的意义是把默认方向反过来：以后不能自然增长，除非有新的明确计划。这个转向比一次性改很多类更重要。

另一个收益是跨项目边界清楚。Node v2114 计划允许 Java 并行，但明确警告不要移动 Java 和 mini-kv 的归档路径。v1789 把这条警告写入 Java 文档和测试，后续 Java agent 不需要每次重新问 Node。只要跑测试，就能看到 archive rule 仍然存在。

本版也没有被 Claude 的“Java ops 很大”吓到而立刻做激进重构。大量旧类承载历史接口、测试和证据路径，直接移动会破坏可追溯性。正确路线是先建立 inventory，再挑安全 cluster，再小批量 contract-preserving consolidation。v1789 只完成第一步之前的计划和门禁，正是为了后续不乱。

`docs/plans` 的出现也是维护收益。过去 docs/ops 更像主题地图，适合读当前证据；plans 更适合承载活动路线和执行手册。把两者分开后，后续可以在 plans 里更新进度，不必把所有流程都塞进 ops README。README 只保留指针，读者需要细节再进入 plan。

本版还把 Java canonical remote 规范写进 playbook。用户之前已经明确 Java 不应再用 `git fetch --all --tags`，应使用 `git fetch javaproject --tags --prune`。把这条写进 Codex playbook，可以减少后续误操作，尤其是在跨项目计划容易混淆 remote 时。

最后，v1789 为后续版本提供清晰承接。v1790 可以做 root ops inventory，不移动代码；v1791 之后才能小批量处理安全 cluster；最终版本再做全量验证和是否进入更深迁移的判断。这样连续推进时，每一步都有边界和证据，不会又回到无限新增 readiness 链的循环。

再补充一点：本版并没有否认历史 ops 证据的价值。大量旧类虽然难读，但它们记录了长期推进中的接口、审批、边界、交接和归档事实，不能因为目录拥挤就粗暴删除。真正要改的是新增和后续维护的方式。先让增长停止，再做盘点，再做小批量收敛，才是对历史负责的重构路线。

## 一句话总结

v1789 根据 Claude 的四项目调查，把 Java ops 根包膨胀问题转成项目内 roadmap、Codex playbook、README 指针、文件数 ratchet、archive-preservation 测试和中文讲解，为后续安全收敛建立基线，而不移动历史归档、不改运行时契约、不触碰其他项目。
