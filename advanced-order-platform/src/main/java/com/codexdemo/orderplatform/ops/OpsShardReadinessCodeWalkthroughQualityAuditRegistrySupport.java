package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditRegistrySupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v367 / Java v1754-v1758";
    static final String AUDITED_BATCH = "Java v1748-v1753";
    static final String QUALITY_GATE_REGISTRY =
            "/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry";
    static final String REGISTRY_STATE =
            "quality-gate-batch-audited-with-medium-granularity-evidence";
    static final int EXPECTED_BATCH_ASSESSMENT_COUNT = 2;
    static final int EXPECTED_VERSION_AUDIT_COUNT = 6;
    static final int EXPECTED_RUBRIC_SCORE_COUNT = 8;
    static final int EXPECTED_REVIEW_FINDING_COUNT = 4;
    static final int EXPECTED_BOUNDARY_AUDIT_COUNT = 8;
    static final int EXPECTED_VERIFICATION_STEP_COUNT = 5;

    private OpsShardReadinessCodeWalkthroughQualityAuditRegistrySupport() {
    }

    static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BatchAssessment>
                    batchAssessments,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit>
                    versionAudits,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore>
                    rubricScores,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.ReviewFinding>
                    reviewFindings,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit>
                    boundaryAudits,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep>
                    verificationSteps,
            List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection>
                    markdownSections
    ) {
        var batchCopy = List.copyOf(batchAssessments);
        var versionCopy = List.copyOf(versionAudits);
        var rubricCopy = List.copyOf(rubricScores);
        var findingCopy = List.copyOf(reviewFindings);
        var boundaryCopy = List.copyOf(boundaryAudits);
        var verificationCopy = List.copyOf(verificationSteps);
        var markdownCopy = List.copyOf(markdownSections);
        int mediumGranularityVersionCount = (int) versionCopy.stream()
                .filter(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .VersionAudit::mediumGranularity)
                .count();
        int passedRubricScoreCount = (int) rubricCopy.stream()
                .filter(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .RubricScore::passed)
                .count();
        int blockingReviewFindingCount = (int) findingCopy.stream()
                .filter(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .ReviewFinding::blocking)
                .count();
        int deniedBoundaryAuditCount = (int) boundaryCopy.stream()
                .filter(audit -> !audit.allowed())
                .count();
        boolean batchesPassed = batchCopy.stream()
                .allMatch(batch -> batch.standardWalkthroughs() && batch.mediumGranularity());
        boolean verificationsRequired = verificationCopy.stream()
                .allMatch(OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse
                        .VerificationStep::required);
        boolean statusPassed = batchCopy.size() == EXPECTED_BATCH_ASSESSMENT_COUNT
                && versionCopy.size() == EXPECTED_VERSION_AUDIT_COUNT
                && rubricCopy.size() == EXPECTED_RUBRIC_SCORE_COUNT
                && findingCopy.size() == EXPECTED_REVIEW_FINDING_COUNT
                && boundaryCopy.size() == EXPECTED_BOUNDARY_AUDIT_COUNT
                && verificationCopy.size() == EXPECTED_VERIFICATION_STEP_COUNT
                && mediumGranularityVersionCount == versionCopy.size()
                && passedRubricScoreCount == rubricCopy.size()
                && blockingReviewFindingCount == 0
                && deniedBoundaryAuditCount == boundaryCopy.size()
                && batchesPassed
                && verificationsRequired;

        List<String> checks = new ArrayList<>();
        checks.add("code-walkthrough-quality-audit-source-plan-" + SOURCE_PLAN);
        checks.add("code-walkthrough-quality-audit-audited-batch-" + AUDITED_BATCH);
        checks.add("code-walkthrough-quality-audit-quality-gate-registry-" + QUALITY_GATE_REGISTRY);
        checks.add("code-walkthrough-quality-audit-batch-assessment-count-" + batchCopy.size());
        checks.add("code-walkthrough-quality-audit-version-audit-count-" + versionCopy.size());
        checks.add("code-walkthrough-quality-audit-medium-version-count-"
                + mediumGranularityVersionCount);
        checks.add("code-walkthrough-quality-audit-rubric-score-count-" + rubricCopy.size());
        checks.add("code-walkthrough-quality-audit-passed-rubric-score-count-"
                + passedRubricScoreCount);
        checks.add("code-walkthrough-quality-audit-review-finding-count-" + findingCopy.size());
        checks.add("code-walkthrough-quality-audit-blocking-review-finding-count-"
                + blockingReviewFindingCount);
        checks.add("code-walkthrough-quality-audit-boundary-audit-count-" + boundaryCopy.size());
        checks.add("code-walkthrough-quality-audit-denied-boundary-audit-count-"
                + deniedBoundaryAuditCount);
        checks.add("code-walkthrough-quality-audit-verification-step-count-"
                + verificationCopy.size());
        checks.add("code-walkthrough-quality-audit-no-shallow-version-found");
        checks.add("code-walkthrough-quality-audit-no-write-routing");
        checks.add("code-walkthrough-quality-audit-no-credential-value");
        checks.add("code-walkthrough-quality-audit-no-raw-endpoint-url");
        checks.add("code-walkthrough-quality-audit-no-upstream-autostart");

        return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                SOURCE_PLAN,
                AUDITED_BATCH,
                QUALITY_GATE_REGISTRY,
                REGISTRY_STATE,
                batchCopy.size(),
                versionCopy.size(),
                mediumGranularityVersionCount,
                rubricCopy.size(),
                passedRubricScoreCount,
                findingCopy.size(),
                blockingReviewFindingCount,
                boundaryCopy.size(),
                deniedBoundaryAuditCount,
                verificationCopy.size(),
                markdownCopy.size(),
                batchCopy,
                versionCopy,
                rubricCopy,
                findingCopy,
                boundaryCopy,
                verificationCopy,
                markdownCopy,
                List.copyOf(checks),
                statusPassed ? "passed" : "blocked"
        );
    }
}
