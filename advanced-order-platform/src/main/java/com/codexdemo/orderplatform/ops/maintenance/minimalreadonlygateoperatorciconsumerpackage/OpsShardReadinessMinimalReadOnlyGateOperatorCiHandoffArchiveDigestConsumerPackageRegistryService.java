package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService {

  static final String RESPONSE_VERSION = "Java v1432";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-registry.v1";

  private final OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
      sourceDigestService;

  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService
          sourceDigestService) {
    this.sourceDigestService = sourceDigestService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
      registry() {
    var sourceDigest = sourceDigestService.registry();
    var evidence = PackageCatalog.evidence(sourceDigest);
    return PackageSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourceDigest,
        evidence,
        ReportRenderer.render(evidence));
  }
}
