package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateReviewChecklistCatalog {

  private OpsShardReadinessCodeWalkthroughQualityGateReviewChecklistCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist>
      reviewChecklists() {
    return List.of(
        checklist(
            "version-size",
            "Would this version still be worth explaining if the file list were hidden?",
            "the version is only a receipt, marker, or one-line routing tweak"),
        checklist(
            "implementation-story",
            "Can the walkthrough explain the route, model, flow, tests, and boundary together?",
            "the explanation cannot connect files into one maintainable story"),
        checklist(
            "evidence-depth",
            "Does the version point to concrete source files, response fields, and test names?",
            "the document says read-only or ready without naming proof"),
        checklist(
            "boundary-clarity",
            "Does the version say which runtime actions remain impossible?",
            "the version omits write routing, credential, raw endpoint, or autostart boundaries"),
        checklist(
            "refactor-payoff",
            "If this is refactor work, does it remove a named maintenance risk?",
            "the change only moves code without test, extension, or review payoff"),
        checklist(
            "archive-discoverability",
            "Can a later maintainer find the walkthrough through the archive index?",
            "new docs land in an oversized or unindexed directory"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist
      checklist(String item, String reviewerQuestion, String releaseBlocker) {
    return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ReviewChecklist(
        item, reviewerQuestion, releaseBlocker, true);
  }
}
