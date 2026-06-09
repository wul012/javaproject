package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionGateCatalogTests {

    @Test
    void gateCatalogPublishesNoRuntimeGateFloor() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.gateCount()).isEqualTo(64);
        assertThat(response.gates())
                .hasSize(64)
                .allSatisfy(gate -> assertThat(gate)
                        .startsWith("signed-approval-draft-text-package-profile-section-registry-no-runtime-gate-"));
    }

    @Test
    void checksRecordFailClosedState() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.checks())
                .contains(
                        "signed-approval-draft-text-package-profile-section-registry-gate-count-64",
                        "signed-approval-draft-text-package-profile-section-registry-runtime-payload-disabled",
                        "signed-approval-draft-text-package-profile-section-registry-secret-value-disabled");
    }
}
