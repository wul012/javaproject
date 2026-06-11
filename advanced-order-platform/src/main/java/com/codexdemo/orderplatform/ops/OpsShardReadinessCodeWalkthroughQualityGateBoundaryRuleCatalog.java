package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateBoundaryRuleCatalog {

    private OpsShardReadinessCodeWalkthroughQualityGateBoundaryRuleCatalog() {
    }

    static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule>
            boundaryRules() {
        return List.of(
                boundary(
                        "no-write-routing",
                        "java-ops",
                        "write routing",
                        "quality gates describe release discipline; they do not execute order writes"
                ),
                boundary(
                        "no-active-shard-router",
                        "java-ops",
                        "active shard router",
                        "the current Node plan does not require Java shard router activation"
                ),
                boundary(
                        "no-credential-value",
                        "java-security",
                        "credential value read",
                        "quality scoring only names handles, policies, and docs"
                ),
                boundary(
                        "no-raw-endpoint-url",
                        "java-security",
                        "raw endpoint URL resolution",
                        "endpoint proof remains symbolic and route based"
                ),
                boundary(
                        "no-managed-audit-connection",
                        "java-ops",
                        "managed audit HTTP or TCP connection",
                        "quality gates are static Java evidence"
                ),
                boundary(
                        "no-deployment-rollback",
                        "java-release",
                        "deployment or rollback",
                        "the registry is a pre-release review artifact only"
                ),
                boundary(
                        "no-java-autostart",
                        "java-runtime",
                        "Java service autostart",
                        "tests instantiate services without starting a local server"
                ),
                boundary(
                        "no-minikv-autostart",
                        "mini-kv-runtime",
                        "mini-kv process autostart",
                        "mini-kv remains an explicitly untouched boundary"
                )
        );
    }

    private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule
            boundary(
                    String code,
                    String owner,
                    String forbiddenAction,
                    String rationale
            ) {
        return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule(
                code,
                owner,
                forbiddenAction,
                false,
                rationale
        );
    }
}
