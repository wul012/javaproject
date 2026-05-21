package com.codexdemo.orderplatform;

import static com.codexdemo.orderplatform.OpsReleaseApprovalSandboxConnectionEchoTestSupport.rehearsalRequest;
import static com.codexdemo.orderplatform.OpsReleaseApprovalSandboxConnectionEchoTestSupport.rehearsalRequestWithSandboxHeaders;
import static org.hamcrest.Matchers.hasItem;
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
        mockMvc.perform(rehearsalRequest())
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

        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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
        mockMvc.perform(rehearsalRequest())
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
        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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
        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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
        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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


}
