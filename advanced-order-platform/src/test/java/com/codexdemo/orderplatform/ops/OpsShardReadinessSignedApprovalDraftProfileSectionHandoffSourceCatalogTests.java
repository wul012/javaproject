package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSourceCatalogTests {

    @Test
    void sourceCatalogPinsTheRegistryAsSingleSource() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.sources()).hasSize(1);
        assertThat(response.sources().getFirst().code())
                .isEqualTo("signed-approval-draft-profile-section-registry");
        assertThat(response.sources().getFirst().sourceVersion()).isEqualTo("Java v1237");
        assertThat(response.sources().getFirst().transferredCount()).isEqualTo(5);
    }

    @Test
    void sourceCatalogKeepsRegistryEndpointAndProfileVisible() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.sourceRegistryEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/signed-approval-draft-profile-section-registry");
        assertThat(response.sourceRegistryProfile())
                .isEqualTo("java-shard-readiness-signed-approval-draft-profile-section-registry.v1");
    }
}
