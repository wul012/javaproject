package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProfileCatalogTests {

  @Test
  void aggregateCountsMatchPublishedLists() {
    var response = ProfileTestData.registry();

    assertThat(response.moduleCount()).isEqualTo(response.modules().size());
    assertThat(response.sourceRouteCount()).isEqualTo(response.sources().size());
    assertThat(response.sectionCount()).isEqualTo(response.sections().size());
    assertThat(response.renderedSectionCount()).isEqualTo(response.renderedSections().size());
    assertThat(response.fieldEntryCount()).isEqualTo(response.fieldEntries().size());
    assertThat(response.routeFieldLockCount()).isEqualTo(response.routeFieldLocks().size());
    assertThat(response.gateCount()).isEqualTo(response.gates().size());
  }

  @Test
  void modulesStayShortOrderedAndRendererOwned() {
    var modules = ProfileTestData.registry().modules();

    assertThat(modules)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::order)
        .containsExactly(219, 220, 221, 222, 223);
    assertThat(modules)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::code)
        .containsExactly(
            "candidate-document-profile-section-types",
            "candidate-document-profile-section-source-catalog",
            "candidate-document-profile-section-field-catalog",
            "candidate-document-profile-section-renderer",
            "candidate-document-profile-section-registry-route");
    assertThat(modules)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry::status)
        .containsOnly("passed");
  }

  @Test
  void sourcesPreserveCandidateRouteOrder() {
    var sources = ProfileTestData.registry().sources();

    assertThat(sources)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource::code)
        .containsExactly(
            "candidate-document-request-package",
            "candidate-document-submission-precheck",
            "candidate-document-intake-packet",
            "candidate-document-material-request",
            "candidate-document-material-submission-precheck");
    assertThat(sources)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource
                ::sourceVersion)
        .containsExactly("Java v1081", "Java v1117", "Java v1142", "Java v1152", "Java v1162");
    assertThat(sources)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource
                ::sourceStatus)
        .containsOnly("passed");
  }

  @Test
  void sectionsKeepStableRendererMetadata() {
    var sections = ProfileTestData.registry().sections();

    assertThat(sections)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::heading)
        .containsExactly(
            "Candidate Document Request Package",
            "Candidate Document Submission Precheck",
            "Candidate Document Intake Packet",
            "Candidate Document Material Request",
            "Candidate Document Material Submission Precheck");
    assertThat(sections)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::fieldEntryCount)
        .containsOnly(5);
    assertThat(sections)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::rendererOwner)
        .containsOnly("candidate-document-profile-section-renderer");
  }

  @Test
  void fieldsPreserveFiveEntrySchema() {
    var response = ProfileTestData.registry();

    assertThat(response.fieldEntries()).hasSize(25);
    assertThat(response.sections())
        .allSatisfy(
            section ->
                assertThat(response.fieldEntries())
                    .filteredOn(entry -> entry.sectionCode().equals(section.code()))
                    .extracting(
                        OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry
                            ::fieldName)
                    .containsExactly("version", "endpoint", "profile", "status", "boundary"));
  }

  @Test
  void onlyFourFieldsAreRouteFacing() {
    var entries = ProfileTestData.registry().fieldEntries();

    assertThat(entries)
        .filteredOn(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry
                ::routeFacing)
        .hasSize(20)
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::fieldName)
        .containsOnly("version", "endpoint", "profile", "status");
    assertThat(entries)
        .filteredOn(entry -> !entry.routeFacing())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry::fieldValue)
        .containsOnly("read-only-no-runtime");
  }

  @Test
  void routeLocksPinThreeFieldsPerSection() {
    var response = ProfileTestData.registry();

    assertThat(response.routeFieldLocks()).hasSize(5);
    assertThat(response.routeFieldLocks())
        .allSatisfy(
            lock -> {
              assertThat(lock.endpoint()).startsWith("/api/v1/ops/shard-readiness/");
              assertThat(lock.profile()).startsWith("java-shard-readiness-");
              assertThat(lock.sourceVersion()).startsWith("Java v");
              assertThat(lock.lockedFieldCount()).isEqualTo(3);
              assertThat(lock.enforcement()).isEqualTo("fail-closed-route-facing-fields");
            });
    assertThat(response.lockedRouteFieldCount()).isEqualTo(15);
  }

  @Test
  void gatesRemainDistinctAndReadOnly() {
    var gates = ProfileTestData.registry().gates();

    assertThat(gates)
        .hasSize(43)
        .doesNotHaveDuplicates()
        .allSatisfy(
            gate ->
                assertThat(gate)
                    .startsWith("candidate-document-profile-section-registry-no-runtime-gate-"));
    assertThat(gates)
        .last()
        .isEqualTo("candidate-document-profile-section-registry-no-runtime-gate-43");
  }
}
