package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedPackageReviewSlotCatalog {

    private OpsShardReadinessComparedPackageReviewSlotCatalog() {
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> allSlots() {
        List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> slots = new ArrayList<>();
        slots.addAll(OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog.sourceEvidenceSlots());
        slots.addAll(OpsShardReadinessComparedPackageReviewComparisonOutcomeSlotCatalog.comparisonOutcomeSlots());
        slots.addAll(OpsShardReadinessComparedPackageReviewIdentityDigestSlotCatalog.identityDigestSlots());
        slots.addAll(OpsShardReadinessComparedPackageReviewPolicyArchiveSlotCatalog.policyArchiveSlots());
        return List.copyOf(slots);
    }
}
