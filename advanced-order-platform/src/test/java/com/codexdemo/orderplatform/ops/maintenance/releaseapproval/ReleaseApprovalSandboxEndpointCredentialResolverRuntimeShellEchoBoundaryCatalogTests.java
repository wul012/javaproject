package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalogTests
    extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void centralizesRuntimeShellDecisionRecordBoundaryNamesAndLines() {
    ReleaseApprovalRehearsalResponse rehearsal =
        readOnlyFixtureService().releaseApprovalRehearsal(headerBackedRehearsalRequest());

    List<String> inputNames =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .decisionRecordWarningDigestBoundaryInputNames();
    List<String> boundaryLines =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .decisionRecordWarningDigestBoundaryLines(
                rehearsal
                    .managedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceipt());

    assertThat(inputNames)
        .containsExactly(
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptDigest",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordState",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordDecision",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRequiredEvidenceCount",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordNoGoConditionCount",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordReadyForNodeV300",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeImplemented",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRuntimeInvocationAllowed",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordCredentialValueRead",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordRawEndpointUrlParsed",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordExternalRequestSent",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSecretProviderInstantiated",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordResolverClientInstantiated",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordApprovalLedgerWritten",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSqlExecuted",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordSchemaMigrationExecuted",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordAutomaticUpstreamStart");
    assertThat(boundaryLines).hasSize(inputNames.size());
    assertThat(boundaryLines)
        .contains(
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordState=runtime-shell-candidate-gate-decision-record-ready",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordDecision=blocked",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordReadyForNodeV300=true",
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordApprovalLedgerWritten=false");
    assertThat(boundaryLines.getLast())
        .isEqualTo(
            "sandboxEndpointCredentialResolverRuntimeShellDecisionRecordAutomaticUpstreamStart=false");
  }

  @Test
  void centralizesPostDecisionPlanIntakeBoundaryNamesAndLines() {
    ReleaseApprovalRehearsalResponse rehearsal =
        readOnlyFixtureService().releaseApprovalRehearsal(headerBackedRehearsalRequest());

    List<String> inputNames =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .postDecisionPlanIntakeWarningDigestBoundaryInputNames();
    List<String> boundaryLines =
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .postDecisionPlanIntakeWarningDigestBoundaryLines(
                rehearsal
                    .managedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceipt());

    assertThat(inputNames)
        .containsExactly(
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptDigest",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeState",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedContinuationDecision",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionDecisionOptionCount",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedDecisionOptionCount",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRejectedRuntimeImplementationOptionCount",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionReadyForNodeV302",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeImplemented",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRuntimeInvocationAllowed",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionCredentialValueRead",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionRawEndpointUrlParsed",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionExternalRequestSent",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSecretProviderInstantiated",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionResolverClientInstantiated",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionApprovalLedgerWritten",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSqlExecuted",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSchemaMigrationExecuted",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionAutomaticUpstreamStart");
    assertThat(boundaryLines).hasSize(inputNames.size());
    assertThat(boundaryLines)
        .contains(
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeState=runtime-shell-post-decision-continuation-plan-intake-ready",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionSelectedContinuationDecision=continue-blocked-planning",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionReadyForNodeV302=true",
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionApprovalLedgerWritten=false");
    assertThat(boundaryLines.getLast())
        .isEqualTo(
            "sandboxEndpointCredentialResolverRuntimeShellPostDecisionAutomaticUpstreamStart=false");
  }

  @Test
  void centralizesReadOnlyRuntimeShellSideEffectBoundaryDefaults() {
    assertRuntimeShellBoundaryClosed(
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .decisionRecordSideEffectBoundary());
    assertRuntimeShellBoundaryClosed(
        ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog
            .postDecisionPlanIntakeSideEffectBoundary());
  }

  private static void assertRuntimeShellBoundaryClosed(
      com.codexdemo.orderplatform.ops.maintenance.releaseapproval
              .ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoRecords
              .RehearsalRuntimeShellDecisionRecordSideEffectBoundary
          boundary) {
    assertThat(boundary.decisionRecordEchoOnly()).isTrue();
    assertThat(boundary.readOnlyDecisionRecordEcho()).isTrue();
    assertRuntimeBlocked(
        boundary.disabledRuntimeShellImplemented(),
        boundary.disabledRuntimeShellEnabled(),
        boundary.disabledRuntimeShellInvocationAllowed(),
        boundary.managedAuditResolverImplementationAllowed(),
        boundary.productionAuditAllowed(),
        boundary.productionWindowAllowed(),
        boundary.executionAllowed(),
        boundary.connectsManagedAudit(),
        boundary.readsManagedAuditCredential(),
        boundary.storesManagedAuditCredential(),
        boundary.credentialValueRead(),
        boundary.credentialValueProvided(),
        boundary.rawEndpointUrlParsed(),
        boundary.rawEndpointUrlRendered(),
        boundary.externalRequestSent(),
        boundary.secretProviderInstantiated(),
        boundary.resolverClientInstantiated(),
        boundary.fakeSecretProviderInstantiated(),
        boundary.fakeResolverClientInstantiated(),
        boundary.approvalLedgerWritten(),
        boundary.managedAuditStoreWritten(),
        boundary.sqlExecuted(),
        boundary.schemaMigrationExecuted(),
        boundary.rollbackExecuted(),
        boundary.automaticUpstreamStart(),
        boundary.javaStartedNodeMiniKvOrHarness());
  }

  private static void assertRuntimeShellBoundaryClosed(
      com.codexdemo.orderplatform.ops.maintenance.releaseapproval
              .ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoRecords
              .RehearsalRuntimeShellPostDecisionPlanIntakeSideEffectBoundary
          boundary) {
    assertThat(boundary.planIntakeEchoOnly()).isTrue();
    assertThat(boundary.readOnlyPlanIntakeEcho()).isTrue();
    assertRuntimeBlocked(
        boundary.disabledRuntimeShellImplemented(),
        boundary.disabledRuntimeShellEnabled(),
        boundary.disabledRuntimeShellInvocationAllowed(),
        boundary.managedAuditResolverImplementationAllowed(),
        boundary.productionAuditAllowed(),
        boundary.productionWindowAllowed(),
        boundary.executionAllowed(),
        boundary.connectsManagedAudit(),
        boundary.readsManagedAuditCredential(),
        boundary.storesManagedAuditCredential(),
        boundary.credentialValueRead(),
        boundary.credentialValueProvided(),
        boundary.rawEndpointUrlParsed(),
        boundary.rawEndpointUrlRendered(),
        boundary.externalRequestSent(),
        boundary.secretProviderInstantiated(),
        boundary.resolverClientInstantiated(),
        boundary.fakeSecretProviderInstantiated(),
        boundary.fakeResolverClientInstantiated(),
        boundary.approvalLedgerWritten(),
        boundary.managedAuditStoreWritten(),
        boundary.sqlExecuted(),
        boundary.schemaMigrationExecuted(),
        boundary.rollbackExecuted(),
        boundary.automaticUpstreamStart(),
        boundary.javaStartedNodeMiniKvOrHarness());
  }

  private static void assertRuntimeBlocked(boolean... flags) {
    assertThat(flags).containsOnly(false);
  }
}
