package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake.OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs;
import java.util.List;

final class OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog {

  private OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog() {}

  static List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> sourceEvidenceSlots() {
    return List.of(
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "source-intake-catalog-consistency",
            "Java v1024",
            "source evidence",
            "Compared package evidence intake catalog is present and read-only.",
            "Can the reviewer trace the intake catalog without accepting material?",
            "reject-missing-source-intake-catalog",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.CATALOG),
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "source-acceptance-reference",
            "Java v1024",
            "source evidence",
            "Source acceptance evidence slot remains a pointer-only requirement.",
            "Is source acceptance still a missing-evidence guard rather than an acceptance event?",
            "reject-missing-source-acceptance-reference",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.SOURCE_ACCEPTANCE),
        OpsShardReadinessComparedPackageReviewSupport.slot(
            "manual-submission-reference",
            "Java v1024",
            "source evidence",
            "Manual submission reference is required before any comparison review can be discussed.",
            "Does the review packet identify the manual submission reference without importing it?",
            "reject-missing-manual-submission-reference",
            OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.SUBMISSION_COMPARISON));
  }
}
