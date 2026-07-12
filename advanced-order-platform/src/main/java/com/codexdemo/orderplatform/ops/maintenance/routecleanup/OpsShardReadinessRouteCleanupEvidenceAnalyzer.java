package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessRouteCleanupEvidenceAnalyzer {

  private OpsShardReadinessRouteCleanupEvidenceAnalyzer() {}

  static List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries() {
    return OpsShardReadinessRouteCleanupEvidenceCatalog.entries();
  }

  static List<Segment> segments() {
    return List.of(
        segment(
            "latest-sibling", OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog.entries()),
        segment(
            "readiness-seed", OpsShardReadinessRouteCleanupReadinessSeedEvidenceCatalog.entries()),
        segment("handoff-core", OpsShardReadinessRouteCleanupHandoffCoreEvidenceCatalog.entries()),
        segment(
            "handoff-assurance",
            OpsShardReadinessRouteCleanupHandoffAssuranceEvidenceCatalog.entries()),
        segment(
            "handoff-governance",
            OpsShardReadinessRouteCleanupHandoffGovernanceEvidenceCatalog.entries()),
        segment(
            "post-completion",
            OpsShardReadinessRouteCleanupPostCompletionEvidenceCatalog.entries()));
  }

  static int latestJavaVersion() {
    return entries().getLast().javaVersion();
  }

  static String latestJavaVersionLabel() {
    return "Java v" + latestJavaVersion();
  }

  static boolean versionsAreContinuous() {
    List<Integer> versions =
        entries().stream()
            .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::javaVersion)
            .toList();
    return versions.equals(
        IntStream.rangeClosed(versions.getFirst(), versions.getLast()).boxed().toList());
  }

  static boolean allEntriesKeepReadOnlyBoundary() {
    return entries().stream()
        .allMatch(
            entry ->
                entry.readOnly()
                    && !entry.executionAllowed()
                    && !entry.startsJavaService()
                    && !entry.startsMiniKvService()
                    && !entry.credentialValueRead()
                    && !entry.rawEndpointParsed()
                    && !entry.managedAuditConnectionOpened()
                    && !entry.writeRoutingChanged());
  }

  static List<String> forbiddenOperations() {
    return List.of(
        "write-routing",
        "active-shard-router",
        "credential-value-read",
        "raw-endpoint-parse",
        "managed-audit-connection",
        "deployment-or-rollback",
        "node-start-or-stop-java-or-mini-kv");
  }

  static String segmentFor(OpsShardReadinessRouteCleanupEvidenceResponse.Entry entry) {
    String phase = entry.phase();
    if (phase.startsWith("handoff-suite")) {
      return "handoff-suite";
    }
    if (phase.startsWith("latest-sibling")) {
      return "latest-sibling";
    }
    if (phase.startsWith("readiness-handoff")) {
      return "readiness-handoff";
    }
    if (phase.startsWith("ci-catalog-health")) {
      return "ci-catalog-health";
    }
    if (phase.startsWith("extended-run")) {
      return "extended-run";
    }
    if (phase.startsWith("twenty-version")) {
      return "twenty-version-closeout";
    }
    return "contract-freeze";
  }

  static String boundaryStatus() {
    return versionsAreContinuous() && allEntriesKeepReadOnlyBoundary() ? "passed" : "blocked";
  }

  private static Segment segment(
      String name, List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries) {
    return new Segment(
        name,
        entries.getFirst().javaVersion(),
        entries.getLast().javaVersion(),
        entries.size(),
        entries.getFirst().phase(),
        entries.getLast().phase(),
        entries.stream()
            .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::sourceNodePlan)
            .distinct()
            .toList(),
        entries.stream().allMatch(entry -> "passed".equals(entry.status())) ? "passed" : "blocked");
  }

  record Segment(
      String name,
      int firstJavaVersion,
      int lastJavaVersion,
      int entryCount,
      String firstPhase,
      String lastPhase,
      List<String> sourceNodePlans,
      String status) {

    Segment {
      sourceNodePlans = List.copyOf(sourceNodePlans);
    }

    @Override
    public List<String> sourceNodePlans() {
      return List.copyOf(sourceNodePlans);
    }
  }
}
