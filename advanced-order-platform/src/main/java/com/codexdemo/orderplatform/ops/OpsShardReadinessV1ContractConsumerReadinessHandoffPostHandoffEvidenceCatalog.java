package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog {

    private OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog() {
    }

    static List<Receipt> receipts() {
        return OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments.segments().stream()
                .map(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments.Segment::receipts)
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
