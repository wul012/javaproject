package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupRegressionGuardService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-regression-guard.v1";

    private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

    private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

    public OpsShardReadinessRouteCleanupRegressionGuardService(
            OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
            OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService
    ) {
        this.endpointManifestService = endpointManifestService;
        this.ciEvidenceService = ciEvidenceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupRegressionGuardResponse guard() {
        OpsShardReadinessRouteCleanupEndpointManifestResponse manifest = endpointManifestService.manifest();
        OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence = ciEvidenceService.evidence();
        List<String> endpoints = manifest.endpoints().stream()
                .map(OpsShardReadinessRouteCleanupEndpointManifestResponse.EndpointEntry::endpoint)
                .toList();
        List<OpsShardReadinessRouteCleanupRegressionGuardResponse.GuardCheck> guards = List.of(
                guard("version-continuity", OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous(),
                        "catalog versions continuous"),
                guard("read-only-boundary", OpsShardReadinessRouteCleanupEvidenceAnalyzer.allEntriesKeepReadOnlyBoundary(),
                        "all catalog entries remain read-only"),
                guard("manifest-core-endpoints", endpoints.contains(OpsShardReadinessRouteCleanupConsumerPacketService.ENDPOINT)
                                && endpoints.contains(OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT),
                        "manifest endpoint count=" + manifest.endpointCount()),
                guard("ci-requirements-present", ciEvidence.validationStepCount() >= 4,
                        "ci validation step count=" + ciEvidence.validationStepCount())
        );
        boolean passed = guards.stream()
                .allMatch(OpsShardReadinessRouteCleanupRegressionGuardResponse.GuardCheck::passed);
        return new OpsShardReadinessRouteCleanupRegressionGuardResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                guards.size(),
                guards,
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupRegressionGuardResponse.GuardCheck guard(
            String name,
            boolean passed,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupRegressionGuardResponse.GuardCheck(
                name,
                passed,
                evidence,
                passed ? "passed" : "blocked"
        );
    }
}
