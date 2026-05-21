package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests
        extends OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport {

    @Test
    void releaseApprovalRehearsalReturnsVerificationHintAndReadOnlyExecutionContract() throws Exception {
        seedReleaseApprovalReplayApprovals();

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verificationHint.hintVersion")
                        .value("java-release-approval-rehearsal-verification-hint.v1"))
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v39"))
                .andExpect(jsonPath("$.verificationHint.warningDigest").exists())
                .andExpect(jsonPath("$.verificationHint.noLedgerWriteProof")
                        .value("NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS"))
                .andExpect(jsonPath("$.verificationHint.noLedgerWriteProved").value(true))
                .andExpect(jsonPath("$.verificationHint.nodeMayTreatAsProductionAuthorization").value(false))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("verificationHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("operatorWindowHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("ciEvidenceHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("artifactRetentionHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("liveReadinessHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("auditPersistenceHandoffHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditExternalAdapterMigrationGuardReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("approvalRecordHandoffHint")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("approvalHandoffVerificationMarker")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditAdapterBoundaryReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("opsEvidenceServiceQualitySplitReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditAdapterImplementationGuardReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionOperatorHandoffMarker")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionPreconditionReceipt")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("executionBoundaries")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("contextWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("operatorWindowEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("ciEvidenceEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("artifactRetentionEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("liveReadinessEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("auditPersistenceHandoffEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("approvalRecordHandoffEchoWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("approvalHandoffVerificationMarkerWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditAdapterBoundaryReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("opsEvidenceServiceQualitySplitReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditAdapterImplementationGuardReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("javaManagedAuditWriteAllowed")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("javaApprovalRecordPersisted")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeMayTreatAsProductionApprovalRecord")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV211ProductionAuditRecordAllowed")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV211RealApprovalDecisionCreated")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV215MayConnectManagedAudit")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV215MayCreateApprovalDecision")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV215MayWriteApprovalLedger")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV215MayExecuteSql")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV217MayConnectManagedAudit")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV217MayWriteApprovalLedger")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeV217MayExecuteSql")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("qualitySplitApiShapeChanged")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("qualitySplitApprovalLedgerWritten")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("qualitySplitSqlExecuted")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("implementationGuardDigest")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("implementationGuardNodeV220AppendWritten")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("implementationGuardJavaSqlExecuted")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditSandboxConnectionOperatorHandoffMarkerWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarkerWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditSandboxConnectionPreconditionReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionOperatorHandoffMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionCredentialValueReadByJava")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionPreflightEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionPreconditionReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionPreflightManualWindowOpenByDefault")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionPreflightNodeAutoStartAllowed")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("nodeMayWriteApprovalLedger")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("executionAllowed=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("ciEvidenceHint.ciArtifactUploadedByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("artifactRetentionHint.javaRetentionFixtureReadOnly=true")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("artifactRetentionHint.githubArtifactAccessedByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("liveReadinessHint.readOnlyEndpointReady=true")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("liveReadinessHint.javaStartedProcessForNode=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("approvalRecordHandoffHint.javaApprovalRecordPersisted=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.sqlExecutedByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenByDefault=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.preflightGateReadOnly=true")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.sqlExecutedByJava=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("executionBoundaries.nodeMayWriteApprovalLedger=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 packet profile")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateProfile with Node v230")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Require managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=true before Node v231")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Keep UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseOperatorSignoffFixtureEndpoint")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackApproverEvidenceFixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackApprovalRecordFixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseBundleManifestEndpoint")
                        .value("/contracts/release-bundle-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseVerificationManifestEndpoint")
                        .value("/contracts/release-verification-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.deploymentRollbackEvidenceEndpoint")
                        .value("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.productionDeploymentRunbookContractEndpoint")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.productionSecretSourceContractEndpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackSqlReviewGateEndpoint")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.liveSignals.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.liveSignals.approvedReplayApprovals").value(1))
                .andExpect(jsonPath("$.liveSignals.replayBacklog").value(2))
                .andExpect(jsonPath("$.liveSignals.pendingOutboxEvents").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.liveSignals.realReplayAllowedByEvidence").value(false))
                .andExpect(jsonPath("$.liveSignals.approvalExecutionDryRun").value(true))
                .andExpect(jsonPath("$.liveSignals.evidenceExecutionAllowed").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.executionBoundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.executionBoundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.executionBoundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("READ_ONLY_RELEASE_APPROVAL_REHEARSAL")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("APPROVAL_DECISION_CREATION_DISABLED")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("ROLLBACK_SQL_EXECUTION_DISABLED")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("REPLAY_APPROVAL_PENDING")))
                .andExpect(jsonPath("$.requiredNodeEnvironment", hasItem("UPSTREAM_PROBES_ENABLED=true")))
                .andExpect(jsonPath("$.requiredNodeEnvironment", hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.nextEvidenceActions",
                        hasItem("GET /api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.nextEvidenceActions",
                        hasItem("GET /contracts/rollback-approver-evidence.fixture.json")));

    }
}
