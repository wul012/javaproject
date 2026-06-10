package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate> gates(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source
    ) {
        boolean sourcePassed = "passed".equals(source.status());
        return List.of(
                gate(
                        "focused-sustainment-tests",
                        "mvn -q \"-Dtest=*ReleaseAcceptanceRoutePathSplitSustainment*\" test",
                        "new sustainment registry",
                        sourcePassed
                ),
                gate(
                        "related-route-path-split-tests",
                        "mvn -q \"-Dtest=*ReleaseAcceptanceRoutePathSplit*\" test",
                        "split, closeout, and sustainment route path evidence",
                        sourcePassed
                ),
                gate(
                        "full-java-regression",
                        "mvn -q test",
                        "advanced-order-platform",
                        sourcePassed
                ),
                gate(
                        "git-diff-whitespace-check",
                        "git diff --check",
                        "source tree",
                        sourcePassed
                ),
                gate(
                        "remote-ci-confirmation",
                        "gh run watch <run-id> --repo wul012/javaproject --exit-status",
                        "GitHub Actions after push",
                        sourcePassed
                )
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate gate(
            String gate,
            String command,
            String scope,
            boolean required
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate(
                gate,
                command,
                scope,
                required
        );
    }
}
