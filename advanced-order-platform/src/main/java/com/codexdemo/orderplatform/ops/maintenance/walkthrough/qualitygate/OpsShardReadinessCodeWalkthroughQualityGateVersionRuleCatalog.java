package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateVersionRuleCatalog {

  private OpsShardReadinessCodeWalkthroughQualityGateVersionRuleCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule>
      versionRules() {
    return List.of(
        rule(
            "no-micro-version-by-default",
            "a version should cover one coherent maintainability or feature outcome",
            "the walkthrough can explain route, model, service flow, tests, and boundary in one story",
            "split only when the next change would blur ownership or make review unsafe"),
        rule(
            "standout-explanation-required",
            "the version must have enough substance for a useful nine-section walkthrough",
            "the explanation names implementation files and why the change improves future work",
            "if the explanation would be only a receipt, merge it into the nearest larger version"),
        rule(
            "evidence-and-tests-travel-together",
            "feature evidence, safety boundary, and tests should land in the same version when practical",
            "the reader can verify the behavior without chasing several tiny commits",
            "split fixtures or docs only when they are independently reviewable"),
        rule(
            "refactor-with-purpose",
            "refactors count as a version only when they reduce a named maintenance risk",
            "the walkthrough says what got easier to test, extend, or audit",
            "avoid renames or file moves that do not change maintainability"),
        rule(
            "batch-size-guard",
            "a batch may contain several related files, but not unrelated domains",
            "the version is broad enough to be meaningful and narrow enough to review",
            "create the next version when the evidence or reviewer audience changes"),
        rule(
            "read-only-boundary-first",
            "Java remains in read-only evidence mode unless a later plan explicitly opens runtime work",
            "the walkthrough proves write routes, credentials, raw endpoints, and autostart stayed closed",
            "defer runtime enablement to a separate plan with explicit approval"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule rule(
      String code, String minimumScope, String explanationRequirement, String splitGuidance) {
    return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule(
        code, minimumScope, explanationRequirement, splitGuidance, true);
  }
}
