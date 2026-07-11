package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController {

  private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
    return service.registry();
  }
}
