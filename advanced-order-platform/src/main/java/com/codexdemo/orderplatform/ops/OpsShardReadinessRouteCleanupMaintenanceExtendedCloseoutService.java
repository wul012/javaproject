package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_EXTENDED_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-extended-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse closeout() {
        return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
                "Java v557",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "first-batch-complete",
                                "release-reviewer",
                                "contract, handoff, field map, read window, and runtime boundary are paired",
                                OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "assurance-batch-complete",
                                "release-reviewer",
                                "consumer, archive, CI, inventory, and operator routes are ready for closeout",
                                OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "contract-freeze-held",
                                "contract-maintainer",
                                "read-only integration and shard readiness contracts stayed frozen",
                                OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "ci-budget-held",
                                "ci-reviewer",
                                "focused checks precede the final full Maven gate",
                                OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "operator-signoff-held",
                                "operator-reviewer",
                                "signoff stays evidence-only and does not approve execution",
                                OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService.ENDPOINT
                        )
                ),
                List.of(
                        "extended-closeout-versions-v534-v558",
                        "extended-closeout-services-and-routes-paired",
                        "extended-closeout-remains-read-only"
                )
        );
    }
}
