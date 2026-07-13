package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessV1ContractAlignmentHandoffService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.V1_CONTRACT_ALIGNMENT_HANDOFF;
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json";
  static final String SNAPSHOT_FREEZE_EVIDENCE_PATH =
      "e/188/evidence/java-shard-readiness-v1-contract-alignment-snapshot-freeze-v188.json";
  static final String HISTORICAL_COMPATIBILITY_EVIDENCE_PATH =
      "e/189/evidence/java-shard-readiness-v187-historical-snapshot-compatibility-v189.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessV1ContractAlignmentHandoffResponse handoff() {
    OpsShardReadinessV1ContractAlignmentResponse alignment =
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SourceAlignment();
    boolean registryContainsAlignment =
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190RegistryContainsAlignment();
    boolean olderSnapshotsRemainUnbackfilled =
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190OlderSnapshotsRemainUnbackfilled();
    boolean historicalSnapshotsProtected =
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190HistoricalSnapshotsProtected();

    return new OpsShardReadinessV1ContractAlignmentHandoffResponse(
        "advanced-order-platform",
        "Java v190",
        alignment.contractName(),
        true,
        false,
        false,
        alignment.version(),
        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
        alignment.evidencePath(),
        alignment.receiptId(),
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SnapshotFreezeVersion(),
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SnapshotFreezeEvidencePath(),
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190HistoricalCompatibilityVersion(),
        OpsShardReadinessV1ContractAlignmentHandoffSnapshot
            .v190HistoricalCompatibilityEvidencePath(),
        alignment.minimalFieldsFrozen(),
        historicalSnapshotsProtected,
        registryContainsAlignment,
        olderSnapshotsRemainUnbackfilled,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        handoffArtifacts(),
        verificationChecks(
            alignment,
            registryContainsAlignment,
            olderSnapshotsRemainUnbackfilled,
            historicalSnapshotsProtected),
        "java-shard-readiness-v1-contract-alignment-handoff-receipt-v190",
        EVIDENCE_PATH,
        historicalSnapshotsProtected ? "passed" : "blocked");
  }

  private List<String> handoffArtifacts() {
    return List.of(
        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
        SNAPSHOT_FREEZE_EVIDENCE_PATH,
        HISTORICAL_COMPATIBILITY_EVIDENCE_PATH);
  }

  private List<String> verificationChecks(
      OpsShardReadinessV1ContractAlignmentResponse alignment,
      boolean registryContainsAlignment,
      boolean olderSnapshotsRemainUnbackfilled,
      boolean historicalSnapshotsProtected) {
    return List.of(
        "contract-name:" + alignment.contractName(),
        "alignment-version:" + alignment.version(),
        "minimal-fields-frozen:" + alignment.minimalFieldsFrozen(),
        "registry-contains-alignment:" + registryContainsAlignment,
        "older-snapshots-remain-unbackfilled:" + olderSnapshotsRemainUnbackfilled,
        "historical-snapshots-protected:" + historicalSnapshotsProtected,
        "execution-allowed:false");
  }
}
