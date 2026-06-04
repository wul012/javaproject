package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupCiRunAttestationService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_RUN_ATTESTATION;

    static final String PROFILE = "java-shard-readiness-route-cleanup-ci-run-attestation.v1";

    private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

    private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

    public OpsShardReadinessRouteCleanupCiRunAttestationService(
            OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService,
            OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService
    ) {
        this.postPushCloseoutService = postPushCloseoutService;
        this.ciEvidenceService = ciEvidenceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupCiRunAttestationResponse attestation() {
        OpsShardReadinessRouteCleanupPostPushCloseoutResponse closeout = postPushCloseoutService.closeout();
        OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence = ciEvidenceService.evidence();
        List<OpsShardReadinessRouteCleanupCiRunAttestationResponse.AttestationItem> items = List.of(
                item("post-push-closeout", closeout.status()),
                item("focused-tests-required", "focused-route-cleanup-tests"),
                item("full-suite-required", "mvn -q test"),
                item("github-actions-required", "Java Maven CI on master"),
                item("cleanup-gate-required", "remove generated target directory before final handoff"),
                item("validation-step-count", String.valueOf(ciEvidence.validationStepCount()))
        );
        boolean passed = closeout.status().equals("passed")
                && ciEvidence.status().equals("passed")
                && ciEvidence.validationStepCount() >= 4;
        return new OpsShardReadinessRouteCleanupCiRunAttestationResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupPostPushCloseoutService.ENDPOINT,
                items.size(),
                items,
                "publish only after local focused tests, full suite, GitHub Actions, and cleanup gate pass",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupCiRunAttestationResponse.AttestationItem item(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupCiRunAttestationResponse.AttestationItem(
                name,
                evidence,
                true,
                "required"
        );
    }
}
