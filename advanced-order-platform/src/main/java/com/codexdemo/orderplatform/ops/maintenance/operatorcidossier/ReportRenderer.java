package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.AcceptanceGate;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.AudienceRoute;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.BoundaryAudit;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.CiLane;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.HandoffReceipt;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.ProvenanceEntry;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.ReleaseChecklistItem;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.SectionDigest;
import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.SourcePackageSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
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
    return List.of(
        sourcePackages(sourcePackages),
        provenance(provenance),
        sectionDigests(sectionDigests),
        audienceRoutes(audienceRoutes),
        ciLanes(ciLanes),
        acceptanceGates(acceptanceGates),
        boundaryAudits(boundaryAudits),
        releaseChecklist(releaseChecklist),
        handoffReceipts(handoffReceipts),
        scorecard(scorecard));
  }

  private static MarkdownSection sourcePackages(List<SourcePackageSnapshot> entries) {
    return MarkdownSections.mapped(
        "Source Consumer Package",
        entries,
        entry ->
            entry.version()
                + " | state="
                + entry.consumerPackageState()
                + " | manifest="
                + entry.manifestEntryCount()
                + " | sections="
                + entry.packageSectionCount()
                + " | ci="
                + entry.ciMatrixEntryCount()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection provenance(List<ProvenanceEntry> entries) {
    return MarkdownSections.mapped(
        "Provenance",
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

  private static MarkdownSection sectionDigests(List<SectionDigest> entries) {
    return MarkdownSections.mapped(
        "Section Digests",
        entries,
        entry ->
            entry.heading()
                + " | lines="
                + entry.lineCount()
                + " | required="
                + entry.required()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection audienceRoutes(List<AudienceRoute> entries) {
    return MarkdownSections.mapped(
        "Audience Routes",
        entries,
        entry ->
            entry.audience()
                + " -> "
                + entry.reviewerLane()
                + " | owner="
                + entry.owner()
                + " | packet="
                + entry.packet()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection ciLanes(List<CiLane> entries) {
    return MarkdownSections.mapped(
        "CI Lanes",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | command="
                + entry.commandFamily()
                + " | replayGroup="
                + entry.replayGroup()
                + " | readOnly="
                + entry.readOnly()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection acceptanceGates(List<AcceptanceGate> entries) {
    return MarkdownSections.mapped(
        "Acceptance Gates",
        entries,
        entry ->
            entry.code()
                + " | artifact="
                + entry.verifyingArtifact()
                + " | evidence="
                + entry.evidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaryAudits(List<BoundaryAudit> entries) {
    return MarkdownSections.mapped(
        "Boundary Audits",
        entries,
        entry ->
            entry.code()
                + " | lockedBehavior="
                + entry.lockedBehavior()
                + " | evidence="
                + entry.auditEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection releaseChecklist(List<ReleaseChecklistItem> entries) {
    return MarkdownSections.mapped(
        "Release Checklist",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.item()
                + " | owner="
                + entry.owner()
                + " | evidence="
                + entry.releaseEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection handoffReceipts(List<HandoffReceipt> entries) {
    return MarkdownSections.mapped(
        "Handoff Receipts",
        entries,
        entry ->
            entry.receiver()
                + " | receiptType="
                + entry.receiptType()
                + " | evidence="
                + entry.sourceEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.mapped(
        "Scorecard",
        entries,
        entry ->
            entry.name()
                + " | expected="
                + entry.expected()
                + " | actual="
                + entry.actual()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }
}
