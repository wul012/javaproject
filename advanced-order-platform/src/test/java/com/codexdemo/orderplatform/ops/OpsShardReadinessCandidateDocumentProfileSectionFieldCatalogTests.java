package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionFieldCatalogTests {

    @Test
    void fieldCatalogCreatesFiveFieldsPerSection() {
        var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

        assertThat(response.fieldEntries()).hasSize(25);
        assertThat(response.sections()).allSatisfy(section -> assertThat(response.fieldEntries())
                .filteredOn(entry -> entry.sectionCode().equals(section.code()))
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::fieldName)
                .containsExactly("version", "endpoint", "profile", "status", "boundary"));
    }

    @Test
    void onlyVersionEndpointProfileAndStatusAreRouteFacing() {
        var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

        assertThat(response.fieldEntries())
                .filteredOn(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::routeFacing)
                .hasSize(20)
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::fieldName)
                .containsOnly("version", "endpoint", "profile", "status");
        assertThat(response.fieldEntries())
                .filteredOn(entry -> !entry.routeFacing())
                .extracting(OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::fieldValue)
                .containsOnly("read-only-no-runtime");
    }
}
