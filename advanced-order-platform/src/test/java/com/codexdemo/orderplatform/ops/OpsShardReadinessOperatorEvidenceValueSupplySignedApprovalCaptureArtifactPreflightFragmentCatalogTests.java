package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalogTests {

    @Test
    void listsTwentyFiveArtifactFragmentsWithoutMaterialization() {
        var fragments = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .allFragments();

        assertThat(fragments).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                        .FRAGMENT_COUNT);
        assertThat(fragments.stream().map(fragment -> fragment.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(fragments.stream().map(fragment -> fragment.sealCode()).collect(Collectors.toSet())).hasSize(25);
        assertThat(fragments).allSatisfy(fragment -> {
            assertThat(fragment.status()).isEqualTo("passed");
            assertThat(fragment.fragmentRequirement()).isNotBlank();
            assertThat(fragment.materializationBlocker()).isNotBlank();
            assertThat(fragment.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
        assertThat(fragments.get(0).code()).contains("REQUEST_ID");
        assertThat(fragments.get(24).code()).contains("CLOSEOUT");
    }
}
