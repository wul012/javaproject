package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughDepthRegistryController {

    private final OpsShardReadinessCodeWalkthroughDepthRegistryService service;

    public OpsShardReadinessCodeWalkthroughDepthRegistryController(
            OpsShardReadinessCodeWalkthroughDepthRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY)
    public OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
        return service.registry();
    }
}
