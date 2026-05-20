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
class OpsReleaseApprovalCredentialResolverEndpointDecisionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDecisionEchoMarker() throws Exception {
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.markerVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-decision-echo-marker.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceEndpointHandlePreflightEchoMarkerSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v26"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordVersion")
                        .value("Node v260"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile")
                        .value("managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordMarkdownEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record?format=markdown"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordState")
                        .value("sandbox-endpoint-credential-resolver-decision-record-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationVersion")
                        .value("Node v259"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationState")
                        .value("sandbox-endpoint-handle-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.nextNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion")
                        .value("Node v261"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceNodeV259.evidenceFileCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceNodeV259.matchedSnippetCount")
                        .value(39))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sourceNodeV259.checkCount")
                        .value(19))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFieldCount")
                        .value(8))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.explicitNoGoConditionCount")
                        .value(9))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.endpointHandle")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialHandle")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverPolicyHandle")
                        .value("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.approvalMarker")
                        .value("ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverMode")
                        .value("policy-record-only-no-value-read"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverCandidateImplementation")
                        .value("not-implemented"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFields[*].id",
                        hasItem("fallback-rotation-plan")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.explicitNoGoConditions[*].code",
                        hasItem("CREDENTIAL_VALUE_REQUIRED")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.rawEndpointUrlMayBeParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.externalRequestMayBeSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.markerWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverDecisionEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile with Node v260")));
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v106-disabled-precheck")
                        .header("x-orderops-operator-id", "auditor-v106")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v106-credential-resolver-disabled-precheck")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v106-credential-resolver-disabled-precheck")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.markerVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-precheck-echo-marker.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceCredentialResolverDecisionEchoMarkerSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v27"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckVersion")
                        .value("Node v262"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile")
                        .value("managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckMarkdownEndpoint")
                        .value("/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck?format=markdown"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckState")
                        .value("sandbox-endpoint-credential-resolver-disabled-precheck-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion")
                        .value("Node v261"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationState")
                        .value("sandbox-endpoint-credential-resolver-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.nextNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion")
                        .value("Node v263"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceNodeV261.checkCount")
                        .value(20))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceNodeV261.passedCheckCount")
                        .value(20))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sourceNodeV261.readyForNodeV262CredentialResolverDisabledPrecheck")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandleCount")
                        .value(6))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.optInGateCount")
                        .value(2))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.failureClassCount")
                        .value(7))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseFieldCount")
                        .value(12))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.inheritedNoGoConditionCount")
                        .value(9))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandles[*].name",
                        hasItem("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.failureTaxonomy[*].code",
                        hasItem("CREDENTIAL_VALUE_REQUESTED")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.fields",
                        hasItem("resolverClientInstantiated")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.resolverClientInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.secretProviderInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.rawEndpointUrlParsed")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseShape.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.secretProviderInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.resolverClientInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.markerWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.resolverClientMayBeInstantiated=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile with Node v262")));
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverTestOnlyShellEchoMarker()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal")
                        .header("x-orderops-request-id", "rehearsal-v107-test-only-shell")
                        .header("x-orderops-operator-id", "auditor-v107")
                        .header("x-orderops-roles", "auditor,operator,viewer")
                        .header("x-orderops-operator-verified", "true")
                        .header("x-orderops-approval-correlation-id",
                                "approval-v107-credential-resolver-test-only-shell")
                        .header("x-orderops-ci-manifest-version",
                                "real-read-window-ci-archive-artifact-manifest.v1")
                        .header("x-orderops-ci-manifest-digest", "sha256:node-v200-manifest-digest")
                        .header("x-orderops-ci-manifest-endpoint",
                                "/api/v1/production/real-read-window-ci-archive-artifact-manifest")
                        .header("x-orderops-ci-artifact-record-count", "9")
                        .header("x-orderops-ci-approval-correlation-id",
                                "approval-v107-credential-resolver-test-only-shell")
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
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.markerVersion")
                        .value("java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-test-only-shell-echo-marker.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceDisabledPrecheckEchoMarkerSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v28"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractVersion")
                        .value("Node v264"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile")
                        .value("managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-test-only-shell-contract.v1"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractState")
                        .value("sandbox-endpoint-credential-resolver-test-only-shell-contract-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion")
                        .value("Node v263"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationState")
                        .value("sandbox-endpoint-credential-resolver-disabled-precheck-upstream-echo-verification-ready"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.nextNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion")
                        .value("Node v265"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceNodeV263.checkCount")
                        .value(19))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceNodeV263.passedCheckCount")
                        .value(19))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sourceNodeV263.readyForNodeV264CredentialResolverTestOnlyShellContract")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShapeFieldCount")
                        .value(9))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.responseShapeFieldCount")
                        .value(13))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.failureMappingCount")
                        .value(7))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.guardConditionCount")
                        .value(10))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.fields",
                        hasItem("credentialHandle")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.credentialValueAccepted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.rawEndpointUrlAccepted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.responseShape.resolverClientInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.responseShape.secretProviderInstantiated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.responseShape.externalRequestSent")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.failureMapping[*].sourceFailureCode",
                        hasItem("CREDENTIAL_VALUE_REQUESTED")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.guardConditions[*].code",
                        hasItem("NO_EXTERNAL_REQUEST")))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.fakeResolverProbe.responseCode")
                        .value("TEST_ONLY_FAKE_RESOLVER"))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.fakeResolverProbe.credentialValueRead")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sideEffectBoundary.connectsManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForNodeV265SandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerification")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForManagedAuditSandboxAdapterConnection")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.markerWarnings").isEmpty())
                .andExpect(jsonPath("$.verificationHint.schemaFields",
                        hasItem("managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker")))
                .andExpect(jsonPath("$.verificationHint.warningDigestInputs",
                        hasItem("sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerDigest")))
                .andExpect(jsonPath("$.verificationHint.proofClaims",
                        hasItem("managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.credentialValueAccepted=false")))
                .andExpect(jsonPath("$.verificationHint.nodeVerificationActions",
                        hasItem("Compare managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile with Node v264")));
    }
}
