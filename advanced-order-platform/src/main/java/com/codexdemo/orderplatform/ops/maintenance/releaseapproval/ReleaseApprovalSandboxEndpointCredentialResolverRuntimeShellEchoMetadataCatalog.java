package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords.RehearsalRuntimeShellDecisionNoGoCondition;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords.RehearsalRuntimeShellPostDecisionContinuationOption;
import java.util.List;

final class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog {

  private static final List<String> DECISION_RECORD_REQUIRED_EVIDENCE_IDS =
      List.of(
          "node-v298-upstream-echo-ready",
          "java-v134-echo-ready",
          "mini-kv-v131-receipt-ready",
          "runtime-shell-still-blocked");

  private static final List<NoGoConditionTemplate> DECISION_RECORD_NO_GO_CONDITION_TEMPLATES =
      List.of(
          new NoGoConditionTemplate(
              "RUNTIME_SHELL_IMPLEMENTATION_REQUIRED",
              "The next step would have to implement or invoke a runtime shell."),
          new NoGoConditionTemplate(
              "CREDENTIAL_VALUE_REQUIRED",
              "The next step would have to read, store, render, or test credential values."),
          new NoGoConditionTemplate(
              "RAW_ENDPOINT_URL_REQUIRED",
              "The next step would have to parse or render a raw endpoint URL."),
          new NoGoConditionTemplate(
              "MANAGED_AUDIT_CONNECTION_REQUIRED",
              "The next step would have to open managed audit connectivity."),
          new NoGoConditionTemplate(
              "LEDGER_SCHEMA_WRITE_REQUIRED",
              "The next step would have to write ledger state or execute schema migration SQL."),
          new NoGoConditionTemplate(
              "AUTOSTART_REQUIRED",
              "The next step would have to auto-start Java, mini-kv, or managed audit services."));

  private static final List<String> DECISION_RECORD_PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.consumedByNodeRuntimeShellCandidateGateDecisionRecordState=runtime-shell-candidate-gate-decision-record-ready",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.decision=blocked",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.requiredEvidenceCount=4",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.noGoConditionCount=6",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsDisabledRuntimeShellImplementation=false",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsCredentialValueRead=false",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.sideEffectBoundary.externalRequestSent=false",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.readyForNodeV300RuntimeShellDecisionRecordUpstreamEchoVerification=true");

  private static final List<String> DECISION_RECORD_NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.consumedByNodeRuntimeShellCandidateGateDecisionRecordProfile with Node v299",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.decision=blocked before Node v300",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.requiredEvidenceCount=4 before Node v300",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.noGoConditionCount=6 before Node v300",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsDisabledRuntimeShellImplementation=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsDisabledRuntimeShellInvocation=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsCredentialValueRead=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsRawEndpointUrlParse=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsExternalRequest=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsApprovalLedgerWrite=false");

  private static final List<String> DECISION_RECORD_NODE_WARNING_CODES =
      List.of(
          "DECISION_RECORD_ONLY_DOES_NOT_AUTHORIZE_RUNTIME",
          "NODE_V300_REQUIRES_JAVA_V135_AND_MINI_KV_V132");

  private static final List<String> DECISION_RECORD_NODE_RECOMMENDATION_CODES =
      List.of("RUN_PARALLEL_JAVA_V135_MINI_KV_V132", "KEEP_NODE_V300_BEHIND_PARALLEL_EVIDENCE");

  private static final List<String> DECISION_RECORD_NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v132 runtime shell decision record non-participation receipt",
          "Node v300 runtime shell decision record upstream echo verification");

  private static final List<ContinuationOptionTemplate>
      POST_DECISION_PLAN_INTAKE_CONTINUATION_OPTION_TEMPLATES =
          List.of(
              new ContinuationOptionTemplate(
                  "CONTINUE_BLOCKED_PLANNING",
                  "Continue blocked planning",
                  "selected",
                  "v300 proved upstream agreement on the blocked decision, so the next safe step is read-only echo of this continuation intake.",
                  List.of(
                      "write-v301-intake",
                      "request-java-v136-echo",
                      "request-mini-kv-v133-non-participation"),
                  List.of(
                      "implement-runtime-shell",
                      "invoke-runtime-shell",
                      "open-managed-audit-connection")),
              new ContinuationOptionTemplate(
                  "PAUSE_RUNTIME_SHELL_CHAIN",
                  "Pause runtime shell chain",
                  "documented-alternative",
                  "This remains valid if the next echo would not be consumed, but v302 has a narrow consumer for Java v136 and mini-kv v133.",
                  List.of("archive-v301-as-paused", "return-to-quality-work"),
                  List.of("treat-pause-as-production-approval")),
              new ContinuationOptionTemplate(
                  "REQUIRE_EXPLICIT_APPROVAL_PREREQUISITES",
                  "Require explicit approval prerequisites",
                  "documented-alternative",
                  "Future approval prerequisites can be proposed, but v301 has no credential, endpoint, provider, or operator-window approval to unlock runtime.",
                  List.of("list-approval-prerequisites", "keep-prerequisites-read-only"),
                  List.of(
                      "read-credential-value",
                      "parse-raw-endpoint-url",
                      "instantiate-provider-client")),
              new ContinuationOptionTemplate(
                  "IMPLEMENT_RUNTIME_SHELL_NOW",
                  "Implement runtime shell now",
                  "rejected",
                  "v300 aligned a blocked decision record only; it did not approve implementation, invocation, network, credential, or write boundaries.",
                  List.of(),
                  List.of(
                      "implement-runtime-shell", "invoke-runtime-shell",
                      "send-external-request", "write-ledger-or-schema")));

  private static final List<String> POST_DECISION_PLAN_INTAKE_PROOF_CLAIMS =
      List.of(
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.consumedByNodeRuntimeShellPostDecisionPlanIntakeState=runtime-shell-post-decision-continuation-plan-intake-ready",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.selectedContinuationDecision=continue-blocked-planning",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.decisionOptionCount=4",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.rejectedRuntimeImplementationOptionCount=1",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.necessityProof.proofComplete=true",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.runtimeShellImplementationAllowed=false",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.externalRequestAllowed=false",
          "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.readyForNodeV302PostDecisionPlanIntakeUpstreamEchoVerification=true");

  private static final List<String> POST_DECISION_PLAN_INTAKE_NODE_VERIFICATION_ACTIONS =
      List.of(
          "Compare managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.consumedByNodeRuntimeShellPostDecisionPlanIntakeProfile with Node v301",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.selectedContinuationDecision=continue-blocked-planning before Node v302",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.decisionOptionCount=4 before Node v302",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.rejectedRuntimeImplementationOptionCount=1 before Node v302",
          "Require managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.necessityProof.proofComplete=true before Node v302",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.runtimeShellImplementationAllowed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.runtimeShellInvocationAllowed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.credentialValueReadAllowed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.externalRequestAllowed=false",
          "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.approvalLedgerWriteAllowed=false");

  private static final List<String> POST_DECISION_PLAN_INTAKE_NODE_WARNING_CODES =
      List.of(
          "CONTINUATION_PLAN_DOES_NOT_AUTHORIZE_RUNTIME",
          "NODE_V302_REQUIRES_JAVA_V136_AND_MINI_KV_V133");

  private static final List<String> POST_DECISION_PLAN_INTAKE_NODE_RECOMMENDATION_CODES =
      List.of("REQUEST_PARALLEL_JAVA_MINI_KV_ECHO", "STOP_CHAIN_AFTER_V302_WITHOUT_NEW_BLOCKER");

  private static final List<String> POST_DECISION_PLAN_INTAKE_NEXT_REQUIRED_ECHO_VERSIONS =
      List.of(
          "mini-kv v133 runtime shell post-decision plan intake non-participation receipt",
          "Node v302 post-decision plan intake upstream echo verification");

  private ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog() {}

  static List<String> decisionRecordRequiredEvidenceIds() {
    return DECISION_RECORD_REQUIRED_EVIDENCE_IDS;
  }

  static List<String> decisionRecordNoGoConditionCodes() {
    return DECISION_RECORD_NO_GO_CONDITION_TEMPLATES.stream()
        .map(NoGoConditionTemplate::code)
        .toList();
  }

  static List<RehearsalRuntimeShellDecisionNoGoCondition> decisionRecordNoGoConditions() {
    return DECISION_RECORD_NO_GO_CONDITION_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalRuntimeShellDecisionNoGoCondition(
                    template.code(),
                    template.condition(),
                    "pause-and-do-not-implement-runtime-shell"))
        .toList();
  }

  static List<String> decisionRecordProofClaims() {
    return DECISION_RECORD_PROOF_CLAIMS;
  }

  static List<String> decisionRecordNodeVerificationActions() {
    return DECISION_RECORD_NODE_VERIFICATION_ACTIONS;
  }

  static List<String> decisionRecordNodeWarningCodes() {
    return DECISION_RECORD_NODE_WARNING_CODES;
  }

  static List<String> decisionRecordNodeRecommendationCodes() {
    return DECISION_RECORD_NODE_RECOMMENDATION_CODES;
  }

  static List<String> decisionRecordNextRequiredEchoVersions() {
    return DECISION_RECORD_NEXT_REQUIRED_ECHO_VERSIONS;
  }

  static List<String> postDecisionPlanIntakeContinuationOptionCodes() {
    return POST_DECISION_PLAN_INTAKE_CONTINUATION_OPTION_TEMPLATES.stream()
        .map(ContinuationOptionTemplate::code)
        .toList();
  }

  static List<RehearsalRuntimeShellPostDecisionContinuationOption>
      postDecisionPlanIntakeContinuationOptions() {
    return POST_DECISION_PLAN_INTAKE_CONTINUATION_OPTION_TEMPLATES.stream()
        .map(
            template ->
                new RehearsalRuntimeShellPostDecisionContinuationOption(
                    template.code(),
                    template.title(),
                    template.status(),
                    template.rationale(),
                    template.allowedActions(),
                    template.prohibitedActions()))
        .toList();
  }

  static List<String> postDecisionPlanIntakeProofClaims() {
    return POST_DECISION_PLAN_INTAKE_PROOF_CLAIMS;
  }

  static List<String> postDecisionPlanIntakeNodeVerificationActions() {
    return POST_DECISION_PLAN_INTAKE_NODE_VERIFICATION_ACTIONS;
  }

  static List<String> postDecisionPlanIntakeNodeWarningCodes() {
    return POST_DECISION_PLAN_INTAKE_NODE_WARNING_CODES;
  }

  static List<String> postDecisionPlanIntakeNodeRecommendationCodes() {
    return POST_DECISION_PLAN_INTAKE_NODE_RECOMMENDATION_CODES;
  }

  static List<String> postDecisionPlanIntakeNextRequiredEchoVersions() {
    return POST_DECISION_PLAN_INTAKE_NEXT_REQUIRED_ECHO_VERSIONS;
  }

  private record NoGoConditionTemplate(String code, String condition) {}

  private record ContinuationOptionTemplate(
      String code,
      String title,
      String status,
      String rationale,
      List<String> allowedActions,
      List<String> prohibitedActions) {}
}
