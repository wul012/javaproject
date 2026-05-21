package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsOverviewStaticReadOnlyEvidenceIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void staticOpsReadOnlyEvidenceSampleCoversProductionPassBoundary() throws Exception {
        mockMvc.perform(get("/contracts/ops-read-only-evidence.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("OPS_READ_ONLY_EVIDENCE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.service.name").value("advanced-order-platform"))
                .andExpect(jsonPath("$.healthProbe.endpoint").value("/actuator/health"))
                .andExpect(jsonPath("$.healthProbe.expectedStatus").value("UP"))
                .andExpect(jsonPath("$.healthProbe.liveProbeRequiredForPass").value(true))
                .andExpect(jsonPath("$.healthProbe.staticSampleOnly").value(true))
                .andExpect(jsonPath("$.failedEventReplay.realReplayAllowedByEvidence").value(false))
                .andExpect(jsonPath("$.failedEventReplay.realReplayEndpoint")
                        .value("/api/v1/failed-events/{id}/replay"))
                .andExpect(jsonPath("$.outbox.publisherEnabled").value(false))
                .andExpect(jsonPath("$.outbox.rabbitMqEnabled").value(false))
                .andExpect(jsonPath("$.approvalExecution.dryRun").value(true))
                .andExpect(jsonPath("$.approvalExecution.executionBlockers",
                        hasItem("READ_ONLY_EVIDENCE_ENDPOINT")))
                .andExpect(jsonPath("$.readOnlyWindow.windowVersion").value("java-read-only-window.v1"))
                .andExpect(jsonPath("$.readOnlyWindow.nodeAutoStartAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.readyForReadOnlyLiveProbe").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.readyForProductionOperations").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints", hasItem("GET /actuator/health")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")))
                .andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
                        hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.orderIdempotency.boundaryVersion")
                        .value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.orderIdempotency.storeAbstractionVersion")
                        .value("java-idempotency-store.v1"))
                .andExpect(jsonPath("$.orderIdempotency.sameKeyDifferentRequestErrorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.orderIdempotency.activeStore").value("jpa-order-idempotency-store"))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[*].name",
                        hasItem("mini-kv-ttl-token-adapter")))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[1].enabled").value(false))
                .andExpect(jsonPath("$.orderIdempotency.miniKvConnected").value(false))
                .andExpect(jsonPath("$.releaseVerification.manifestVersion")
                        .value("java-release-verification-manifest.v1"))
                .andExpect(jsonPath("$.releaseVerification.requiredChecks",
                        hasItem("http-smoke")))
                .andExpect(jsonPath("$.releaseVerification.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.deploymentRollback.evidenceVersion")
                        .value("java-deployment-rollback-evidence.v1"))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.deploymentRollback.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.deploymentRollback.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.deploymentRollback.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseBundle.manifestVersion")
                        .value("java-release-bundle-manifest.v1"))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.releaseBundle.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.fixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportReadOnly").value(true))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiredSignoffFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.signoffArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureVersion")
                        .value("java-rollback-approver-evidence-fixture.v1"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffVersion")
                        .value("java-rollback-approval-handoff.v1"))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.gateVersion")
                        .value("java-rollback-sql-review-gate.v1"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.contractVersion")
                        .value("java-production-secret-source-contract.v1"))
                .andExpect(jsonPath("$.productionSecretSourceContract.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.contractVersion")
                        .value("java-production-deployment-runbook-contract.v1"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.warnings", hasItem("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/evidence")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/ops-evidence-field-guide.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.productionPassBoundary.readyForProductionPassEvidence").value(false))
                .andExpect(jsonPath("$.productionPassBoundary.allowedProbeEndpoints",
                        hasItem("GET /api/v1/ops/evidence")))
                .andExpect(jsonPath("$.productionPassBoundary.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")));

}
}
