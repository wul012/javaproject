package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static com.codexdemo.orderplatform.ops.maintenance.releaseapproval.DecisionMarkerRules.*;

import java.util.List;

final class DecisionMarkerBuilder {

  private static final String WARNING_DIGEST_WARNING_INPUT_NAME =
      "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerWarnings";

  private static final List<String> WARNING_DIGEST_BOUNDARY_INPUT_NAMES =
      List.of(
          "sandboxEndpointCredentialResolverDecisionEchoMarkerDigest",
          "sandboxEndpointCredentialResolverDecisionRequiredFieldCount",
          "sandboxEndpointCredentialResolverDecisionNoGoConditionCount",
          "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeRead",
          "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeLoaded",
          "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeStored",
          "sandboxEndpointCredentialResolverDecisionRawEndpointUrlMayBeParsed",
          "sandboxEndpointCredentialResolverDecisionExternalRequestMayBeSent",
          "sandboxEndpointCredentialResolverDecisionManagedAuditConnectionMayOpen",
          "sandboxEndpointCredentialResolverDecisionSchemaMigrationMayExecute",
          "sandboxEndpointCredentialResolverDecisionApprovalLedgerMayBeWritten",
          "sandboxEndpointCredentialResolverDecisionJavaOrMiniKvStartAllowed");

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFieldCount=8",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.explicitNoGoConditionCount=9",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverMode=policy-record-only-no-value-read",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverCandidateImplementation=not-implemented",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeLoaded=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.rawEndpointUrlMayBeParsed=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.managedAuditConnectionMayOpen=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.externalRequestMayBeSent=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.approvalLedgerMayBeWritten=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.credentialValueRead=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false",
          "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForManagedAuditSandboxAdapterConnection=false");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile with Node v260",
          "Require managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification=true before Node v261",
          "Verify managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverMode=policy-record-only-no-value-read",
          "Verify managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.resolverPolicyHandle is handle-only",
          "Verify managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFieldCount=8",
          "Verify managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.explicitNoGoConditionCount=9",
          "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeLoaded=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.rawEndpointUrlMayBeParsed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.externalRequestSent=false",
          "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.connectsManagedAudit=false");

  private static final MarkerEvidence EVIDENCE =
      new MarkerEvidence(
          WARNING_DIGEST_WARNING_INPUT_NAME,
          WARNING_DIGEST_BOUNDARY_INPUT_NAMES,
          PROOF_CLAIMS,
          NODE_VERIFICATION_ACTIONS);

  ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
          .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
      build(
          ReleaseApprovalSandboxConnectionAdapterPreflightEchoRecords
                  .RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
              endpointHandlePreflightEchoMarker) {
    boolean sourceAccepted = sourceMarkerAccepted(endpointHandlePreflightEchoMarker);
    ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
            .RehearsalSandboxEndpointCredentialResolverSourceEcho
        sourceNodeV259 = sourceNodeV259(sourceAccepted);
    ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
            .RehearsalSandboxEndpointCredentialResolverDecisionRecord
        decisionRecord = decisionRecord();
    ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
            .RehearsalSandboxEndpointCredentialResolverSideEffectBoundary
        sideEffectBoundary = sideEffectBoundary();

    List<String> markerWarnings =
        ReleaseApprovalDigestSupport.warnings(
            ReleaseApprovalDigestSupport.warningIf(
                !sourceAccepted,
                "NODE_V261_SOURCE_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_ECHO_MARKER_NOT_READY"));

    boolean sourceNodeV259Echoed = sourceNodeV259Ready(sourceNodeV259);
    boolean decisionFieldsEchoed = decisionFieldsEchoed(decisionRecord);
    boolean endpointHandleEchoed = endpointHandleEchoed(decisionRecord);
    boolean credentialHandleEchoed = credentialHandleEchoed(decisionRecord);
    boolean resolverPolicyEchoed = resolverPolicyEchoed(decisionRecord);
    boolean approvalMarkerEchoed = approvalMarkerEchoed(decisionRecord);
    boolean operatorIdentityRequirementEchoed = operatorIdentityRequirementEchoed(decisionRecord);
    boolean approvalCorrelationRequirementEchoed =
        approvalCorrelationRequirementEchoed(decisionRecord);
    boolean redactionPolicyEchoed = redactionPolicyEchoed(decisionRecord);
    boolean fallbackRotationPlanEchoed = fallbackRotationPlanEchoed(decisionRecord);
    boolean explicitNoGoConditionsEchoed = explicitNoGoConditionsEchoed(decisionRecord);
    boolean sideEffectBoundaryEchoed = noCredentialConnectionWriteOrAutoStart(sideEffectBoundary);
    boolean readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification =
        sourceNodeV259Echoed
            && decisionFieldsEchoed
            && endpointHandleEchoed
            && credentialHandleEchoed
            && resolverPolicyEchoed
            && approvalMarkerEchoed
            && operatorIdentityRequirementEchoed
            && approvalCorrelationRequirementEchoed
            && redactionPolicyEchoed
            && fallbackRotationPlanEchoed
            && explicitNoGoConditionsEchoed
            && sideEffectBoundaryEchoed;

    String markerDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line(
                    "markerVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_ECHO_MARKER_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "sourceEndpointHandlePreflightEchoMarkerVersion",
                    endpointHandlePreflightEchoMarker.markerVersion()),
                ReleaseApprovalDigestSupport.line(
                    "sourceEndpointHandlePreflightEchoMarkerSchemaVersion",
                    ReleaseApprovalContractConstants
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_ECHO_MARKER_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile",
                    ReleaseApprovalUpstreamContractConstants
                        .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_PROFILE),
                ReleaseApprovalDigestSupport.line("recordMode", RECORD_MODE),
                ReleaseApprovalDigestSupport.line("sourceSpan", SOURCE_SPAN),
                ReleaseApprovalDigestSupport.line("sourceNodeV259", sourceNodeV259),
                ReleaseApprovalDigestSupport.line("decisionRecord", decisionRecord),
                ReleaseApprovalDigestSupport.line("sideEffectBoundary", sideEffectBoundary),
                ReleaseApprovalDigestSupport.line(
                    "readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification",
                    readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification)));

    return new ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_ECHO_MARKER_VERSION,
        endpointHandlePreflightEchoMarker.markerVersion(),
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE_PREFLIGHT_ECHO_MARKER_SCHEMA_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_MARKDOWN_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V260_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_DECISION_RECORD_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_ENDPOINT,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V259_SANDBOX_ENDPOINT_HANDLE_UPSTREAM_ECHO_VERIFICATION_STATE,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_VERSION,
        ReleaseApprovalUpstreamContractConstants
            .NODE_V261_SANDBOX_ENDPOINT_CREDENTIAL_RESOLVER_UPSTREAM_ECHO_VERIFICATION_PROFILE,
        true,
        RECORD_MODE,
        SOURCE_SPAN,
        sourceNodeV259,
        decisionRecord,
        sideEffectBoundary,
        sourceNodeV259Echoed,
        decisionFieldsEchoed,
        endpointHandleEchoed,
        credentialHandleEchoed,
        resolverPolicyEchoed,
        approvalMarkerEchoed,
        operatorIdentityRequirementEchoed,
        approvalCorrelationRequirementEchoed,
        redactionPolicyEchoed,
        fallbackRotationPlanEchoed,
        explicitNoGoConditionsEchoed,
        sideEffectBoundaryEchoed,
        readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification,
        false,
        false,
        false,
        false,
        markerDigest,
        REQUIRED_DECISION_FIELD_IDS,
        EXPLICIT_NO_GO_CONDITION_CODES,
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
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
          marker) {
    return EVIDENCE.warningLines(marker.markerWarnings());
  }

  List<String> warningDigestBoundaryLines(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
          marker) {
    return List.of(
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionEchoMarkerDigest", marker.markerDigest()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionRequiredFieldCount",
            marker.decisionRecord().requiredDecisionFieldCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionNoGoConditionCount",
            marker.decisionRecord().explicitNoGoConditionCount()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeRead",
            marker.decisionRecord().credentialValueMayBeRead()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeLoaded",
            marker.decisionRecord().credentialValueMayBeLoaded()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeStored",
            marker.decisionRecord().credentialValueMayBeStored()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionRawEndpointUrlMayBeParsed",
            marker.decisionRecord().rawEndpointUrlMayBeParsed()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionExternalRequestMayBeSent",
            marker.decisionRecord().externalRequestMayBeSent()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionManagedAuditConnectionMayOpen",
            marker.decisionRecord().managedAuditConnectionMayOpen()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionSchemaMigrationMayExecute",
            marker.decisionRecord().schemaMigrationMayExecute()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionApprovalLedgerMayBeWritten",
            marker.decisionRecord().approvalLedgerMayBeWritten()),
        ReleaseApprovalDigestSupport.line(
            "sandboxEndpointCredentialResolverDecisionJavaOrMiniKvStartAllowed",
            marker.decisionRecord().nodeMayStartJavaOrMiniKv()));
  }

  boolean noCredentialConnectionWriteOrAutoStartProved(
      ReleaseApprovalSandboxEndpointCredentialResolverDecisionEchoRecords
              .RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker
          marker) {
    return decisionFieldsEchoed(marker.decisionRecord())
        && endpointHandleEchoed(marker.decisionRecord())
        && credentialHandleEchoed(marker.decisionRecord())
        && resolverPolicyEchoed(marker.decisionRecord())
        && approvalMarkerEchoed(marker.decisionRecord())
        && operatorIdentityRequirementEchoed(marker.decisionRecord())
        && approvalCorrelationRequirementEchoed(marker.decisionRecord())
        && redactionPolicyEchoed(marker.decisionRecord())
        && fallbackRotationPlanEchoed(marker.decisionRecord())
        && explicitNoGoConditionsEchoed(marker.decisionRecord())
        && noCredentialConnectionWriteOrAutoStart(marker.sideEffectBoundary())
        && !marker.readyForManagedAuditSandboxAdapterConnection()
        && !marker.readyForProductionAudit()
        && !marker.readyForProductionWindow()
        && !marker.nodeMayTreatAsProductionAuditRecord();
  }
}
