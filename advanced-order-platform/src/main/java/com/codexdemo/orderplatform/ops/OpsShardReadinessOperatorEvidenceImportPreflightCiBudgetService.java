package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-import-preflight-ci-budget.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse budget() {
        return OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
                "Java v605",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "support-unit-test",
                                "ci-reviewer",
                                "response support flags remain fail-closed and source-plan pinned",
                                "OpsShardReadinessOperatorEvidenceImportPreflightSupportTests"
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "foundation-service-tests",
                                "ci-reviewer",
                                "foundation services are tested without Spring startup",
                                "OpsShardReadinessOperatorEvidenceImportPreflightFoundationServiceTests"
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "assurance-service-tests",
                                "ci-reviewer",
                                "assurance services are tested without Spring startup",
                                "OpsShardReadinessOperatorEvidenceImportPreflightAssuranceServiceTests"
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "route-integration-tests",
                                "ci-reviewer",
                                "foundation and assurance controllers are tested with MockMvc",
                                OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService.ENDPOINT
                        ),
                        OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "full-maven-gate",
                                "release-reviewer",
                                "full mvn test remains the final release gate",
                                OpsShardReadinessManualEvidenceWorksheetCiBudgetService.ENDPOINT
                        )
                ),
                List.of(
                        "import-preflight-ci-budget-focused-first",
                        "import-preflight-ci-budget-routes-separated",
                        "import-preflight-ci-budget-full-maven-last"
                )
        );
    }
}
