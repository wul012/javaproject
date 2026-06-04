package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupArchiveHandoffReceiptServiceTests {

    @Test
    void buildsArchiveHandoffReceiptFromArchivePlanAndSignoff() {
        OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse receipt =
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.archiveHandoffReceiptService().receipt();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(399);
        assertThat(receipt.project()).isEqualTo("advanced-order-platform");
        assertThat(receipt.version()).isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(receipt.readOnly()).isTrue();
        assertThat(receipt.executionAllowed()).isFalse();
        assertThat(receipt.archiveHandoffReceiptEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-handoff-receipt");
        assertThat(receipt.archiveHandoffReceiptProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-archive-handoff-receipt.v1");
        assertThat(receipt.finalArchivePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-final-archive-plan");
        assertThat(receipt.consumerSignoffPacketEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-consumer-signoff-packet");
        assertThat(receipt.receiptItemCount()).isEqualTo(5);
        assertThat(receipt.receiptItems())
                .extracting(OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse.ReceiptItem::name)
                .containsExactly(
                        "archive-plan",
                        "consumer-signoff",
                        "post-push-closeout",
                        "cleanup-gate",
                        "node-workspace"
                );
        assertThat(receipt.receiptId()).startsWith("archive-handoff-v");
        assertThat(receipt.status()).isEqualTo("passed");
    }
}
