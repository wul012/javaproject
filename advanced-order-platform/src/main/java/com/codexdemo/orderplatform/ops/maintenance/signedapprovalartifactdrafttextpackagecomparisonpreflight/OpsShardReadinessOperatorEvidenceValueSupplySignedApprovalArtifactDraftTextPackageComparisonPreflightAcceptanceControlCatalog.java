package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight;

import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightAcceptanceControlCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .AcceptanceControl>
      allControls() {
    return controlsFor(
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightLaneCatalog
            .allLanes());
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .AcceptanceControl>
      controlsFor(
          List<
                  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
                      .ComparisonLane>
              lanes) {
    return lanes.stream()
        .map(
            lane ->
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                    .control(
                        "acceptance-control-" + lane.code(),
                        "draft-text-package-comparison",
                        "Reject submitted package material when lane is missing, uncompared, or unacceptable: "
                            + lane.comparisonLane(),
                        lane.acceptanceControl()))
        .toList();
  }

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
              .ComparisonGate>
      allGates() {
    return List.of(
        gate(
            "comparison-preflight-gate-no-package-acceptance",
            "acceptance",
            "comparison preflight cannot accept submitted package material"),
        gate(
            "comparison-preflight-gate-no-draft-text-parse",
            "draft-text",
            "signed draft text stays opaque and unparsed"),
        gate(
            "comparison-preflight-gate-no-detached-signature-parse",
            "signature",
            "detached signature payload stays opaque and unparsed"),
        gate(
            "comparison-preflight-gate-no-approval-grant",
            "approval",
            "approval grant remains absent"),
        gate(
            "comparison-preflight-gate-no-evidence-import",
            "evidence",
            "source evidence remains handle-only"),
        gate(
            "comparison-preflight-gate-no-operator-value-import",
            "value",
            "operator value remains handle-only"),
        gate(
            "comparison-preflight-gate-no-runtime-payload",
            "runtime",
            "runtime payload remains locked"),
        gate(
            "comparison-preflight-gate-no-write-routing",
            "routing",
            "write routing and active shard router remain out of scope"),
        gate(
            "comparison-preflight-gate-no-sibling-mutation",
            "sibling",
            "Java, Node, and mini-kv state are not mutated"),
        gate(
            "comparison-preflight-gate-fail-closed",
            "comparison",
            "uncompared or unacceptable material remains fail-closed"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
          .ComparisonGate
      gate(String code, String category, String gate) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
        .gate(code, category, gate);
  }
}
