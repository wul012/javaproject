package com.codexdemo.orderplatform.ops;

import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupTagManifestService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TAG_MANIFEST;

    static final String PROFILE = "java-shard-readiness-route-cleanup-tag-manifest.v1";

    private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

    public OpsShardReadinessRouteCleanupTagManifestService(
            OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService
    ) {
        this.ciRunAttestationService = ciRunAttestationService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupTagManifestResponse manifest() {
        OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation =
                ciRunAttestationService.attestation();
        List<OpsShardReadinessRouteCleanupTagManifestResponse.TagEntry> tags =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().stream()
                        .sorted(Comparator.comparing(
                                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::javaVersion
                        ).reversed())
                        .limit(8)
                        .sorted(Comparator.comparing(
                                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::javaVersion
                        ))
                        .map(entry -> new OpsShardReadinessRouteCleanupTagManifestResponse.TagEntry(
                                entry.javaVersion(),
                                "v" + entry.javaVersion() + "-order-platform-route-cleanup-"
                                        + entry.phase().replace("handoff-suite-", "").replace("-service", ""),
                                entry.evidenceType(),
                                "expected"
                        ))
                        .toList();
        return new OpsShardReadinessRouteCleanupTagManifestResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupCiRunAttestationService.ENDPOINT,
                tags.size(),
                tags,
                "tags are descriptive Java route-cleanup tags pushed only to javaproject",
                ciRunAttestation.status().equals("passed") ? "passed" : "blocked"
        );
    }
}
