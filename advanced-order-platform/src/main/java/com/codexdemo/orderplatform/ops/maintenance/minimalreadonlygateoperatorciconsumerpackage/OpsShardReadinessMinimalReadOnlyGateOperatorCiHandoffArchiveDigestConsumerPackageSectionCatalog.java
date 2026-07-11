package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              .PackageSection>
      sections(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              source) {
    boolean sourcePassed = "passed".equals(source.status());
    return List.of(
        section("source-digest-summary", "release-review", source.version(), sourcePassed),
        section("manifest", "operator-ci", source.profile(), sourcePassed),
        section(
            "consumer-packets",
            "operator-ci",
            "packets=" + source.consumerPacketCount(),
            sourcePassed),
        section(
            "ci-matrix",
            "ci",
            "replay-instructions=" + source.replayInstructionCount(),
            sourcePassed),
        section(
            "boundary-locks",
            "operator",
            "locked-boundaries=" + source.lockedBoundaryCount(),
            sourcePassed));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .PackageSection
      section(String section, String owner, String sourceEvidence, boolean ready) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
        .PackageSection(section, owner, sourceEvidence, ready, ready ? "passed" : "blocked");
  }
}
