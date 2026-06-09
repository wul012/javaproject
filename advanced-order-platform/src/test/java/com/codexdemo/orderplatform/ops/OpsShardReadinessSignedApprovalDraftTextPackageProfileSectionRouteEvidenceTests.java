package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRouteEvidenceTests {

    @Test
    void endpointProfileAndRegistryStateMatchPublishedRoute() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.endpoint())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.ENDPOINT);
        assertThat(response.profile())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.PROFILE);
        assertThat(response.registryState())
                .isEqualTo("signed-approval-draft-text-package-profile-sections-extracted-with-route-output-stable");
    }

    @Test
    void checksNameTheNodeV1531RendererSplitBoundary() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.checks())
                .contains(
                        "signed-approval-draft-text-package-profile-section-registry-service-assembled-from-nine-read-only-routes",
                        "signed-approval-draft-text-package-profile-section-registry-node-v1531-renderer-split-aligned");
    }
}
