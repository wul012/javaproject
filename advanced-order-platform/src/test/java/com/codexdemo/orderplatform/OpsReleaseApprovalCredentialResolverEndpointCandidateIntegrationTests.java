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
class OpsReleaseApprovalCredentialResolverEndpointCandidateIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v112-plan-intake")
                        .header("x-orderops-operator-id", "auditor-v112")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v112-credential-resolver-plan-intake")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v112-credential-resolver-plan-intake")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-pre-implementation-plan-intake-echo-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceProductionReadinessBlockedDecisionEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v31"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeVersion")
                        .value("Node v270"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeProfile")
                        .value("managed-audit-manual-sandbox-connection-credential-resolver-pre-implementation-plan-intake.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeState")
                        .value("credential-resolver-pre-implementation-plan-intake-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.planIntakeOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.readyForCredentialResolverPreImplementationPlan")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.sourceNodeV269.verificationState")
                        .value("credential-resolver-production-readiness-blocked-decision-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sourceNodeV270.sourceNodeV269.missingPreImplementationRequirementCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.boundaryCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.definedBoundaryCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.allRequiredBoundariesDefined")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.credentialValueReadAllowed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.rawEndpointUrlParseAllowed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.boundaries[*].requirementFromV268",
                        hasItem("REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.planIntake.intakeMode")
                        .value("node-v270-plan-intake-only"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.planIntake.missingBoundaryCount")
                        .value(0))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.planIntake.nextNodeVerificationVersion")
                        .value("Node v272"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.checks.allTenBoundariesDefined")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.checks.credentialValueStillForbidden")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.checks.rawEndpointStillForbidden")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.checks.externalRequestStillSimulationOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sideEffectBoundary.approvalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sideEffectBoundary.sqlExecuted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.sideEffectBoundary.automaticUpstreamStart")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.readyForNodeV272CredentialResolverPreImplementationPlanVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v40"))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.preImplementationPlan.boundaryCount=10")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceipt.consumedByNodeCredentialResolverPreImplementationPlanIntakeProfile with Node v270")));
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v113-disabled-candidate")
                        .header("x-orderops-operator-id", "auditor-v113")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v113-credential-resolver-disabled-candidate")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v113-credential-resolver-disabled-candidate")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-implementation-candidate-echo-receipt.v2"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sourcePreImplementationPlanIntakeEchoReceiptSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v32"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewVersion")
                        .value("Node v273"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewProfile")
                        .value("managed-audit-manual-sandbox-connection-credential-resolver-disabled-implementation-candidate-review.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewState")
                        .value("credential-resolver-disabled-implementation-candidate-review-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sourceNodeV273.sourceNodeV272.verificationState")
                        .value("credential-resolver-pre-implementation-plan-intake-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sourceNodeV273.sourceNodeV272.boundaryCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.candidateDecisionCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.candidateReadyDecisionCount")
                        .value(4))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.approvalRequiredDecisionCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidateReadyBoundaryCodes",
                        hasItem("DISABLED_SECRET_PROVIDER_STUB")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.approvalRequiredBoundaryCodes",
                        hasItem("AUDIT_LEDGER_WRITE_POLICY")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.interfaceShape.handleOnlyRequest")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.interfaceShape.includesCredentialValue")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.interfaceShape.includesRawEndpointUrl")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.fakeWiringReview.fakeWiringReviewOnly")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.fakeWiringReview.fakeRuntimeInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.checks.allCandidateDecisionsCovered")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.checks.ledgerWriteStillBlocked")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.approvalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.sideEffectBoundary.automaticUpstreamStart")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.echoWorkflowTemplateApplied")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.echoWorkflowMissingSteps").isEmpty())
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.readyForNodeV274CredentialResolverDisabledCandidateVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.receiptWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v40"))
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.candidate.candidateDecisionCount=10")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceipt.consumedByNodeCredentialResolverDisabledImplementationCandidateReviewProfile with Node v273")));
    }
}
