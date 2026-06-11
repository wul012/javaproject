package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCodeWalkthroughQualityAuditRegistryService {

    static final String RESPONSE_VERSION = "Java v1758";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-code-walkthrough-quality-audit-registry.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse registry() {
        var batchAssessments =
                OpsShardReadinessCodeWalkthroughQualityAuditBatchCatalog.batchAssessments();
        var versionAudits =
                OpsShardReadinessCodeWalkthroughQualityAuditVersionCatalog.versionAudits();
        var rubricScores =
                OpsShardReadinessCodeWalkthroughQualityAuditRubricCatalog.rubricScores();
        var reviewFindings =
                OpsShardReadinessCodeWalkthroughQualityAuditReviewFindingCatalog.reviewFindings();
        var boundaryAudits =
                OpsShardReadinessCodeWalkthroughQualityAuditBoundaryCatalog.boundaryAudits();
        var verificationSteps =
                OpsShardReadinessCodeWalkthroughQualityAuditVerificationCatalog.verificationSteps();
        return OpsShardReadinessCodeWalkthroughQualityAuditRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                batchAssessments,
                versionAudits,
                rubricScores,
                reviewFindings,
                boundaryAudits,
                verificationSteps,
                OpsShardReadinessCodeWalkthroughQualityAuditRegistryRenderer.render(
                        batchAssessments,
                        versionAudits,
                        rubricScores,
                        reviewFindings,
                        boundaryAudits,
                        verificationSteps
                )
        );
    }
}
