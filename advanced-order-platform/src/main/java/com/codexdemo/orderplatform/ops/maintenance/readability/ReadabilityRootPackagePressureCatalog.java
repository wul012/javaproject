package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityRootPackagePressureCatalog {

    private ReadabilityRootPackagePressureCatalog() {
    }

    static List<ReadabilityUpkeepAuditResponse.RootPackagePressure> pressures() {
        return List.of(
                pressure(
                        "shard-readiness-evidence",
                        "com.codexdemo.orderplatform.ops",
                        "historical OpsShardReadiness classes remain broad but route-compatible",
                        "Keep published evidence routes stable and migrate only with explicit compatibility tests.",
                        false
                ),
                pressure(
                        "code-walkthrough-depth",
                        "com.codexdemo.orderplatform.ops",
                        "depth registry classes are still discoverable only from tests and docs",
                        "Use docs/ops maps before any later relocation.",
                        false
                ),
                pressure(
                        "readability-upkeep",
                        "com.codexdemo.orderplatform.ops.maintenance.readability",
                        "new work can stay smaller because the package carries the context",
                        "Keep new readability route paths, responses, catalogs, renderers, support, services, and controllers here.",
                        false
                ),
                pressure(
                        "archive-documentation",
                        "docs/ops and segmented walkthrough roots",
                        "screenshots and explanations can sprawl without a segmented archive rule",
                        "Link archive rules from docs/ops and keep walkthroughs in versioned batches.",
                        false
                )
        );
    }

    private static ReadabilityUpkeepAuditResponse.RootPackagePressure pressure(
            String area,
            String currentLocation,
            String pressure,
            String preferredDirection,
            boolean migrationRequiredNow
    ) {
        return new ReadabilityUpkeepAuditResponse.RootPackagePressure(
                area,
                currentLocation,
                pressure,
                preferredDirection,
                migrationRequiredNow
        );
    }
}
