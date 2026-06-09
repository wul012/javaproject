package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionSourceCatalogTests {

    @Test
    void sourceCatalogPreservesCandidateDocumentRouteOrder() {
        var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

        assertThat(response.sources())
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource::code)
                .containsExactly(
                        "candidate-document-request-package",
                        "candidate-document-submission-precheck",
                        "candidate-document-intake-packet",
                        "candidate-document-material-request",
                        "candidate-document-material-submission-precheck");
        assertThat(response.sources())
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource::sourceVersion)
                .containsExactly("Java v1081", "Java v1117", "Java v1142", "Java v1152", "Java v1162");
        assertThat(response.sources())
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource::sourceStatus)
                .containsOnly("passed");
    }
}
