package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionRouteEvidenceTests {

    @Test
    void endpointProfileAndRegistryStateMatchPublishedRoute() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.endpoint())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService.ENDPOINT);
        assertThat(response.profile())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService.PROFILE);
        assertThat(response.registryState())
                .isEqualTo("signed-approval-draft-profile-sections-extracted-with-route-output-stable");
    }

    @Test
    void checksNameTheServiceAssemblyBoundary() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.checks())
                .contains("signed-approval-draft-profile-section-registry-service-assembled-from-five-read-only-routes");
    }
}
