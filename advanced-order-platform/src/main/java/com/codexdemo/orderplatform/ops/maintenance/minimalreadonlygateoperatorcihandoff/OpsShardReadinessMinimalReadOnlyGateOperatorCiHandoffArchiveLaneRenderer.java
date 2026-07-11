package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .OperatorLaneVerification>
              lanes) {
    List<String> lines = new ArrayList<>();
    lines.add("operator-lane-verification-count=" + lanes.size());
    lanes.forEach(
        lane ->
            lines.add(
                lane.order()
                    + ". "
                    + lane.lane()
                    + " | "
                    + lane.owner()
                    + " | archived="
                    + lane.archived()
                    + " | status="
                    + lane.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
        "Operator Lane Verifications", lines);
  }
}
