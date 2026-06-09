package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionFieldCatalogTests {

    @Test
    void fieldCatalogPublishesSevenFieldsPerTextPackageSection() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.fieldEntryCount()).isEqualTo(63);
        assertThat(response.sections())
                .allSatisfy(section -> assertThat(response.fieldEntries())
                        .filteredOn(field -> field.sectionCode().equals(section.code()))
                        .hasSize(7));
    }

    @Test
    void fieldCatalogKeepsOnlyBoundaryNonRouteFacing() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.fieldEntries())
                .filteredOn(field -> field.fieldName().equals("boundary"))
                .allSatisfy(field -> {
                    assertThat(field.routeFacing()).isFalse();
                    assertThat(field.fieldValue()).isEqualTo("read-only-no-runtime");
                });
        assertThat(response.fieldEntries())
                .filteredOn(field -> !field.fieldName().equals("boundary"))
                .allSatisfy(field -> assertThat(field.routeFacing()).isTrue());
    }
}
