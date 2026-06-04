package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupArchiveHandoffReceiptService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_HANDOFF_RECEIPT;

    static final String PROFILE = "java-shard-readiness-route-cleanup-archive-handoff-receipt.v1";

    private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

    private final OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService;

    private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

    public OpsShardReadinessRouteCleanupArchiveHandoffReceiptService(
            OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService,
            OpsShardReadinessRouteCleanupConsumerSignoffPacketService consumerSignoffPacketService,
            OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService
    ) {
        this.finalArchivePlanService = finalArchivePlanService;
        this.consumerSignoffPacketService = consumerSignoffPacketService;
        this.postPushCloseoutService = postPushCloseoutService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse receipt() {
        OpsShardReadinessRouteCleanupFinalArchivePlanResponse archivePlan = finalArchivePlanService.plan();
        OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse signoff = consumerSignoffPacketService.packet();
        OpsShardReadinessRouteCleanupPostPushCloseoutResponse closeout = postPushCloseoutService.closeout();
        List<OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse.ReceiptItem> items = List.of(
                item("archive-plan", archivePlan.decision()),
                item("consumer-signoff", signoff.status()),
                item("post-push-closeout", closeout.decision()),
                item("cleanup-gate", "target directory is removed during final handoff"),
                item("node-workspace", "not touched by Java post-completion handoff")
        );
        boolean passed = archivePlan.status().equals("passed")
                && signoff.status().equals("passed")
                && closeout.status().equals("passed");
        return new OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT,
                OpsShardReadinessRouteCleanupConsumerSignoffPacketService.ENDPOINT,
                items.size(),
                items,
                "archive-handoff-v" + OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion(),
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse.ReceiptItem item(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse.ReceiptItem(
                name,
                evidence,
                "passed"
        );
    }
}
