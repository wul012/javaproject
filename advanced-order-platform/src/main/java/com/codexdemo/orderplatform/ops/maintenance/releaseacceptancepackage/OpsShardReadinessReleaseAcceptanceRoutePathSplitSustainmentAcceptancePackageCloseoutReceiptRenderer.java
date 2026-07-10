package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptRenderer {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptRenderer() {}

  static List<String> render(
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
                  .AcceptedCriterion>
          criteria) {
    return criteria.stream()
        .map(
            criterion ->
                "- "
                    + criterion.name()
                    + " status="
                    + criterion.status()
                    + " evidence="
                    + criterion.evidence())
        .toList();
  }
}
