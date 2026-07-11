package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteRenderer {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteRenderer() {}

  static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
      render(
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff>
              routes) {
    List<String> lines = new ArrayList<>();
    lines.add("route-handoff-count=" + routes.size());
    routes.forEach(
        route ->
            lines.add(
                String.join(
                    " | ",
                    route.receiver(),
                    route.owner(),
                    route.packet(),
                    OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("ready", route.ready()),
                    OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(route.status()))));
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport.section(
        "Route Handoffs", lines);
  }
}
