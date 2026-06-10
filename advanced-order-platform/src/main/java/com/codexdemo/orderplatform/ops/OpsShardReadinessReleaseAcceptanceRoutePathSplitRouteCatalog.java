package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry> routes() {
        return List.of(
                entry("MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY",
                        OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY",
                        OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY",
                        OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY),
                entry("MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY",
                        OpsShardReadinessRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY),
                entry("RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY",
                        OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths
                                .RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY),
                entry("RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY",
                        OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY,
                        OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY)
        );
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck> compatibilityChecks() {
        return routes().stream()
                .map(route -> new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck(
                        route.symbol(),
                        route.path(),
                        route.path(),
                        route.legacyCompatible()
                ))
                .toList();
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry entry(
            String symbol,
            String stablePath,
            String splitPath
    ) {
        boolean matched = stablePath.equals(splitPath);
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry(
                symbol,
                splitPath,
                "OpsShardReadinessRoutePaths." + symbol,
                "OpsShardReadinessReleaseAcceptanceRoutePaths." + symbol,
                matched,
                matched ? "passed" : "blocked"
        );
    }
}
