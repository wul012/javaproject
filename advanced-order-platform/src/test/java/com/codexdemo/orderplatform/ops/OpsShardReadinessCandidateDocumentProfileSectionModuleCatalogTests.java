package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionModuleCatalogTests {

    @Test
    void modulesStayShortOrderedAndRendererOwned() {
        var modules = OpsShardReadinessCandidateDocumentProfileSectionModuleCatalog.modules();

        assertThat(modules)
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::order)
                .containsExactly(219, 220, 221, 222, 223);
        assertThat(modules)
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::code)
                .containsExactly(
                        "candidate-document-profile-section-types",
                        "candidate-document-profile-section-source-catalog",
                        "candidate-document-profile-section-field-catalog",
                        "candidate-document-profile-section-renderer",
                        "candidate-document-profile-section-registry-route");
        assertThat(modules)
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::status)
                .containsOnly("passed");
    }
}
