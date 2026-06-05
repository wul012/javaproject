package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetSupportTests {

    @Test
    void buildsReadOnlyWorksheetResponseWithExecutionLocks() {
        OpsShardReadinessManualEvidenceWorksheetResponse response =
                OpsShardReadinessManualEvidenceWorksheetSupport.response(
                        "Java v559",
                        "/api/v1/ops/shard-readiness/manual-evidence-worksheet-example",
                        "java-manual-evidence-worksheet-example.v1",
                        List.of(OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "blank-slot",
                                "operator-worksheet-maintainer",
                                "slot has no manual value",
                                OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT
                        )),
                        List.of("example-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v559");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(response.readyForManualEvidenceEntry()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.sourcePlan()).isEqualTo("Node v861");
        assertThat(response.itemCount()).isOne();
        assertThat(response.passedItemCount()).isOne();
        assertThat(response.items().getFirst().status()).isEqualTo("passed");
        assertThat(response.checks()).contains(
                "source-plan-Node v861",
                "operator-entry-worksheet-ready",
                "manual-evidence-entry-locked",
                "live-execution-locked",
                "production-execution-locked",
                "example-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
