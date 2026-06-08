package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog {

    private OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> allGuards() {
        return OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog.allSlots().stream()
                .map(slot -> OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.guard(
                        slot.missingDocumentGuard() + "-guard",
                        slot.sourceBlueprintSection(),
                        "Block intake preflight until " + slot.documentRequirement() + " exists.",
                        slot.missingDocumentGuard()))
                .toList();
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> sourceGuards() {
        return allGuards().subList(0, 3);
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> comparisonGuards() {
        return allGuards().subList(3, 6);
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> policyGuards() {
        return allGuards().subList(6, 8);
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard> closeoutGuards() {
        return allGuards().subList(8, 10);
    }
}
