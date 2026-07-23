package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;

final class EndpointPreflightRules {

  static final String REVIEW_MODE = "sandbox-endpoint-handle-preflight-review-only";
  static final String SOURCE_SPAN = "Node v257";
  static final String ENDPOINT_HANDLE = "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE";
  static final String CREDENTIAL_HANDLE = "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE";
  static final String OWNER_APPROVAL_ARTIFACT_ID = "owner-approval-artifact-review-only";
  static final String SCHEMA_REHEARSAL_ID = "schema-migration-rehearsal-review-only";
  static final String OPERATOR_WINDOW_MARKER = "manual-sandbox-endpoint-window-review-only";
  static final String NETWORK_ALLOWLIST_HANDLE =
      "ORDEROPS_MANAGED_AUDIT_SANDBOX_NETWORK_ALLOWLIST_HANDLE";
  static final String TLS_POLICY_HANDLE = "ORDEROPS_MANAGED_AUDIT_SANDBOX_TLS_POLICY_HANDLE";
  static final String REDACTION_POLICY_HANDLE =
      "ORDEROPS_MANAGED_AUDIT_SANDBOX_REDACTION_POLICY_HANDLE";
  static final int REQUIRED_REVIEW_ITEM_COUNT = 7;
  static final int COMPLETED_REVIEW_ITEM_COUNT = 7;
  static final int FORBIDDEN_OPERATION_COUNT = 7;
  static final int SOURCE_EVIDENCE_FILE_COUNT = 6;
  static final int SOURCE_MATCHED_SNIPPET_COUNT = 33;

  static final List<String> REQUIRED_REVIEW_ITEMS =
      List.of(
          "endpoint handle review",
          "credential handle review",
          "owner approval artifact review",
          "network allowlist review",
          "TLS policy review",
          "redaction policy review",
          "operator window review");

  static final List<String> FORBIDDEN_OPERATIONS =
      List.of(
          "read credential value",
          "parse raw endpoint URL",
          "send real managed audit request",
          "execute schema migration",
          "write approval ledger",
          "start Java or mini-kv",
          "promote mini-kv to managed audit storage backend");

  static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "Java v104 sandbox endpoint handle preflight echo marker",
          "mini-kv v113 sandbox endpoint handle non-participation receipt");

  static boolean sourceMarkerAccepted(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
          marker) {
    return marker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification()
        && marker.markerWarnings().isEmpty()
        && marker.requestShapeEchoed()
        && marker.responseShapeEchoed()
        && marker.timeoutBoundaryEchoed()
        && marker.failureMappingEchoed()
        && marker.cleanupBoundaryEchoed()
        && marker.sideEffectBoundaryEchoed()
        && !marker.readyForManagedAuditSandboxAdapterConnection();
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandlePreflightSourceEcho
      sourceNodeV257(boolean readyForNodeV258PreflightReview) {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandlePreflightSourceEcho(
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_STATE,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        SOURCE_EVIDENCE_FILE_COUNT,
        SOURCE_MATCHED_SNIPPET_COUNT,
        readyForNodeV258PreflightReview);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandlePreflightReviewShape
      preflightReview() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandlePreflightReviewShape(
        REVIEW_MODE,
        SOURCE_SPAN,
        ENDPOINT_HANDLE,
        CREDENTIAL_HANDLE,
        OWNER_APPROVAL_ARTIFACT_ID,
        SCHEMA_REHEARSAL_ID,
        OPERATOR_WINDOW_MARKER,
        true,
        true,
        true,
        REQUIRED_REVIEW_ITEM_COUNT,
        COMPLETED_REVIEW_ITEM_COUNT,
        FORBIDDEN_OPERATION_COUNT,
        true,
        true,
        true);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandleNetworkAllowlistReview
      networkAllowlistReview() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandleNetworkAllowlistReview(
        true, NETWORK_ALLOWLIST_HANDLE, false, false, true);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandleTlsPolicyReview
      tlsPolicyReview() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandleTlsPolicyReview(true, TLS_POLICY_HANDLE, false, false, true);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandleRedactionPolicyReview
      redactionPolicy() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandleRedactionPolicyReview(
        true, REDACTION_POLICY_HANDLE, true, true, true, true);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandleOperatorWindowReview
      operatorWindow() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandleOperatorWindowReview(true, false, true, true, true, true);
  }

  static ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalSandboxEndpointHandlePreflightSideEffectBoundary
      sideEffectBoundary() {
    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalSandboxEndpointHandlePreflightSideEffectBoundary(
        false, false, false, false, false, false, false, false, false, false, false, false, false,
        false, false, false);
  }

  static boolean sourceNodeV257Ready(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandlePreflightSourceEcho
          source) {
    return ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_VERSION
            .equals(source.sourceVersion())
        && ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_PROFILE
            .equals(source.profileVersion())
        && ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_STATE
            .equals(source.verificationState())
        && source.readyForUpstreamEchoVerification()
        && source.requestShapeAligned()
        && source.responseShapeAligned()
        && source.timeoutBoundaryAligned()
        && source.failureMappingAligned()
        && source.cleanupBoundaryAligned()
        && source.archiveNoRerunAligned()
        && source.credentialBoundaryAligned()
        && source.connectionBoundaryAligned()
        && source.writeBoundaryAligned()
        && source.autoStartBoundaryAligned()
        && source.upstreamActionsStillDisabled()
        && !source.readyForManagedAuditSandboxAdapterConnection()
        && !source.connectsManagedAudit()
        && !source.readsManagedAuditCredential()
        && !source.storesManagedAuditCredential()
        && !source.schemaMigrationExecuted()
        && !source.automaticUpstreamStart()
        && source.evidenceFileCount() == SOURCE_EVIDENCE_FILE_COUNT
        && source.matchedSnippetCount() == SOURCE_MATCHED_SNIPPET_COUNT
        && source.readyForNodeV258PreflightReview();
  }

  static boolean endpointHandleReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandlePreflightReviewShape
          review) {
    return ENDPOINT_HANDLE.equals(review.endpointHandle())
        && review.endpointHandleReviewed()
        && review.endpointHandleOnly()
        && review.readOnlyPreflightReview();
  }

  static boolean credentialHandleReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandlePreflightReviewShape
          review) {
    return CREDENTIAL_HANDLE.equals(review.credentialHandle())
        && review.credentialHandleReviewed()
        && review.credentialHandleOnly()
        && review.readOnlyPreflightReview();
  }

  static boolean ownerApprovalArtifactReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandlePreflightReviewShape
          review) {
    return REVIEW_MODE.equals(review.reviewMode())
        && SOURCE_SPAN.equals(review.sourceSpan())
        && OWNER_APPROVAL_ARTIFACT_ID.equals(review.ownerApprovalArtifactId())
        && SCHEMA_REHEARSAL_ID.equals(review.schemaRehearsalId())
        && OPERATOR_WINDOW_MARKER.equals(review.operatorWindowMarker())
        && review.ownerApprovalArtifactReviewed()
        && review.requiredReviewItemCount() == REQUIRED_REVIEW_ITEM_COUNT
        && review.completedReviewItemCount() == COMPLETED_REVIEW_ITEM_COUNT
        && review.forbiddenOperationCount() == FORBIDDEN_OPERATION_COUNT;
  }

  static boolean networkAllowlistReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandleNetworkAllowlistReview
          review) {
    return review.reviewRequired()
        && NETWORK_ALLOWLIST_HANDLE.equals(review.allowlistHandle())
        && !review.rawHostIncluded()
        && !review.cidrIncluded()
        && review.reviewed();
  }

  static boolean tlsPolicyReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandleTlsPolicyReview
          review) {
    return review.reviewRequired()
        && TLS_POLICY_HANDLE.equals(review.policyHandle())
        && !review.certificateMaterialIncluded()
        && !review.privateKeyIncluded()
        && review.reviewed();
  }

  static boolean redactionPolicyEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandleRedactionPolicyReview
          review) {
    return review.reviewRequired()
        && REDACTION_POLICY_HANDLE.equals(review.policyHandle())
        && review.credentialValueRedacted()
        && review.rawEndpointUrlRedacted()
        && review.payloadSecretRedacted()
        && review.reviewed();
  }

  static boolean operatorWindowReviewEchoed(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandleOperatorWindowReview
          review) {
    return review.manualWindowRequired()
        && !review.windowOpen()
        && review.executionBlockedUntilWindowOpen()
        && review.operatorIdentityRequired()
        && review.approvalCorrelationRequired()
        && review.reviewed();
  }

  static boolean noCredentialConnectionWriteOrAutoStart(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalSandboxEndpointHandlePreflightSideEffectBoundary
          boundary) {
    return !boundary.rawEndpointUrlParsed()
        && !boundary.rawEndpointUrlIncluded()
        && !boundary.credentialValueRead()
        && !boundary.externalRequestSent()
        && !boundary.schemaMigrationExecuted()
        && !boundary.automaticUpstreamStart()
        && !boundary.connectsManagedAudit()
        && !boundary.readsManagedAuditCredential()
        && !boundary.storesManagedAuditCredential()
        && !boundary.executionAllowed()
        && !boundary.approvalLedgerWritten()
        && !boundary.javaStarted()
        && !boundary.miniKvStarted()
        && !boundary.externalAuditServiceStarted()
        && !boundary.productionAuditAllowed()
        && !boundary.productionWindowAllowed();
  }
}
