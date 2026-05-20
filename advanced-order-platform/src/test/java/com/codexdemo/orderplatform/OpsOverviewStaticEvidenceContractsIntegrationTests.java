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
class OpsOverviewStaticEvidenceContractsIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void staticOpsEvidenceFieldGuideExplainsReadOnlyCaptureFields() throws Exception {
        mockMvc.perform(get("/contracts/ops-evidence-field-guide.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideVersion").value("java-ops-evidence-field-guide.v1"))
                .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("OPS_EVIDENCE_FIELD_GUIDE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.sourceSampleEndpoint")
                        .value("/contracts/ops-read-only-evidence.sample.json"))
                .andExpect(jsonPath("$.releaseReviewUse.intendedConsumer")
                        .value("Node read-only capture release evidence review"))
                .andExpect(jsonPath("$.releaseReviewUse.mayBeUsedForProductionPass").value(false))
                .andExpect(jsonPath("$.releaseReviewUse.requiredLiveEvidence",
                        hasItem("GET /actuator/health returns UP")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("service")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("healthProbe")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("readOnlyWindow")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("orderIdempotency")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("executionBoundaries")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseVerification")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("deploymentRollback")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseBundle")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseHandoffChecklistFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseAuditRetentionFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseOperatorSignoffFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApproverEvidenceFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApprovalHandoff")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApprovalRecordFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackSqlReviewGate")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("productionSecretSourceContract")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("productionDeploymentRunbookContract")))
                .andExpect(jsonPath("$.fieldGroups[1].fields[*].path",
                        hasItem("healthProbe.staticSampleOnly")))
                .andExpect(jsonPath("$.fieldGroups[2].fields[*].path",
                        hasItem("readOnlyWindow.readyForReadOnlyLiveProbe")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.sameKeyDifferentRequestErrorCode")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.storeAbstractionVersion")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.storeCandidates")))
                .andExpect(jsonPath("$.fieldGroups[4].fields[*].path",
                        hasItem("failedEventReplay.realReplayAllowedByEvidence")))
                .andExpect(jsonPath("$.fieldGroups[5].fields[*].path",
                        hasItem("releaseVerification.manifestVersion")))
                .andExpect(jsonPath("$.fieldGroups[6].fields[*].path",
                        hasItem("deploymentRollback.evidenceVersion")))
                .andExpect(jsonPath("$.fieldGroups[6].fields[*].path",
                        hasItem("deploymentRollback.databaseMigrationRollbackAutomatic")))
                .andExpect(jsonPath("$.fieldGroups[7].fields[*].path",
                        hasItem("releaseBundle.manifestVersion")))
                .andExpect(jsonPath("$.fieldGroups[7].fields[*].path",
                        hasItem("releaseBundle.nodeMayTriggerRollback")))
                .andExpect(jsonPath("$.fieldGroups[8].fields[*].path",
                        hasItem("releaseHandoffChecklistFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[8].fields[*].path",
                        hasItem("releaseHandoffChecklistFixture.deploymentExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[9].fields[*].path",
                        hasItem("releaseAuditRetentionFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[9].fields[*].path",
                        hasItem("releaseAuditRetentionFixture.auditExportReadOnly")))
                .andExpect(jsonPath("$.fieldGroups[10].fields[*].path",
                        hasItem("rollbackApprovalHandoff.handoffVersion")))
                .andExpect(jsonPath("$.fieldGroups[10].fields[*].path",
                        hasItem("rollbackApprovalHandoff.rollbackSqlExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[11].fields[*].path",
                        hasItem("rollbackApprovalRecordFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[11].fields[*].path",
                        hasItem("rollbackApprovalRecordFixture.rollbackExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[12].fields[*].path",
                        hasItem("rollbackSqlReviewGate.gateVersion")))
                .andExpect(jsonPath("$.fieldGroups[12].fields[*].path",
                        hasItem("rollbackSqlReviewGate.sqlExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[13].fields[*].path",
                        hasItem("productionSecretSourceContract.contractVersion")))
                .andExpect(jsonPath("$.fieldGroups[13].fields[*].path",
                        hasItem("productionSecretSourceContract.nodeMayReadSecretValues")))
                .andExpect(jsonPath("$.fieldGroups[14].fields[*].path",
                        hasItem("productionDeploymentRunbookContract.contractVersion")))
                .andExpect(jsonPath("$.fieldGroups[14].fields[*].path",
                        hasItem("productionDeploymentRunbookContract.nodeMayTriggerDeployment")))
                .andExpect(jsonPath("$.fieldGroups[15].fields[*].path",
                        hasItem("releaseOperatorSignoffFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[15].fields[*].path",
                        hasItem("releaseOperatorSignoffFixture.nodeMayCreateApprovalDecision")))
                .andExpect(jsonPath("$.fieldGroups[16].fields[*].path",
                        hasItem("rollbackApproverEvidenceFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[16].fields[*].path",
                        hasItem("rollbackApproverEvidenceFixture.rollbackSqlExecutionAllowed")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")));
    }

    @Test
    void staticOrderIdempotencyBoundarySampleExplainsCreateOrderConflictBoundary() throws Exception {
        mockMvc.perform(get("/contracts/order-idempotency-boundary.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boundaryVersion").value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.scenario").value("ORDER_IDEMPOTENCY_BOUNDARY_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.createOrderEndpoint").value("/api/v1/orders"))
                .andExpect(jsonPath("$.requiredHeader").value("Idempotency-Key"))
                .andExpect(jsonPath("$.requestFingerprint.version")
                        .value("order-create-request-sha256.v1"))
                .andExpect(jsonPath("$.requestFingerprint.scope",
                        hasItem("items aggregated by productId")))
                .andExpect(jsonPath("$.sameKeySameRequest.httpStatus").value(200))
                .andExpect(jsonPath("$.sameKeySameRequest.sideEffects",
                        hasItem("NO_NEW_INVENTORY_RESERVATION")))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.httpStatus").value(409))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.errorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.sideEffects",
                        hasItem("NO_NEW_OUTBOX_EVENT")))
                .andExpect(jsonPath("$.storage.authoritativeStore").value("orders table"))
                .andExpect(jsonPath("$.storage.miniKvConnected").value(false))
                .andExpect(jsonPath("$.storage.orderAuthoritativeStoreRemainsJavaDatabase").value(true))
                .andExpect(jsonPath("$.verticalSliceBoundary.intendedConsumer")
                        .value("Node idempotency vertical readiness review"))
                .andExpect(jsonPath("$.verticalSliceBoundary.mayBeUsedForProductionPass").value(false))
                .andExpect(jsonPath("$.forbiddenOperationsForReadOnlyEvidence",
                        hasItem("Connecting mini-kv as the authoritative order store")));
    }

    @Test
    void staticOrderIdempotencyStoreAbstractionSampleExplainsDisabledMiniKvCandidate() throws Exception {
        mockMvc.perform(get("/contracts/order-idempotency-store-abstraction.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abstractionVersion").value("java-idempotency-store.v1"))
                .andExpect(jsonPath("$.scenario").value("ORDER_IDEMPOTENCY_STORE_ABSTRACTION_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.activeStore.name").value("jpa-order-idempotency-store"))
                .andExpect(jsonPath("$.activeStore.implementation").value("JpaIdempotencyStore"))
                .andExpect(jsonPath("$.activeStore.mode").value("JPA_DATABASE"))
                .andExpect(jsonPath("$.activeStore.orderAuthoritative").value(true))
                .andExpect(jsonPath("$.activeStore.columns",
                        hasItem("orders.idempotency_key")))
                .andExpect(jsonPath("$.disabledCandidates[0].name").value("mini-kv-ttl-token-adapter"))
                .andExpect(jsonPath("$.disabledCandidates[0].enabled").value(false))
                .andExpect(jsonPath("$.disabledCandidates[0].connected").value(false))
                .andExpect(jsonPath("$.disabledCandidates[0].mode").value("DISABLED_CANDIDATE_ONLY"))
                .andExpect(jsonPath("$.boundaries.orderAuthoritativeStoreRemainsJavaDatabase").value(true))
                .andExpect(jsonPath("$.boundaries.changesPaymentOrInventoryTransaction").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerWrites").value(false))
                .andExpect(jsonPath("$.futureAdapterRules",
                        hasItem("Adapter must be introduced behind IdempotencyStore without changing create-order semantics")));
    }

    @Test
    void staticReleaseVerificationManifestExplainsJavaReleaseGate() throws Exception {
        mockMvc.perform(get("/contracts/release-verification-manifest.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manifestVersion").value("java-release-verification-manifest.v1"))
                .andExpect(jsonPath("$.scenario").value("JAVA_RELEASE_VERIFICATION_MANIFEST_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.releaseSubject.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.releaseSubject.buildTool").value("Maven"))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("focused-maven-tests")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("non-docker-regression-tests")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("maven-package")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("http-smoke")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.releaseGate.intendedConsumer")
                        .value("Node cross-project release verification intake gate"))
                .andExpect(jsonPath("$.releaseGate.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.releaseGate.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.releaseGate.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderCreateSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.archiveExpectation.runtimeArchiveRoot").value("c/<version>"));
    }

    @Test
    void staticDeploymentRollbackEvidenceSampleExplainsRollbackBoundaries() throws Exception {
        mockMvc.perform(get("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evidenceVersion").value("java-deployment-rollback-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("DEPLOYMENT_ROLLBACK_EVIDENCE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.rollbackMode").value("READ_ONLY_BOUNDARY_SAMPLE"))
                .andExpect(jsonPath("$.rollbackSubjects", hasItem("java-package")))
                .andExpect(jsonPath("$.rollbackSubjects", hasItem("database-migrations")))
                .andExpect(jsonPath("$.packageRollback.supported").value(true))
                .andExpect(jsonPath("$.packageRollback.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.configurationRollback.supported").value(true))
                .andExpect(jsonPath("$.configurationRollback.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.configurationRollback.nodeMayModifyRuntimeConfig").value(false))
                .andExpect(jsonPath("$.databaseMigrationRollback.automatic").value(false))
                .andExpect(jsonPath("$.databaseMigrationRollback.requiresOperatorConfirmation").value(true))
                .andExpect(jsonPath("$.databaseMigrationRollback.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.staticContractRollback.byArtifactVersion").value(true))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-handoff-checklist-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-approval-handoff")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("production-secret-source-contract")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("production-deployment-runbook-contract")))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering rollback from Node")));
    }

    @Test
    void staticReleaseBundleManifestExplainsReadOnlyBundleGate() throws Exception {
        mockMvc.perform(get("/contracts/release-bundle-manifest.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manifestVersion").value("java-release-bundle-manifest.v1"))
                .andExpect(jsonPath("$.scenario").value("JAVA_RELEASE_BUNDLE_MANIFEST_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.releaseSubject.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.releaseSubject.artifact")
                        .value("target/advanced-order-platform-0.1.0-SNAPSHOT.jar"))
                .andExpect(jsonPath("$.bundleMode").value("READ_ONLY_RELEASE_BUNDLE"))
                .andExpect(jsonPath("$.bundleInputs.releaseVerificationManifest")
                        .value("/contracts/release-verification-manifest.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.deploymentRollbackEvidence")
                        .value("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseHandoffChecklistFixture")
                        .value("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseAuditRetentionFixture")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseOperatorSignoffFixture")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackApprovalHandoff")
                        .value("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackApprovalRecordFixture")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackSqlReviewGate")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.productionSecretSourceContract")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.productionDeploymentRunbookContract")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.runtimeArchiveRoot").value("c/<version>"))
                .andExpect(jsonPath("$.artifactEvidence.packageDockerRequired").value(false))
                .andExpect(jsonPath("$.verificationEvidence[*].name", hasItem("focused-maven-tests")))
                .andExpect(jsonPath("$.verificationEvidence[*].name", hasItem("http-smoke")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Maven from Node")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

}
