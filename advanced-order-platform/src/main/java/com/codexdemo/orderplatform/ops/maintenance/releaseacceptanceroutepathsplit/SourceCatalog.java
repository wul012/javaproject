package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse;
import java.util.List;

final class SourceCatalog {

  private SourceCatalog() {}

  static List<SourceSnapshot> snapshots(
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse source) {
    return List.of(
        new SourceSnapshot(
            "release-acceptance-archive-verification-handoff",
            source.version(),
            source.endpoint(),
            source.status()));
  }
}
