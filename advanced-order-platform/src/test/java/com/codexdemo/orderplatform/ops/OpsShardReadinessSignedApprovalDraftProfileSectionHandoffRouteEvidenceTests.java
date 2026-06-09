package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteEvidenceTests {

    @Test
    void endpointProfileAndHandoffStateMatchPublishedRoute() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.endpoint())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.ENDPOINT);
        assertThat(response.profile())
                .isEqualTo(OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.PROFILE);
        assertThat(response.handoffState())
                .isEqualTo("signed-approval-draft-profile-section-registry-handoff-ready");
    }

    @Test
    void checksNameTheRegistryOnlyAssemblyBoundary() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.checks())
                .contains("signed-approval-draft-profile-section-handoff-consumes-v1237-registry-only");
    }
}
