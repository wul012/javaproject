package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService {

    static final String RESPONSE_VERSION = "Java v1312";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY;
    static final String PROFILE =
            "java-shard-readiness-minimal-read-only-gate-execution-registry.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
        var sourcePlans = OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalog.sourcePlans();
        var readTargets = OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalog.readTargets();
        var gateChecks = OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckCatalog.gateChecks();
        var boundaryRules = OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryPolicyCatalog.boundaryRules();
        var ciBatches = OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchCatalog.ciBatches();
        var archiveRequirements =
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationCatalog.archiveRequirements();
        var operatorHandoffs =
                OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffCatalog.operatorHandoffs();
        return OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourcePlans,
                readTargets,
                gateChecks,
                boundaryRules,
                ciBatches,
                archiveRequirements,
                operatorHandoffs,
                OpsShardReadinessMinimalReadOnlyGateExecutionRegistryRenderer.render(
                        readTargets,
                        gateChecks,
                        boundaryRules,
                        ciBatches,
                        archiveRequirements,
                        operatorHandoffs
                )
        );
    }
}
