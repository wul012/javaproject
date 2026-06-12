package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityRouteServiceTestMapCatalog {

    private ReadabilityRouteServiceTestMapCatalog() {
    }

    static List<ReadabilityUpkeepAuditResponse.RouteServiceTestMap> routeMaps() {
        return List.of(
                map(
                        "/api/v1/ops/readability/upkeep-registry",
                        "ReadabilityUpkeepRoutePaths.UPKEEP_REGISTRY",
                        "ReadabilityUpkeepRegistryController",
                        "ReadabilityUpkeepRegistryService",
                        "ReadabilityUpkeepRegistryResponse",
                        List.of(
                                "ReadabilityUpkeepRoutePathsTests",
                                "ReadabilityUpkeepRegistryServiceTests",
                                "ReadabilityUpkeepRegistryRendererTests",
                                "ReadabilityUpkeepBoundaryTests",
                                "ReadabilityUpkeepRegistryControllerTests"
                        )
                ),
                map(
                        "/api/v1/ops/readability/upkeep-audit",
                        "ReadabilityUpkeepRoutePaths.UPKEEP_AUDIT",
                        "ReadabilityUpkeepAuditController",
                        "ReadabilityUpkeepAuditService",
                        "ReadabilityUpkeepAuditResponse",
                        List.of(
                                "ReadabilityUpkeepRoutePathsTests",
                                "ReadabilityUpkeepAuditCatalogTests",
                                "ReadabilityUpkeepAuditServiceTests",
                                "ReadabilityUpkeepAuditRendererTests",
                                "ReadabilityUpkeepAuditBoundaryTests",
                                "ReadabilityUpkeepAuditControllerTests"
                        )
                ),
                map(
                        "docs/ops/route-service-test-map.md",
                        "docs route map",
                        "documentation",
                        "docs/ops",
                        "markdown",
                        List.of(
                                "ReadabilityUpkeepDocsTests",
                                "OpsCodeWalkthroughArchiveComplianceTests"
                        )
                )
        );
    }

    private static ReadabilityUpkeepAuditResponse.RouteServiceTestMap map(
            String route,
            String routeConstant,
            String controller,
            String service,
            String response,
            List<String> tests
    ) {
        return new ReadabilityUpkeepAuditResponse.RouteServiceTestMap(
                route,
                routeConstant,
                controller,
                service,
                response,
                List.copyOf(tests),
                true
        );
    }
}
