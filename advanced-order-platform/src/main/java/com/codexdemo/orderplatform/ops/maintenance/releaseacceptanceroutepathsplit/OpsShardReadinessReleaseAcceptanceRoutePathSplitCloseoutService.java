package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService {

  static final String RESPONSE_VERSION = "Java v1579";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY;

  private final OpsShardReadinessReleaseAcceptanceRoutePathSplitService sourceService;

  public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitService sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
    var source = sourceService.registry();
    var closeoutItems = CloseoutItemCatalog.items(source);
    var boundaryAssertions = CloseoutBoundaryCatalog.assertions(source);
    return CloseoutAssembler.response(
        RESPONSE_VERSION,
        ENDPOINT,
        source,
        closeoutItems,
        boundaryAssertions,
        CloseoutRenderer.render(closeoutItems, boundaryAssertions));
  }
}
