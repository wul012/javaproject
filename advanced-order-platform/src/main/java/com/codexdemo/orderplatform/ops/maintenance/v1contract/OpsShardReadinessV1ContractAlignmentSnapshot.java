package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.OpsShardReadinessResponse;
import java.util.List;

final class OpsShardReadinessV1ContractAlignmentSnapshot {

  private OpsShardReadinessV1ContractAlignmentSnapshot() {}

  static String v187ContractName() {
    return "shard-readiness.v1";
  }

  static OpsShardReadinessResponse v187SourceReadiness() {
    return new OpsShardReadinessResponse(
        "advanced-order-platform",
        "Java v153",
        true,
        false,
        false,
        0,
        0,
        "fixture",
        "e/153/evidence/java-shard-readiness-v153.json",
        "passed");
  }

  static String v187SourceEndpoint() {
    return "/api/v1/ops/shard-readiness";
  }

  static String v187SourceFixtureEndpoint() {
    return "/contracts/java-shard-readiness-v153.fixture.json";
  }

  static List<String> v187MinimalFields() {
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
}
