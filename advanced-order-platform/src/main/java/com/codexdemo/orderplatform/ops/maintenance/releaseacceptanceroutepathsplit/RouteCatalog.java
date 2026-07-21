package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry;
import java.util.List;

final class RouteCatalog {

  private RouteCatalog() {}

  static List<RoutePathEntry> routes() {
    return List.of(
        entry(
            "MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY",
            OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths.MINIMAL_READ_ONLY_GATE_EXECUTION_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_EXECUTION_ARCHIVE_VERIFICATION_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY",
            OpsShardReadinessRoutePaths.MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_VERIFICATION_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_REGISTRY),
        entry(
            "MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY",
            OpsShardReadinessRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .MINIMAL_READ_ONLY_GATE_OPERATOR_CI_HANDOFF_ARCHIVE_DIGEST_CONSUMER_PACKAGE_VERIFICATION_DOSSIER_RELEASE_ACCEPTANCE_ARCHIVE_REGISTRY),
        entry(
            "RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY",
            OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ARCHIVE_VERIFICATION_HANDOFF_REGISTRY),
        entry(
            "RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY",
            OpsShardReadinessRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY,
            OpsShardReadinessReleaseAcceptanceRoutePaths
                .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY));
  }

  private static RoutePathEntry entry(String symbol, String stablePath, String splitPath) {
    boolean matched =
        OpsShardReadinessRoutePaths.BASE_PATH.equals(
                OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
            && stablePath.equals(splitPath);
    return new RoutePathEntry(
        symbol,
        splitPath,
        stablePath,
        splitPath,
        "OpsShardReadinessRoutePaths." + symbol,
        "OpsShardReadinessReleaseAcceptanceRoutePaths." + symbol,
        matched,
        matched ? "passed" : "blocked");
  }
}
