package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.MarkdownSection
      render(
          List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane>
              lanes) {
    List<String> lines = new ArrayList<>();
    lines.add("operator-lane-count=" + lanes.size());
    lanes.forEach(
        lane ->
            lines.add(
                lane.order()
                    + ". "
                    + lane.lane()
                    + " | "
                    + lane.owner()
                    + " | ready="
                    + lane.ready()
                    + " | "
                    + lane.instruction()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.section(
        "Operator Lanes", lines);
  }
}
