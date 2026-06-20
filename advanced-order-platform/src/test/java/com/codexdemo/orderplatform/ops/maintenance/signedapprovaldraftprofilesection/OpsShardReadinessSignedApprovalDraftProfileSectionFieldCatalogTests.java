package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionFieldCatalogTests {

  @Test
  void fieldCatalogPublishesSixFieldsPerSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.fieldEntryCount()).isEqualTo(30);
    assertThat(response.sections())
        .allSatisfy(
            section ->
                assertThat(response.fieldEntries())
                    .filteredOn(field -> field.sectionCode().equals(section.code()))
                    .hasSize(6));
  }

  @Test
  void fieldCatalogMarksOnlyBoundaryAsNonRouteFacing() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.fieldEntries())
        .filteredOn(field -> field.fieldName().equals("boundary"))
        .allSatisfy(
            field -> {
              assertThat(field.routeFacing()).isFalse();
              assertThat(field.fieldValue()).isEqualTo("read-only-no-runtime");
            });
    assertThat(response.fieldEntries())
        .filteredOn(field -> !field.fieldName().equals("boundary"))
        .allSatisfy(field -> assertThat(field.routeFacing()).isTrue());
  }
}
