package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;

final class ReleaseApprovalRehearsalInputHardeningDecisionEchoBuilder {

  ReleaseApprovalRehearsalResponseRecords.RehearsalInputHardeningDecisionEcho build(
      ReleaseApprovalRehearsalResponseRecords.RehearsalEvidenceExportHint evidenceExportHint) {
    InputHardeningDecisionFlags flags = InputHardeningDecisionFlags.readOnlyInputHardeningEcho();
    return new ReleaseApprovalRehearsalResponseRecords.RehearsalInputHardeningDecisionEcho(
        ReleaseApprovalContractConstants
            .RELEASE_APPROVAL_REHEARSAL_INPUT_HARDENING_DECISION_ECHO_VERSION,
        "Node v329",
        ReleaseApprovalUpstreamContractConstants.NODE_V329_INPUT_HARDENING_DECISION_PROFILE_VERSION,
        ReleaseApprovalUpstreamContractConstants.NODE_V329_INPUT_HARDENING_DECISION_STATE,
        ReleaseApprovalUpstreamContractConstants.NODE_V329_INPUT_HARDENING_DECISION,
        ReleaseApprovalUpstreamContractConstants.NODE_V329_INPUT_HARDENING_DECISION_DIGEST,
        "D:\\nodeproj\\orderops-node\\d\\329\\evidence\\implementation-candidate-gate-input-hardening-decision-v329-http.json",
        evidenceExportHint.exportHintVersion(),
        evidenceExportHint.exportMode(),
        flags.readOnlyEcho(),
        flags.consumesNodeV329(),
        flags.stableJavaEvidenceExportAvailable(),
        flags.readyForNodeV330CandidateGateUpstreamAlignment(),
        flags.readyForDisabledRuntimeShellDesignDraft(),
        flags.readyForRuntimeShellImplementation(),
        flags.requiresCredentialValue(),
        flags.parsesRawEndpointUrl(),
        flags.opensManagedAuditConnection(),
        flags.executesNetworkRequest(),
        flags.writesApprovalLedger(),
        flags.executesSchemaMigration(),
        flags.triggersDeploymentOrRollback(),
        flags.startsUpstreamProcess(),
        List.of("java-stable-evidence-export"),
        List.of(
            "mini-kv-stable-current-receipt",
            "node-fail-closed-diagnostics",
            "route-evidence-consumability"),
        List.of(
            "CREDENTIAL_VALUE_REQUIRED",
            "RAW_ENDPOINT_URL_REQUIRED",
            "PROVIDER_OR_CLIENT_REQUIRED",
            "NETWORK_REQUEST_REQUIRED",
            "JAVA_WRITE_REQUIRED",
            "MINI_KV_WRITE_OR_ADMIN_REQUIRED",
            "AUTO_START_REQUIRED"),
        List.of(
            "Compare inputHardeningDecisionEcho.sourceProfileVersion with Node v329 profileVersion",
            "Require inputHardeningDecisionEcho.stableJavaEvidenceExportAvailable=true before Node v330",
            "Keep inputHardeningDecisionEcho.readyForDisabledRuntimeShellDesignDraft=false",
            "Keep inputHardeningDecisionEcho.executesNetworkRequest=false",
            "Keep inputHardeningDecisionEcho.writesApprovalLedger=false"));
  }

  private record InputHardeningDecisionFlags(
      boolean readOnlyEcho,
      boolean consumesNodeV329,
      boolean stableJavaEvidenceExportAvailable,
      boolean readyForNodeV330CandidateGateUpstreamAlignment,
      boolean readyForDisabledRuntimeShellDesignDraft,
      boolean readyForRuntimeShellImplementation,
      boolean requiresCredentialValue,
      boolean parsesRawEndpointUrl,
      boolean opensManagedAuditConnection,
      boolean executesNetworkRequest,
      boolean writesApprovalLedger,
      boolean executesSchemaMigration,
      boolean triggersDeploymentOrRollback,
      boolean startsUpstreamProcess) {

    static InputHardeningDecisionFlags readOnlyInputHardeningEcho() {
      return new InputHardeningDecisionFlags(
          true, true, true, false, false, false, false, false, false, false, false, false, false,
          false);
    }
  }
}
