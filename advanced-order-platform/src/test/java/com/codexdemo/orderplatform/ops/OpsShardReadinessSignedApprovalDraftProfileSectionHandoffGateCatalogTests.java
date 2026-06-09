package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffGateCatalogTests {

    @Test
    void gateCatalogPublishesNoRuntimeGateFloor() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.gateCount()).isEqualTo(52);
        assertThat(response.gates())
                .hasSize(52)
                .allSatisfy(gate -> assertThat(gate)
                        .startsWith("signed-approval-draft-profile-section-handoff-no-runtime-gate-"));
    }

    @Test
    void checksRecordFailClosedGateCounts() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.checks())
                .contains(
                        "signed-approval-draft-profile-section-handoff-gate-count-52",
                        "signed-approval-draft-profile-section-handoff-runtime-payload-disabled",
                        "signed-approval-draft-profile-section-handoff-write-disabled");
    }
}
