package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService {

  static final String RESPONSE_VERSION = "Java v1312";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY;
  static final String PROFILE = "java-shard-readiness-minimal-read-only-gate-execution-registry.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
    var evidence = RegistryCatalog.evidence();
    return OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        evidence.sourcePlans(),
        evidence.readTargets(),
        evidence.gateChecks(),
        evidence.boundaryRules(),
        evidence.ciBatches(),
        evidence.archiveRequirements(),
        evidence.operatorHandoffs(),
        ExecutionRenderer.render(
            evidence.readTargets(),
            evidence.gateChecks(),
            evidence.boundaryRules(),
            evidence.ciBatches(),
            evidence.archiveRequirements(),
            evidence.operatorHandoffs()));
  }
}
