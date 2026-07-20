package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.ArtifactManifestEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.BoundarySealEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.CiAttestationEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.CloseoutLedgerEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.OperatorPackEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.RetentionWindowEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.RoutePackageEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse.SourceArchiveSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<SourceArchiveSnapshot> snapshots,
      List<ArtifactManifestEntry> manifest,
      List<RoutePackageEntry> routes,
      List<OperatorPackEntry> packs,
      List<CiAttestationEntry> attestations,
      List<BoundarySealEntry> seals,
      List<RetentionWindowEntry> windows,
      List<CloseoutLedgerEntry> ledger,
      List<ScorecardEntry> scorecard) {
    return List.of(
        sourceArchive(snapshots),
        artifactManifest(manifest),
        routePackages(routes),
        operatorPacks(packs),
        ciAttestations(attestations),
        boundarySeals(seals),
        retentionWindows(windows),
        closeoutLedger(ledger),
        scorecard(scorecard));
  }

  private static MarkdownSection sourceArchive(List<SourceArchiveSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Archive",
        "source-archive-snapshot-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.version(),
                entry.endpoint(),
                entry.profile(),
                entry.releaseAcceptanceState(),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection artifactManifest(List<ArtifactManifestEntry> entries) {
    return MarkdownSections.counted(
        "Artifact Manifest",
        "artifact-manifest-count",
        entries,
        entry ->
            entry.name()
                + "="
                + entry.value()
                + " | "
                + flag("required", entry.required())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection routePackages(List<RoutePackageEntry> entries) {
    return MarkdownSections.counted(
        "Route Packages",
        "route-package-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.receiver(),
                entry.owner(),
                entry.packet(),
                flag("ready", entry.ready()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection operatorPacks(List<OperatorPackEntry> entries) {
    return MarkdownSections.counted(
        "Operator Packs",
        "operator-pack-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.owner()
                + " | "
                + entry.sourceEvidence()
                + " | "
                + flag("ready", entry.ready())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection ciAttestations(List<CiAttestationEntry> entries) {
    return MarkdownSections.counted(
        "CI Attestations",
        "ci-attestation-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | "
                + entry.commandFamily()
                + " | "
                + flag("readOnly", entry.readOnly())
                + " | "
                + flag("sourcePassed", entry.sourcePassed())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection boundarySeals(List<BoundarySealEntry> entries) {
    return MarkdownSections.counted(
        "Boundary Seals",
        "boundary-seal-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.code(),
                entry.lockedBehavior(),
                entry.auditEvidence(),
                flag("locked", entry.locked()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection retentionWindows(List<RetentionWindowEntry> entries) {
    return MarkdownSections.counted(
        "Retention Windows",
        "retention-window-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.name(),
                entry.sourceEvidence(),
                entry.retentionWindow(),
                flag("ready", entry.ready()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection closeoutLedger(List<CloseoutLedgerEntry> entries) {
    return MarkdownSections.counted(
        "Closeout Ledger",
        "closeout-ledger-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.item()
                + " | "
                + entry.owner()
                + " | "
                + entry.evidence()
                + " | "
                + flag("ready", entry.ready())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.counted(
        "Scorecard",
        "scorecard-entry-count",
        entries,
        entry ->
            entry.name()
                + "="
                + entry.actual()
                + "/"
                + entry.expected()
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static String flag(String name, boolean value) {
    return name + "=" + value;
  }

  private static String status(String value) {
    return "status=" + value;
  }
}
