package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessService;
import java.util.List;

final class OpsShardReadinessV1ContractEvidencePacketSnapshot {

  private OpsShardReadinessV1ContractEvidencePacketSnapshot() {}

  static OpsShardReadinessV1ContractEvidencePacketResponse v193Packet() {
    return new OpsShardReadinessV1ContractEvidencePacketResponse(
        "advanced-order-platform",
        "Java v193",
        "shard-readiness.v1",
        true,
        false,
        false,
        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
        OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint(),
        OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceFixtureEndpoint(),
        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentHandoffService.SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractAlignmentHandoffService.HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        v193EvidenceChain(),
        v193NodeConsumableEndpoints(),
        v193NodeConsumableFixtureEndpoints(),
        v193BlockedOperations(),
        v193VerificationChecks(),
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "java-shard-readiness-v1-contract-evidence-packet-receipt-v193",
        OpsShardReadinessV1ContractEvidencePacketService.EVIDENCE_PATH,
        "passed");
  }

  static List<String> v193EvidenceChain() {
    return List.of(
        OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
        OpsShardReadinessV1ContractAlignmentHandoffService.SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractAlignmentHandoffService.HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractAlignmentHandoffService.EVIDENCE_PATH,
        "e/191/evidence/java-shard-readiness-v190-handoff-snapshot-freeze-v191.json",
        "e/192/evidence/java-shard-readiness-v190-handoff-historical-snapshot-compatibility-v192.json");
  }

  static List<String> v193NodeConsumableEndpoints() {
    return List.of(
        OpsShardReadinessService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
        OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
        OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT);
  }

  static List<String> v193NodeConsumableFixtureEndpoints() {
    return List.of(
        OpsShardReadinessService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT,
        OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT);
  }

  private static List<String> v193BlockedOperations() {
    return List.of(
        "write-routing",
        "active-shard-router",
        "credential-value-read",
        "raw-endpoint-parse",
        "managed-audit-connection",
        "deployment-or-rollback",
        "node-start-or-stop-java-or-mini-kv");
  }

  private static List<String> v193VerificationChecks() {
    return List.of(
        "contract-name:shard-readiness.v1",
        "source-readiness-endpoint:"
            + OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint(),
        "alignment-receipt:java-shard-readiness-v1-contract-alignment-receipt-v187",
        "handoff-receipt:java-shard-readiness-v1-contract-alignment-handoff-receipt-v190",
        "evidence-chain-count:6",
        "node-consumable-endpoint-count:4",
        "minimal-fields-frozen:true",
        "historical-snapshots-protected:true",
        "execution-allowed:false");
  }
}
