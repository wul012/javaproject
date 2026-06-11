> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第六十六版代码讲解：release approval rehearsal 只读聚合入口

## 本版角色

v66 按最新计划 `D:\nodeproj\orderops-node\docs\plans\v182-post-rehearsal-quality-roadmap.md` 推进 Java 侧真实运行纵深准备。它不再横向新增静态 contract fixture，而是在现有 ops evidence 基础上增加一个动态只读聚合入口：

```text
GET /api/v1/ops/release-approval-rehearsal
```

这个入口给后续 Node v185 real-read rehearsal intake 使用，帮助 Node 一次读取 Java 的 release approval rehearsal 输入、live replay/outbox 信号和执行禁止边界。

## 项目进度

v65 已经把 rollback approver evidence 固化成只读证据。v66 往前走一步：不再只给单份证据，而是把 release operator signoff、rollback approver evidence、rollback approval record、release bundle、verification manifest、deployment rollback、production runbook、secret source 和 rollback SQL review gate 收拢成一个 live 聚合响应。

这让 Java 从“提供证据碎片”推进到“提供只读演练视图”，但仍保持订单交易核心的边界：

```text
不创建 approval decision
不写 approval ledger
不执行 deployment
不执行 rollback
不执行 rollback SQL
不连接生产数据库
不读取生产密钥
```

## 核心流程

```text
Node / operator
 -> GET /api/v1/ops/release-approval-rehearsal
 -> OpsOverviewController.releaseApprovalRehearsal()
 -> OpsEvidenceService.releaseApprovalRehearsal()
 -> 复用 evidence()
 -> 聚合 releaseApprovalInputs、liveSignals、executionBoundaries、blockers、nextEvidenceActions
```

## 多代码引用讲解

### 1. Controller 只暴露 GET

[OpsOverviewController.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java:29) 新增端点：

```java
@GetMapping("/release-approval-rehearsal")
public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal() {
    return opsEvidenceService.releaseApprovalRehearsal();
}
```

这里没有 POST、PUT 或任何命令式动作，所以入口天然只读。

### 2. Response 明确拆成三组

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:6) 定义顶层响应：

```java
public record ReleaseApprovalRehearsalResponse(
        Instant sampledAt,
        String rehearsalVersion,
        String sourceEvidenceEndpoint,
        String rehearsalMode,
        boolean readOnly,
        boolean executionAllowed,
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
)
```

这个 record 的重点是把字段分层：`releaseApprovalInputs` 是输入证据，`liveSignals` 是当前运行信号，`executionBoundaries` 是禁止执行边界。这样比把所有字段平铺到 ops evidence 里更容易被 Node 消费。

### 3. Service 复用现有 evidence，避免重复查询逻辑

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:167) 的聚合方法先调用 `evidence()`：

```java
@Transactional(readOnly = true)
public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal() {
    OpsEvidenceResponse evidence = evidence();
    return new ReleaseApprovalRehearsalResponse(
            evidence.sampledAt(),
            RELEASE_APPROVAL_REHEARSAL_VERSION,
            "/api/v1/ops/evidence",
            "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
            true,
            false,
            releaseApprovalInputs(evidence),
            liveSignals(evidence),
            executionBoundaries(),
            releaseApprovalRehearsalBlockers(evidence),
            evidence.readOnlyWindow().requiredNodeEnvironment(),
            releaseApprovalNextEvidenceActions()
    );
}
```

这是一处质量优化：v66 没有复制 failed event/outbox/contract 组装逻辑，而是复用 `OpsEvidenceResponse`，减少后续两个视图漂移的风险。

### 4. releaseApprovalInputs 只收证据 endpoint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:874) 将现有证据 endpoint 收成 Node 可读输入：

```java
return new ReleaseApprovalRehearsalResponse.ReleaseApprovalInputs(
        evidence.releaseOperatorSignoffFixture().fixtureEndpoint(),
        evidence.rollbackApproverEvidenceFixture().fixtureEndpoint(),
        evidence.rollbackApprovalRecordFixture().fixtureEndpoint(),
        evidence.releaseBundle().manifestEndpoint(),
        evidence.releaseVerification().manifestEndpoint(),
        evidence.deploymentRollback().evidenceEndpoint(),
        evidence.productionDeploymentRunbookContract().contractEndpoint(),
        evidence.productionSecretSourceContract().contractEndpoint(),
        evidence.rollbackSqlReviewGate().gateEndpoint(),
        List.of(...)
);
```

这里故意只引用 endpoint 和证据字段，不读取 secret value，也不触发任何 approval/deployment/rollback。

### 5. liveSignals 提供真实运行纵深

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:901) 从 live evidence 里取当前信号：

```java
return new ReleaseApprovalRehearsalResponse.LiveSignals(
        evidence.failedEventReplay().pendingReplayApprovals(),
        evidence.failedEventReplay().approvedReplayApprovals(),
        evidence.failedEventReplay().rejectedReplayApprovals(),
        evidence.failedEventReplay().replayBacklog(),
        evidence.outbox().pendingEvents(),
        evidence.failedEventReplay().realReplayAllowedByEvidence(),
        evidence.approvalExecution().dryRun(),
        evidence.executionAllowed()
);
```

这就是 v66 相比 v65 的进步：它不只是静态说明边界，还能把当前失败事件审批、重放积压和 outbox 积压带给控制面。

### 6. executionBoundaries 强制保持 no-execution

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:914) 将所有写能力固定为 false：

```java
return new ReleaseApprovalRehearsalResponse.ExecutionBoundaries(
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
);
```

唯一为 true 的是 `nodeMayConsume`，表示 Node 可以读取。创建 approval decision、写 ledger、部署、回滚、执行 SQL、接触生产库和生产密钥都不允许。

### 7. 测试锁定聚合语义

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:778) 直接验证 service 级响应：

```java
ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
assertThat(rehearsal.rehearsalVersion()).isEqualTo("java-release-approval-rehearsal.v1");
assertThat(rehearsal.executionAllowed()).isFalse();
assertThat(rehearsal.liveSignals().pendingReplayApprovals()).isEqualTo(2);
assertThat(rehearsal.executionBoundaries().nodeMayCreateApprovalDecision()).isFalse();
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:845) 再从 MVC 层验证真实 GET 输出，确保 JSON 字段能被 Node 直接消费。

## 验证与归档

本版验证重点：

```text
mvn -Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests test
后续继续运行 non-Docker regression、package、HTTP smoke
```

运行记录、截图和清理记录写入：

```text
D:\javaproj\advanced-order-platform\c\66\解释\说明.md
D:\javaproj\advanced-order-platform\c\66\图片
```

## 成熟度变化

v66 后，Java 侧对 Node 的支持从“静态证据样本集合”提升到“只读演练聚合视图”。这更接近真实生产前的控制面联调方式，同时没有扩大 Java 的执行风险面。

## 一句话总结

v66 增加了 release approval rehearsal 的动态只读聚合入口，让 Node 后续能读取更接近真实运行的 Java evidence，但仍不授权审批、ledger、部署、回滚、SQL、生产库或密钥访问。
