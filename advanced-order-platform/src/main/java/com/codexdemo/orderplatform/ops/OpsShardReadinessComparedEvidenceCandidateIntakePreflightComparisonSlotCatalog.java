package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint.OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog {

  private OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog() {}

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot>
      comparisonSlots() {
    return List.of(
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "offline-comparison-document",
            "offline-comparison-result",
            "offline comparison result, mismatch summary",
            "real compared candidate offline comparison document",
            "reject-missing-offline-comparison-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.COMPARISON),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "identity-digest-document",
            "identity-digest-lineage",
            "identity binding, digest lineage",
            "real compared candidate identity digest document",
            "reject-missing-identity-digest-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.COMPARISON),
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot(
            "signature-envelope-document",
            "signature-envelope-metadata",
            "signature envelope metadata, detached signature observation",
            "real compared candidate signature envelope document",
            "reject-missing-signature-envelope-document",
            OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.COMPARISON));
  }
}
