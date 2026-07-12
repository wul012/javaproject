package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String archiveHandoffReceiptEndpoint,
    String archiveHandoffReceiptProfile,
    String finalArchivePlanEndpoint,
    String consumerSignoffPacketEndpoint,
    int receiptItemCount,
    List<ReceiptItem> receiptItems,
    String receiptId,
    String status) {

  public record ReceiptItem(String name, String evidence, String status) {}
}
