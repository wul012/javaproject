package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityUpkeepAuditTopicCatalog {

    private ReadabilityUpkeepAuditTopicCatalog() {
    }

    static List<ReadabilityUpkeepAuditResponse.AuditTopic> topics() {
        return List.of(
                topic(
                        "route-service-test-map",
                        "docs/ops/route-service-test-map.md",
                        "route, controller, service, response, and test ownership",
                        "Can a maintainer trace each read-only route without searching the whole ops root?"
                ),
                topic(
                        "root-package-pressure",
                        "docs/ops/root-package-pressure-map.md",
                        "root ops pressure points and preferred subpackage direction",
                        "Does new readability work avoid adding avoidable classes to the old root package?"
                ),
                topic(
                        "registry-template-follow-through",
                        "docs/ops/registry-template.md",
                        "route path, response, catalog, renderer, support, service, controller, tests",
                        "Does the new audit registry keep the same layered template as the prior registry?"
                ),
                topic(
                        "class-name-trial-continuity",
                        "docs/ops/class-name-trial.md",
                        "shorter names only after package context carries the missing words",
                        "Are new classes shorter because the package is precise rather than because context was removed?"
                ),
                topic(
                        "walkthrough-depth-guard",
                        "代码讲解记录_生产雏形阶段4/v1784-v1788",
                        "Chinese longform walkthroughs with actual project-local workload",
                        "Can the version explanation justify the work without padding?"
                )
        );
    }

    private static ReadabilityUpkeepAuditResponse.AuditTopic topic(
            String code,
            String docsPath,
            String evidence,
            String maintenanceQuestion
    ) {
        return new ReadabilityUpkeepAuditResponse.AuditTopic(
                code,
                docsPath,
                evidence,
                maintenanceQuestion,
                true
        );
    }
}
