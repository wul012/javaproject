package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService;
import com.codexdemo.orderplatform.ops.maintenance.credentialresolver.OpsShardReadinessCredentialResolverRoutePaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessCredentialResolverRoutePaths.BASE_PATH)
public class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveController {

  private final OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService
      service;

  public OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveController(
      OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCredentialResolverRoutePaths
          .CREDENTIAL_RESOLVER_DISABLED_FAKE_HARNESS_EVIDENCE_ARCHIVE)
  public OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse archive() {
    return service.archive();
  }
}
