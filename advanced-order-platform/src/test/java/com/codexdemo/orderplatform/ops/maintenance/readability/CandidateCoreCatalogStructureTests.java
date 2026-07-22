package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class CandidateCoreCatalogStructureTests {

  private static final Path MAIN_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final Path FAMILY = MAIN_ROOT.resolve("candidatedocument");
  private static final Path DOC = Path.of("docs", "ops", "candidate-core-catalogs-v1888.md");
  private static final List<FamilyCase> FAMILIES =
      List.of(
          new FamilyCase(
              "SubmissionCatalog.java",
              "OpsShardReadinessCandidateDocumentSubmissionPrecheckService.java",
              "SubmissionCatalog",
              4),
          new FamilyCase(
              "IntakeCatalog.java",
              "OpsShardReadinessCandidateDocumentIntakePacketService.java",
              "IntakeCatalog",
              6),
          new FamilyCase(
              "ProfileCatalog.java",
              "OpsShardReadinessCandidateDocumentProfileSectionRegistryService.java",
              "ProfileCatalog",
              6));
  private static final List<String> RETIRED =
      List.of(
          "OpsShardReadinessCandidateDocumentSubmissionCheckpointCatalog.java",
          "OpsShardReadinessCandidateDocumentSubmissionValidatorCatalog.java",
          "OpsShardReadinessCandidateDocumentSubmissionArtifactCatalog.java",
          "OpsShardReadinessCandidateDocumentIntakePacketSourceCatalog.java",
          "OpsShardReadinessCandidateDocumentIntakePacketModuleCatalog.java",
          "OpsShardReadinessCandidateDocumentIntakePacketSlotCatalog.java",
          "OpsShardReadinessCandidateDocumentIntakePacketGuardCatalog.java",
          "OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionModuleCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionSourceCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionFieldCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionRouteLockCatalog.java",
          "OpsShardReadinessCandidateDocumentProfileSectionGateCatalog.java");

  @Test
  void threeCoreCatalogsOwnTheFamilies() {
    assertThat(FAMILIES).allSatisfy(family -> assertThat(FAMILY.resolve(family.owner())).exists());
    assertThat(RETIRED).allSatisfy(retired -> assertThat(FAMILY.resolve(retired)).doesNotExist());
  }

  @Test
  void servicesAssembleEvidenceOnce() throws IOException {
    for (FamilyCase family : FAMILIES) {
      String source = OpsExtractionTestSupport.read(FAMILY.resolve(family.service()));
      String call = family.type() + ".from(";
      assertThat(source).contains("var evidence");
      assertThat(occurrences(source, call)).isEqualTo(1);
    }
  }

  @Test
  void bundlesRemainSmallAndImmutable() throws IOException {
    for (FamilyCase family : FAMILIES) {
      String source = OpsExtractionTestSupport.read(FAMILY.resolve(family.owner()));
      assertThat(source.lines().count()).isLessThanOrEqualTo(300);
      assertThat(source).contains("record Evidence(");
      assertThat(occurrences(source, "List.copyOf(")).isEqualTo(family.copyCount());
    }
  }

  @Test
  void profileRenderingStaysOutsideCatalog() throws IOException {
    String catalog = OpsExtractionTestSupport.read(FAMILY.resolve("ProfileCatalog.java"));
    String service =
        OpsExtractionTestSupport.read(
            FAMILY.resolve("OpsShardReadinessCandidateDocumentProfileSectionRegistryService.java"));
    assertThat(catalog).doesNotContain("ProfileRenderer");
    assertThat(service).contains("ProfileRenderer.render(");
  }

  @Test
  void designFreezesScopeAndResponses() throws IOException {
    assertThat(OpsExtractionTestSupport.read(DOC))
        .contains(
            "## Family design",
            "14 个旧 Catalog -> 3",
            "25/25/8/40/19",
            "920742a06cdbe7f0502abeb4c4b38d2f772088677aabdc5a2eb594f2bc0ce0fa",
            "5/5/10/10/8/35/23",
            "cb0b888fcc190b1272834cabf7c1bb414471d486da55212cc562cdd6af4c4e95",
            "5/5/5/25/5/5/43/21",
            "d3cbe7af21f604737121aa8a5e4d9e05f5dd9ed3e1c7013ec2757b8d60dbc660",
            "## Failure Conditions");
  }

  private static int occurrences(String source, String token) {
    return (source.length() - source.replace(token, "").length()) / token.length();
  }

  private record FamilyCase(String owner, String service, String type, int copyCount) {}
}
