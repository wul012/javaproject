package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessSandboxConnectionRoutePaths.BASE_PATH)
public class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController {

    private final OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService service;

    public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessSandboxConnectionRoutePaths
            .SANDBOX_CONNECTION_BLOCKED_EXECUTION_CONTEXT_NORMALIZATION_DOSSIER)
    public OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse dossier() {
        return service.dossier();
    }
}
