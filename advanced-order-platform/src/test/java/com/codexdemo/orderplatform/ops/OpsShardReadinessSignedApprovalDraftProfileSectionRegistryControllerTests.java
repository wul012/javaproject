package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryControllerTests {

    @Test
    void registryRouteExposesReadOnlySignedApprovalDraftProfileSectionRegistry() {
        assertThat(OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY)
                .isEqualTo("/signed-approval-draft-profile-section-registry");

        var response = new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController(
                OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.service()).registry();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/signed-approval-draft-profile-section-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-signed-approval-draft-profile-section-registry.v1");
        assertThat(response.version()).isEqualTo("Java v1237");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }
}
