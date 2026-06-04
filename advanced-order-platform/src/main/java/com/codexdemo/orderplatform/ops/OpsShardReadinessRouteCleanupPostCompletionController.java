package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupPostCompletionController {

    private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

    private final OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService;

    private final OpsShardReadinessRouteCleanupTagManifestService tagManifestService;

    private final OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService;

    public OpsShardReadinessRouteCleanupPostCompletionController(
            OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService,
            OpsShardReadinessRouteCleanupCiRunAttestationService ciRunAttestationService,
            OpsShardReadinessRouteCleanupTagManifestService tagManifestService,
            OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService
    ) {
        this.postPushCloseoutService = postPushCloseoutService;
        this.ciRunAttestationService = ciRunAttestationService;
        this.tagManifestService = tagManifestService;
        this.releaseEvidenceBundleService = releaseEvidenceBundleService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_PUSH_CLOSEOUT)
    public OpsShardReadinessRouteCleanupPostPushCloseoutResponse postPushCloseout() {
        return postPushCloseoutService.closeout();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_RUN_ATTESTATION)
    public OpsShardReadinessRouteCleanupCiRunAttestationResponse ciRunAttestation() {
        return ciRunAttestationService.attestation();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TAG_MANIFEST)
    public OpsShardReadinessRouteCleanupTagManifestResponse tagManifest() {
        return tagManifestService.manifest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_EVIDENCE_BUNDLE)
    public OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse releaseEvidenceBundle() {
        return releaseEvidenceBundleService.bundle();
    }
}
