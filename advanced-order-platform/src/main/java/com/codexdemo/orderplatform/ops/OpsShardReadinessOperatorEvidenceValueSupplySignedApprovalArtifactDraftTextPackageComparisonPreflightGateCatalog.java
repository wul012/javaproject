package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightGateCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .ComparisonGate> allGates() {
        return List.of(
                gate("comparison-preflight-gate-no-package-acceptance", "acceptance",
                        "comparison preflight cannot accept submitted package material"),
                gate("comparison-preflight-gate-no-draft-text-parse", "draft-text",
                        "signed draft text stays opaque and unparsed"),
                gate("comparison-preflight-gate-no-detached-signature-parse", "signature",
                        "detached signature payload stays opaque and unparsed"),
                gate("comparison-preflight-gate-no-approval-grant", "approval",
                        "approval grant remains absent"),
                gate("comparison-preflight-gate-no-evidence-import", "evidence",
                        "source evidence remains handle-only"),
                gate("comparison-preflight-gate-no-operator-value-import", "value",
                        "operator value remains handle-only"),
                gate("comparison-preflight-gate-no-runtime-payload", "runtime",
                        "runtime payload remains locked"),
                gate("comparison-preflight-gate-no-write-routing", "routing",
                        "write routing and active shard router remain out of scope"),
                gate("comparison-preflight-gate-no-sibling-mutation", "sibling",
                        "Java, Node, and mini-kv state are not mutated"),
                gate("comparison-preflight-gate-fail-closed", "comparison",
                        "uncompared or unacceptable material remains fail-closed")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightResponse
            .ComparisonGate gate(String code, String category, String gate) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .gate(code, category, gate);
    }
}

