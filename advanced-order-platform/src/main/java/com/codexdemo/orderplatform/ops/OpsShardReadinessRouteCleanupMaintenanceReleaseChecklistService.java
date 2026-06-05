package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-release-checklist.v1";

    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;
    private final OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService;
    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService;

    public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService,
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService
    ) {
        this.upkeepCatalogService = upkeepCatalogService;
        this.readinessGateService = readinessGateService;
        this.upkeepCloseoutService = upkeepCloseoutService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse checklist() {
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse catalog =
                upkeepCatalogService.catalog();
        OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse gate =
                readinessGateService.gate();
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse closeout =
                upkeepCloseoutService.closeout();
        List<OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse.ChecklistItem> items = List.of(
                item("catalog-baseline", catalog.endpoint(), "catalog-maintainer",
                        "upkeep-items-" + catalog.itemCount(), catalog.status()),
                item("readiness-gate", gate.endpoint(), "release-reviewer",
                        "accepted-checks-" + gate.acceptedCheckCount(), gate.status()),
                item("closeout", closeout.endpoint(), "operator-handoff-reviewer",
                        "checked-reports-" + closeout.checkedReportCount(), closeout.status()),
                item("source-plan", closeout.endpoint(), "roadmap-reviewer",
                        closeout.sourcePlan(), closeout.status()),
                item("read-only-boundary", gate.endpoint(), "runtime-boundary-reviewer",
                        "executionAllowed-" + gate.executionAllowed(), gate.status())
        );
        int accepted = (int) items.stream().filter(check -> "passed".equals(check.status())).count();
        List<String> checks = List.of(
                "release-checklist-item-count-" + items.size(),
                "catalog-readiness-gate-and-closeout-present",
                "source-plan-remains-node-v549",
                "execution-remains-disabled",
                "release-checklist-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse(
                "advanced-order-platform",
                "Java v512",
                true,
                false,
                ENDPOINT,
                PROFILE,
                items.size(),
                accepted,
                items,
                checks,
                accepted == items.size() ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse.ChecklistItem item(
            String name,
            String sourceEndpoint,
            String owner,
            String evidence,
            String status
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse.ChecklistItem(
                name,
                sourceEndpoint,
                owner,
                evidence,
                status
        );
    }
}
