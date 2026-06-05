package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-archive-verifier-summary.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse summary() {
        return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
                "Java v549",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "json-output",
                                "archive-reviewer",
                                "route JSON output must be captured by operator",
                                OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "markdown-output",
                                "archive-reviewer",
                                "route Markdown output remains external archive concern",
                                OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "digest-summary",
                                "ci-reviewer",
                                "SHA-256 summary belongs to archive verifier",
                                OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "route-boundary",
                                "runtime-boundary-reviewer",
                                "service does not read or write archive files",
                                OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.ENDPOINT
                        )
                ),
                List.of(
                        "archive-verifier-summary-items-4",
                        "archive-verifier-summary-does-not-touch-files",
                        "archive-verifier-summary-ready-for-route"
                )
        );
    }
}
