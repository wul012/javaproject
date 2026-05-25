package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
class OpsReleaseApprovalCredentialResolverReadinessIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void releaseApprovalRehearsalExposesApprovalRequiredImplementationReadinessEchoReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v116-approval-required-readiness")
                        .header("x-orderops-operator-id", "auditor-v116")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v116-credential-resolver-approval-required-readiness")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v116-credential-resolver-approval-required-readiness")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-approval-required-implementation-readiness-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sourceDisabledImplementationCandidateEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v34"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewVersion")
                        .value("Node v281"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewProfile")
                        .value("managed-audit-manual-sandbox-connection-credential-resolver-approval-required-implementation-readiness-review.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewState")
                        .value("credential-resolver-approval-required-implementation-readiness-review-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sourceNodeV281.summary.boundaryCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sourceNodeV281.summary.requiredArtifactCount")
                        .value(18))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sourceNodeV275.sourceSpan")
                        .value("Node v274 + Java v115 + mini-kv v121"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.readinessReview.implementationStage")
                        .value("blocked-until-java-v116-mini-kv-v122-and-node-v282"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.boundaryReadiness",
                        hasSize(6)))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.requiredArtifactIds",
                        hasItem("approval-ledger-write-policy-id")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sideEffectBoundary.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sideEffectBoundary.approvalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.sideEffectBoundary.sqlExecuted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.echoWorkflowMissingSteps").isEmpty())
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.readyForNodeV282CredentialResolverApprovalRequiredImplementationReadinessVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.readyForManagedAuditResolverImplementation")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v50"))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.requiredArtifactIds.size=18")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt.consumedByNodeCredentialResolverApprovalRequiredImplementationReadinessReviewProfile with Node v281")));
    }
}
