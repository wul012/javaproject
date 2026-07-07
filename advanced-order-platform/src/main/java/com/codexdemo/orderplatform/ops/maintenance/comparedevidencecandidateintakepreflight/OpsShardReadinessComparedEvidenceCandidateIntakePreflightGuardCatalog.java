package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog {

  private static final List<String> GATES =
      List.of(
          "source-blueprint-present",
          "source-document-count-zero",
          "source-missing-document-guarded",
          "source-synthetic-document-blocked",
          "operator-provenance-blueprint-present",
          "operator-provenance-document-count-zero",
          "operator-provenance-guarded",
          "manual-submission-blueprint-present",
          "manual-submission-document-count-zero",
          "manual-submission-guarded",
          "offline-comparison-blueprint-present",
          "offline-comparison-document-count-zero",
          "offline-comparison-guarded",
          "identity-digest-blueprint-present",
          "identity-digest-document-count-zero",
          "identity-digest-guarded",
          "signature-envelope-blueprint-present",
          "signature-envelope-document-count-zero",
          "signature-envelope-guarded",
          "policy-execution-blueprint-present",
          "policy-execution-document-count-zero",
          "policy-execution-guarded",
          "approval-archive-blueprint-present",
          "approval-archive-document-count-zero",
          "approval-archive-guarded",
          "exclusion-boundary-blueprint-present",
          "exclusion-boundary-document-count-zero",
          "secret-value-exclusion-guarded",
          "synthetic-document-exclusion-guarded",
          "runtime-payload-exclusion-guarded",
          "write-exclusion-guarded",
          "sibling-mutation-exclusion-guarded",
          "candidate-closeout-blueprint-present",
          "candidate-closeout-document-count-zero",
          "reviewer-traceability-guarded",
          "candidate-intake-preflight-closeout-rendered");

  private OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog() {}

  static List<String> allGates() {
    return GATES;
  }

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard>
      allGuards() {
    return OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog.allSlots().stream()
        .map(
            slot ->
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.guard(
                    slot.missingDocumentGuard() + "-guard",
                    slot.sourceBlueprintSection(),
                    "Block intake preflight until " + slot.documentRequirement() + " exists.",
                    slot.missingDocumentGuard()))
        .toList();
  }

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard>
      sourceGuards() {
    return allGuards().subList(0, 3);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard>
      comparisonGuards() {
    return allGuards().subList(3, 6);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard>
      policyGuards() {
    return allGuards().subList(6, 8);
  }

  static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeGuard>
      closeoutGuards() {
    return allGuards().subList(8, 10);
  }
}
