package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot> snapshots(
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse source) {
    return List.of(
        new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot(
            "release-acceptance-archive-verification-handoff",
            source.version(),
            source.endpoint(),
            source.status()));
  }
}
