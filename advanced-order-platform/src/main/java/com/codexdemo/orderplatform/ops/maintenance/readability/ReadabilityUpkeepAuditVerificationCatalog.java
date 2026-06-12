package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityUpkeepAuditVerificationCatalog {

    private ReadabilityUpkeepAuditVerificationCatalog() {
    }

    static List<ReadabilityUpkeepAuditResponse.VerificationStep> verificationSteps() {
        return List.of(
                step(
                        "audit-catalog-tests",
                        "ReadabilityUpkeepAuditCatalogTests",
                        "topic, route-service-test, root-package-pressure, boundary, and verification catalogs"
                ),
                step(
                        "route-path-tests",
                        "ReadabilityUpkeepRoutePathsTests",
                        "registry and audit path constants"
                ),
                step(
                        "audit-service-tests",
                        "ReadabilityUpkeepAuditServiceTests",
                        "counts, checks, status, and source registry endpoint"
                ),
                step(
                        "audit-renderer-tests",
                        "ReadabilityUpkeepAuditRendererTests",
                        "stable markdown sections for audit handoff"
                ),
                step(
                        "audit-boundary-tests",
                        "ReadabilityUpkeepAuditBoundaryTests",
                        "all risky runtime actions remain denied"
                ),
                step(
                        "audit-controller-tests",
                        "ReadabilityUpkeepAuditControllerTests",
                        "controller delegates to the read-only service"
                ),
                step(
                        "docs-upkeep-tests",
                        "ReadabilityUpkeepDocsTests",
                        "docs/ops maps, registry template, and maintenance cycle"
                ),
                step(
                        "walkthrough-compliance-tests",
                        "OpsCodeWalkthroughArchiveComplianceTests",
                        "Chinese longform walkthrough structure and workload"
                )
        );
    }

    private static ReadabilityUpkeepAuditResponse.VerificationStep step(
            String name,
            String commandOrClass,
            String scope
    ) {
        return new ReadabilityUpkeepAuditResponse.VerificationStep(
                name,
                commandOrClass,
                scope,
                true
        );
    }
}
