package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController {

    private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
            service;

    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(
            OpsShardReadinessRoutePaths
                    .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY
    )
    public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
            registry() {
        return service.registry();
    }
}
