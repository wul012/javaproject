package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static com.codexdemo.orderplatform.ops.maintenance.releaseapproval.EndpointPreflightRules.*;

import java.util.List;

final class EndpointPreflightBuilder {

  private static final String WARNING_DIGEST_WARNING_INPUT_NAME =
      "managedAuditSandboxEndpointHandlePreflightEchoMarkerWarnings";

  private static final List<String> WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
      List.of(
          "sandboxEndpointHandlePreflightEchoMarkerDigest",
          "sandboxEndpointHandlePreflightRequiredReviewItemCount",
          "sandboxEndpointHandlePreflightCompletedReviewItemCount",
          "sandboxEndpointHandlePreflightForbiddenOperationCount",
          "sandboxEndpointHandlePreflightEndpointHandleOnly",
          "sandboxEndpointHandlePreflightCredentialHandleOnly",
          "sandboxEndpointHandlePreflightRawEndpointUrlParsed",
          "sandboxEndpointHandlePreflightRawEndpointUrlIncluded",
          "sandboxEndpointHandlePreflightCredentialValueRead",
          "sandboxEndpointHandlePreflightExternalRequestSent",
          "sandboxEndpointHandlePreflightSchemaMigrationExecuted",
          "sandboxEndpointHandlePreflightAutomaticUpstreamStart",
          "sandboxEndpointHandlePreflightConnectsManagedAudit",
          "sandboxEndpointHandlePreflightJavaStarted",
          "sandboxEndpointHandlePreflightMiniKvStarted");

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.requiredReviewItemCount=7",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.completedReviewItemCount=7",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.endpointHandleOnly=true",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.credentialHandleOnly=true",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.networkAllowlistReview.rawHostIncluded=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.tlsPolicyReview.certificateMaterialIncluded=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.redactionPolicy.rawEndpointUrlRedacted=true",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.operatorWindow.windowOpen=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.credentialValueRead=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.externalRequestSent=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.schemaMigrationExecuted=false",
          "managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForManagedAuditSandboxAdapterConnection=false");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile with Node v258",
          "Require managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification=true before Node v259",
          "Verify managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.endpointHandleOnly=true",
          "Verify managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.credentialHandleOnly=true",
          "Verify managedAuditSandboxEndpointHandlePreflightEchoMarker.networkAllowlistReview.rawHostIncluded=false",
          "Verify managedAuditSandboxEndpointHandlePreflightEchoMarker.tlsPolicyReview.certificateMaterialIncluded=false",
          "Verify managedAuditSandboxEndpointHandlePreflightEchoMarker.redactionPolicy.rawEndpointUrlRedacted=true",
          "Keep managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false",
          "Keep managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.credentialValueRead=false",
          "Keep managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.externalRequestSent=false");

  private static final MarkerEvidence EVIDENCE =
      new MarkerEvidence(
          WARNING_DIGEST_WARNING_INPUT_NAME,
          WARNING_DIGEST_BOUNDARY_INPUT_NAMES,
          PROOF_CLAIMS,
          NODE_VERIFICATION_ACTIONS);

  ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
          .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
      build(
          ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
                  .RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
              fakeTransportDryRunPacketEchoMarker) {
    boolean sourceAccepted = sourceMarkerAccepted(fakeTransportDryRunPacketEchoMarker);
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandlePreflightSourceEcho
        sourceNodeV257 = sourceNodeV257(sourceAccepted);
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandlePreflightReviewShape
        preflightReview = preflightReview();
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandleNetworkAllowlistReview
        networkAllowlistReview = networkAllowlistReview();
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandleTlsPolicyReview
        tlsPolicyReview = tlsPolicyReview();
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandleRedactionPolicyReview
        redactionPolicy = redactionPolicy();
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandleOperatorWindowReview
        operatorWindow = operatorWindow();
    ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
            .RehearsalSandboxEndpointHandlePreflightSideEffectBoundary
        sideEffectBoundary = sideEffectBoundary();

    List<String> markerWarnings =
        ReleaseApprovalDigestSupport.warnings(
            ReleaseApprovalDigestSupport.warningIf(
                !sourceAccepted,
                "NODE_V259_SOURCE_FAKE_TRANSPORT_DRY_RUN_PACKET_ECHO_MARKER_NOT_READY"));

    boolean sourceNodeV257Echoed = sourceAccepted && sourceNodeV257Ready(sourceNodeV257);
    boolean endpointHandleReviewEchoed = endpointHandleReviewEchoed(preflightReview);
    boolean credentialHandleReviewEchoed = credentialHandleReviewEchoed(preflightReview);
    boolean ownerApprovalArtifactReviewEchoed = ownerApprovalArtifactReviewEchoed(preflightReview);
    boolean networkAllowlistReviewEchoed = networkAllowlistReviewEchoed(networkAllowlistReview);
    boolean tlsPolicyReviewEchoed = tlsPolicyReviewEchoed(tlsPolicyReview);
    boolean redactionPolicyEchoed = redactionPolicyEchoed(redactionPolicy);
    boolean operatorWindowReviewEchoed = operatorWindowReviewEchoed(operatorWindow);
    boolean sideEffectBoundaryEchoed = noCredentialConnectionWriteOrAutoStart(sideEffectBoundary);
    boolean readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification =
        sourceNodeV257Echoed
            && endpointHandleReviewEchoed
            && credentialHandleReviewEchoed
            && ownerApprovalArtifactReviewEchoed
            && networkAllowlistReviewEchoed
            && tlsPolicyReviewEchoed
            && redactionPolicyEchoed
            && operatorWindowReviewEchoed
            && sideEffectBoundaryEchoed;

    String markerDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line(
                    "markerVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_ECHO_MARKER_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "sourceFakeTransportDryRunPacketEchoMarkerVersion",
                    fakeTransportDryRunPacketEchoMarker.markerVersion()),
                ReleaseApprovalDigestSupport.line(
                    "sourceFakeTransportDryRunPacketEchoMarkerSchemaVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_FAKE_TRANSPORT_DRY_RUN_PACKET_ECHO_MARKER_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeSandboxEndpointHandlePreflightReviewProfile",
                    ReleaseApprovalUpstreamContractConstants
                        .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_PROFILE),
                ReleaseApprovalDigestSupport.line("reviewMode", REVIEW_MODE),
                ReleaseApprovalDigestSupport.line("sourceSpan", SOURCE_SPAN),
                ReleaseApprovalDigestSupport.line("sourceNodeV257", sourceNodeV257),
                ReleaseApprovalDigestSupport.line("preflightReview", preflightReview),
                ReleaseApprovalDigestSupport.line("networkAllowlistReview", networkAllowlistReview),
                ReleaseApprovalDigestSupport.line("tlsPolicyReview", tlsPolicyReview),
                ReleaseApprovalDigestSupport.line("redactionPolicy", redactionPolicy),
                ReleaseApprovalDigestSupport.line("operatorWindow", operatorWindow),
                ReleaseApprovalDigestSupport.line("sideEffectBoundary", sideEffectBoundary),
                ReleaseApprovalDigestSupport.line(
                    "readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification",
                    readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification)));

    return new ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
        .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_ECHO_MARKER_VERSION,
        fakeTransportDryRunPacketEchoMarker.markerVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_FAKE_TRANSPORT_DRY_RUN_PACKET_ECHO_MARKER_SCHEMA_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_MARKDOWN_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V258_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_REVIEW_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V257_FAKE_TRANSPORT_PACKET_UPSTREAM_ECHO_VERIFICATION_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        true,
        REVIEW_MODE,
        SOURCE_SPAN,
        sourceNodeV257,
        preflightReview,
        networkAllowlistReview,
        tlsPolicyReview,
        redactionPolicy,
        operatorWindow,
        sideEffectBoundary,
        sourceNodeV257Echoed,
        endpointHandleReviewEchoed,
        credentialHandleReviewEchoed,
        ownerApprovalArtifactReviewEchoed,
        networkAllowlistReviewEchoed,
        tlsPolicyReviewEchoed,
        redactionPolicyEchoed,
        operatorWindowReviewEchoed,
        sideEffectBoundaryEchoed,
        readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification,
        false,
        false,
        false,
        false,
        markerDigest,
        REQUIRED_REVIEW_ITEMS,
        FORBIDDEN_OPERATIONS,
        NEXT_REQUIRED_ECHO_VERSIONS,
        markerWarnings,
        EVIDENCE.nodeActions());
  }

  MarkerEvidence evidence() {
    return EVIDENCE;
  }

  List<String> warningDigestWarningLines(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
          marker) {
    return EVIDENCE.warningLines(marker.markerWarnings());
  }

  List<String> warningDigestBoundaryLines(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
          marker) {
    return List.of(
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightEchoMarkerDigest", marker.markerDigest()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightRequiredReviewItemCount",
            marker.preflightReview().requiredReviewItemCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightCompletedReviewItemCount",
            marker.preflightReview().completedReviewItemCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightForbiddenOperationCount",
            marker.preflightReview().forbiddenOperationCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightEndpointHandleOnly",
            marker.preflightReview().endpointHandleOnly()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightCredentialHandleOnly",
            marker.preflightReview().credentialHandleOnly()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightRawEndpointUrlParsed",
            marker.sideEffectBoundary().rawEndpointUrlParsed()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightRawEndpointUrlIncluded",
            marker.sideEffectBoundary().rawEndpointUrlIncluded()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightCredentialValueRead",
            marker.sideEffectBoundary().credentialValueRead()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightExternalRequestSent",
            marker.sideEffectBoundary().externalRequestSent()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightSchemaMigrationExecuted",
            marker.sideEffectBoundary().schemaMigrationExecuted()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightAutomaticUpstreamStart",
            marker.sideEffectBoundary().automaticUpstreamStart()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightConnectsManagedAudit",
            marker.sideEffectBoundary().connectsManagedAudit()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightJavaStarted", marker.sideEffectBoundary().javaStarted()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointHandlePreflightMiniKvStarted",
            marker.sideEffectBoundary().miniKvStarted()));
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
              .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
          marker) {
    return endpointHandleReviewEchoed(marker.preflightReview())
        && credentialHandleReviewEchoed(marker.preflightReview())
        && ownerApprovalArtifactReviewEchoed(marker.preflightReview())
        && networkAllowlistReviewEchoed(marker.networkAllowlistReview())
        && tlsPolicyReviewEchoed(marker.tlsPolicyReview())
        && redactionPolicyEchoed(marker.redactionPolicy())
        && operatorWindowReviewEchoed(marker.operatorWindow())
        && noCredentialConnectionWriteOrAutoStart(marker.sideEffectBoundary())
        && !marker.readyForManagedAuditSandboxAdapterConnection()
        && !marker.readyForProductionAudit()
        && !marker.readyForProductionWindow()
        && !marker.nodeMayTreatAsProductionAuditRecord();
  }
}
