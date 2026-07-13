package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate.OpsShardReadinessCodeWalkthroughQualityGateRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughQualityGateRegistryController {

  private final OpsShardReadinessCodeWalkthroughQualityGateRegistryService service;

  public OpsShardReadinessCodeWalkthroughQualityGateRegistryController(
      OpsShardReadinessCodeWalkthroughQualityGateRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCodeWalkthroughQualityGateRoutePaths.CODE_WALKTHROUGH_QUALITY_GATE_REGISTRY)
  public OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse registry() {
    return service.registry();
  }
}
