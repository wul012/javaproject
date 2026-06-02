package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog {

    private OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog() {
    }

    static List<Receipt> receipts() {
        return Stream.of(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffSeedReceipts.receipts(),
                        OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffGrowthReceipts.receipts(),
                        OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffArchiveReceipts.receipts(),
                        OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCompletionReceipts.receipts()
                )
                .flatMap(List::stream)
                .toList();
    }

    static List<Integer> versions() {
        return receipts().stream()
                .map(Receipt::version)
                .toList();
    }

    static List<String> evidencePaths() {
        return receipts().stream()
                .map(Receipt::evidencePath)
                .toList();
    }

    static Receipt receipt(int version, String scope, String evidencePath) {
        return new Receipt(version, scope, evidencePath);
    }

    record Receipt(int version, String scope, String evidencePath) {
    }
}
