package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryController {

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
      service;

  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryController(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
          service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY)
  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
      registry() {
    return service.registry();
  }
}
