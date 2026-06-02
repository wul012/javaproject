package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffSegmentBoundaryTests {

    @Test
    void keepsSeedReceiptsBoundedToV226V241() {
        assertSegment(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffSeedReceipts.receipts(),
                226,
                241,
                16
        );
    }

    @Test
    void keepsGrowthReceiptsBoundedToV242V259() {
        assertSegment(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffGrowthReceipts.receipts(),
                242,
                259,
                18
        );
    }

    @Test
    void keepsArchiveReceiptsBoundedToV260V274() {
        assertSegment(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffArchiveReceipts.receipts(),
                260,
                274,
                15
        );
    }

    @Test
    void keepsCompletionReceiptsBoundedToV275V289() {
        assertSegment(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCompletionReceipts.receipts(),
                275,
                289,
                15
        );
    }

    private static void assertSegment(List<Receipt> receipts, int firstVersion, int lastVersion, int count) {
        assertThat(receipts).hasSize(count);
        assertThat(receipts)
                .extracting(Receipt::version)
                .containsExactlyElementsOf(IntStream.rangeClosed(firstVersion, lastVersion).boxed().toList());
    }
}
