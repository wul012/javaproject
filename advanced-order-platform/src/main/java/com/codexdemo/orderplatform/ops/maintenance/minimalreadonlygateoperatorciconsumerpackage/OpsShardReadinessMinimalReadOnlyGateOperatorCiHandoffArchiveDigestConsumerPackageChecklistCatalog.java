package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              .HandoffChecklistItem>
      checklist(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        item(1, "read-source-digest", "operator", ready),
        item(2, "confirm-boundary-locks", "operator", ready),
        item(3, "run-focused-first", "ci", ready),
        item(4, "preserve-read-only-env", "ci", ready),
        item(5, "archive-ci-conclusion", "release-review", ready));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .HandoffChecklistItem
      item(int order, String item, String owner, boolean ready) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
        .HandoffChecklistItem(order, item, owner, ready, ready ? "passed" : "blocked");
  }
}
