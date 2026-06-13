package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance.OpsShardReadinessCodeWalkthroughComplianceRegistryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCodeWalkthroughComplianceRegistryController {

  private final OpsShardReadinessCodeWalkthroughComplianceRegistryService service;

  public OpsShardReadinessCodeWalkthroughComplianceRegistryController(
      OpsShardReadinessCodeWalkthroughComplianceRegistryService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessRoutePaths.CODE_WALKTHROUGH_COMPLIANCE_REGISTRY)
  public OpsShardReadinessCodeWalkthroughComplianceRegistryResponse registry() {
    return service.registry();
  }
}
