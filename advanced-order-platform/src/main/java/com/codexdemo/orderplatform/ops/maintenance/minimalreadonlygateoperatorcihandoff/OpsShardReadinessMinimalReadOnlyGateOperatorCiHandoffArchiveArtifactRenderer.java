package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .ArtifactVerification>
              artifacts) {
    List<String> lines = new ArrayList<>();
    lines.add("artifact-verification-count=" + artifacts.size());
    artifacts.forEach(
        artifact ->
            lines.add(
                artifact.artifact()
                    + " | "
                    + artifact.producer()
                    + " | archived="
                    + artifact.archived()
                    + " | status="
                    + artifact.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
        "Artifact Verifications", lines);
  }
}
