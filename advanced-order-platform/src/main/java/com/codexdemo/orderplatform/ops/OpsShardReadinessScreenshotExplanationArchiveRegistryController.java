package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessScreenshotExplanationArchiveRegistryController {

    private final OpsShardReadinessScreenshotExplanationArchiveRegistryService service;

    public OpsShardReadinessScreenshotExplanationArchiveRegistryController(
            OpsShardReadinessScreenshotExplanationArchiveRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SCREENSHOT_EXPLANATION_ARCHIVE_REGISTRY)
    public OpsShardReadinessScreenshotExplanationArchiveRegistryResponse registry() {
        return service.registry();
    }
}
