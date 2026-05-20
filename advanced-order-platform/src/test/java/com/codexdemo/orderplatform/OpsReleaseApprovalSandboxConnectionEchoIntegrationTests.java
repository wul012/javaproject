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
class OpsReleaseApprovalSandboxConnectionEchoIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void releaseApprovalRehearsalExposesSandboxConnectionDryRunEnvelopeEchoReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-envelope-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.sourceSandboxConnectionPreconditionReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v19"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeVersion")
                        .value("Node v236"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile")
                        .value("managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-dry-run-request-envelope"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.nextNodeReadinessGateVersion")
                        .value("Node v237"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.ownerApprovalArtifactIdField")
                        .value("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.credentialHandleNameField")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.timeoutBudgetField")
                        .value("timeoutBudgetMs"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.operatorReviewFieldsComplete")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.envelopeCreatesConnectionCommand")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialHandleOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueReadByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.managedAuditStoreWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.allEnvelopeFieldsEchoed")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialValueExcluded")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptDigest").exists())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.echoedEnvelopeFieldNames",
                        hasItem("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.echoedEnvelopeFieldNames",
                        hasItem("timeoutBudgetMs")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptWarnings",
                        hasItem("NODE_V237_SOURCE_SANDBOX_CONNECTION_PRECONDITION_RECEIPT_NOT_READY")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.nodeV237Prerequisites",
                        hasItem("mini-kv v101 no-start / no-write evidence follow-up must be present")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditSandboxConnectionDryRunEnvelopeEchoReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionDryRunEnvelopeEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionDryRunEnvelopeCredentialValueIncluded")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope=false")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile with Node v236")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Require managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate=true before Node v237")));

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile")
                        .value("managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.envelopeFieldBoundary.operatorReviewFieldsComplete")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialHandleOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueReadByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.receiptDigest").exists());
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxConnectionOperatorWindowChecklistEchoReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-window-checklist-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.sourceSandboxConnectionDryRunEnvelopeEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v20"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistVersion")
                        .value("Node v238"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile")
                        .value("managed-audit-manual-sandbox-connection-operator-window-checklist.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.nextNodeEvidenceVerificationVersion")
                        .value("Node v239"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.requiredApprovalCount")
                        .value(3))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.checklistStepCount")
                        .value(8))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.pauseConditionCount")
                        .value(8))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.forbiddenOperationCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.operatorChecklistReadOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.checklistCreatesConnectionCommand")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.approvalBoundary.approvalItemCount")
                        .value(3))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.approvalBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialHandleOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueReadByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForNodeV239ManualSandboxConnectionEvidenceVerification")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.receiptDigest").exists())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.echoedApprovalItemIds",
                        hasItem("security-reviewer")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.echoedChecklistStepPhases",
                        hasItem("final-stop-gate")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.echoedPauseConditionCodes",
                        hasItem("UPSTREAM_ACTIONS_ENABLED")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.receiptWarnings",
                        hasItem("NODE_V239_SOURCE_SANDBOX_CONNECTION_DRY_RUN_ENVELOPE_ECHO_RECEIPT_NOT_READY")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("managedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptWarnings")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile with Node v238")));
    }

    @Test
    void releaseApprovalRehearsalExposesDryRunCommandPackageEchoReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-command-package-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.sourceSandboxConnectionOperatorWindowChecklistEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v21"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.consumedByNodeDryRunCommandPackageVersion")
                        .value("Node v241"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.consumedByNodeDryRunCommandPackageProfile")
                        .value("managed-audit-manual-sandbox-connection-dry-run-command-package.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.nextNodeUpstreamEchoVerificationVersion")
                        .value("Node v244"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.packageShape.commandCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.packageShape.disabledByDefault")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.packageShape.dryRunOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.fieldEcho.credentialHandleCommandId")
                        .value("verify-credential-handle"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.fieldEcho.schemaRehearsalCommandId")
                        .value("review-schema-rehearsal"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.fieldEcho.rollbackPathCommandId")
                        .value("review-rollback-path"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.fieldEcho.timeoutBudgetMs")
                        .value(15000))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.fieldEcho.credentialValueEchoed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.carriesCredentialValue")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.readyForNodeV244ManualSandboxDryRunCommandUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.echoedCommandIds",
                        hasItem("confirm-manual-abort-marker")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionDryRunCommandPackageEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.consumedByNodeDryRunCommandPackageProfile with Node v241")));
    }

    @Test
    void releaseApprovalRehearsalExposesPrecheckPacketEchoReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-precheck-packet-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.sourceDryRunCommandPackageEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v22"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.consumedByNodePrecheckPacketVersion")
                        .value("Node v245"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.consumedByNodePrecheckPacketProfile")
                        .value("managed-audit-manual-sandbox-connection-precheck-packet.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.nextNodePrecheckUpstreamReceiptVerificationVersion")
                        .value("Node v246"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.packetShape.precheckItemCount")
                        .value(7))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.packetShape.disabledByDefault")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.packetShape.dryRunOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.fieldEcho.credentialHandleReviewItemId")
                        .value("credential-handle-review"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.fieldEcho.schemaMigrationRehearsalItemId")
                        .value("schema-migration-rehearsal"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.fieldEcho.rollbackPathItemId")
                        .value("rollback-path"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.fieldEcho.timeoutBudgetMs")
                        .value(15000))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.fieldEcho.credentialValueEchoed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.carriesCredentialValue")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.schemaMigrationSqlExecutedByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.echoedPrecheckItemIds",
                        hasItem("timeout-policy")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionPrecheckPacketEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionPrecheckPacketEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionPrecheckPacketEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionPrecheckPacketEchoReceipt.consumedByNodePrecheckPacketProfile with Node v245")));
    }

    @Test
    void releaseApprovalRehearsalExposesDisabledAdapterClientPrecheckEchoReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-disabled-adapter-client-precheck-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.sourcePrecheckPacketEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v23"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeDisabledAdapterClientPrecheckVersion")
                        .value("Node v252"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeDisabledAdapterClientPrecheckProfile")
                        .value("managed-audit-manual-sandbox-connection-disabled-adapter-client-precheck.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeTestOnlyAdapterShellContractVersion")
                        .value("Node v253"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.nextNodeDisabledAdapterClientUpstreamEchoVerificationVersion")
                        .value("Node v254"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.requiredEnvHandleCount")
                        .value(5))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.failureClassCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.dryRunResponseFieldCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.precheckCreatesRealClient")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.clientBoundary.clientImplementationStatus")
                        .value("not-implemented"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.clientBoundary.clientMayBeInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.clientBoundary.externalRequestMayBeSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.clientBoundary.credentialValueMayBeLoaded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.optInGate.gateName")
                        .value("ORDEROPS_MANAGED_AUDIT_ADAPTER_CLIENT_ENABLED"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.optInGate.currentDefault")
                        .value("false"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.optInGate.precheckTreatsEnabledAsBlocked")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.externalRequestSentByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.readyForNodeV254DisabledAdapterClientUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.echoedRequiredEnvHandles",
                        hasItem("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE")))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.echoedFailureClassCodes",
                        hasItem("CREDENTIAL_VALUE_REQUESTED")))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeDisabledAdapterClientPrecheckProfile with Node v252")));
    }

    @Test
    void releaseApprovalRehearsalExposesFakeTransportDryRunPacketEchoMarker() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.markerVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-connection-fake-transport-dry-run-packet-echo-marker.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.sourceDisabledAdapterClientPrecheckEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v24"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketProfile")
                        .value("managed-audit-manual-sandbox-connection-fake-transport-adapter-dry-run-verification-packet.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketState")
                        .value("fake-transport-adapter-dry-run-verification-packet-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportPacketArchiveVerificationVersion")
                        .value("Node v256"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportPacketArchiveVerificationProfile")
                        .value("managed-audit-manual-sandbox-connection-fake-transport-packet-archive-verification.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.nextNodeFakeTransportPacketUpstreamEchoVerificationVersion")
                        .value("Node v257"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.credentialValueIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.rawEndpointUrlIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.payloadMayContainSecrets")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.requestShapeFieldCount")
                        .value(8))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.timeoutBudgetMs")
                        .value(15000))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.status")
                        .value("fake-transport-dry-run-accepted"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.code")
                        .value("TEST_ONLY_FAKE_TRANSPORT_DRY_RUN"))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.connectionAttempted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.schemaMigrationExecuted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.productionRecordWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.responseShapeFieldCount")
                        .value(9))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.timeoutBoundary.budgetSpent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.timeoutBoundary.timerStarted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.cleanupArtifactCount")
                        .value(0))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.temporaryDirectoryCreated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.temporaryFileCreated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.sideEffectBoundary.javaStarted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.sideEffectBoundary.miniKvStarted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.markerWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxConnectionFakeTransportDryRunPacketEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.cleanupArtifactCount=0")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketProfile with Node v255")));
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointHandlePreflightEchoMarker() throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("X-Rehearsal-Request-Id", "rehearsal-v67-001")
                        .header("X-Operator-Identity", "release-operator@example.test")
                        .header("X-Audit-Correlation-Id", "audit-correlation-v67")
                        .header("x-orderops-operator-id", "operator-198")
                        .header("x-orderops-roles", "operator,auditor")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id", "approval-v198-operator-window")
                        .header("x-orderops-ci-upload-contract-version",
                                "real-read-window-ci-artifact-upload-dry-run-contract.v1")
                        .header("x-orderops-ci-upload-contract-digest",
                                "sha256:node-v202-upload-contract-digest")
                        .header("x-orderops-ci-artifact-name", "orderops-real-read-window-evidence-v191-v201")
                        .header("x-orderops-ci-artifact-root", "c/")
                        .header("x-orderops-ci-retention-days", "30")
                        .header("x-orderops-ci-upload-mode", "dry-run-contract-only")
                        .header("x-orderops-runtime-preflight-version",
                                "three-project-real-read-runtime-smoke-preflight.v1")
                        .header("x-orderops-runtime-preflight-digest",
                                "sha256:node-v204-preflight-digest")
                        .header("x-orderops-runtime-smoke-session-id",
                                "runtime-smoke-v205-session-001")
                        .header("x-orderops-runtime-read-target-id",
                                "java-release-approval-rehearsal")
                        .header("x-orderops-runtime-window-mode",
                                "manual-open-window-plan")
                        .header("x-orderops-managed-audit-candidate-version",
                                "managed-audit-persistence-boundary-candidate.v1")
                        .header("x-orderops-managed-audit-candidate-digest",
                                "sha256:node-v208-managed-audit-candidate-digest")
                        .header("x-orderops-managed-audit-sink-mode",
                                "file-or-sqlite-dry-run-candidate")
                        .header("x-orderops-managed-audit-retention-days", "30")
                        .header("x-orderops-managed-audit-rotation-policy",
                                "size-and-age-rotation-candidate")
                        .header("x-orderops-approval-binding-contract-version",
                                "managed-audit-identity-approval-binding-contract.v1")
                        .header("x-orderops-approval-binding-contract-digest",
                                "sha256:node-v210-approval-binding-digest")
                        .header("x-orderops-approval-request-id", "approval-request-v210-001")
                        .header("x-orderops-approval-decision-state", "APPROVED_DRY_RUN_ONLY")
                        .header("x-orderops-approval-record-correlation-id",
                                "approval-record-correlation-v210"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.markerVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-handle-preflight-echo-marker.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sourceFakeTransportDryRunPacketEchoMarkerSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v25"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewVersion")
                        .value("Node v258"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile")
                        .value("managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewMarkdownEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review?format=markdown"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewState")
                        .value("sandbox-endpoint-handle-preflight-review-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sourceNodeFakeTransportPacketUpstreamEchoVerificationVersion")
                        .value("Node v257"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sourceNodeFakeTransportPacketUpstreamEchoVerificationState")
                        .value("fake-transport-packet-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.nextNodeSandboxEndpointHandleUpstreamEchoVerificationVersion")
                        .value("Node v259"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sourceNodeV257.evidenceFileCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sourceNodeV257.matchedSnippetCount")
                        .value(33))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.endpointHandle")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.credentialHandle")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.requiredReviewItemCount")
                        .value(7))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.completedReviewItemCount")
                        .value(7))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.endpointHandleOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.credentialHandleOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.networkAllowlistReview.rawHostIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.networkAllowlistReview.cidrIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.tlsPolicyReview.certificateMaterialIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.tlsPolicyReview.privateKeyIncluded")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.redactionPolicy.rawEndpointUrlRedacted")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.operatorWindow.windowOpen")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.schemaMigrationExecuted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointHandlePreflightEchoMarker.markerWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointHandlePreflightEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointHandlePreflightEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile with Node v258")));
    }

}
