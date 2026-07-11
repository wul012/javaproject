package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport {

  private static final String EVIDENCE_PREFIX =
      "java-shard-readiness-v1-contract-consumer-readiness-handoff-";

  private OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport() {}

  static List<OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt>
      receipts() {
    return OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts();
  }

  static List<Integer> versions() {
    return OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions();
  }

  static List<String> evidencePaths() {
    return OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
        .evidencePaths();
  }

  static List<String> scopes() {
    return receipts().stream()
        .map(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
                ::scope)
        .toList();
  }

  static void assertReceiptCountAtLeast(int minimum) {
    assertThat(receipts()).hasSizeGreaterThanOrEqualTo(minimum);
  }

  static void assertVersionRun(int first, int last) {
    Integer[] expected = IntStream.rangeClosed(first, last).boxed().toArray(Integer[]::new);

    assertThat(versions()).containsSubsequence(expected);
  }

  static void assertContinuousCatalogFrom(int first) {
    List<Integer> catalogVersions = versions();

    assertThat(catalogVersions).startsWith(first);
    assertThat(catalogVersions)
        .containsExactlyElementsOf(
            IntStream.rangeClosed(first, catalogVersions.getLast()).boxed().toList());
  }

  static void assertExactVersionWindow(int first, int last) {
    assertThat(versions().stream().filter(version -> version >= first && version <= last).toList())
        .containsExactlyElementsOf(IntStream.rangeClosed(first, last).boxed().toList());
  }

  static void assertEvidencePathsUniqueAndVersionScoped() {
    assertThat(receipts())
        .doesNotHaveDuplicates()
        .allSatisfy(
            receipt ->
                assertThat(receipt.evidencePath())
                    .contains("/" + receipt.version() + "/")
                    .endsWith("-v" + receipt.version() + ".json"));
    assertThat(evidencePaths()).doesNotHaveDuplicates();
  }

  static void assertEvidencePath(String actualPath, int version, String slug) {
    assertThat(actualPath)
        .isEqualTo(
            "e/" + version + "/evidence/" + EVIDENCE_PREFIX + slug + "-v" + version + ".json");
  }
}
