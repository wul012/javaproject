package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.ArrayList;
import java.util.List;

final class ReadabilityUpkeepAuditSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String DOCS_ROOT = "docs/ops";
    static final String PACKAGE_ROOT =
            "com.codexdemo.orderplatform.ops.maintenance.readability";
    static final String AUDIT_STATE =
            "readability-upkeep-audit-registry-active-v1786";
    static final int EXPECTED_TOPIC_COUNT = 5;
    static final int EXPECTED_ROUTE_SERVICE_TEST_MAP_COUNT = 3;
    static final int EXPECTED_ROOT_PACKAGE_PRESSURE_COUNT = 4;
    static final int EXPECTED_BOUNDARY_RULE_COUNT = 8;
    static final int EXPECTED_VERIFICATION_STEP_COUNT = 8;

    private ReadabilityUpkeepAuditSupport() {
    }

    static ReadabilityUpkeepAuditResponse response(
            String version,
            String endpoint,
            String profile,
            String sourceRegistryEndpoint,
            List<ReadabilityUpkeepAuditResponse.AuditTopic> topics,
            List<ReadabilityUpkeepAuditResponse.RouteServiceTestMap> routeMaps,
            List<ReadabilityUpkeepAuditResponse.RootPackagePressure> pressures,
            List<ReadabilityUpkeepAuditResponse.BoundaryRule> boundaryRules,
            List<ReadabilityUpkeepAuditResponse.VerificationStep> verificationSteps,
            List<ReadabilityUpkeepAuditResponse.MarkdownSection> markdownSections
    ) {
        var topicCopy = List.copyOf(topics);
        var routeMapCopy = List.copyOf(routeMaps);
        var pressureCopy = List.copyOf(pressures);
        var boundaryRuleCopy = List.copyOf(boundaryRules);
        var verificationStepCopy = List.copyOf(verificationSteps);
        var markdownSectionCopy = List.copyOf(markdownSections);

        int deniedBoundaryRuleCount = (int) boundaryRuleCopy.stream()
                .filter(rule -> !rule.allowed())
                .count();
        boolean topicsRequired = topicCopy.stream()
                .allMatch(ReadabilityUpkeepAuditResponse.AuditTopic::required);
        boolean routesReadOnly = routeMapCopy.stream()
                .allMatch(ReadabilityUpkeepAuditResponse.RouteServiceTestMap::readOnly);
        boolean migrationsDeferred = pressureCopy.stream()
                .noneMatch(ReadabilityUpkeepAuditResponse.RootPackagePressure
                        ::migrationRequiredNow);
        boolean verificationRequired = verificationStepCopy.stream()
                .allMatch(ReadabilityUpkeepAuditResponse.VerificationStep::required);
        boolean statusPassed = topicCopy.size() == EXPECTED_TOPIC_COUNT
                && routeMapCopy.size() == EXPECTED_ROUTE_SERVICE_TEST_MAP_COUNT
                && pressureCopy.size() == EXPECTED_ROOT_PACKAGE_PRESSURE_COUNT
                && boundaryRuleCopy.size() == EXPECTED_BOUNDARY_RULE_COUNT
                && verificationStepCopy.size() == EXPECTED_VERIFICATION_STEP_COUNT
                && deniedBoundaryRuleCount == boundaryRuleCopy.size()
                && topicsRequired
                && routesReadOnly
                && migrationsDeferred
                && verificationRequired;

        List<String> checks = new ArrayList<>();
        checks.add("readability-upkeep-audit-docs-root-" + DOCS_ROOT);
        checks.add("readability-upkeep-audit-package-root-" + PACKAGE_ROOT);
        checks.add("readability-upkeep-audit-source-registry-" + sourceRegistryEndpoint);
        checks.add("readability-upkeep-audit-topic-count-" + topicCopy.size());
        checks.add("readability-upkeep-audit-route-map-count-" + routeMapCopy.size());
        checks.add("readability-upkeep-audit-root-pressure-count-" + pressureCopy.size());
        checks.add("readability-upkeep-audit-boundary-rule-count-" + boundaryRuleCopy.size());
        checks.add("readability-upkeep-audit-denied-boundary-count-"
                + deniedBoundaryRuleCount);
        checks.add("readability-upkeep-audit-verification-step-count-"
                + verificationStepCopy.size());
        checks.add("readability-upkeep-audit-route-service-test-map-present");
        checks.add("readability-upkeep-audit-root-package-pressure-present");
        checks.add("readability-upkeep-audit-no-migration-now");
        checks.add("readability-upkeep-audit-no-write-routing");
        checks.add("readability-upkeep-audit-no-credential-value");
        checks.add("readability-upkeep-audit-no-upstream-autostart");

        return new ReadabilityUpkeepAuditResponse(
                PROJECT,
                version,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                DOCS_ROOT,
                PACKAGE_ROOT,
                sourceRegistryEndpoint,
                AUDIT_STATE,
                topicCopy.size(),
                routeMapCopy.size(),
                pressureCopy.size(),
                boundaryRuleCopy.size(),
                deniedBoundaryRuleCount,
                verificationStepCopy.size(),
                markdownSectionCopy.size(),
                topicCopy,
                routeMapCopy,
                pressureCopy,
                boundaryRuleCopy,
                verificationStepCopy,
                markdownSectionCopy,
                List.copyOf(checks),
                statusPassed ? "passed" : "blocked"
        );
    }
}
