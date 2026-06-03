package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupCiEvidenceService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_EVIDENCE;

    static final String PROFILE = "java-shard-readiness-route-cleanup-ci-evidence.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupCiEvidenceResponse evidence() {
        List<OpsShardReadinessRouteCleanupCiEvidenceResponse.ValidationStep> steps = List.of(
                step("focused-route-cleanup-tests",
                        "mvn -q \"-Dtest=OpsShardReadinessRouteCleanup*Tests,OpsShardReadinessRoutePathsTests\" test",
                        "java"),
                step("full-java-suite", "mvn -q test", "java"),
                step("github-actions-master", "Java Maven CI on master", "ci"),
                step("cleanup-gate", "remove generated target directory before final handoff", "java")
        );
        return new OpsShardReadinessRouteCleanupCiEvidenceResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                steps.size(),
                steps,
                "focused tests, full suite, GitHub Actions, and cleanup gate must pass before final report",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }

    private OpsShardReadinessRouteCleanupCiEvidenceResponse.ValidationStep step(
            String name,
            String commandOrCheck,
            String owner
    ) {
        return new OpsShardReadinessRouteCleanupCiEvidenceResponse.ValidationStep(
                name,
                commandOrCheck,
                owner,
                true,
                "required"
        );
    }
}
