package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService {

  static final String RESPONSE_VERSION = "Java v1377";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-verification-registry.v1";

  private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService
      sourceHandoffService;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService sourceHandoffService) {
    this.sourceHandoffService = sourceHandoffService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
      registry() {
    var sourceHandoff = sourceHandoffService.registry();
    var evidence = ArchiveCatalog.evidence(sourceHandoff);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            sourceHandoff,
            evidence,
            ArchiveRenderer.render(evidence));
  }
}
