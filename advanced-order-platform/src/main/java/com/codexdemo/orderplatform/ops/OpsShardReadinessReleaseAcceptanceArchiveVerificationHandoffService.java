package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService {

  static final String RESPONSE_VERSION = "Java v1547";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-archive-verification-handoff-registry.v1";

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService
      sourceArchiveRegistryService;

  public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryService
          sourceArchiveRegistryService) {
    this.sourceArchiveRegistryService = sourceArchiveRegistryService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse registry() {
    var source = sourceArchiveRegistryService.registry();
    var sourceArchiveSnapshots =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSourceCatalog.snapshots(source);
    var verificationRequirements =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementCatalog.requirements(
            source);
    var artifactCrossChecks =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactCatalog.crossChecks(
            source);
    var routeHandoffs =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteCatalog.handoffs(source);
    var operatorInstructions =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorCatalog.instructions(
            source);
    var ciProofs =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiCatalog.proofs(source);
    var boundaryGuards =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffBoundaryCatalog.guards(source);
    var retentionGuards =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionCatalog.guards(source);
    var closeoutHandoffs =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutCatalog.handoffs(
            source);
    var scorecard =
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardCatalog.scorecard(
            sourceArchiveSnapshots,
            verificationRequirements,
            artifactCrossChecks,
            routeHandoffs,
            operatorInstructions,
            ciProofs,
            boundaryGuards,
            retentionGuards,
            closeoutHandoffs);
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        source,
        sourceArchiveSnapshots,
        verificationRequirements,
        artifactCrossChecks,
        routeHandoffs,
        operatorInstructions,
        ciProofs,
        boundaryGuards,
        retentionGuards,
        closeoutHandoffs,
        scorecard,
        OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRenderer.render(
            sourceArchiveSnapshots,
            verificationRequirements,
            artifactCrossChecks,
            routeHandoffs,
            operatorInstructions,
            ciProofs,
            boundaryGuards,
            retentionGuards,
            closeoutHandoffs,
            scorecard));
  }
}
