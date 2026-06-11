package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCodeWalkthroughQualityGateRegistryService {

    static final String RESPONSE_VERSION = "Java v1753";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-code-walkthrough-quality-gate-registry.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse registry() {
        var versionRules =
                OpsShardReadinessCodeWalkthroughQualityGateVersionRuleCatalog.versionRules();
        var explanationRubrics =
                OpsShardReadinessCodeWalkthroughQualityGateExplanationRubricCatalog
                        .explanationRubrics();
        var evidenceAnchors =
                OpsShardReadinessCodeWalkthroughQualityGateEvidenceAnchorCatalog.evidenceAnchors();
        var reviewChecklists =
                OpsShardReadinessCodeWalkthroughQualityGateReviewChecklistCatalog.reviewChecklists();
        var boundaryRules =
                OpsShardReadinessCodeWalkthroughQualityGateBoundaryRuleCatalog.boundaryRules();
        return OpsShardReadinessCodeWalkthroughQualityGateRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                versionRules,
                explanationRubrics,
                evidenceAnchors,
                reviewChecklists,
                boundaryRules,
                OpsShardReadinessCodeWalkthroughQualityGateRegistryRenderer.render(
                        versionRules,
                        explanationRubrics,
                        evidenceAnchors,
                        reviewChecklists,
                        boundaryRules
                )
        );
    }
}
