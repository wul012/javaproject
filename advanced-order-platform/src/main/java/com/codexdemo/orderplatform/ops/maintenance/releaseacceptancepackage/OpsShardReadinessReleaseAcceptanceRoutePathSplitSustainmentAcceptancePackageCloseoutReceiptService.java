package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService {

  static final String RESPONSE_VERSION = "Java v1637";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE_CLOSEOUT_RECEIPT;
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package-closeout-receipt.v1";

  private final OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService
      sourceService;

  public
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService
          sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
      receipt() {
    var source = sourceService.registry();
    var criteria =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptCriteriaCatalog
            .criteria(source);
    var markdownLines = ReceiptRenderer.render(criteria);
    boolean accepted =
        "passed".equals(source.status())
            && "Java v1634".equals(source.version())
            && criteria.stream().allMatch(criterion -> "accepted".equals(criterion.status()));
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse(
        "advanced-order-platform",
        RESPONSE_VERSION,
        true,
        false,
        "Node v1903",
        "Node v1879-v1903",
        source.version(),
        source.endpoint(),
        ENDPOINT,
        PROFILE,
        criteria.size(),
        markdownLines.size(),
        List.copyOf(criteria),
        List.copyOf(markdownLines),
        checks(source, criteria, markdownLines),
        "accepted-route-path-split-sustainment-package-" + source.version(),
        accepted ? "passed" : "blocked");
  }

  private List<String> checks(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse source,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
                  .AcceptedCriterion>
          criteria,
      List<String> markdownLines) {
    List<String> checks = new ArrayList<>();
    checks.add("release-acceptance-route-path-split-package-closeout-source-plan-Node v1903");
    checks.add(
        "release-acceptance-route-path-split-package-closeout-node-parallel-plan-Node v1879-v1903");
    checks.add(
        "release-acceptance-route-path-split-package-closeout-source-version-" + source.version());
    checks.add(
        "release-acceptance-route-path-split-package-closeout-source-status-" + source.status());
    checks.add(
        "release-acceptance-route-path-split-package-closeout-criteria-count-" + criteria.size());
    checks.add(
        "release-acceptance-route-path-split-package-closeout-markdown-line-count-"
            + markdownLines.size());
    checks.add("release-acceptance-route-path-split-package-closeout-no-runtime-execution");
    checks.add("release-acceptance-route-path-split-package-closeout-no-sibling-service-startup");
    checks.add("release-acceptance-route-path-split-package-closeout-ready-for-handoff");
    return List.copyOf(checks);
  }
}
