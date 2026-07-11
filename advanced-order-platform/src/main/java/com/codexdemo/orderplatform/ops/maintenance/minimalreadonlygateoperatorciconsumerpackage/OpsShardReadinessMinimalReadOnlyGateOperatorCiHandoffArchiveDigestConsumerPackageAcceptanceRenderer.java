package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .AcceptanceCriterion>
              criteria) {
    List<String> lines = new ArrayList<>();
    lines.add("acceptance-criterion-count=" + criteria.size());
    criteria.forEach(
        criterion ->
            lines.add(
                criterion.code()
                    + " | "
                    + criterion.evidence()
                    + " | passed="
                    + criterion.passed()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
        .section("Acceptance Criteria", lines);
  }
}
