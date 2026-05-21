package com.codexdemo.orderplatform;

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
class OpsReleaseApprovalSandboxConnectionMarkerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void releaseApprovalRehearsalExposesFakeTransportDryRunPacketEchoMarker() throws Exception {
        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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
        mockMvc.perform(rehearsalRequestWithSandboxHeaders())
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
