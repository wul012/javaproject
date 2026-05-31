package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEvidenceCatalogHandoffServiceTests {

    @Test
    void buildsReadOnlyCatalogHandoffForBatchNodeConsumption() {
        OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse handoff =
                new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(catalogService()).handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v177");
        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.shardEnabled()).isFalse();
        assertThat(handoff.sourceCatalogVersion()).isEqualTo("Java v175");
        assertThat(handoff.sourceCatalogReceiptId())
                .isEqualTo("java-shard-readiness-read-only-evidence-catalog-receipt-v175");
        assertThat(handoff.sourceCatalogEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/read-only-evidence-catalog");
        assertThat(handoff.sourceCatalogFixtureEndpoint())
                .isEqualTo("/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json");
        assertThat(handoff.sourceCatalogLiveEndpointCount()).isEqualTo(20);
        assertThat(handoff.sourceCatalogFixtureEndpointCount()).isEqualTo(20);
        assertThat(handoff.sourceCatalogFrozen()).isTrue();
        assertThat(handoff.readyForBatchNodeConsumption()).isTrue();
        assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(handoff.writeRoutingAllowed()).isFalse();
        assertThat(handoff.activeShardRouterAllowed()).isFalse();
        assertThat(handoff.credentialValueRead()).isFalse();
        assertThat(handoff.rawEndpointParsed()).isFalse();
        assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
        assertThat(handoff.deploymentAllowed()).isFalse();
        assertThat(handoff.rollbackAllowed()).isFalse();
        assertThat(handoff.handoffProfile())
                .isEqualTo("java-shard-readiness-read-only-evidence-catalog-handoff.v1");
        assertThat(handoff.receiptId())
                .isEqualTo("java-shard-readiness-read-only-evidence-catalog-handoff-receipt-v177");
        assertThat(handoff.handoffArtifacts())
                .contains(
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "e/176/evidence/java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json"
                );
        assertThat(handoff.consumerRules())
                .contains(
                        "consume-versioned-fixtures-before-live-probes",
                        "node-must-not-start-or-stop-java-or-mini-kv",
                        "node-must-fail-closed-if-catalog-status-is-not-passed"
                );
        assertThat(handoff.failClosedChecks())
                .contains(
                        "source-catalog-status-must-be-passed:passed",
                        "source-catalog-live-endpoint-count:20",
                        "source-catalog-fixture-endpoint-count:20"
                );
        assertThat(handoff.blockedOperations())
                .contains(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(handoff.evidencePath())
                .isEqualTo("e/177/evidence/java-shard-readiness-read-only-evidence-catalog-handoff-v177.json");
        assertThat(handoff.status()).isEqualTo("passed");
    }

    private OpsShardReadinessReadOnlyEvidenceCatalogService catalogService() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffService handoffService =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
        OpsShardReadinessEchoService echoService = new OpsShardReadinessEchoService(
                new OpsShardReadinessService(),
                new OpsShardReadinessHardeningService(),
                indexService,
                handoffService
        );
        return new OpsShardReadinessReadOnlyEvidenceCatalogService(
                echoService,
                new OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService(liveReadGateService(handoffService))
        );
    }

    private OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService(
            OpsShardReadinessEvidenceHandoffService handoffService
    ) {
        OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
                new OpsShardReadinessActiveShardPlanHandoffService(handoffService);
        OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService =
                new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService);
        OpsShardReadinessOperatorServiceLifecycleService operatorLifecycleService =
                new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService);
        OpsShardReadinessDeclaredOperatorLifecycleService declaredLifecycleService =
                new OpsShardReadinessDeclaredOperatorLifecycleService(operatorLifecycleService);
        OpsShardReadinessRuntimeExecutionArtifactCandidateService artifactCandidateService =
                new OpsShardReadinessRuntimeExecutionArtifactCandidateService(declaredLifecycleService);
        OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService =
                new OpsShardReadinessRuntimeExecutionPacketContributionService(artifactCandidateService);
        OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService =
                new OpsShardReadinessRuntimeExecutionApprovalGateInputService(packetContributionService);
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService =
                new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(approvalGateInputService);
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService templateCompatibilityService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(contractHandoffService);
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService compatibilityIntakeService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                        templateCompatibilityService
                );
        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService valueValidationService =
                new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(compatibilityIntakeService);
        return new OpsShardReadinessRuntimeExecutionLiveReadGateService(valueValidationService);
    }
}
