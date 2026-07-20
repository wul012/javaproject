package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService {

  static final String RESPONSE_VERSION = "Java v1634";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE;

  private final OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService sourceService;

  public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      registry() {
    var source = sourceService.registry();
    var sourceSnapshots =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog
            .snapshots(source);
    var lineage =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageCatalog
            .lineage(source);
    var decisions =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionCatalog
            .decisions(source);
    var archiveItems =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveCatalog
            .items(source);
    var reviewItems =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewCatalog
            .items(source);
    var ciEvidence =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiCatalog
            .evidence(source);
    var runtimeBoundaries =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryCatalog
            .boundaries(source);
    var nextChangeRules =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeCatalog
            .rules(source);
    var scorecard =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardCatalog
            .scorecard(
                sourceSnapshots,
                lineage,
                decisions,
                archiveItems,
                reviewItems,
                ciEvidence,
                runtimeBoundaries,
                nextChangeRules);
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            source,
            sourceSnapshots,
            lineage,
            decisions,
            archiveItems,
            reviewItems,
            ciEvidence,
            runtimeBoundaries,
            nextChangeRules,
            scorecard,
            ReportRenderer.render(
                sourceSnapshots,
                lineage,
                decisions,
                archiveItems,
                reviewItems,
                ciEvidence,
                runtimeBoundaries,
                nextChangeRules,
                scorecard));
  }
}
