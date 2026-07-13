package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController {

  private final OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService
      service;

  public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController(
      OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessReleaseAcceptanceRoutePaths
          .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY)
  public OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
      registry() {
    return service.registry();
  }
}
