package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughDepthRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367 / Java v1774-v1778";
  static final String PRIOR_QUALITY_GATE =
      "/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry";
  static final String REGISTRY_STATE = "chinese-longform-walkthrough-depth-enforced-from-v1774";
  static final int EFFECTIVE_FROM_VERSION = 1774;
  static final int MINIMUM_CHINESE_CHARACTER_COUNT = 3000;
  static final int EXPECTED_DEPTH_RULE_COUNT = 5;
  static final int EXPECTED_LANGUAGE_RULE_COUNT = 4;
  static final int EXPECTED_EVIDENCE_RULE_COUNT = 5;
  static final int EXPECTED_BOUNDARY_RULE_COUNT = 8;
  static final int EXPECTED_VERIFICATION_STEP_COUNT = 5;

  private OpsShardReadinessCodeWalkthroughDepthRegistrySupport() {}

  static OpsShardReadinessCodeWalkthroughDepthRegistryResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule> depthRules,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule> languageRules,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.EvidenceRule> evidenceRules,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule> boundaryRules,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep>
          verificationSteps,
      List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.MarkdownSection>
          markdownSections) {
    var depthRuleCopy = List.copyOf(depthRules);
    var languageRuleCopy = List.copyOf(languageRules);
    var evidenceRuleCopy = List.copyOf(evidenceRules);
    var boundaryRuleCopy = List.copyOf(boundaryRules);
    var verificationStepCopy = List.copyOf(verificationSteps);
    var markdownSectionCopy = List.copyOf(markdownSections);
    int deniedBoundaryRuleCount =
        (int) boundaryRuleCopy.stream().filter(rule -> !rule.allowed()).count();
    boolean depthRulesRequired =
        depthRuleCopy.stream()
            .allMatch(OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule::required);
    boolean languageRulesRequired =
        languageRuleCopy.stream()
            .allMatch(OpsShardReadinessCodeWalkthroughDepthRegistryResponse.LanguageRule::required);
    boolean verificationRequired =
        verificationStepCopy.stream()
            .allMatch(
                OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep::required);
    boolean minimumIsEnforced =
        depthRuleCopy.stream()
            .allMatch(rule -> rule.minimumChineseCharacters() >= MINIMUM_CHINESE_CHARACTER_COUNT);
    boolean statusPassed =
        depthRuleCopy.size() == EXPECTED_DEPTH_RULE_COUNT
            && languageRuleCopy.size() == EXPECTED_LANGUAGE_RULE_COUNT
            && evidenceRuleCopy.size() == EXPECTED_EVIDENCE_RULE_COUNT
            && boundaryRuleCopy.size() == EXPECTED_BOUNDARY_RULE_COUNT
            && verificationStepCopy.size() == EXPECTED_VERIFICATION_STEP_COUNT
            && deniedBoundaryRuleCount == boundaryRuleCopy.size()
            && depthRulesRequired
            && languageRulesRequired
            && verificationRequired
            && minimumIsEnforced;

    List<String> checks = new ArrayList<>();
    checks.add("code-walkthrough-depth-source-plan-" + SOURCE_PLAN);
    checks.add("code-walkthrough-depth-prior-quality-gate-" + PRIOR_QUALITY_GATE);
    checks.add("code-walkthrough-depth-effective-from-v" + EFFECTIVE_FROM_VERSION);
    checks.add(
        "code-walkthrough-depth-minimum-chinese-characters-" + MINIMUM_CHINESE_CHARACTER_COUNT);
    checks.add("code-walkthrough-depth-rule-count-" + depthRuleCopy.size());
    checks.add("code-walkthrough-depth-language-rule-count-" + languageRuleCopy.size());
    checks.add("code-walkthrough-depth-evidence-rule-count-" + evidenceRuleCopy.size());
    checks.add("code-walkthrough-depth-boundary-rule-count-" + boundaryRuleCopy.size());
    checks.add("code-walkthrough-depth-denied-boundary-rule-count-" + deniedBoundaryRuleCount);
    checks.add("code-walkthrough-depth-verification-step-count-" + verificationStepCopy.size());
    checks.add("code-walkthrough-depth-chinese-default");
    checks.add("code-walkthrough-depth-one-version-one-walkthrough");
    checks.add("code-walkthrough-depth-no-short-receipts");
    checks.add("code-walkthrough-depth-no-padding-workload-evidence");
    checks.add("code-walkthrough-depth-project-local-workload-proof");
    checks.add("code-walkthrough-depth-no-write-routing");
    checks.add("code-walkthrough-depth-no-credential-value");
    checks.add("code-walkthrough-depth-no-raw-endpoint-url");
    checks.add("code-walkthrough-depth-no-upstream-autostart");

    return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        PRIOR_QUALITY_GATE,
        REGISTRY_STATE,
        EFFECTIVE_FROM_VERSION,
        MINIMUM_CHINESE_CHARACTER_COUNT,
        depthRuleCopy.size(),
        languageRuleCopy.size(),
        evidenceRuleCopy.size(),
        boundaryRuleCopy.size(),
        deniedBoundaryRuleCount,
        verificationStepCopy.size(),
        markdownSectionCopy.size(),
        depthRuleCopy,
        languageRuleCopy,
        evidenceRuleCopy,
        boundaryRuleCopy,
        verificationStepCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        statusPassed ? "passed" : "blocked");
  }
}
