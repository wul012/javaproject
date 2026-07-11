package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffController {

  private final OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service;

  public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffController(
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessReleaseAcceptanceRoutePaths
          .RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY)
  public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
    return service.registry();
  }
}
