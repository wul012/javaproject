package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceResponse.Entry;
import java.util.List;

final class OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog {

  private OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog() {}

  static List<Entry> entries() {
    return List.of(
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            306,
            "Node v549",
            "route-cleanup-catalog-contract-freeze",
            "typed-read-only-catalog-entry",
            "java-shard-readiness-route-cleanup-catalog-contract-freeze-v306"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            307,
            "Node v538",
            "latest-sibling-evidence-intake",
            "read-only-sibling-intake-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-evidence-intake-v307"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            308,
            "Node v540",
            "latest-sibling-evidence-report",
            "read-only-sibling-report-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-evidence-report-v308"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            309,
            "Node v541",
            "latest-sibling-evidence-report-archive",
            "read-only-report-archive-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-evidence-report-archive-v309"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            310,
            "Node v542",
            "latest-sibling-evidence-archive-verification",
            "read-only-archive-verification-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-evidence-archive-verification-v310"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            311,
            "Node v543",
            "latest-sibling-evidence-archive-verification-route",
            "read-only-archive-verification-route-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-archive-verification-route-v311"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            312,
            "Node v544",
            "latest-sibling-live-smoke-preflight",
            "read-only-live-smoke-preflight-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-preflight-v312"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            313,
            "Node v545",
            "latest-sibling-live-smoke",
            "read-only-live-smoke-evidence-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-v313"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            314,
            "Node v546",
            "latest-sibling-live-smoke-archive-verification",
            "read-only-live-smoke-archive-verification-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-archive-verification-v314"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            315,
            "Node v547",
            "latest-sibling-live-smoke-archive-verification-route",
            "read-only-live-smoke-archive-route-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-archive-route-v315"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            316,
            "Node v548",
            "latest-sibling-live-smoke-archive-verification-route-archive",
            "read-only-live-smoke-route-archive-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-route-archive-v316"),
        OpsShardReadinessRouteCleanupEvidenceCatalog.entry(
            317,
            "Node v549",
            "latest-sibling-live-smoke-archive-verification-route-archive-verification",
            "read-only-live-smoke-route-archive-verification-entry",
            "java-shard-readiness-route-cleanup-latest-sibling-live-smoke-route-archive-verification-v317"));
  }
}
