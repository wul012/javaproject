package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs;
import java.util.List;

final class OpsShardReadinessComparedPackageReviewPolicyArchiveSlotCatalog {

  private OpsShardReadinessComparedPackageReviewPolicyArchiveSlotCatalog() {}

  static List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> policyArchiveSlots() {
    return List.of(
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "policy-execution-lock-review",
            "Java v1024",
            "policy archive",
            "Policy and execution lock remain fail-closed during compared package review.",
            "Does review preserve execution lock before a separate future grant?",
            "reject-missing-policy-execution-lock-review",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.ASSURANCE_CLOSEOUT),
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "approval-grant-separation-review",
            "Java v1024",
            "policy archive",
            "Approval grant separation is explicit and not implied by review completeness.",
            "Is approval still separated from evidence review completion?",
            "reject-missing-approval-grant-separation-review",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.ASSURANCE_CLOSEOUT),
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "archive-closeout-review",
            "Java v1024",
            "policy archive",
            "Archive closeout names the review boundary without writing archive material.",
            "Can the closeout be traced without creating a new archive artifact?",
            "reject-missing-archive-closeout-review",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.ASSURANCE_CLOSEOUT));
  }
}
