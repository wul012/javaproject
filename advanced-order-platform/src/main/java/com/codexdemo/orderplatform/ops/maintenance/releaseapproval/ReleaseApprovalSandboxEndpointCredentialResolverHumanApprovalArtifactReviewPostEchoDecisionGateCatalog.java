package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoRecords.RehearsalHumanApprovalArtifactReviewPostEchoNoGoCondition;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoRecords.RehearsalHumanApprovalArtifactReviewPostEchoPrerequisite;
import java.util.List;

final
class ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateCatalog {

  private static final List<PrerequisiteTemplate> PREREQUISITES =
      List.of(
          prerequisite(
              "signed-human-approval-artifact",
              "Signed human approval artifact instance",
              "missing: v308 defined the review packet shape, but no signed artifact instance is present"),
          prerequisite(
              "credential-handle-approval",
              "Credential handle approval attestation",
              "missing: credential handle review status is contract-only; no approval attestation is present"),
          prerequisite(
              "endpoint-handle-allowlist-approval",
              "Endpoint handle allowlist approval",
              "missing: endpoint handle allowlist review remains a required artifact field"),
          prerequisite(
              "no-network-safety-fixture",
              "No-network safety fixture",
              "missing: no fixture proves the future runtime path refuses HTTP/TCP before approval"),
          prerequisite(
              "abort-rollback-semantics",
              "Manual abort and rollback semantics",
              "missing: abort and rollback semantics have not been rehearsed for a runtime shell path"),
          prerequisite(
              "java-mini-kv-decision-echo",
              "Java v144 + mini-kv v137 decision echo",
              "missing: upstreams have not echoed the v310 post-echo decision gate yet"));

  private static final List<NoGoTemplate> NO_GO_CONDITIONS =
      List.of(
          noGo(
              "RUNTIME_SHELL_IMPLEMENTATION_REQUESTED",
              "Any next step asks Node to implement runtime shell code."),
          noGo(
              "RUNTIME_SHELL_INVOCATION_REQUESTED",
              "Any next step asks Node to invoke a runtime shell."),
          noGo(
              "CREDENTIAL_VALUE_READ_REQUESTED",
              "Any next step asks Node, Java, or mini-kv to read credential values."),
          noGo(
              "RAW_ENDPOINT_URL_PARSE_REQUESTED",
              "Any next step asks Node to parse or render a raw endpoint URL."),
          noGo(
              "PROVIDER_CLIENT_INSTANTIATION_REQUESTED",
              "Any next step asks Node to instantiate providers or resolver clients."),
          noGo(
              "EXTERNAL_REQUEST_REQUESTED",
              "Any next step asks Node to send HTTP/TCP to managed audit."),
          noGo(
              "LEDGER_OR_SCHEMA_WRITE_REQUESTED",
              "Any next step asks Java or Node to write ledger/schema state."),
          noGo(
              "MINIKV_WRITE_OR_AUTHORITY_REQUESTED",
              "Any next step asks mini-kv to run LOAD/COMPACT/RESTORE/SETNXEX or become authority."),
          noGo(
              "AUTOMATIC_UPSTREAM_START_REQUESTED",
              "Any next step asks Node to automatically start Java, mini-kv, or external audit services."));

  private static final List<String> PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.consumedByNodePostEchoDecisionGateState=human-approval-artifact-review-post-echo-decision-gate-ready",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.prerequisiteCount=6",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.noGoConditionCount=9",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.allowsDisabledRuntimeShellImplementation=false",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.sideEffectBoundary.approvalLedgerWritten=false",
          "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.readyForNodeV311PostEchoDecisionUpstreamEchoVerification=true");

  private static final List<String> NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.consumedByNodePostEchoDecisionGateProfile with Node v310",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.prerequisiteCount=6 before Node v311",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.noGoConditionCount=9 before Node v311",
          "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.readyForNodeV311PostEchoDecisionUpstreamEchoVerification=true before Node v311",
          "Keep managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.sideEffectBoundary.credentialValueRead=false",
          "Keep managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.sideEffectBoundary.runtimeShellInvocationAllowed=false");

  private static final List<String> WARNING_CODES =
      List.of("POST_ECHO_DECISION_DOES_NOT_AUTHORIZE_RUNTIME");

  private static final List<String> RECOMMENDATION_CODES =
      List.of("RUN_JAVA_V144_AND_MINIKV_V137_IN_PARALLEL", "KEEP_RUNTIME_SHELL_BLOCKED");

  private static final List<String> NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v137 human approval artifact review post-echo decision gate non-participation receipt",
          "Node v311 human approval artifact review post-echo decision upstream echo verification");

  private
  ReleaseApprovalSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateCatalog() {}

  static List<RehearsalHumanApprovalArtifactReviewPostEchoPrerequisite> requiredPrerequisites() {
    return PREREQUISITES.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewPostEchoPrerequisite(
                    template.id(),
                    template.label(),
                    template.currentEvidence(),
                    "documented-missing",
                    true))
        .toList();
  }

  static List<RehearsalHumanApprovalArtifactReviewPostEchoNoGoCondition> noGoConditions() {
    return NO_GO_CONDITIONS.stream()
        .map(
            template ->
                new RehearsalHumanApprovalArtifactReviewPostEchoNoGoCondition(
                    template.code(),
                    template.condition(),
                    "pause-and-do-not-implement-runtime-shell"))
        .toList();
  }

  static List<String> prerequisiteIds() {
    return PREREQUISITES.stream().map(PrerequisiteTemplate::id).toList();
  }

  static List<String> noGoConditionCodes() {
    return NO_GO_CONDITIONS.stream().map(NoGoTemplate::code).toList();
  }

  static List<String> proofClaims() {
    return PROOF_CLAIMS;
  }

  static List<String> nodeVerificationActions() {
    return NODE_VERIFICATION_ACTIONS;
  }

  static List<String> warningCodes() {
    return WARNING_CODES;
  }

  static List<String> recommendationCodes() {
    return RECOMMENDATION_CODES;
  }

  static List<String> nextRequiredEchoVersions() {
    return NEXT_REQUIRED_ECHO_VERSIONS;
  }

  private static PrerequisiteTemplate prerequisite(
      String id, String label, String currentEvidence) {
    return new PrerequisiteTemplate(id, label, currentEvidence);
  }

  private static NoGoTemplate noGo(String code, String condition) {
    return new NoGoTemplate(code, condition);
  }

  private record PrerequisiteTemplate(String id, String label, String currentEvidence) {}

  private record NoGoTemplate(String code, String condition) {}
}
