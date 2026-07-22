package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService {

  static final String RESPONSE_VERSION = "Java v1337";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-execution-archive-verification-registry.v1";

  private final OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService sourceRegistryService;

  public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService sourceRegistryService) {
    this.sourceRegistryService = sourceRegistryService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
      registry() {
    var sourceRegistry = sourceRegistryService.registry();
    var evidence = ArchiveCatalog.evidence(sourceRegistry);
    return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourceRegistry,
        evidence,
        ArchiveRenderer.render(evidence));
  }
}
