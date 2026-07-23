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
    var evidence = PackageCatalog.evidence(source);
    return PackageSupport.response(
        RESPONSE_VERSION, ENDPOINT, source, evidence, ReportRenderer.render(evidence));
  }
}
