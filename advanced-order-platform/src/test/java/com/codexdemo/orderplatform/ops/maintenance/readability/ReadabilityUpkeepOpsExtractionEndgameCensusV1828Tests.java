package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path CENSUS = DOCS_ROOT.resolve("extraction-endgame-census-v1828.md");
  private static final Path WAIVERS = DOCS_ROOT.resolve("extraction-waivers.md");
  private static final Set<String> SHARED_ROOT_KEEP =
      Set.of(
          "ContextHeaderField.java",
          "OpsEvidenceResponse.java",
          "OpsEvidenceService.java",
          "OpsShardReadinessEvidenceEndpoints.java",
          "OpsShardReadinessRoutePaths.java");

  @Test
  void endgameCensusStaysDiscoverableAndBinding() throws IOException {
    assertThat(Files.isRegularFile(CENSUS)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String census = read(CENSUS);

    assertThat(readme)
        .contains(
            "extraction-endgame-census-v1828.md",
            "874",
            "105",
            "769",
            "751",
            "646",
            "732",
            "627",
            "696",
            "591");
    assertThat(census)
        .contains(
            "Current direct-root Java files: **696**",
            "Target final direct-root Java files: **105**",
            "Remaining direct-root non-controller files to move or collapse: **591**",
            "MinimalReadOnlyGateOperatorCiHandoff",
            "RouteCleanup web",
            "ReleaseAcceptanceRoutePathSplit",
            "The final root target may only move downward");
  }

  @Test
  void censusBucketsCoverCurrentDirectRootOpsFiles() throws IOException {
    List<String> fileNames;
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      fileNames =
          paths
              .filter(Files::isRegularFile)
              .map(path -> path.getFileName().toString())
              .filter(name -> name.endsWith(".java"))
              .sorted()
              .toList();
    }

    Map<String, List<String>> assigned = new LinkedHashMap<>();
    List<String> unassigned = new ArrayList<>();
    for (String fileName : fileNames) {
      Bucket bucket = bucketFor(fileName);
      if (bucket == null) {
        unassigned.add(fileName);
      } else {
        assigned.computeIfAbsent(bucket.name(), unused -> new ArrayList<>()).add(fileName);
      }
    }

    assertThat(fileNames).hasSize(696);
    assertThat(unassigned).isEmpty();
    for (Bucket bucket : buckets()) {
      assertThat(assigned.getOrDefault(bucket.name(), List.of()))
          .as(bucket.name())
          .hasSize(bucket.expectedCount());
    }

    int retainedRoot =
        assigned.get("keep-root controllers").size()
            + assigned.get("keep-root shared core and global route aggregator").size();
    assertThat(retainedRoot).isEqualTo(105);
    assertThat(fileNames.size() - retainedRoot).isEqualTo(591);
  }

  @Test
  void waiverListKeepsRootRetentionNarrow() throws IOException {
    assertThat(Files.isRegularFile(WAIVERS)).isTrue();
    String waivers = read(WAIVERS);

    assertThat(waivers)
        .contains(
            "ContextHeaderField.java",
            "OpsEvidenceResponse.java",
            "OpsEvidenceService.java",
            "OpsShardReadinessEvidenceEndpoints.java",
            "Reviewer check",
            "Explicit non-waivers",
            "OpsEvidenceStaticReleaseArtifact.java",
            "OpsShardReadinessReleaseAcceptanceRoutePaths.java");
    assertThat(SHARED_ROOT_KEEP)
        .containsExactlyInAnyOrder(
            "ContextHeaderField.java",
            "OpsEvidenceResponse.java",
            "OpsEvidenceService.java",
            "OpsShardReadinessEvidenceEndpoints.java",
            "OpsShardReadinessRoutePaths.java");
  }

  private static Bucket bucketFor(String fileName) {
    for (Bucket bucket : buckets()) {
      if (bucket.matches(fileName)) {
        return bucket;
      }
    }
    return null;
  }

  private static List<Bucket> buckets() {
    return List.of(
        new Bucket("keep-root controllers", 100, matches(".*Controller\\.java$")),
        new Bucket(
            "keep-root shared core and global route aggregator", 5, SHARED_ROOT_KEEP::contains),
        new Bucket("OpsEvidence static release support", 2, matches("^OpsEvidenceStaticRelease")),
        new Bucket(
            "MinimalReadOnlyGateOperatorCiHandoff",
            140,
            matches("^OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoff")),
        new Bucket(
            "MinimalReadOnlyGateExecution",
            31,
            matches("^OpsShardReadinessMinimalReadOnlyGateExecution")),
        new Bucket("RouteCleanup web", 170, matches("^OpsShardReadinessRouteCleanup")),
        new Bucket(
            "ReleaseAcceptanceRoutePathSplit",
            0,
            matches("^OpsShardReadinessReleaseAcceptanceRoutePathSplit")),
        new Bucket(
            "ReleaseAcceptanceArchiveVerificationHandoff",
            25,
            matches("^OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoff")),
        new Bucket(
            "ReleaseAcceptance root route owner",
            0,
            matches("^OpsShardReadinessReleaseAcceptanceRoutePaths\\.java$")),
        new Bucket(
            "ReleaseApprovalSandboxEndpointCredentialResolver records",
            59,
            matches("^ReleaseApprovalSandboxEndpointCredentialResolver")),
        new Bucket(
            "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver builders",
            23,
            matches("^ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver")),
        new Bucket(
            "ReleaseApprovalManagedAuditSandboxConnection builders",
            9,
            matches("^ReleaseApprovalManagedAuditSandboxConnection")),
        new Bucket(
            "ReleaseApprovalManagedAudit adapter/quality builders",
            7,
            matches("^ReleaseApprovalManagedAudit|^ReleaseApprovalOpsEvidenceServiceQualitySplit")),
        new Bucket(
            "ReleaseApprovalSandboxConnection records",
            2,
            matches(
                "^ReleaseApprovalSandboxConnection|^ReleaseApprovalRehearsalSandboxConnection")),
        new Bucket(
            "ReleaseApprovalRehearsal shared hints/request/builders",
            10,
            matches("^ReleaseApprovalRehearsal")),
        new Bucket("ReleaseApprovalVerification hints", 6, matches("^ReleaseApprovalVerification")),
        new Bucket(
            "ReleaseApproval shared support",
            2,
            matches("^ReleaseApprovalDigestSupport|^ReleaseApprovalEchoMarkerSupport")),
        new Bucket(
            "OperatorEvidenceValueSupplyAdapterPreflight",
            0,
            matches("^OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflight")),
        new Bucket(
            "OperatorEvidenceValueSupply base",
            0,
            matches("^OpsShardReadinessOperatorEvidenceValueSupply")),
        new Bucket(
            "ComparedEvidenceCandidateBlueprint",
            0,
            matches("^OpsShardReadinessComparedEvidenceCandidateBlueprint")),
        new Bucket(
            "ComparedEvidenceCandidateIntakePreflight",
            0,
            matches("^OpsShardReadinessComparedEvidenceCandidateIntakePreflight")),
        new Bucket(
            "ComparedEvidenceEvaluationPreflight",
            0,
            matches("^OpsShardReadinessComparedEvidenceEvaluationPreflight")),
        new Bucket("ComparedPackageReview", 0, matches("^OpsShardReadinessComparedPackageReview")),
        new Bucket(
            "SignedApprovalDraftProfileSection",
            0,
            matches("^OpsShardReadinessSignedApprovalDraftProfileSection")),
        new Bucket(
            "V1Contract consumer/alignment snapshots", 42, matches("^OpsShardReadinessV1Contract")),
        new Bucket(
            "ReadOnlyEvidence catalog snapshots",
            11,
            matches("^OpsShardReadinessReadOnlyEvidence|^OpsShardReadinessReadOnlyEndpoint")),
        new Bucket(
            "RuntimeExecutionApprovalInputTemplate",
            4,
            matches("^OpsShardReadinessRuntimeExecutionApprovalInputTemplate")),
        new Bucket(
            "RuntimeExecutionApproval/Input residuals",
            14,
            matches(
                "^OpsShardReadinessRuntimeExecutionApproval|^OpsShardReadinessRuntimeExecutionArtifact|"
                    + "^OpsShardReadinessRuntimeExecutionLive|^OpsShardReadinessRuntimeExecutionPacket|"
                    + "^OpsShardReadinessRuntimeExecutionPass")),
        new Bucket(
            "ActiveShardPlanHandoff", 2, matches("^OpsShardReadinessActiveShardPlanHandoff")),
        new Bucket("OpsOverview mini-family", 2, matches("^OpsOverview")),
        new Bucket("PrototypeConsumerGate", 4, matches("^OpsShardReadinessPrototypeConsumerGate")),
        new Bucket(
            "Prototype catalog/evidence/handoff residuals",
            8,
            matches("^OpsShardReadinessPrototype")),
        new Bucket(
            "Readiness core simple endpoints",
            18,
            matches(
                "^OpsShardReadiness(DeclaredOperatorLifecycle|Echo|EvidenceHandoff|EvidenceIndex|"
                    + "EvidenceVerification|Hardening|LiveReadGatePlan|OperatorServiceLifecycle|"
                    + "Service|Response)")));
  }

  private static Predicate<String> matches(String regex) {
    Pattern pattern = Pattern.compile(regex);
    return name -> pattern.matcher(name).find();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  private record Bucket(String name, int expectedCount, Predicate<String> matcher) {
    boolean matches(String fileName) {
      return matcher.test(fileName);
    }
  }
}
