package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

public record ReadabilityUpkeepRegistryResponse(
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
        String sourceAdvice,
        String docsRoot,
        String packageRoot,
        String registryState,
        int topicCount,
        int packageRuleCount,
        int templateRuleCount,
        int classNameTrialCount,
        int boundaryRuleCount,
        int deniedBoundaryRuleCount,
        int verificationStepCount,
        int markdownSectionCount,
        List<TopicMap> topics,
        List<PackageRule> packageRules,
        List<RegistryTemplateRule> templateRules,
        List<ClassNameTrial> classNameTrials,
        List<BoundaryRule> boundaryRules,
        List<VerificationStep> verificationSteps,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record TopicMap(
            String code,
            String docsPath,
            String sourcePattern,
            String maintainerQuestion,
            boolean indexed
    ) {
    }

    public record PackageRule(
            String code,
            String packageName,
            String scope,
            boolean appliesToNewCode
    ) {
    }

    public record RegistryTemplateRule(
            String code,
            String requiredLayer,
            String evidence,
            boolean required
    ) {
    }

    public record ClassNameTrial(
            String code,
            String oldNamePattern,
            String newNamePattern,
            String rationale,
            boolean activeForNewSubpackages
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
