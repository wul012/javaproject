package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService {

  static final String RESPONSE_VERSION = "Java v1502";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry.v1";

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService
      sourceDossierService;

  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService
          sourceDossierService) {
    this.sourceDossierService = sourceDossierService;
  }

  @Transactional(readOnly = true)
  public
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
      registry() {
    var source = sourceDossierService.registry();
    var sourceDossiers =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceDossierCatalog
            .snapshots(source);
    var readinessGates =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessCatalog
            .gates(source);
    var evidenceChain =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceEvidenceChainCatalog
            .chain(source);
    var signoffLanes =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSignoffLaneCatalog
            .lanes(source);
    var ciReplayLanes =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCiReplayCatalog
            .lanes(source);
    var boundaryControls =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceBoundaryControlCatalog
            .controls(source);
    var retentionPolicies =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRetentionPolicyCatalog
            .policies(source);
    var replayDecisions =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReplayDecisionCatalog
            .decisions(source);
    var closeoutCheckpoints =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutCatalog
            .checkpoints(source);
    var scorecard =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceScorecardCatalog
            .scorecard(
                source,
                readinessGates,
                evidenceChain,
                signoffLanes,
                ciReplayLanes,
                boundaryControls,
                retentionPolicies,
                replayDecisions,
                closeoutCheckpoints);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            source,
            sourceDossiers,
            readinessGates,
            evidenceChain,
            signoffLanes,
            ciReplayLanes,
            boundaryControls,
            retentionPolicies,
            replayDecisions,
            closeoutCheckpoints,
            scorecard,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryRenderer
                .render(
                    sourceDossiers,
                    readinessGates,
                    evidenceChain,
                    signoffLanes,
                    ciReplayLanes,
                    boundaryControls,
                    retentionPolicies,
                    replayDecisions,
                    closeoutCheckpoints,
                    scorecard));
  }
}
