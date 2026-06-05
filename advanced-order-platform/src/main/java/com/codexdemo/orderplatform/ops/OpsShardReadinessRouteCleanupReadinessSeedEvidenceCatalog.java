package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanupEvidenceResponse.Entry;
import java.util.List;

final class OpsShardReadinessRouteCleanupReadinessSeedEvidenceCatalog {

    private OpsShardReadinessRouteCleanupReadinessSeedEvidenceCatalog() {
    }

    static List<Entry> entries() {
        return List.of(
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        318,
                        "Node v501",
                        "readiness-handoff-evidence-intake",
                        "read-only-readiness-handoff-intake-entry",
                        "java-shard-readiness-route-cleanup-readiness-handoff-evidence-intake-v318"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        319,
                        "Node v502",
                        "readiness-handoff-evidence-report",
                        "read-only-readiness-handoff-report-entry",
                        "java-shard-readiness-route-cleanup-readiness-handoff-evidence-report-v319"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        320,
                        "Node v503",
                        "readiness-handoff-evidence-report-archive",
                        "read-only-readiness-handoff-report-archive-entry",
                        "java-shard-readiness-route-cleanup-readiness-handoff-evidence-report-archive-v320"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        321,
                        "Node v504",
                        "readiness-handoff-evidence-archive-verification",
                        "read-only-readiness-handoff-archive-verification-entry",
                        "java-shard-readiness-route-cleanup-readiness-handoff-archive-verification-v321"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        322,
                        "Node v505",
                        "readiness-handoff-evidence-archive-verification-route",
                        "read-only-readiness-handoff-archive-route-entry",
                        "java-shard-readiness-route-cleanup-readiness-handoff-archive-route-v322"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        323,
                        "Node v532",
                        "ci-catalog-health-closeout",
                        "read-only-ci-catalog-health-entry",
                        "java-shard-readiness-route-cleanup-ci-catalog-health-closeout-v323"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        324,
                        "Node v537",
                        "extended-run-final-closeout",
                        "read-only-extended-run-closeout-entry",
                        "java-shard-readiness-route-cleanup-extended-run-final-closeout-v324"
                ),
                OpsShardReadinessRouteCleanupEvidenceEntryFactory.entry(
                        325,
                        "Node v549",
                        "twenty-version-functional-run-closeout",
                        "read-only-twenty-version-closeout-entry",
                        "java-shard-readiness-route-cleanup-twenty-version-functional-run-closeout-v325"
                )
        );
    }
}