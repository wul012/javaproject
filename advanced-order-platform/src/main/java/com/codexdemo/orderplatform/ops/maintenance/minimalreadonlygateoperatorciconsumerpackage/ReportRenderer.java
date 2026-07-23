package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.AcceptanceCriterion;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.CiMatrixEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ConsumerAudience;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.HandoffChecklistItem;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ManifestEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.PackageSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.SourceDigestSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(PackageCatalog.Evidence evidence) {
    return List.of(
        sources(evidence.sourceDigests()),
        manifest(evidence.manifest()),
        audiences(evidence.audiences()),
        sections(evidence.sections()),
        acceptance(evidence.criteria()),
        ciMatrix(evidence.ciMatrix()),
        locks(evidence.locks()),
        checklist(evidence.checklist()),
        scorecard(evidence.scorecard()));
  }

  private static MarkdownSection sources(List<SourceDigestSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Digest",
        "source-digest-count",
        entries,
        source ->
            source.version()
                + " | "
                + source.endpoint()
                + " | "
                + source.digestState()
                + " | status="
                + source.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection manifest(List<ManifestEntry> entries) {
    return MarkdownSections.counted(
        "Manifest",
        "manifest-entry-count",
        entries,
        entry ->
            entry.name()
                + "="
                + entry.value()
                + " | required="
                + entry.required()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection audiences(List<ConsumerAudience> entries) {
    return MarkdownSections.counted(
        "Consumer Audiences",
        "consumer-audience-count",
        entries,
        audience ->
            audience.audience()
                + " | "
                + audience.owner()
                + " | packet="
                + audience.packet()
                + " | status="
                + audience.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection sections(List<PackageSection> entries) {
    return MarkdownSections.counted(
        "Package Sections",
        "package-section-count",
        entries,
        section ->
            section.section()
                + " | "
                + section.owner()
                + " | "
                + section.sourceEvidence()
                + " | status="
                + section.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection acceptance(List<AcceptanceCriterion> entries) {
    return MarkdownSections.counted(
        "Acceptance Criteria",
        "acceptance-criterion-count",
        entries,
        criterion ->
            criterion.code() + " | " + criterion.evidence() + " | passed=" + criterion.passed(),
        MarkdownSection::new);
  }

  private static MarkdownSection ciMatrix(List<CiMatrixEntry> entries) {
    return MarkdownSections.counted(
        "CI Matrix",
        "ci-matrix-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | "
                + entry.commandFamily()
                + " | read-only="
                + entry.readOnly()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection locks(List<BoundaryLock> entries) {
    return MarkdownSections.counted(
        "Boundary Locks",
        "boundary-lock-count",
        entries,
        lock -> lock.code() + " | locked=" + lock.locked() + " | " + lock.reason(),
        MarkdownSection::new);
  }

  private static MarkdownSection checklist(List<HandoffChecklistItem> entries) {
    return MarkdownSections.counted(
        "Handoff Checklist",
        "handoff-checklist-count",
        entries,
        item ->
            item.order()
                + ". "
                + item.item()
                + " | "
                + item.owner()
                + " | ready="
                + item.ready()
                + " | status="
                + item.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.counted(
        "Scorecard",
        "scorecard-entry-count",
        entries,
        score ->
            score.name()
                + "="
                + score.actual()
                + "/"
                + score.expected()
                + " | status="
                + score.status(),
        MarkdownSection::new);
  }
}
