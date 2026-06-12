package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

public record ReadabilityUpkeepAuditResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean startsJavaService,
        boolean startsMiniKvService,
        boolean writesBusinessState,
        boolean readsCredentialValue,
        boolean resolvesRawEndpointUrl,
        boolean managedAuditConnectionAllowed,
        String endpoint,
        String profile,
        String docsRoot,
        String packageRoot,
        String sourceRegistryEndpoint,
        String auditState,
        int topicCount,
        int routeServiceTestMapCount,
        int rootPackagePressureCount,
        int boundaryRuleCount,
        int deniedBoundaryRuleCount,
        int verificationStepCount,
        int markdownSectionCount,
        List<AuditTopic> topics,
        List<RouteServiceTestMap> routeServiceTestMaps,
        List<RootPackagePressure> rootPackagePressures,
        List<BoundaryRule> boundaryRules,
        List<VerificationStep> verificationSteps,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record AuditTopic(
            String code,
            String docsPath,
            String evidence,
            String maintenanceQuestion,
            boolean required
    ) {
    }

    public record RouteServiceTestMap(
            String route,
            String routeConstant,
            String controller,
            String service,
            String response,
            List<String> tests,
            boolean readOnly
    ) {
    }

    public record RootPackagePressure(
            String area,
            String currentLocation,
            String pressure,
            String preferredDirection,
            boolean migrationRequiredNow
    ) {
    }

    public record BoundaryRule(
            String code,
            String forbiddenAction,
            boolean allowed,
            String rationale
    ) {
    }

    public record VerificationStep(
            String name,
            String commandOrClass,
            String scope,
            boolean required
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
