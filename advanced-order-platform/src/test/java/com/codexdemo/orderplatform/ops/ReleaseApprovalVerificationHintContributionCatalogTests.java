package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseApprovalVerificationHintContributionCatalogTests {

    @Test
    void buildsVerificationHintContributionsInExpectedEchoOrder() {
        List<ReleaseApprovalVerificationHintContribution> contributions =
                ReleaseApprovalVerificationHintContributionCatalog.build(
                        new ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionPrecheckPacketEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverPreImplementationPlanIntakeEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledImplementationCandidateEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellHandoffEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledRuntimeShellCandidateGateEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverRuntimeShellStopPrerequisiteDecisionEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverApprovalPrerequisiteArtifactIntakeEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPacketEchoReceiptBuilder(),
                        new ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptBuilder()
                );

        assertThat(contributions).hasSize(29);
        assertThat(contributions.getFirst().warningDigestWarningInputValues())
                .containsExactly("managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings");
        assertThat(contributions.getFirst().nodeVerificationActionValues())
                .contains(
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224"
                )
                .doesNotContain(
                        "Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true"
                );

        ReleaseApprovalVerificationHintContribution last = contributions.getLast();
        assertThat(last.warningDigestWarningInputValues())
                .containsExactly(
                        "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceiptWarnings"
                );
        assertThat(last.proofClaimValues())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.prerequisiteCount=6",
                        "managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.readyForNodeV311PostEchoDecisionUpstreamEchoVerification=true"
                );
        assertThat(last.nodeVerificationActionValues())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.consumedByNodePostEchoDecisionGateProfile with Node v310",
                        "Require managedAuditSandboxEndpointCredentialResolverHumanApprovalArtifactReviewPostEchoDecisionGateEchoReceipt.decisionGate.prerequisiteCount=6 before Node v311"
                );
    }
}
