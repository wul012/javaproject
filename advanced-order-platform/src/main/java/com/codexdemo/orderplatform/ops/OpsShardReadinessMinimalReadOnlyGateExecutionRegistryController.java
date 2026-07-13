package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController {

  private final OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service;

  public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController(
      OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessReleaseAcceptanceRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY)
  public OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse registry() {
    return service.registry();
  }
}
