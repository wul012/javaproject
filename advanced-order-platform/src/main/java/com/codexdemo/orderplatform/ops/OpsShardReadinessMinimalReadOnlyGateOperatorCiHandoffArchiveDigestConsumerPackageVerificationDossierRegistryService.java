package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService {

  static final String RESPONSE_VERSION = "Java v1467";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-registry.v1";

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
      sourceConsumerPackageService;

  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService
          sourceConsumerPackageService) {
    this.sourceConsumerPackageService = sourceConsumerPackageService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
      registry() {
    var source = sourceConsumerPackageService.registry();
    var sourcePackages =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourcePackageCatalog
            .snapshots(source);
    var provenance =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierProvenanceCatalog
            .provenance(source);
    var sectionDigests =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSectionDigestCatalog
            .digests(source);
    var audienceRoutes =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog
            .routes(source);
    var ciLanes =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog
            .lanes(source);
    var acceptanceGates =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog
            .gates(source);
    var boundaryAudits =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog
            .audits(source);
    var releaseChecklist =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseChecklistCatalog
            .checklist(source);
    var handoffReceipts =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptCatalog
            .receipts(source);
    var scorecard =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierScorecardCatalog
            .scorecard(
                source,
                sourcePackages,
                provenance,
                sectionDigests,
                audienceRoutes,
                ciLanes,
                acceptanceGates,
                boundaryAudits,
                releaseChecklist,
                handoffReceipts);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            source,
            sourcePackages,
            provenance,
            sectionDigests,
            audienceRoutes,
            ciLanes,
            acceptanceGates,
            boundaryAudits,
            releaseChecklist,
            handoffReceipts,
            scorecard,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryRenderer
                .render(
                    sourcePackages,
                    provenance,
                    sectionDigests,
                    audienceRoutes,
                    ciLanes,
                    acceptanceGates,
                    boundaryAudits,
                    releaseChecklist,
                    handoffReceipts,
                    scorecard));
  }
}
