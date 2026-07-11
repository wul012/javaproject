package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService {

  static final String RESPONSE_VERSION = "Java v1402";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-minimal-read-only-gate-operator-ci-handoff-archive-digest-registry.v1";

  private final
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
      sourceArchiveService;

  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService(
      OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService
          sourceArchiveService) {
    this.sourceArchiveService = sourceArchiveService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
      registry() {
    var sourceArchive = sourceArchiveService.registry();
    var sourceArchives =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceArchiveCatalog
            .snapshots(sourceArchive);
    var digestSections =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionCatalog
            .digestSections(sourceArchive);
    var consumerPackets =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketCatalog
            .packets(sourceArchive);
    var replayInstructions =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionCatalog
            .replayInstructions(sourceArchive);
    var boundaryLocks =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog.locks(
            sourceArchive);
    var scorecard =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardCatalog
            .scorecard(
                sourceArchive, digestSections, consumerPackets, replayInstructions, boundaryLocks);
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport
        .response(
            RESPONSE_VERSION,
            ENDPOINT,
            PROFILE,
            sourceArchive,
            sourceArchives,
            digestSections,
            consumerPackets,
            replayInstructions,
            boundaryLocks,
            scorecard,
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryRenderer
                .render(
                    sourceArchives,
                    digestSections,
                    consumerPackets,
                    replayInstructions,
                    boundaryLocks,
                    scorecard));
  }
}
