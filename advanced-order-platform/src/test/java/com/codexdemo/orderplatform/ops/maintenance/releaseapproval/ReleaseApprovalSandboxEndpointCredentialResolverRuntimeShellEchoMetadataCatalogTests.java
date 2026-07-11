package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalogTests {

  @Test
  void centralizesRuntimeShellDecisionRecordMetadataInStableOrder() {
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordRequiredEvidenceIds())
        .containsExactly(
            "node-v298-upstream-echo-ready",
            "java-v134-echo-ready",
            "mini-kv-v131-receipt-ready",
            "runtime-shell-still-blocked");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNoGoConditionCodes())
        .containsExactly(
            "RUNTIME_SHELL_IMPLEMENTATION_REQUIRED",
            "CREDENTIAL_VALUE_REQUIRED",
            "RAW_ENDPOINT_URL_REQUIRED",
            "MANAGED_AUDIT_CONNECTION_REQUIRED",
            "LEDGER_SCHEMA_WRITE_REQUIRED",
            "AUTOSTART_REQUIRED");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNoGoConditions())
        .extracting(condition -> condition.action())
        .containsOnly("pause-and-do-not-implement-runtime-shell");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNodeWarningCodes())
        .containsExactly(
            "DECISION_RECORD_ONLY_DOES_NOT_AUTHORIZE_RUNTIME",
            "NODE_V300_REQUIRES_JAVA_V135_AND_MINI_KV_V132");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNodeRecommendationCodes())
        .containsExactly(
            "RUN_PARALLEL_JAVA_V135_MINI_KV_V132", "KEEP_NODE_V300_BEHIND_PARALLEL_EVIDENCE");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNextRequiredEchoVersions())
        .containsExactly(
            "mini-kv v132 runtime shell decision record non-participation receipt",
            "Node v300 runtime shell decision record upstream echo verification");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordProofClaims())
        .contains(
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.decision=blocked",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.readyForNodeV300RuntimeShellDecisionRecordUpstreamEchoVerification=true");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .decisionRecordNodeVerificationActions())
        .contains(
            "Compare managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.consumedByNodeRuntimeShellCandidateGateDecisionRecordProfile with Node v299",
            "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt.decisionRecord.allowsExternalRequest=false");
  }

  @Test
  void centralizesPostDecisionPlanIntakeMetadataInStableOrder() {
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeContinuationOptionCodes())
        .containsExactly(
            "CONTINUE_BLOCKED_PLANNING",
            "PAUSE_RUNTIME_SHELL_CHAIN",
            "REQUIRE_EXPLICIT_APPROVAL_PREREQUISITES",
            "IMPLEMENT_RUNTIME_SHELL_NOW");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeContinuationOptions())
        .extracting(option -> option.status())
        .containsExactly(
            "selected", "documented-alternative", "documented-alternative", "rejected");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeNodeWarningCodes())
        .containsExactly(
            "CONTINUATION_PLAN_DOES_NOT_AUTHORIZE_RUNTIME",
            "NODE_V302_REQUIRES_JAVA_V136_AND_MINI_KV_V133");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeNodeRecommendationCodes())
        .containsExactly(
            "REQUEST_PARALLEL_JAVA_MINI_KV_ECHO", "STOP_CHAIN_AFTER_V302_WITHOUT_NEW_BLOCKER");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeNextRequiredEchoVersions())
        .containsExactly(
            "mini-kv v133 runtime shell post-decision plan intake non-participation receipt",
            "Node v302 post-decision plan intake upstream echo verification");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeProofClaims())
        .contains(
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.selectedContinuationDecision=continue-blocked-planning",
            "managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.readyForNodeV302PostDecisionPlanIntakeUpstreamEchoVerification=true");
    assertThat(
            ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog
                .postDecisionPlanIntakeNodeVerificationActions())
        .contains(
            "Compare managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.consumedByNodeRuntimeShellPostDecisionPlanIntakeProfile with Node v301",
            "Keep managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt.planIntake.externalRequestAllowed=false");
  }
}
