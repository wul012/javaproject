package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.*;
import java.util.List;

final class DossierCatalog {

  static final int SOURCE_COUNT = 1;
  static final int PROVENANCE_COUNT = 6;
  static final int DIGEST_COUNT = 9;
  static final int AUDIENCE_COUNT = 4;
  static final int CI_COUNT = 5;
  static final int GATE_COUNT = 5;
  static final int AUDIT_COUNT = 8;
  static final int CHECKLIST_COUNT = 5;
  static final int RECEIPT_COUNT = 4;
  static final int SCORECARD_COUNT = 10;

  private DossierCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    var sourcePackages = snapshots(source);
    var provenance = provenance(source);
    var sectionDigests = digests(source);
    var audienceRoutes = routes(source);
    var ciLanes = lanes(source);
    var acceptanceGates = gates(source);
    var boundaryAudits = audits(source);
    var releaseChecklist = checklist(source);
    var handoffReceipts = receipts(source);
    return new Evidence(
        sourcePackages,
        provenance,
        sectionDigests,
        audienceRoutes,
        ciLanes,
        acceptanceGates,
        boundaryAudits,
        releaseChecklist,
        handoffReceipts,
        scorecard(
            source,
            sourcePackages,
            provenance,
            sectionDigests,
            audienceRoutes,
            ciLanes,
            acceptanceGates,
            boundaryAudits,
            releaseChecklist,
            handoffReceipts));
  }

  private static List<SourcePackageSnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return List.of(
        new SourcePackageSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.consumerPackageState(),
            source.manifestEntryCount(),
            source.packageSectionCount(),
            source.ciMatrixEntryCount(),
            source.boundaryLockCount(),
            source.handoffChecklistCount(),
            source.status()));
  }

  private static List<ProvenanceEntry> provenance(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return List.of(
        provenance("source-consumer-package-version", source.version()),
        provenance("source-consumer-package-endpoint", source.endpoint()),
        provenance("source-consumer-package-profile", source.profile()),
        provenance("source-digest-version", source.sourceDigestVersion()),
        provenance("source-digest-state", source.sourceDigestState()),
        provenance("source-consumer-package-state", source.consumerPackageState()));
  }

  private static ProvenanceEntry provenance(String name, String value) {
    boolean passed = value != null && !value.isBlank();
    return new ProvenanceEntry(name, value, true, passed ? "passed" : "blocked");
  }

  private static List<SectionDigest> digests(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.markdownSections().stream()
        .map(
            section -> {
              int lines = section.lines().size();
              boolean passed = !section.heading().isBlank() && lines > 0;
              return new SectionDigest(
                  section.heading(), lines, true, passed ? "passed" : "blocked");
            })
        .toList();
  }

  private static List<AudienceRoute> routes(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.consumerAudiences().stream()
        .map(
            route ->
                new AudienceRoute(
                    route.audience(),
                    route.owner(),
                    route.packet(),
                    reviewerLane(route.packet()),
                    route.ready(),
                    route.status()))
        .toList();
  }

  private static String reviewerLane(String packet) {
    if (packet.contains("ci")) {
      return "ci-non-docker-regression";
    }
    if (packet.contains("operator")) {
      return "operator-review";
    }
    if (packet.contains("archive")) {
      return "archive-verification";
    }
    return "read-only-consumer-review";
  }

  private static List<CiLane> lanes(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.ciMatrix().stream()
        .map(
            lane ->
                new CiLane(
                    lane.order(),
                    lane.batch(),
                    lane.commandFamily(),
                    lane.readOnly(),
                    lane.sourcePassed(),
                    replayGroup(lane.commandFamily()),
                    lane.status()))
        .toList();
  }

  private static String replayGroup(String commandFamily) {
    return switch (commandFamily) {
      case "focused" -> "focused-preflight";
      case "grouped" -> "grouped-non-docker-regression";
      case "build" -> "package-build";
      case "smoke" -> "read-only-smoke";
      default -> "read-only-review";
    };
  }

  private static List<AcceptanceGate> gates(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.acceptanceCriteria().stream()
        .map(
            gate ->
                new AcceptanceGate(
                    gate.code(),
                    gate.evidence(),
                    gate.code() + "-verification-dossier",
                    gate.passed(),
                    gate.status()))
        .toList();
  }

  private static List<BoundaryAudit> audits(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.boundaryLocks().stream()
        .map(
            audit ->
                new BoundaryAudit(
                    audit.code(),
                    audit.lockedBehavior(),
                    audit.locked(),
                    "consumer-package-boundary-lock:" + audit.reason(),
                    audit.locked() ? "passed" : "blocked"))
        .toList();
  }

  private static List<ReleaseChecklistItem> checklist(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return source.handoffChecklist().stream()
        .map(
            item ->
                new ReleaseChecklistItem(
                    item.order(),
                    item.item(),
                    item.owner(),
                    "consumer-package-checklist:" + item.status(),
                    item.ready(),
                    item.status()))
        .toList();
  }

  private static List<HandoffReceipt> receipts(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    return List.of(
        receipt("operator-ci-handoff-owner", source.version(), "consumer-package-source", source),
        receipt(
            "node-v368-archive-verifier",
            source.sourceDigestVersion(),
            "archive-verification-input",
            source),
        receipt("node-v369-operator-ci", source.profile(), "operator-ci-handoff-input", source),
        receipt(
            "java-read-only-boundary-owner",
            source.endpoint(),
            "read-only-boundary-continuity",
            source));
  }

  private static HandoffReceipt receipt(
      String receiver,
      String sourceEvidence,
      String receiptType,
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source) {
    boolean ready =
        "passed".equals(source.status()) && sourceEvidence != null && !sourceEvidence.isBlank();
    return new HandoffReceipt(
        receiver, sourceEvidence, receiptType, ready, ready ? "passed" : "blocked");
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          source,
      List<SourcePackageSnapshot> sourcePackages,
      List<ProvenanceEntry> provenance,
      List<SectionDigest> digests,
      List<AudienceRoute> routes,
      List<CiLane> lanes,
      List<AcceptanceGate> gates,
      List<BoundaryAudit> audits,
      List<ReleaseChecklistItem> checklist,
      List<HandoffReceipt> receipts) {
    return List.of(
        score("source-consumer-package-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score("source-package-snapshot", SOURCE_COUNT, sourcePackages.size()),
        score(
            "provenance",
            PROVENANCE_COUNT,
            matching(provenance, item -> "passed".equals(item.status()))),
        score(
            "section-digests",
            DIGEST_COUNT,
            matching(digests, item -> "passed".equals(item.status()))),
        score("audience-routes", AUDIENCE_COUNT, matching(routes, AudienceRoute::ready)),
        score("ci-lanes", CI_COUNT, matching(lanes, CiLane::readOnly)),
        score("acceptance-gates", GATE_COUNT, matching(gates, AcceptanceGate::passed)),
        score("boundary-audits", AUDIT_COUNT, matching(audits, BoundaryAudit::locked)),
        score(
            "release-checklist", CHECKLIST_COUNT, matching(checklist, ReleaseChecklistItem::ready)),
        score("handoff-receipts", RECEIPT_COUNT, matching(receipts, HandoffReceipt::ready)));
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourcePackageSnapshot> sourcePackages,
      List<ProvenanceEntry> provenance,
      List<SectionDigest> sectionDigests,
      List<AudienceRoute> audienceRoutes,
      List<CiLane> ciLanes,
      List<AcceptanceGate> acceptanceGates,
      List<BoundaryAudit> boundaryAudits,
      List<ReleaseChecklistItem> releaseChecklist,
      List<HandoffReceipt> handoffReceipts,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourcePackages = List.copyOf(sourcePackages);
      provenance = List.copyOf(provenance);
      sectionDigests = List.copyOf(sectionDigests);
      audienceRoutes = List.copyOf(audienceRoutes);
      ciLanes = List.copyOf(ciLanes);
      acceptanceGates = List.copyOf(acceptanceGates);
      boundaryAudits = List.copyOf(boundaryAudits);
      releaseChecklist = List.copyOf(releaseChecklist);
      handoffReceipts = List.copyOf(handoffReceipts);
      scorecard = List.copyOf(scorecard);
    }
  }
}
