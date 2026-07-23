package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import static com.codexdemo.orderplatform.ops.maintenance.evidencecore.EvidenceCounts.matching;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.AcceptanceCriterion;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.CiMatrixEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ConsumerAudience;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.HandoffChecklistItem;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ManifestEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.PackageSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.SourceDigestSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final class PackageCatalog {

  static final int SOURCE_COUNT = 1;
  static final int MANIFEST_COUNT = 5;
  static final int AUDIENCE_COUNT = 4;
  static final int SECTION_COUNT = 5;
  static final int ACCEPTANCE_COUNT = 5;
  static final int CI_COUNT = 5;
  static final int LOCK_COUNT = 8;
  static final int CHECKLIST_COUNT = 5;
  static final int SCORECARD_COUNT = 8;

  private PackageCatalog() {}

  static Evidence evidence(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    var manifest = manifest(source);
    var audiences = audiences(source);
    var sections = sections(source);
    var criteria = criteria(source);
    var ciMatrix = ciMatrix(source);
    var locks = locks(source);
    var checklist = checklist(source);
    return new Evidence(
        snapshots(source),
        manifest,
        audiences,
        sections,
        criteria,
        ciMatrix,
        locks,
        checklist,
        scorecard(source, manifest, audiences, sections, criteria, ciMatrix, locks, checklist));
  }

  private static List<SourceDigestSnapshot> snapshots(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return List.of(
        new SourceDigestSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.sourceArchiveVersion(),
            source.digestState(),
            source.digestSectionCount(),
            source.consumerPacketCount(),
            source.replayInstructionCount(),
            source.boundaryLockCount(),
            source.status()));
  }

  private static List<ManifestEntry> manifest(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return List.of(
        manifestEntry("source-digest-version", source.version()),
        manifestEntry("source-archive-version", source.sourceArchiveVersion()),
        manifestEntry("source-digest-state", source.digestState()),
        manifestEntry("source-endpoint", source.endpoint()),
        manifestEntry("source-profile", source.profile()));
  }

  private static ManifestEntry manifestEntry(String name, String value) {
    boolean passed = value != null && !value.isBlank();
    return new ManifestEntry(name, value, true, passed ? "passed" : "blocked");
  }

  private static List<ConsumerAudience> audiences(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return source.consumerPackets().stream().map(PackageCatalog::audience).toList();
  }

  private static ConsumerAudience audience(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .ConsumerPacket
          source) {
    return new ConsumerAudience(
        source.packet(),
        source.owner(),
        source.packet(),
        source.ready(),
        source.ready() ? "passed" : "blocked");
  }

  private static List<PackageSection> sections(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    boolean sourcePassed = "passed".equals(source.status());
    return List.of(
        section("source-digest-summary", "release-review", source.version(), sourcePassed),
        section("manifest", "operator-ci", source.profile(), sourcePassed),
        section(
            "consumer-packets",
            "operator-ci",
            "packets=" + source.consumerPacketCount(),
            sourcePassed),
        section(
            "ci-matrix",
            "ci",
            "replay-instructions=" + source.replayInstructionCount(),
            sourcePassed),
        section(
            "boundary-locks",
            "operator",
            "locked-boundaries=" + source.lockedBoundaryCount(),
            sourcePassed));
  }

  private static PackageSection section(String name, String owner, String evidence, boolean ready) {
    return new PackageSection(name, owner, evidence, ready, ready ? "passed" : "blocked");
  }

  private static List<AcceptanceCriterion> criteria(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return List.of(
        criterion(
            "source-digest-passed", "status=" + source.status(), "passed".equals(source.status())),
        criterion(
            "digest-sections-passed",
            "digest-sections="
                + source.passedDigestSectionCount()
                + "/"
                + source.digestSectionCount(),
            source.passedDigestSectionCount() == source.digestSectionCount()),
        criterion(
            "consumer-packets-ready",
            "consumer-packets="
                + source.readyConsumerPacketCount()
                + "/"
                + source.consumerPacketCount(),
            source.readyConsumerPacketCount() == source.consumerPacketCount()),
        criterion(
            "replay-instructions-read-only",
            "replay="
                + source.readOnlyReplayInstructionCount()
                + "/"
                + source.replayInstructionCount(),
            source.readOnlyReplayInstructionCount() == source.replayInstructionCount()),
        criterion(
            "boundaries-locked",
            "boundaries=" + source.lockedBoundaryCount() + "/" + source.boundaryLockCount(),
            source.lockedBoundaryCount() == source.boundaryLockCount()));
  }

  private static AcceptanceCriterion criterion(String code, String evidence, boolean passed) {
    return new AcceptanceCriterion(code, evidence, passed, passed ? "passed" : "blocked");
  }

  private static List<CiMatrixEntry> ciMatrix(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return source.replayInstructions().stream().map(PackageCatalog::ciEntry).toList();
  }

  private static CiMatrixEntry ciEntry(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .ReplayInstruction
          source) {
    return new CiMatrixEntry(
        source.order(),
        source.batch(),
        source.commandFamily(),
        source.readOnly(),
        source.sourcePassed(),
        source.readOnly() && source.sourcePassed() ? "passed" : "blocked");
  }

  private static List<BoundaryLock> locks(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    return source.boundaryLocks().stream().map(PackageCatalog::lock).toList();
  }

  private static BoundaryLock lock(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .BoundaryLock
          source) {
    return new BoundaryLock(
        source.code(), source.lockedBehavior(), source.locked(), source.reason());
  }

  private static List<HandoffChecklistItem> checklist(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        checklistItem(1, "read-source-digest", "operator", ready),
        checklistItem(2, "confirm-boundary-locks", "operator", ready),
        checklistItem(3, "run-focused-first", "ci", ready),
        checklistItem(4, "preserve-read-only-env", "ci", ready),
        checklistItem(5, "archive-ci-conclusion", "release-review", ready));
  }

  private static HandoffChecklistItem checklistItem(
      int order, String item, String owner, boolean ready) {
    return new HandoffChecklistItem(order, item, owner, ready, ready ? "passed" : "blocked");
  }

  private static List<ScorecardEntry> scorecard(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse source,
      List<ManifestEntry> manifest,
      List<ConsumerAudience> audiences,
      List<PackageSection> sections,
      List<AcceptanceCriterion> criteria,
      List<CiMatrixEntry> ciMatrix,
      List<BoundaryLock> locks,
      List<HandoffChecklistItem> checklist) {
    return List.of(
        score("source-digest-status", 1, "passed".equals(source.status()) ? 1 : 0),
        score(
            "manifest",
            MANIFEST_COUNT,
            matching(manifest, entry -> "passed".equals(entry.status()))),
        score("consumer-audiences", AUDIENCE_COUNT, matching(audiences, ConsumerAudience::ready)),
        score("package-sections", SECTION_COUNT, matching(sections, PackageSection::ready)),
        score(
            "acceptance-criteria",
            ACCEPTANCE_COUNT,
            matching(criteria, AcceptanceCriterion::passed)),
        score("ci-matrix", CI_COUNT, matching(ciMatrix, CiMatrixEntry::readOnly)),
        score("boundary-locks", LOCK_COUNT, matching(locks, BoundaryLock::locked)),
        score(
            "handoff-checklist",
            CHECKLIST_COUNT,
            matching(checklist, HandoffChecklistItem::ready)));
  }

  private static ScorecardEntry score(String name, int expected, int actual) {
    return new ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
  }

  record Evidence(
      List<SourceDigestSnapshot> sourceDigests,
      List<ManifestEntry> manifest,
      List<ConsumerAudience> audiences,
      List<PackageSection> sections,
      List<AcceptanceCriterion> criteria,
      List<CiMatrixEntry> ciMatrix,
      List<BoundaryLock> locks,
      List<HandoffChecklistItem> checklist,
      List<ScorecardEntry> scorecard) {
    Evidence {
      sourceDigests = List.copyOf(sourceDigests);
      manifest = List.copyOf(manifest);
      audiences = List.copyOf(audiences);
      sections = List.copyOf(sections);
      criteria = List.copyOf(criteria);
      ciMatrix = List.copyOf(ciMatrix);
      locks = List.copyOf(locks);
      checklist = List.copyOf(checklist);
      scorecard = List.copyOf(scorecard);
    }
  }
}
