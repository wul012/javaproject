package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

final class OpsShardReadinessV1ContractAlignmentHandoffSnapshot {

  private OpsShardReadinessV1ContractAlignmentHandoffSnapshot() {}

  static OpsShardReadinessV1ContractAlignmentResponse v190SourceAlignment() {
    return new OpsShardReadinessV1ContractAlignmentResponse(
        "advanced-order-platform",
        "Java v187",
        "shard-readiness.v1",
        true,
        false,
        false,
        "Java v153",
        "/api/v1/ops/shard-readiness",
        "/contracts/java-shard-readiness-v153.fixture.json",
        "e/153/evidence/java-shard-readiness-v153.json",
        v190MinimalFields(),
        true,
        true,
        true,
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "java-shard-readiness-v1-contract-alignment-receipt-v187",
        v190SourceVerificationChecks(),
        v190BlockedOperations(),
        OpsShardReadinessV1ContractAlignmentService.EVIDENCE_PATH,
        "passed");
  }

  static String v190SnapshotFreezeVersion() {
    return "Java v188";
  }

  static String v190SnapshotFreezeEvidencePath() {
    return OpsShardReadinessV1ContractAlignmentHandoffService.SNAPSHOT_FREEZE_EVIDENCE_PATH;
  }

  static String v190HistoricalCompatibilityVersion() {
    return "Java v189";
  }

  static String v190HistoricalCompatibilityEvidencePath() {
    return OpsShardReadinessV1ContractAlignmentHandoffService
        .HISTORICAL_COMPATIBILITY_EVIDENCE_PATH;
  }

  static boolean v190RegistryContainsAlignment() {
    return true;
  }

  static boolean v190OlderSnapshotsRemainUnbackfilled() {
    return true;
  }

  static boolean v190HistoricalSnapshotsProtected() {
    return true;
  }

  static List<String> v190MinimalFields() {
    return List.of(
        "project",
        "version",
        "readOnly",
        "executionAllowed",
        "shardEnabled",
        "shardCount",
        "slotCount",
        "routingMode",
        "evidencePath",
        "status");
  }

  private static List<String> v190SourceVerificationChecks() {
    return List.of(
        "contract-name:shard-readiness.v1",
        "source-readiness-version:Java v153",
        "minimal-field-count:10",
        "read-only-matches:true",
        "execution-blocked:true",
        "shard-routing-disabled:true",
        "shard-counts-closed:true",
        "routing-mode-fixture-backed:true");
  }

  private static List<String> v190BlockedOperations() {
    return List.of(
        "write-routing",
        "active-shard-router",
        "credential-value-read",
        "raw-endpoint-parse",
        "managed-audit-connection",
        "deployment-or-rollback",
        "node-start-or-stop-java-or-mini-kv");
  }
}
