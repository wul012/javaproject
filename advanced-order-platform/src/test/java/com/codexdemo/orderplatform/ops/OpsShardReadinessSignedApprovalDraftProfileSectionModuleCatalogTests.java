package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionModuleCatalogTests {

    @Test
    void moduleCatalogPublishesRendererBoundaryAfterCandidateDocumentModules() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.modules())
                .extracting(OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry::order)
                .containsExactly(224, 225, 226, 227, 228, 229, 230, 231);
        assertThat(response.modules())
                .extracting(OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry::code)
                .contains(
                        "signed-approval-draft-profile-section-renderer",
                        "signed-approval-draft-profile-section-registry-route");
    }

    @Test
    void moduleCatalogStaysPassedAndSized() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

        assertThat(response.moduleCount()).isEqualTo(8);
        assertThat(response.modules())
                .allSatisfy(module -> assertThat(module.status()).isEqualTo("passed"));
    }
}
