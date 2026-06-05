package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceImportPreflightSupportTests {

    @Test
    void buildsReadOnlyImportPreflightResponseWithAllExecutionLocks() {
        OpsShardReadinessOperatorEvidenceImportPreflightResponse response =
                OpsShardReadinessOperatorEvidenceImportPreflightSupport.response(
                        "Java v584",
                        "/api/v1/ops/shard-readiness/operator-evidence-import-preflight-example",
                        "java-operator-evidence-import-preflight-example.v1",
                        List.of(OpsShardReadinessOperatorEvidenceImportPreflightSupport.item(
                                "preflight-slot",
                                "import-preflight-maintainer",
                                "slot has no imported value",
                                OpsShardReadinessManualEvidenceWorksheetCloseoutService.ENDPOINT
                        )),
                        List.of("example-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v584");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForOperatorEvidenceImportPreflight()).isTrue();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForManualEvidenceEntry()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.sourcePlan()).isEqualTo("Node v886");
        assertThat(response.itemCount()).isOne();
        assertThat(response.passedItemCount()).isOne();
        assertThat(response.items().getFirst().status()).isEqualTo("passed");
        assertThat(response.checks()).contains(
                "source-plan-Node v886",
                "operator-evidence-import-preflight-ready",
                "evidence-import-locked",
                "manual-evidence-entry-locked",
                "live-execution-locked",
                "production-execution-locked",
                "example-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
