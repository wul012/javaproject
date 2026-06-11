package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateEvidenceAnchorCatalog {

    private OpsShardReadinessCodeWalkthroughQualityGateEvidenceAnchorCatalog() {
    }

    static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor>
            evidenceAnchors() {
        return List.of(
                anchor(
                        "node-plan-anchor",
                        "java-roadmap",
                        "D:\\nodeproj\\orderops-node\\docs\\plans2\\v367-post-minimal-read-only-integration-gate-execution-roadmap.md",
                        "Java current work remains read-only unless an invalid-read-contract appears"
                ),
                anchor(
                        "walkthrough-standard-anchor",
                        "java-docs",
                        "advanced-order-platform/代码讲解记录_写作规范.md",
                        "new walkthroughs use the nine required sections"
                ),
                anchor(
                        "compliance-registry-anchor",
                        "java-ops",
                        "GET /api/v1/ops/shard-readiness/code-walkthrough-compliance-registry",
                        "future walkthroughs after v289 are standard and not historical clearances"
                ),
                anchor(
                        "quality-gate-registry-anchor",
                        "java-ops",
                        "GET /api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry",
                        "version granularity and explanation quality rules are visible as read-only evidence"
                ),
                anchor(
                        "archive-index-anchor",
                        "java-docs",
                        "advanced-order-platform/代码讲解记录_总索引.md",
                        "new version ranges remain discoverable without overloading one directory"
                ),
                anchor(
                        "test-anchor",
                        "java-tests",
                        "OpsShardReadinessCodeWalkthroughQualityGateRegistry*Tests",
                        "route, service, boundary, renderer, controller, and immutability are guarded"
                )
        );
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor
            anchor(
                    String anchor,
                    String owner,
                    String source,
                    String requiredProof
            ) {
        return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor(
                anchor,
                owner,
                source,
                requiredProof,
                true
        );
    }
}
