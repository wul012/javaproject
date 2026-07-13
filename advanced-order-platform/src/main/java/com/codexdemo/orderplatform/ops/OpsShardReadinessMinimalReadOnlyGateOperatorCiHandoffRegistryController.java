package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController {

  private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessReleaseAcceptanceRoutePaths
          .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse registry() {
    return service.registry();
  }
}
