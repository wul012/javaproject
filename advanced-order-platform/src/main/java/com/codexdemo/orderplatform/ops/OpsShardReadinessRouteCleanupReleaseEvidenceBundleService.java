package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupReleaseEvidenceBundleService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_EVIDENCE_BUNDLE;

    static final String PROFILE = "java-shard-readiness-route-cleanup-release-evidence-bundle.v1";

    private final OpsShardReadinessRouteCleanupCompletionCertificateService completionCertificateService;

    private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

    private final OpsShardReadinessRouteCleanupTagManifestService tagManifestService;

    public OpsShardReadinessRouteCleanupReleaseEvidenceBundleService(
            OpsShardReadinessRouteCleanupCompletionCertificateService completionCertificateService,
            OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService,
            OpsShardReadinessRouteCleanupTagManifestService tagManifestService
    ) {
        this.completionCertificateService = completionCertificateService;
        this.ciRunAttestationService = ciRunAttestationService;
        this.tagManifestService = tagManifestService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse bundle() {
        OpsShardReadinessRouteCleanupCompletionCertificateResponse certificate =
                completionCertificateService.certificate();
        OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation =
                ciRunAttestationService.attestation();
        OpsShardReadinessRouteCleanupTagManifestResponse tagManifest = tagManifestService.manifest();
        List<String> sources = List.of(
                OpsShardReadinessRouteCleanupCompletionCertificateService.ENDPOINT,
                OpsShardReadinessRouteCleanupCiRunAttestationService.ENDPOINT,
                OpsShardReadinessRouteCleanupTagManifestService.ENDPOINT
        );
        List<OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse.BundleItem> items = List.of(
                item("completion-certificate", certificate.certificateId()),
                item("ci-run-attestation", ciRunAttestation.requirement()),
                item("tag-manifest", String.valueOf(tagManifest.tagCount())),
                item("boundary-status", OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
                item("latest-version", OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel())
        );
        boolean passed = certificate.status().equals("passed")
                && ciRunAttestation.status().equals("passed")
                && tagManifest.status().equals("passed");
        return new OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                sources.size(),
                sources,
                items.size(),
                items,
                passed ? "release-evidence-bundle-ready-for-route" : "blocked",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse.BundleItem item(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse.BundleItem(
                name,
                evidence,
                "passed"
        );
    }
}
