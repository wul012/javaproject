package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReleaseApprovalVerificationHintContributionCatalogTests
    extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void buildsVerificationHintContributionsInExpectedEchoOrder() {
    List<ReleaseApprovalVerificationHintContribution> contributions =
        ReleaseApprovalVerificationHintContributionCatalog.build(
            new ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder()
                .build(
                    readOnlyFixtureService()
                        .releaseApprovalRehearsal()
                        .approvalRecordHandoffHint()));

    assertThat(contributions).hasSize(34);
    assertThat(contributions.getFirst().warningDigestWarningInputValues())
        .containsExactly("managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings");
    assertThat(contributions.getFirst().nodeVerificationActionValues())
        .contains(
            "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224")
        .doesNotContain(
            "Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true");

    ReleaseApprovalVerificationHintContribution last = contributions.getLast();
    assertThat(last.warningDigestWarningInputValues())
        .containsExactly(
            "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceiptWarnings");
    assertThat(last.proofClaimValues())
        .contains(
            "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10",
            "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.prohibitedFieldCount=14",
            "managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.readyForNodeV327AbortRollbackSemanticsUpstreamEchoVerification=true");
    assertThat(last.nodeVerificationActionValues())
        .contains(
            "Compare managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.consumedByNodeAbortRollbackSemanticsContractProfile with Node v326",
            "Require managedAuditSandboxEndpointCredentialResolverAbortRollbackSemanticsContractEchoReceipt.abortRollbackSemanticsContract.requiredFieldCount=10 before Node v327");
  }
}
