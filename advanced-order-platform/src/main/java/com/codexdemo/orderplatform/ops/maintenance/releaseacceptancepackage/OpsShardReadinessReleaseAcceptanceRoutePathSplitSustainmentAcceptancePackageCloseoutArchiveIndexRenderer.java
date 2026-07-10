package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexRenderer {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexRenderer() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
              .MarkdownSection>
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .SourceSnapshot>
              sourceSnapshots,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .CriteriaEcho>
              criteriaEchoes,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .ArchiveIndexItem>
              archiveItems,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .VerificationGate>
              verificationGates,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .HandoffNote>
              handoffNotes) {
    return List.of(
        section(
            "Source Receipt",
            sourceSnapshots.stream()
                .map(
                    snapshot ->
                        "- "
                            + snapshot.source()
                            + " "
                            + snapshot.version()
                            + " status="
                            + snapshot.status())
                .toList()),
        section(
            "Criteria Echoes",
            criteriaEchoes.stream()
                .map(echo -> "- " + echo.name() + " status=" + echo.status())
                .toList()),
        section(
            "Archive Items",
            archiveItems.stream()
                .map(
                    item ->
                        "- "
                            + item.item()
                            + " retention="
                            + item.retention()
                            + " ready="
                            + item.ready())
                .toList()),
        section(
            "Verification Gates",
            verificationGates.stream()
                .map(gate -> "- " + gate.gate() + " passed=" + gate.passed())
                .toList()),
        section(
            "Handoff Notes",
            handoffNotes.stream()
                .map(note -> "- " + note.audience() + " ready=" + note.ready())
                .toList()));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }
}
