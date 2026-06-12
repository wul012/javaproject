package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityPackageRuleCatalog {

    private ReadabilityPackageRuleCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.PackageRule> packageRules() {
        return List.of(
                rule(
                        "new-readability-registry-subpackage",
                        "com.codexdemo.orderplatform.ops.maintenance.readability",
                        "late-stage readability upkeep registries",
                        true
                ),
                rule(
                        "walkthrough-depth-future-subpackage",
                        "com.codexdemo.orderplatform.ops.walkthrough.depth",
                        "future code walkthrough depth extensions",
                        true
                ),
                rule(
                        "archive-layout-future-subpackage",
                        "com.codexdemo.orderplatform.ops.archive.layout",
                        "future screenshot and explanation archive layout extensions",
                        true
                ),
                rule(
                        "legacy-root-package-preserved",
                        "com.codexdemo.orderplatform.ops",
                        "existing ops classes stay in place unless a focused refactor moves them",
                        false
                )
        );
    }

    private static ReadabilityUpkeepRegistryResponse.PackageRule rule(
            String code,
            String packageName,
            String scope,
            boolean appliesToNewCode
    ) {
        return new ReadabilityUpkeepRegistryResponse.PackageRule(
                code,
                packageName,
                scope,
                appliesToNewCode
        );
    }
}
