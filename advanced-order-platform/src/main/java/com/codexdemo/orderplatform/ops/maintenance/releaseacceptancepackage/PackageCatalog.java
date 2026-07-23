package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ArchiveItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.CiEvidence;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.DecisionRecord;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.NextChangeRule;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ReviewItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.RuntimeBoundary;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.VersionLineage;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final class PackageCatalog {

  private PackageCatalog() {}

  record Evidence(
      List<SourceSnapshot> sourceSnapshots,
      List<VersionLineage> lineage,
      List<DecisionRecord> decisions,
      List<ArchiveItem> archiveItems,
      List<ReviewItem> reviewItems,
      List<CiEvidence> ciEvidence,
      List<RuntimeBoundary> runtimeBoundaries,
      List<NextChangeRule> nextChangeRules,
      List<ScorecardEntry> scorecard) {

    Evidence {
      sourceSnapshots = List.copyOf(sourceSnapshots);
      lineage = List.copyOf(lineage);
      decisions = List.copyOf(decisions);
      archiveItems = List.copyOf(archiveItems);
      reviewItems = List.copyOf(reviewItems);
      ciEvidence = List.copyOf(ciEvidence);
      runtimeBoundaries = List.copyOf(runtimeBoundaries);
      nextChangeRules = List.copyOf(nextChangeRules);
      scorecard = List.copyOf(scorecard);
    }
  }

  static Evidence evidence(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    var sourceSnapshots = sourceSnapshots(source);
    var lineage = lineage(source);
    var decisions = decisions(source);
    var archiveItems = archiveItems(source);
    var reviewItems = reviewItems(source);
    var ciEvidence = ciEvidence(source);
    var runtimeBoundaries = runtimeBoundaries(source);
    var nextChangeRules = nextChangeRules(source);
    var scorecard =
        scorecard(
            sourceSnapshots,
            lineage,
            decisions,
            archiveItems,
            reviewItems,
            ciEvidence,
            runtimeBoundaries,
            nextChangeRules);
    return new Evidence(
        sourceSnapshots,
        lineage,
        decisions,
        archiveItems,
        reviewItems,
        ciEvidence,
        runtimeBoundaries,
        nextChangeRules,
        scorecard);
  }

  private static List<SourceSnapshot> sourceSnapshots(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return List.of(
        new SourceSnapshot(
            "release-acceptance-route-path-split-sustainment",
            source.version(),
            source.endpoint(),
            source.status(),
            source.profile()));
  }

  private static List<VersionLineage> lineage(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        lineageEntry(
            "route-path-split", source.sourceSplitVersion(), source.sourceSplitEndpoint(), passed),
        lineageEntry(
            "route-path-split-closeout",
            source.sourceCloseoutVersion(),
            source.sourceCloseoutEndpoint(),
            passed),
        lineageEntry("route-path-split-sustainment", source.version(), source.endpoint(), passed));
  }

  private static List<DecisionRecord> decisions(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return List.of(
        decision(
            "accept-sustainment-registry",
            "release-acceptance-maintainer",
            "v1604 sustainment registry passed",
            "passed".equals(source.status())),
        decision(
            "freeze-stable-route-delegate",
            "route-owner",
            "stable-route-delegate ownership rule is enforced",
            ownershipHeld(source, "stable-route-delegate")),
        decision(
            "require-catalog-before-route",
            "catalog-owner",
            "catalog-ownership rule is enforced before endpoint growth",
            ownershipHeld(source, "catalog-ownership")),
        decision(
            "require-renderer-split",
            "renderer-owner",
            "renderer-ownership rule keeps markdown sections separated",
            ownershipHeld(source, "renderer-ownership")),
        decision(
            "keep-runtime-disabled",
            "ops-boundary-owner",
            "package remains read-only and execution is not allowed",
            source.readOnly() && !source.executionAllowed()),
        decision(
            "parallel-node-no-fresh-evidence",
            "sibling-plan-owner",
            "Node v1879-v1903 remains parallel and needs no fresh Java or mini-kv startup",
            "passed".equals(source.status())));
  }

  private static List<ArchiveItem> archiveItems(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        archiveItem("sustainment-response", "release-evidence-bundle", source.endpoint(), passed),
        archiveItem(
            "version-tags-v1580-v1604",
            "version-lineage",
            source.version(),
            "Java v1604".equals(source.version())),
        archiveItem(
            "boundary-lock-matrix",
            "runtime-boundary-archive",
            "boundary-count=" + source.boundaryGuardCount(),
            allBoundariesLocked(source)),
        archiveItem(
            "ci-gate-ledger",
            "ci-run-archive",
            "ci-gates=" + source.ciGateCount(),
            allCiGatesRequired(source)),
        archiveItem(
            "consumer-handoff-rules",
            "handoff-archive",
            "handoffs=" + source.consumerHandoffCount(),
            allConsumersReady(source)));
  }

  private static List<ReviewItem> reviewItems(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        reviewItem("release-reviewer", "status-and-counts", "source status passed", passed),
        reviewItem(
            "route-owner",
            "route-delegate",
            "stable delegate remains enforced",
            ownershipHeld(source, "stable-route-delegate")),
        reviewItem(
            "test-owner",
            "coverage",
            "catalog, renderer, controller, and immutability tests exist",
            ownershipHeld(source, "test-ownership")),
        reviewItem("ci-owner", "ci-gates", "five CI gates are required", source.ciGateCount() == 5),
        reviewItem(
            "archive-owner", "archive-items", "acceptance package is ready for retention", passed));
  }

  private static List<CiEvidence> ciEvidence(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.ciGates().stream()
        .map(
            gate ->
                new CiEvidence(
                    gate.gate(),
                    gate.command(),
                    gate.required() ? "required" : "optional",
                    gate.required()))
        .toList();
  }

  private static List<RuntimeBoundary> runtimeBoundaries(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.boundaryGuards().stream()
        .map(
            boundary ->
                new RuntimeBoundary(
                    boundary.boundary(),
                    "locked-from-sustainment",
                    boundary.evidence(),
                    boundary.locked()))
        .toList();
  }

  private static List<NextChangeRule> nextChangeRules(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        nextChangeRule(
            "new-route-path",
            "route-path catalog then release acceptance route group",
            "route-owner",
            ready),
        nextChangeRule("new-consumer", "consumer catalog", "handoff-owner", ready),
        nextChangeRule("new-ci-gate", "CI catalog and renderer", "ci-owner", ready),
        nextChangeRule(
            "new-boundary",
            "boundary catalog and runtime boundary package",
            "ops-boundary-owner",
            ready),
        nextChangeRule(
            "source-plan-roll",
            "support constants and source catalog",
            "sibling-plan-owner",
            ready),
        nextChangeRule(
            "markdown-copy-change",
            "section renderer for the affected concern",
            "renderer-owner",
            ready));
  }

  private static List<ScorecardEntry> scorecard(
      List<SourceSnapshot> sourceSnapshots,
      List<VersionLineage> lineage,
      List<DecisionRecord> decisions,
      List<ArchiveItem> archiveItems,
      List<ReviewItem> reviewItems,
      List<CiEvidence> ciEvidence,
      List<RuntimeBoundary> runtimeBoundaries,
      List<NextChangeRule> nextChangeRules) {
    return List.of(
        scorecardEntry(
            "source",
            sourceSnapshots.stream().allMatch(snapshot -> "passed".equals(snapshot.status())),
            "sustainment source passed"),
        scorecardEntry(
            "lineage",
            lineage.stream().allMatch(item -> "passed".equals(item.status())),
            "split, closeout, and sustainment versions are linked"),
        scorecardEntry(
            "decisions",
            decisions.stream().allMatch(DecisionRecord::accepted),
            "acceptance decisions are explicit"),
        scorecardEntry(
            "archive",
            archiveItems.stream().allMatch(ArchiveItem::ready),
            "archive items are ready"),
        scorecardEntry(
            "review", reviewItems.stream().allMatch(ReviewItem::passed), "review checklist passed"),
        scorecardEntry(
            "ci", ciEvidence.stream().allMatch(CiEvidence::passed), "CI evidence remains required"),
        scorecardEntry(
            "runtime-boundaries",
            runtimeBoundaries.stream().allMatch(RuntimeBoundary::locked),
            "runtime boundaries are locked"),
        scorecardEntry(
            "next-change",
            nextChangeRules.stream().allMatch(NextChangeRule::ready),
            "future changes have landing zones"),
        scorecardEntry(
            "maintainability",
            true,
            "acceptance package is split into focused catalogs and renderers"));
  }

  private static boolean ownershipHeld(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      String component) {
    return source.ownershipRules().stream()
        .anyMatch(rule -> rule.component().equals(component) && rule.enforced());
  }

  private static boolean allBoundariesLocked(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.boundaryGuards().stream().allMatch(boundary -> boundary.locked());
  }

  private static boolean allCiGatesRequired(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.ciGates().stream().allMatch(gate -> gate.required());
  }

  private static boolean allConsumersReady(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.consumerHandoffs().stream().allMatch(handoff -> handoff.ready());
  }

  private static VersionLineage lineageEntry(
      String stage, String version, String endpoint, boolean passed) {
    return new VersionLineage(stage, version, endpoint, passed ? "passed" : "blocked");
  }

  private static DecisionRecord decision(
      String decision, String owner, String rationale, boolean accepted) {
    return new DecisionRecord(decision, owner, rationale, accepted);
  }

  private static ArchiveItem archiveItem(
      String artifact, String retention, String evidence, boolean ready) {
    return new ArchiveItem(artifact, retention, evidence, ready);
  }

  private static ReviewItem reviewItem(
      String reviewer, String checklist, String expectation, boolean passed) {
    return new ReviewItem(reviewer, checklist, expectation, passed);
  }

  private static NextChangeRule nextChangeRule(
      String trigger, String landingZone, String reviewer, boolean ready) {
    return new NextChangeRule(trigger, landingZone, reviewer, ready);
  }

  private static ScorecardEntry scorecardEntry(String category, boolean passed, String detail) {
    return new ScorecardEntry(category, passed, detail);
  }
}
