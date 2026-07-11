package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1853Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "v1contract"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "v1contract"));
  private static final Path DOC =
      Path.of("docs", "ops", "v1-contract-consumer-alignment-extraction-v1853.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段7",
          "v1853-v1857",
          "version-1853-production-excellence-v1-contract-consumer-alignment-extraction.md");
  private static final String FAMILY_PREFIX = "OpsShardReadinessV1Contract";
  private static final String PACKAGE_IMPORT = "ops.maintenance.v1contract";
  private static final List<String> SERVICE_PREFIXES =
      List.of(
          "OpsShardReadinessV1ContractAlignment",
          "OpsShardReadinessV1ContractAlignmentHandoff",
          "OpsShardReadinessV1ContractEvidencePacket",
          "OpsShardReadinessV1ContractOperatorChecklist",
          "OpsShardReadinessV1ContractHandoffManifest",
          "OpsShardReadinessV1ContractConsumerProbePlan",
          "OpsShardReadinessV1ContractEndpointCatalog",
          "OpsShardReadinessV1ContractConsumerHandoffBundle",
          "OpsShardReadinessV1ContractConsumerVerificationChecklist",
          "OpsShardReadinessV1ContractConsumerEvidenceDigest",
          "OpsShardReadinessV1ContractConsumerReadinessHandoff");
  private static final Set<String> RETAINED_ROOT_TESTS =
      Set.of(
          "OpsShardReadinessV1ContractControllerSplitTests.java",
          "OpsShardReadinessV1ContractConsumerReadinessHandoffControllerMappingTests.java",
          "OpsShardReadinessV1ContractRouteInventoryTests.java");

  @Test
  void completeV1ContractClosureMovesWhileControllerStaysRoot() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT)).hasSize(42);
    assertThat(javaFiles(PACKAGE_ROOT))
        .allMatch(path -> path.getFileName().toString().startsWith(FAMILY_PREFIX));

    assertThat(javaFiles(OPS_ROOT).stream().filter(this::isV1ContractFile).toList())
        .extracting(path -> path.getFileName().toString())
        .containsExactly("OpsShardReadinessV1ContractController.java");
    String controller = read(OPS_ROOT.resolve("OpsShardReadinessV1ContractController.java"));
    assertThat(controller).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageLocalTestsMoveWhileControllerAndRouteTestsStayRoot() throws IOException {
    assertThat(javaFiles(PACKAGE_TEST_ROOT)).hasSize(99);
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .allMatch(path -> path.getFileName().toString().startsWith(FAMILY_PREFIX));

    assertThat(javaFiles(TEST_ROOT).stream().filter(this::isV1ContractFile).toList())
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrderElementsOf(RETAINED_ROOT_TESTS);
    for (String file :
        List.of(
            "OpsShardReadinessV1ContractConsumerReadinessHandoffControllerMappingTests.java",
            "OpsShardReadinessV1ContractRouteInventoryTests.java")) {
      assertThat(read(TEST_ROOT.resolve(file))).as(file).contains(PACKAGE_IMPORT);
    }
    assertThat(read(TEST_ROOT.resolve("OpsShardReadinessV1ContractControllerSplitTests.java")))
        .contains(
            "OpsShardReadinessV1ContractController.class",
            "V1_CONTRACT_ALIGNMENT",
            "V1_CONTRACT_CONSUMER_READINESS_HANDOFF");
  }

  @Test
  void publicWebBoundaryAndPrivateSnapshotsStayDeliberate() throws IOException {
    for (String prefix : SERVICE_PREFIXES) {
      assertThat(read(PACKAGE_ROOT.resolve(prefix + "Service.java")))
          .as(prefix)
          .contains(
              "public class " + prefix + "Service",
              "public static final String ENDPOINT",
              "public static final String FIXTURE_ENDPOINT",
              "public static final String EVIDENCE_PATH");
      assertThat(read(PACKAGE_ROOT.resolve(prefix + "Response.java")))
          .as(prefix)
          .contains("public record " + prefix + "Response");
    }

    for (Path path : javaFiles(PACKAGE_ROOT)) {
      if (path.getFileName().toString().endsWith("Snapshot.java")) {
        assertThat(read(path))
            .as(path.getFileName().toString())
            .doesNotContain("public final class");
      }
    }

    String historical =
        read(
            TEST_ROOT.resolve(
                "OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests.java"));
    assertThat(historical)
        .contains("OpsShardReadinessV1ContractTestSupport")
        .doesNotContain(
            "OpsShardReadinessV1ContractAlignmentSnapshot.",
            "OpsShardReadinessV1ContractAlignmentHandoffSnapshot.",
            "OpsShardReadinessV1ContractEvidencePacketSnapshot.",
            "OpsShardReadinessV1ContractOperatorChecklistSnapshot.",
            "OpsShardReadinessV1ContractHandoffManifestSnapshot.",
            "OpsShardReadinessV1ContractConsumerProbePlanSnapshot.");
  }

  @Test
  void sharedContractAndEndpointPairsExposeOnlyImmutableBoundaries() throws IOException {
    assertThat(read(PACKAGE_ROOT.resolve("OpsShardReadinessV1Contract.java")))
        .contains(
            "public final class OpsShardReadinessV1Contract",
            "public static final String CONTRACT_NAME",
            "public static List<String> minimalFields()",
            "public static boolean alignsWithReadOnlyContract");

    assertThat(read(PACKAGE_ROOT.resolve("OpsShardReadinessV1ContractEndpointPairs.java")))
        .contains(
            "public final class OpsShardReadinessV1ContractEndpointPairs",
            "public static List<EndpointPair> endpointPairs()",
            "public record EndpointPair(String liveEndpoint, String fixtureEndpoint)")
        .doesNotContain("OpsShardReadinessEvidenceEndpoints.EndpointPair");
    assertThat(read(OPS_ROOT.resolve("OpsShardReadinessEvidenceEndpoints.java")))
        .contains(
            PACKAGE_IMPORT,
            "OpsShardReadinessV1ContractEndpointPairs.endpointPairs()",
            "endpointPair(");
  }

  @Test
  void globalRouteOwnerPreservesAllElevenPublicSuffixes() throws IOException {
    String routePaths = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    for (String suffix :
        List.of(
            "V1_CONTRACT_ALIGNMENT",
            "V1_CONTRACT_ALIGNMENT_HANDOFF",
            "V1_CONTRACT_EVIDENCE_PACKET",
            "V1_CONTRACT_OPERATOR_CHECKLIST",
            "V1_CONTRACT_HANDOFF_MANIFEST",
            "V1_CONTRACT_CONSUMER_PROBE_PLAN",
            "V1_CONTRACT_ENDPOINT_CATALOG",
            "V1_CONTRACT_CONSUMER_HANDOFF_BUNDLE",
            "V1_CONTRACT_CONSUMER_VERIFICATION_CHECKLIST",
            "V1_CONTRACT_CONSUMER_EVIDENCE_DIGEST",
            "V1_CONTRACT_CONSUMER_READINESS_HANDOFF")) {
      assertThat(routePaths).as(suffix).contains("public static final String " + suffix);
    }
  }

  @Test
  void externalConsumersAndSpotbugsFollowThePackageMove() throws IOException {
    for (String consumer :
        List.of(
            "OpsShardReadinessPrototypeConsumerGateService.java",
            "OpsShardReadinessPrototypeEvidenceService.java",
            "OpsShardReadinessPrototypeHandoffService.java")) {
      assertThat(read(OPS_ROOT.resolve(consumer))).as(consumer).contains(PACKAGE_IMPORT);
    }

    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String prefix : SERVICE_PREFIXES) {
      String response = prefix + "Response";
      assertThat(spotbugs)
          .contains(PACKAGE_IMPORT + "." + response)
          .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    }

    assertThat(read(TEST_ROOT.resolve("OpsShardReadinessEvidenceEndpointsTestSupport.java")))
        .contains(
            "public final class OpsShardReadinessEvidenceEndpointsTestSupport",
            "liveEndpoints()",
            "fixtureEndpoints()",
            "liveProbeEndpoints()",
            "fixtureProbeEndpoints()");
  }

  @Test
  void shrinkOnlyCensusAndVersionEvidenceCloseTheBatch() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(310);
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }

    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 471 -> 429",
            "movable 366 -> 324",
            "V1Contract bucket 42 -> 0",
            "v1contract");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1853",
            "禁止硬凑",
            "本项目",
            "## 实际工作量说明",
            "## 入口路由",
            "## 响应模型",
            "## 上游证据配置",
            "## 服务层核心流程",
            "## Java 证据检查",
            "## mini-kv 证据检查",
            "## 阻断与安全边界",
            "## 测试覆盖",
            "## 一句话总结");
  }

  private List<Path> javaFiles(Path root) throws IOException {
    try (Stream<Path> files = Files.list(root)) {
      return files.filter(Files::isRegularFile).filter(this::isJava).sorted().toList();
    }
  }

  private boolean isV1ContractFile(Path path) {
    return path.getFileName().toString().startsWith(FAMILY_PREFIX);
  }

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
