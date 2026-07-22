package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService {

  static final String RESPONSE_VERSION = "Java v1352";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-registry.v1";

  private final OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
      sourceArchiveService;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
          sourceArchiveService) {
    this.sourceArchiveService = sourceArchiveService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
    var sourceArchive = sourceArchiveService.registry();
    var evidence = HandoffCatalog.evidence(sourceArchive);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourceArchive,
        evidence,
        HandoffRenderer.render(evidence));
  }
}
