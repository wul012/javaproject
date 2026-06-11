package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessScreenshotExplanationArchiveRegistryService {

    static final String RESPONSE_VERSION = "Java v1764";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-screenshot-explanation-archive-registry.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessScreenshotExplanationArchiveRegistryResponse registry() {
        var currentArchives =
                OpsShardReadinessScreenshotExplanationArchiveCurrentCatalog
                        .currentArchiveAssessments();
        var segmentPlans =
                OpsShardReadinessScreenshotExplanationArchiveSegmentCatalog.segmentPlans();
        var namingRules =
                OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog.namingRules();
        var boundaryRules =
                OpsShardReadinessScreenshotExplanationArchiveBoundaryCatalog.boundaryRules();
        var verificationSteps =
                OpsShardReadinessScreenshotExplanationArchiveVerificationCatalog.verificationSteps();
        return OpsShardReadinessScreenshotExplanationArchiveRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                currentArchives,
                segmentPlans,
                namingRules,
                boundaryRules,
                verificationSteps,
                OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer.render(
                        currentArchives,
                        segmentPlans,
                        namingRules,
                        boundaryRules,
                        verificationSteps
                )
        );
    }
}
