package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static com.codexdemo.orderplatform.ops.maintenance.releaseapproval.DisabledPrecheckRules.*;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords.RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho;
import java.util.List;

final class DisabledPrecheckBuilder {

  private static final String WARNING_DIGEST_WARNING_INPUT_NAME =
      "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerWarnings";

  private static final List<String> WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
      List.of(
          "sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest",
          "sandboxEndpointCredentialResolverDisabledPrecheckRequiredEnvHandleCount",
          "sandboxEndpointCredentialResolverDisabledPrecheckOptInGateCount",
          "sandboxEndpointCredentialResolverDisabledPrecheckFailureClassCount",
          "sandboxEndpointCredentialResolverDisabledPrecheckDryRunResponseFieldCount",
          "sandboxEndpointCredentialResolverDisabledPrecheckInheritedNoGoConditionCount",
          "sandboxEndpointCredentialResolverDisabledPrecheckResolverClientMayBeInstantiated",
          "sandboxEndpointCredentialResolverDisabledPrecheckSecretProviderMayBeInstantiated",
          "sandboxEndpointCredentialResolverDisabledPrecheckCredentialValueMayBeLoaded",
          "sandboxEndpointCredentialResolverDisabledPrecheckRawEndpointUrlMayBeParsed",
          "sandboxEndpointCredentialResolverDisabledPrecheckExternalRequestMayBeSent",
          "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectCredentialValueRead",
          "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectRawEndpointUrlParsed",
          "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectExternalRequestSent");

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandleCount=6",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.optInGateCount=2",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.failureClassCount=7",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseFieldCount=12",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.inheritedNoGoConditionCount=9",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.resolverClientMayBeInstantiated=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.secretProviderMayBeInstantiated=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.credentialValueMayBeLoaded=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.rawEndpointUrlMayBeParsed=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.externalRequestMayBeSent=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.externalRequestSent=false",
          "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForManagedAuditSandboxAdapterConnection=false");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile with Node v262",
          "Require managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification=true before Node v263",
          "Verify managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandleCount=6",
          "Verify managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.optInGateCount=2",
          "Verify managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.failureClassCount=7",
          "Verify managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.dryRunResponseFieldCount=12",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.resolverClientMayBeInstantiated=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.secretProviderMayBeInstantiated=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.credentialValueMayBeLoaded=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.rawEndpointUrlMayBeParsed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.externalRequestSent=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.connectsManagedAudit=false");

  private static final MarkerEvidence EVIDENCE =
      new MarkerEvidence(
          WARNING_DIGEST_WARNING_INPUT_NAME,
          WARNING_DIGEST_BOUNDARY_INPUT_NAMES,
          PROOF_CLAIMS,
          NODE_VERIFICATION_ACTIONS);

  RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker build(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
          decisionEchoMarker) {
    SourceGate sourceGate = SourceGate.from(decisionEchoMarker);
    RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSourceEcho sourceNodeV261 =
        sourceNodeV261(sourceGate);
    RehearsalSandboxEndpointCredentialResolverDisabledPrecheckRecord disabledPrecheck =
        disabledPrecheck();
    RehearsalSandboxEndpointCredentialResolverDisabledPrecheckSideEffectBoundary
        sideEffectBoundary = sideEffectBoundary();
    EchoReadiness readiness =
        EchoReadiness.from(sourceNodeV261, disabledPrecheck, sideEffectBoundary);
    List<String> markerWarnings = markerWarnings(readiness);

    String markerDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line(
                    "markerVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_ECHO_MARKER_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "sourceCredentialResolverDecisionEchoMarkerVersion",
                    decisionEchoMarker.markerVersion()),
                ReleaseApprovalDigestSupport.line(
                    "sourceCredentialResolverDecisionEchoMarkerSchemaVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_ECHO_MARKER_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile",
                    ReleaseApprovalUpstreamContractConstants
                        .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_PROFILE),
                ReleaseApprovalDigestSupport.line("precheckMode", PRECHECK_MODE),
                ReleaseApprovalDigestSupport.line("sourceSpan", SOURCE_SPAN),
                ReleaseApprovalDigestSupport.line("sourceNodeV261", sourceNodeV261),
                ReleaseApprovalDigestSupport.line("disabledPrecheck", disabledPrecheck),
                ReleaseApprovalDigestSupport.line("sideEffectBoundary", sideEffectBoundary),
                ReleaseApprovalDigestSupport.line(
                    "readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification",
                    readiness.readyForNodeV263())));

    return new RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_ECHO_MARKER_VERSION,
        decisionEchoMarker.markerVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_ECHO_MARKER_SCHEMA_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_MARKDOWN_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V262_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V263_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V263_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DISABLED_PRECHECK_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        true,
        PRECHECK_MODE,
        SOURCE_SPAN,
        sourceNodeV261,
        disabledPrecheck,
        sideEffectBoundary,
        readiness.sourceNodeV261Echoed(),
        readiness.envHandlesEchoed(),
        readiness.optInGatesEchoed(),
        readiness.failureTaxonomyEchoed(),
        readiness.dryRunResponseShapeEchoed(),
        readiness.inheritedNoGoConditionsEchoed(),
        readiness.resolverImplementationAbsentEchoed(),
        readiness.secretProviderAbsentEchoed(),
        readiness.sideEffectBoundaryEchoed(),
        readiness.upstreamActionsStillDisabledEchoed(),
        readiness.readyForNodeV263(),
        false,
        false,
        false,
        false,
        markerDigest,
        REQUIRED_ENV_HANDLE_NAMES,
        OPT_IN_GATE_NAMES,
        FAILURE_CLASS_CODES,
        DRY_RUN_RESPONSE_FIELDS,
        INHERITED_NO_GO_CONDITIONS,
        NODE_WARNING_CODES,
        NODE_RECOMMENDATION_CODES,
        NEXT_REQUIRED_ECHO_VERSIONS,
        markerWarnings,
        EVIDENCE.nodeActions());
  }

  MarkerEvidence evidence() {
    return EVIDENCE;
  }

  List<String> warningDigestWarningLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker marker) {
    return EVIDENCE.warningLines(marker.markerWarnings());
  }

  List<String> warningDigestBoundaryLines(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker marker) {
    return List.of(
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest",
            marker.markerDigest()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckRequiredEnvHandleCount",
            marker.disabledPrecheck().requiredEnvHandleCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckOptInGateCount",
            marker.disabledPrecheck().optInGateCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckFailureClassCount",
            marker.disabledPrecheck().failureClassCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckDryRunResponseFieldCount",
            marker.disabledPrecheck().dryRunResponseFieldCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckInheritedNoGoConditionCount",
            marker.disabledPrecheck().inheritedNoGoConditionCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckResolverClientMayBeInstantiated",
            marker.disabledPrecheck().resolverClientMayBeInstantiated()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckSecretProviderMayBeInstantiated",
            marker.disabledPrecheck().secretProviderMayBeInstantiated()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckCredentialValueMayBeLoaded",
            marker.disabledPrecheck().credentialValueMayBeLoaded()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckRawEndpointUrlMayBeParsed",
            marker.disabledPrecheck().rawEndpointUrlMayBeParsed()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckExternalRequestMayBeSent",
            marker.disabledPrecheck().externalRequestMayBeSent()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectCredentialValueRead",
            marker.sideEffectBoundary().credentialValueRead()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectRawEndpointUrlParsed",
            marker.sideEffectBoundary().rawEndpointUrlParsed()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectExternalRequestSent",
            marker.sideEffectBoundary().externalRequestSent()));
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker marker) {
    return disabledPrecheckSideEffectsBlocked(
            marker.disabledPrecheck(), marker.sideEffectBoundary())
        && !marker.readyForManagedAuditSandboxAdapterConnection()
        && !marker.readyForProductionAudit()
        && !marker.readyForProductionWindow()
        && !marker.nodeMayTreatAsProductionAuditRecord();
  }
}
