package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt;
import com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments.Segment;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffSegmentBoundaryTests {

    @Test
    void keepsSegmentRegistryOrderedByMaintenanceWindow() {
        List<Segment> segments =
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffReceiptSegments.segments();

        assertThat(segments)
                .extracting(Segment::name, Segment::firstVersion, Segment::lastVersion)
                .containsExactly(
                        tuple("seed", 226, 241),
                        tuple("growth", 242, 259),
                        tuple("archive", 260, 274),
                        tuple("completion", 275, 289)
                );

        assertSegment(segments.get(0).receipts(), 226, 241, 16);
        assertSegment(segments.get(1).receipts(), 242, 259, 18);
        assertSegment(segments.get(2).receipts(), 260, 274, 15);
        assertSegment(segments.get(3).receipts(), 275, 289, 15);
    }

    private static void assertSegment(List<Receipt> receipts, int firstVersion, int lastVersion, int count) {
        assertThat(receipts).hasSize(count);
        assertThat(receipts)
                .extracting(Receipt::version)
                .containsExactlyElementsOf(IntStream.rangeClosed(firstVersion, lastVersion).boxed().toList());
    }
}
