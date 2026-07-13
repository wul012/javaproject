# Java v1837 ReleaseApproval 验证组合拆分

## 目标与边界

本版继续暂停新功能，处理 ReleaseApproval 只读证据构建链中的参数爆炸。旧 `ReleaseApprovalVerificationHintBuilder` 构造器接收 34 个阶段 builder，`build` 又接收七个基础提示、几十个阶段 receipt、失败分类和执行边界；调用方明明已经持有完整 `ReceiptChain`，却先逐项拆开再传回。这样的代码能运行，但字段错位风险高，审查者难以确认摘要与证明使用的是同一批阶段对象。

本版不改变 `/api/v1/ops` 路由，不改变 `RehearsalVerificationHint` 的字段、schema version、hint version、warning digest 算法、输入顺序、proof claim 文本、Node 验证动作、只读布尔值或任何冻结 fixture。唯一变化是内部组合方式：用一个包内 context 聚合基础提示、权威 `ReceiptChain`、失败分类和执行边界；贡献目录、摘要构建器和无账本写证明器从同一个链读取数据。

## 需求—实现—证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 只保留一个阶段顺序来源 | `ReleaseApprovalVerificationHintContext` 持有原 `ReceiptChain`，不复制阶段清单 | 结构门禁止响应构建器逐项读取 builder | 聚焦门通过 |
| 消除构造器参数爆炸 | 提示构建器无显式构造参数；摘要构建器只接收一个 `ReceiptChain` | 反射断言构造器参数数与 build 参数数 | 通过 |
| 无写证明职责独立 | `ReleaseApprovalNoLedgerWriteProofEvaluator` 原样承接完整布尔链 | 默认与 header-backed 精确回归、结构门 | 通过 |
| 摘要输入和顺序不变 | 摘要主体保留，入口只把 context/chain 解包为原变量名 | 854 行精确列表测试、header-backed 测试 | 通过 |
| 贡献顺序不变 | ContributionCatalog 改为接收 chain，原 `List.of` 次序未动 | 首项、末项、数量 34 与动作文本测试 | 通过 |
| 真实响应不变 | ResponseBuilder 只创建 context，再调用提示构建器 | 两组 Spring 集成测试 | 通过 |
| 三个热点实质退出 | 874/675/564→70/421/421，合并 support 412 | 正式 StreamReader/JUnit 行数口径 | 通过 |
| 聚合预算净下降 | >500 38→35，>750 4→3，>1000 保持 2 | 普查脚本与维护预算门 | 通过 |
| ops 文件数不增长 | context 与 hint 同文件，evaluator 与 contribution 同 support 文件 | root 805、remaining 700、unassigned 0 | 通过 |

## 组合结构

`ReleaseApprovalVerificationHintContext` 是十个分量的包内 record：七个基础提示、一个 `ReceiptChain`、失败分类和执行边界。它与提示构建器同在一个 70 行文件中，不增加 root ops 文件；它不是响应 DTO，不参与 Jackson schema，也不跨包公开。record 的价值是让每个参数有稳定名字，并让编译器在新增、遗漏或误排字段时直接报错。

`ReleaseApprovalVerificationHintBuilder` 现在只编排四件事：从 chain 构建有序 contribution 列表；调用摘要构建器；调用无写证明器；按原顺序组装 schemaFields、warningDigestInputs、proofClaims 和 nodeVerificationActions。它不再持有 34 个字段，也不再包含数百行安全布尔表达式。

`ReleaseApprovalVerificationWarningDigestBuilder` 仍保留原摘要行主体和 `ReleaseApprovalDigestSupport.digest`。构造器只接收 chain，并用 chain 中原有 builder 创建 line catalog 的 Builders；`build` 只接收 context，在入口将 context 与 chain accessor 绑定到原变量名，之后的行列表保持原序。这样 diff 能清楚区分“输入来源改变”和“摘要算法改变”，本版只发生前者。

`ReleaseApprovalNoLedgerWriteProofEvaluator` 负责完整的只读/无写证明。它与 27 行 contribution record 合并在 412 行 `ReleaseApprovalVerificationSupport.java` 中，替代原 contribution 文件，因此也不增加 root/total ops 文件。它检查基础提示没有写审批账本、没有上传或访问 CI 工件、没有启动 Node 进程；检查 managed audit、SQL、部署、回滚、恢复、凭据读取与外部连接均未执行；最后逐阶段调用既有 builder 的 no-write 方法。表达式顺序与旧方法一致，只是从主构建器搬到职责明确的包内类型。

## 预算结果

正式普查报告 1483 个生产 Java 文件，与 v1836 总数持平，最大文件仍为 1530 行。超过 500、750、1000 行的文件数为 35、3、2；相对 v1836 分别净下降 3、1、0。测试文件当前为 834 个，测试热点预算保持 8、2、0，没有为了验证本次重构而制造新的测试巨型文件。

单文件预算固定为：提示/context 文件 70、摘要构建器 421、响应构建器 421、verification support 412、贡献目录 382。旧提示构建器 874 行的上限被 70 替代，不能回升；摘要、响应和 support 也进入命名预算。

## 验证策略

第一层是编译与反射结构门。编译器确认 context 的十个分量、ReceiptChain accessor 与嵌套类型全部匹配；反射确认提示、摘要和证明入口都只有一个 context 参数，摘要构造器只有一个 chain 参数。源码结构门进一步禁止主提示构建器出现具体 credential-resolver 阶段名，禁止响应构建器重新逐项读取 builder。

第二层是精确内容回归。v1867 将原巨型测试按职责拆为 `RehearsalHintDigestTests` 与 `RehearsalHintContractTests`：前者锁定 schemaFields、warningDigestInputs、proofClaims 和 nodeVerificationActions 的长列表顺序，后者覆盖 header-backed 变体与贡献目录的 34 个贡献、首项、末项和过滤规则。第三层是真实 Spring 集成，验证只读证据 HTTP 聚合仍可启动 Flyway、JPA 和 Web 上下文并输出完整响应。

第四层是维护预算、Spotless、SpotBugs、完整 Maven verify、JaCoCo 与远端 CI。第一次完整 verify 运行了 1692 个测试，其中 23 个历史 readability/census 测试因两个新增 root 文件而失败；业务、响应和编译没有错误。本版没有把 root 上限 805 改成 807，而是把 context 合入提示文件、把 evaluator 与 contribution 合成 support 文件。随后 `ops-root-census.ps1 -Json` 恢复 direct-root 805、remaining 700、unassigned 0，全部 23 个受影响历史类与新门聚焦重跑通过。最终完整 verify 仍需重新执行。

## 失败条件

- 任一响应字段、schema/hint version、digest 行、列表次序、proof 文本或 Node 动作改变，版本失败。
- 为让重构通过而修改现有期望、fixture 字节或放宽精确列表断言，版本失败。
- 新建第二份阶段列表，导致 ReceiptChain 不再是唯一顺序来源，版本失败。
- 提示、摘要或响应构建器任一仍超过 500 行，或者新证明器超过 500 行，版本失败。
- >500、>750、>1000 聚合预算或任一命名单文件上限回升，版本失败。
- 中文讲解晚于最终 verify，或提交、tag、push、远端 CI、清理任一缺失，版本不算完成。

## 当前证据

生产编译通过；默认、header-backed、沙箱连接提示的精确回归通过；ContributionCatalog、参数结构门、维护预算、两组 Spring 集成、Spotless 和 SpotBugs 通过。第一次完整 verify 暴露的 root 文件数问题已通过零净增长合并解决，相关 23 个历史类全部重跑通过。修正后的完整 `mvnw verify` 用时 10 分 01 秒：1692 个测试，0 失败、0 错误、0 跳过；JaCoCo 全部覆盖率门达标；SpotBugs `BugInstance=0`、`Error=0`；BUILD SUCCESS。提交、tag、push 和远端 CI 尚待完成，因此不提前宣称版本完成。

远端 GitHub Actions run `29069744872` 对实现提交 `c3574002` 独立复现成功：`Build and headless regression` 用时 17 分 42 秒，完成 Spotless ratchet、无 Docker 全量 verify、生产配置启动冒烟与 JaCoCo 报告上传；`Docker-tagged integration tests` 用时 1 分 54 秒并通过。本版由 tag `v1837-order-platform-production-excellence-release-approval-verification-composition-split` 固定证据边界。
