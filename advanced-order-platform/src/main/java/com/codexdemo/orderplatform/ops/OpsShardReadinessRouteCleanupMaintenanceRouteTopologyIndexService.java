package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ROUTE_TOPOLOGY_INDEX;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-route-topology-index.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse index() {
        List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item> items =
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items();
        List<OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse.RouteNode> routes =
                IntStream.range(0, items.size())
                        .mapToObj(index -> node(items, index))
                        .toList();
        List<String> checks = List.of(
                "route-count-" + routes.size(),
                "route-versions-are-ascending",
                "route-neighbors-are-explicit",
                "route-endpoints-use-shard-readiness-base-path",
                "topology-index-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse(
                "advanced-order-platform",
                "Java v495",
                true,
                false,
                ENDPOINT,
                PROFILE,
                routes.size(),
                routes.getFirst().routeVersion(),
                routes.getLast().routeVersion(),
                routes,
                checks,
                status(routes)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse.RouteNode node(
            List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item> items,
            int index
    ) {
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item = items.get(index);
        return new OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse.RouteNode(
                item.name(),
                item.routeVersion(),
                item.endpoint(),
                index == 0 ? "none" : items.get(index - 1).endpoint(),
                index == items.size() - 1 ? "none" : items.get(index + 1).endpoint(),
                item.evidencePath(),
                item.status()
        );
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse.RouteNode> routes
    ) {
        boolean passed = routes.size() == 9
                && routes.getFirst().previousEndpoint().equals("none")
                && routes.getLast().nextEndpoint().equals("none")
                && routes.stream().allMatch(route -> route.endpoint().startsWith(OpsShardReadinessRoutePaths.BASE_PATH))
                && routes.stream().allMatch(route -> "passed".equals(route.status()));
        return passed ? "passed" : "blocked";
    }
}
