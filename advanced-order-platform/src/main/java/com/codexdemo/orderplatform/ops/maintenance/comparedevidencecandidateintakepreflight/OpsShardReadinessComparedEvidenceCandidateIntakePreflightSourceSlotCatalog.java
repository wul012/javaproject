package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog {

  private OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot>
      sourceSlots() {
    return List.of(
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "source-intake-readiness-document",
            "source-intake-readiness",
            "source intake readiness, artifact shape",
            "real compared candidate source readiness document",
            "reject-missing-source-readiness-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.SOURCE),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "operator-provenance-document",
            "operator-provenance",
            "operator provenance, source evidence handle",
            "real compared candidate operator provenance document",
            "reject-missing-operator-provenance-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.SOURCE),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "manual-submission-document",
            "manual-submission-reference",
            "manual submission reference, operator value handle",
            "real compared candidate manual submission document",
            "reject-missing-manual-submission-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.SOURCE));
  }
}
