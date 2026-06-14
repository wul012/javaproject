package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367 / Java v1748-v1753";
  static final String PRIOR_COMPLIANCE_REGISTRY =
      "/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry";
  static final String REGISTRY_STATE =
      "larger-version-granularity-enforced-with-standout-walkthrough-rubric";
  static final int EXPECTED_VERSION_RULE_COUNT = 6;
  static final int EXPECTED_EXPLANATION_RUBRIC_COUNT = 8;
  static final int EXPECTED_EVIDENCE_ANCHOR_COUNT = 6;
  static final int EXPECTED_REVIEW_CHECKLIST_COUNT = 6;

  private OpsShardReadinessCodeWalkthroughQualityGateRegistrySupport() {}

  static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule> versionRules,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric>
          explanationRubrics,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor>
          evidenceAnchors,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist>
          reviewChecklists,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.BoundaryRule> boundaryRules,
      List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection>
          markdownSections) {
    var versionRuleCopy = List.copyOf(versionRules);
    var explanationRubricCopy = List.copyOf(explanationRubrics);
    var evidenceAnchorCopy = List.copyOf(evidenceAnchors);
    var reviewChecklistCopy = List.copyOf(reviewChecklists);
    var boundaryRuleCopy = List.copyOf(boundaryRules);
    var markdownSectionCopy = List.copyOf(markdownSections);
    int deniedBoundaryRuleCount =
        (int) boundaryRuleCopy.stream().filter(rule -> !rule.allowed()).count();
    boolean rulesRequired =
        versionRuleCopy.stream()
            .allMatch(
                OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule::required);
    boolean anchorsRuntimeFree =
        evidenceAnchorCopy.stream()
            .allMatch(
                OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.EvidenceAnchor
                    ::runtimeFree);
    boolean checklistsBlockRelease =
        reviewChecklistCopy.stream()
            .allMatch(
                OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist
                    ::blocksRelease);
    boolean statusPassed =
        versionRuleCopy.size() == EXPECTED_VERSION_RULE_COUNT
            && explanationRubricCopy.size() == EXPECTED_EXPLANATION_RUBRIC_COUNT
            && evidenceAnchorCopy.size() == EXPECTED_EVIDENCE_ANCHOR_COUNT
            && reviewChecklistCopy.size() == EXPECTED_REVIEW_CHECKLIST_COUNT
            && deniedBoundaryRuleCount == boundaryRuleCopy.size()
            && rulesRequired
            && anchorsRuntimeFree
            && checklistsBlockRelease;

    List<String> checks = new ArrayList<>();
    checks.add("code-walkthrough-quality-gate-source-plan-" + SOURCE_PLAN);
    checks.add("code-walkthrough-quality-gate-prior-registry-" + PRIOR_COMPLIANCE_REGISTRY);
    checks.add("code-walkthrough-quality-gate-version-rule-count-" + versionRuleCopy.size());
    checks.add(
        "code-walkthrough-quality-gate-explanation-rubric-count-" + explanationRubricCopy.size());
    checks.add("code-walkthrough-quality-gate-evidence-anchor-count-" + evidenceAnchorCopy.size());
    checks.add(
        "code-walkthrough-quality-gate-review-checklist-count-" + reviewChecklistCopy.size());
    checks.add("code-walkthrough-quality-gate-boundary-rule-count-" + boundaryRuleCopy.size());
    checks.add(
        "code-walkthrough-quality-gate-denied-boundary-rule-count-" + deniedBoundaryRuleCount);
    checks.add(
        "code-walkthrough-quality-gate-markdown-section-count-" + markdownSectionCopy.size());
    checks.add("code-walkthrough-quality-gate-no-micro-version-by-default");
    checks.add("code-walkthrough-quality-gate-standout-explanation-required");
    checks.add("code-walkthrough-quality-gate-evidence-and-tests-travel-together");
    checks.add("code-walkthrough-quality-gate-no-write-routing");
    checks.add("code-walkthrough-quality-gate-no-credential-value");
    checks.add("code-walkthrough-quality-gate-no-raw-endpoint-url");
    checks.add("code-walkthrough-quality-gate-no-upstream-autostart");

    return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse(
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
        PRIOR_COMPLIANCE_REGISTRY,
        REGISTRY_STATE,
        versionRuleCopy.size(),
        explanationRubricCopy.size(),
        evidenceAnchorCopy.size(),
        reviewChecklistCopy.size(),
        boundaryRuleCopy.size(),
        deniedBoundaryRuleCount,
        markdownSectionCopy.size(),
        versionRuleCopy,
        explanationRubricCopy,
        evidenceAnchorCopy,
        reviewChecklistCopy,
        boundaryRuleCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        statusPassed ? "passed" : "blocked");
  }
}
