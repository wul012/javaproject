package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController {

  private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
      service;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessReleaseAcceptanceRoutePaths
          .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
      registry() {
    return service.registry();
  }
}
