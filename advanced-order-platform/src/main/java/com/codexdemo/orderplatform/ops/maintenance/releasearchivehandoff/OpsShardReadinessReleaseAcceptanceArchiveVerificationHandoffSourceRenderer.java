package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceRenderer {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceRenderer() {}

  static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .SourceArchiveSnapshot>
              snapshots) {
    List<String> lines = new ArrayList<>();
    lines.add("source-archive-snapshot-count=" + snapshots.size());
    snapshots.forEach(
        snapshot ->
            lines.add(
                String.join(
                    " | ",
                    snapshot.version(),
                    snapshot.endpoint(),
                    snapshot.profile(),
                    snapshot.archiveRegistryState(),
                    OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(snapshot.status()))));
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport.section(
        "Source Archive", lines);
  }
}
