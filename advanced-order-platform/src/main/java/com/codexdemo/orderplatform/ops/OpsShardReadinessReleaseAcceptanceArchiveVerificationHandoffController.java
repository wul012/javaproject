package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffController {

    private final OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service;

    public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffController(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY)
    public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
        return service.registry();
    }
}
