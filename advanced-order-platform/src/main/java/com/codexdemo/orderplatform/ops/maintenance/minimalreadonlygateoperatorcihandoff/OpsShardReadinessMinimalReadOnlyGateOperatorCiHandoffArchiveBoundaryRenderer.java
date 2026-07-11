package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .BoundaryVerification>
              boundaries) {
    List<String> lines = new ArrayList<>();
    lines.add("boundary-verification-count=" + boundaries.size());
    boundaries.forEach(
        boundary ->
            lines.add(
                boundary.code()
                    + " | locked="
                    + boundary.locked()
                    + " | archived="
                    + boundary.archived()
                    + " | status="
                    + boundary.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
        "Boundary Verifications", lines);
  }
}
