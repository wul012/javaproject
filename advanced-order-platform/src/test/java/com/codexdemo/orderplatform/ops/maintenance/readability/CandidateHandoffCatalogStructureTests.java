package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CandidateHandoffCatalogStructureTests {

  private static final Path MAIN_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final Path FAMILY = MAIN_ROOT.resolve("candidatedocument");
  private static final Path DOC = Path.of("docs", "ops", "candidate-handoff-catalogs-v1887.md");
  private static final List<String> OWNERS =
      List.of("HandoffCatalog.java", "PrecheckHandoffCatalog.java");
  private static final List<String> RETIRED_PARTS =
      List.of("Source", "Module", "Artifact", "Policy", "Archive", "Consumer", "Gate");
  private static final List<FamilyCase> FAMILIES =
      List.of(
          new FamilyCase(
              "OpsShardReadinessCandidateDocumentHandoff",
              "HandoffCatalog.java",
              "OpsShardReadinessCandidateDocumentHandoffService.java",
              "HandoffCatalog"),
          new FamilyCase(
              "OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoff",
              "PrecheckHandoffCatalog.java",
              "OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService.java",
              "PrecheckHandoffCatalog"));

  @Test
  void onlyTwoHandoffCatalogsExist() throws IOException {
    assertThat(OpsExtractionTestSupport.allJavaFiles(FAMILY))
        .filteredOn(path -> path.getFileName().toString().endsWith("HandoffCatalog.java"))
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrderElementsOf(OWNERS);
  }

  @Test
  void retiredCatalogsStayAbsent() {
    for (FamilyCase family : FAMILIES) {
      for (String part : RETIRED_PARTS) {
        assertThat(FAMILY.resolve(family.retiredPrefix() + part + "Catalog.java")).doesNotExist();
      }
    }
  }

  @Test
  void servicesAssembleEvidenceOnce() throws IOException {
    for (FamilyCase family : FAMILIES) {
      String service = OpsExtractionTestSupport.read(FAMILY.resolve(family.service()));
      String call = family.ownerType() + ".from(";
      assertThat(service).contains("var evidence = " + call);
      assertThat(occurrences(service, call)).isEqualTo(1);
    }
  }

  @Test
  void bundlesRemainSmallAndImmutable() throws IOException {
    for (FamilyCase family : FAMILIES) {
      String source = OpsExtractionTestSupport.read(FAMILY.resolve(family.owner()));
      assertThat(source.lines().count()).isLessThanOrEqualTo(300);
      assertThat(source).contains("record Evidence(");
      assertThat(occurrences(source, "List.copyOf(")).isEqualTo(7);
    }
  }

  @Test
  void designRecordsFrozenCompatibility() throws IOException {
    assertThat(OpsExtractionTestSupport.read(DOC))
        .contains(
            "## Family design",
            "## Requirement Evidence Matrix",
            "6/5/15/15/8/10/25/20",
            "3c988b527fcf1b53946d9cab7ea91866609b2424ce981c87ad3fef8b849e13c2",
            "6/5/10/10/8/10/42/26",
            "91473893363f7062af79e05237e1b43407f73bd14176efcfe844fc0331f21cf5",
            "## Failure Conditions");
  }

  private static int occurrences(String source, String token) {
    return (source.length() - source.replace(token, "").length()) / token.length();
  }

  private record FamilyCase(String retiredPrefix, String owner, String service, String ownerType) {}
}
