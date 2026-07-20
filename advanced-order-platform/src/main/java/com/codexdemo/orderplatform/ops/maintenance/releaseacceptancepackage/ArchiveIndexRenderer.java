package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.ArchiveIndexItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.CriteriaEcho;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.HandoffNote;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.VerificationGate;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ArchiveIndexRenderer {

  private ArchiveIndexRenderer() {}

  static List<MarkdownSection> render(
      List<SourceSnapshot> snapshots,
      List<CriteriaEcho> criteria,
      List<ArchiveIndexItem> items,
      List<VerificationGate> gates,
      List<HandoffNote> notes) {
    return List.of(
        source(snapshots), criteria(criteria), archive(items), verification(gates), handoff(notes));
  }

  private static MarkdownSection source(List<SourceSnapshot> entries) {
    return MarkdownSections.mapped(
        "Source Receipt",
        entries,
        entry -> "- " + entry.source() + " " + entry.version() + " status=" + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection criteria(List<CriteriaEcho> entries) {
    return MarkdownSections.mapped(
        "Criteria Echoes",
        entries,
        entry -> "- " + entry.name() + " status=" + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection archive(List<ArchiveIndexItem> entries) {
    return MarkdownSections.mapped(
        "Archive Items",
        entries,
        entry ->
            "- " + entry.item() + " retention=" + entry.retention() + " ready=" + entry.ready(),
        MarkdownSection::new);
  }

  private static MarkdownSection verification(List<VerificationGate> entries) {
    return MarkdownSections.mapped(
        "Verification Gates",
        entries,
        entry -> "- " + entry.gate() + " passed=" + entry.passed(),
        MarkdownSection::new);
  }

  private static MarkdownSection handoff(List<HandoffNote> entries) {
    return MarkdownSections.mapped(
        "Handoff Notes",
        entries,
        entry -> "- " + entry.audience() + " ready=" + entry.ready(),
        MarkdownSection::new);
  }
}
