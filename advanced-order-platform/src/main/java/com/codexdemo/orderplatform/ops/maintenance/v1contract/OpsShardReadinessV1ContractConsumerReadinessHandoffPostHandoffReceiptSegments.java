package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt;
import java.util.List;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments {

  private OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments() {}

  static List<Segment> segments() {
    return List.of(
        new Segment(
            "seed",
            226,
            241,
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffSeedReceipts.receipts()),
        new Segment(
            "growth",
            242,
            259,
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffGrowthReceipts
                .receipts()),
        new Segment(
            "archive",
            260,
            274,
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffArchiveReceipts
                .receipts()),
        new Segment(
            "completion",
            275,
            289,
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCompletionReceipts
                .receipts()));
  }

  record Segment(String name, int firstVersion, int lastVersion, List<Receipt> receipts) {

    Segment {
      receipts = List.copyOf(receipts);
    }
  }
}
