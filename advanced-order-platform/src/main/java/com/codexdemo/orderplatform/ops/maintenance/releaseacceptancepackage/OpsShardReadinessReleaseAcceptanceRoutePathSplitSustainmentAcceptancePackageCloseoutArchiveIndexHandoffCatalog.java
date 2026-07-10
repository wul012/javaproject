package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexHandoffCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexHandoffCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
              .HandoffNote>
      notes(
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
              source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        note(
            "archive-curator",
            "retain receipt, criteria, markdown, checks, and tags together",
            ready),
        note("release-reviewer", "use the archive index as the first scan point", ready),
        note(
            "route-owner",
            "add new route split evidence beside this index, not inside old receipt",
            ready),
        note(
            "ci-maintainer",
            "confirm focused, related, and remote CI gates before future tags",
            ready));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
          .HandoffNote
      note(String audience, String note, boolean ready) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
        .HandoffNote(audience, note, ready);
  }
}
