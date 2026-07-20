package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse.AcceptedCriterion;
import java.util.List;

final class ReceiptRenderer {

  private ReceiptRenderer() {}

  static List<String> render(List<AcceptedCriterion> criteria) {
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
