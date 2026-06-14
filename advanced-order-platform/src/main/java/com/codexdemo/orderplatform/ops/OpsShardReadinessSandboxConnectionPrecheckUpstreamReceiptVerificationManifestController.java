package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService;
import com.codexdemo.orderplatform.ops.maintenance.sandboxconnection.OpsShardReadinessSandboxConnectionRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessSandboxConnectionRoutePaths.BASE_PATH)
public
class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController {

  private final OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService
      service;

  public OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController(
      OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService
          service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessSandboxConnectionRoutePaths
          .SANDBOX_CONNECTION_PRECHECK_UPSTREAM_RECEIPT_VERIFICATION_MANIFEST)
  public OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
      manifest() {
    return service.manifest();
  }
}
