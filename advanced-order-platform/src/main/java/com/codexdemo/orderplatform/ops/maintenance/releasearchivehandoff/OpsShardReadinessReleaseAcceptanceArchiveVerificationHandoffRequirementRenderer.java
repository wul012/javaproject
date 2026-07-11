package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementRenderer {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementRenderer() {}

  static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .VerificationRequirement>
              requirements) {
    List<String> lines = new ArrayList<>();
    lines.add("verification-requirement-count=" + requirements.size());
    requirements.forEach(
        requirement ->
            lines.add(
                requirement.code()
                    + "="
                    + requirement.actual()
                    + "/"
                    + requirement.expected()
                    + " | "
                    + requirement.evidence()
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("passed", requirement.passed())
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(requirement.status())));
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport.section(
        "Verification Requirements", lines);
  }
}
