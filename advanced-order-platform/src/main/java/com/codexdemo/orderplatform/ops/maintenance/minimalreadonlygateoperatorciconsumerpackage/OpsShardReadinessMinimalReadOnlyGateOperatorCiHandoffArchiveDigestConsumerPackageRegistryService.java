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
    var sourceDigests =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceDigestCatalog
            .snapshots(sourceDigest);
    var manifest =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestCatalog
            .manifest(sourceDigest);
    var audiences =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceCatalog
            .audiences(sourceDigest);
    var sections =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionCatalog
            .sections(sourceDigest);
    var criteria =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceCatalog
            .criteria(sourceDigest);
    var ciMatrix =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog
            .matrix(sourceDigest);
    var locks =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryLockCatalog
            .locks(sourceDigest);
    var checklist =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistCatalog
            .checklist(sourceDigest);
    var scorecard =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardCatalog
            .scorecard(
                sourceDigest, manifest, audiences, sections, criteria, ciMatrix, locks, checklist);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            sourceDigest,
            sourceDigests,
            manifest,
            audiences,
            sections,
            criteria,
            ciMatrix,
            locks,
            checklist,
            scorecard,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryRenderer
                .render(
                    sourceDigests,
                    manifest,
                    audiences,
                    sections,
                    criteria,
                    ciMatrix,
                    locks,
                    checklist,
                    scorecard));
  }
}
