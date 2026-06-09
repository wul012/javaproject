package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController {

    private final OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService service;

    public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY)
    public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse registry() {
        return service.registry();
    }
}
