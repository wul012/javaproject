package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetCiBudgetService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CI_BUDGET;
    static final String PROFILE =
            "java-shard-readiness-manual-evidence-worksheet-ci-budget.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessManualEvidenceWorksheetResponse budget() {
        return OpsShardReadinessManualEvidenceWorksheetSupport.response(
                "Java v580",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "support-unit-test",
                                "ci-maintainer",
                                "support response lock flags stay covered by unit test",
                                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "foundation-service-tests",
                                "ci-maintainer",
                                "foundation services stay grouped for fast checks",
                                OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "assurance-service-tests",
                                "ci-maintainer",
                                "assurance services stay grouped for fast checks",
                                OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "route-integration-tests",
                                "ci-maintainer",
                                "foundation and assurance MockMvc route contracts are separate",
                                OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "full-maven-gate",
                                "release-reviewer",
                                "full Maven test remains the final push gate",
                                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
                        )
                ),
                List.of(
                        "ci-budget-focused-tests-first",
                        "ci-budget-route-tests-separated",
                        "ci-budget-full-maven-gate-last"
                )
        );
    }
}
