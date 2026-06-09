package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController {

    private final OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service;

    public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController(
            OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY)
    public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
        return service.registry();
    }
}
