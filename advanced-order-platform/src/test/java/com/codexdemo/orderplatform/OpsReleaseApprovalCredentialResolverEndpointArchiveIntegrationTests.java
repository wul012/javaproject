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
class OpsReleaseApprovalCredentialResolverEndpointArchiveIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v110-fake-shell-archive")
                        .header("x-orderops-operator-id", "auditor-v110")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v110-credential-resolver-fake-shell-archive")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v110-credential-resolver-fake-shell-archive")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-fake-shell-archive-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sourceTestOnlyShellEchoMarkerSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v29"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationVersion")
                        .value("Node v266"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile")
                        .value("managed-audit-manual-sandbox-connection-credential-resolver-fake-shell-archive-verification.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationState")
                        .value("credential-resolver-fake-shell-archive-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion")
                        .value("Node v265"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.nextNodeSandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerificationVersion")
                        .value("Node v267"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sourceNodeV266.checkCount")
                        .value(28))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sourceNodeV266.passedCheckCount")
                        .value(28))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.archiveFileCount")
                        .value(9))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.requiredSnippetCount")
                        .value(24))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.matchedSnippetCount")
                        .value(24))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.files[*].id",
                        hasItem("active-plan")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.snippets[*].id",
                        hasItem("plan-v266")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveVerification.archiveVerificationReadsFilesOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveVerification.archiveVerificationRerunsFakeShellBehavior")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveChecks.sourceNodeV265ConsumesUpstreamEchoes")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveChecks.noArchiveVerificationFakeShellRerun")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.approvalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForNodeV267SandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v45"))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.archiveFileCount=9")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile with Node v266")));
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v111-blocked-decision")
                        .header("x-orderops-operator-id", "auditor-v111")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v111-credential-resolver-blocked-decision")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v111-credential-resolver-blocked-decision")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-production-readiness-blocked-decision-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceFakeShellArchiveEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v30"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateVersion")
                        .value("Node v268"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile")
                        .value("managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-decision-gate.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateState")
                        .value("blocked"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.readinessDecision")
                        .value("blocked"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.checkCount")
                        .value(25))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.passedCheckCount")
                        .value(15))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.missingPreImplementationRequirementCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.productionBlockerCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsCredentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsManagedAuditConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsApprovalLedgerWrite")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.automaticUpstreamStart")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.missingRequirementCodes",
                        hasItem("REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForNodeV269CredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v45"))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.readinessDecision=blocked")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile with Node v268")));
    }
}
