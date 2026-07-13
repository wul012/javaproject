package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth.OpsShardReadinessCodeWalkthroughDepthRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughDepthRegistryController {

  private final OpsShardReadinessCodeWalkthroughDepthRegistryService service;

  public OpsShardReadinessCodeWalkthroughDepthRegistryController(
      OpsShardReadinessCodeWalkthroughDepthRegistryService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessCodeWalkthroughDepthRoutePaths.CODE_WALKTHROUGH_DEPTH_REGISTRY)
  public OpsShardReadinessCodeWalkthroughDepthRegistryResponse registry() {
    return service.registry();
  }
}
