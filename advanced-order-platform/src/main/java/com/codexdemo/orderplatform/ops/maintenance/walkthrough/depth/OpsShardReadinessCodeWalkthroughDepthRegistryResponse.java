package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import java.util.List;

public record OpsShardReadinessCodeWalkthroughDepthRegistryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean startsJavaService,
    boolean startsMiniKvService,
    boolean readsCredentialValue,
    boolean resolvesRawEndpointUrl,
    boolean managedAuditHttpAllowed,
    String endpoint,
    String profile,
    String sourcePlan,
    String priorQualityGate,
    String registryState,
    int effectiveFromVersion,
    int minimumChineseCharacterCount,
    int depthRuleCount,
    int languageRuleCount,
    int evidenceRuleCount,
    int boundaryRuleCount,
    int deniedBoundaryRuleCount,
    int verificationStepCount,
    int markdownSectionCount,
    List<DepthRule> depthRules,
    List<LanguageRule> languageRules,
    List<EvidenceRule> evidenceRules,
    List<BoundaryRule> boundaryRules,
    List<VerificationStep> verificationSteps,
    List<MarkdownSection> markdownSections,
    List<String> checks,
    String status) {

  public record DepthRule(
      String code, String requirement, int minimumChineseCharacters, boolean required) {}

  public record LanguageRule(
      String code, String requirement, String rejectionSignal, boolean required) {}

  public record EvidenceRule(
      String code, String requiredEvidence, String maintainerQuestion, int minimumMentions) {}

  public record BoundaryRule(
      String code, String forbiddenAction, boolean allowed, String rationale) {}

  public record VerificationStep(
      String name, String commandOrClass, String scope, boolean required) {}

  public record MarkdownSection(String heading, List<String> lines) {}
}
