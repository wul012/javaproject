package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityGateExplanationRubricCatalog {

  private OpsShardReadinessCodeWalkthroughQualityGateExplanationRubricCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric>
      explanationRubrics() {
    return List.of(
        rubric(
            "入口路由",
            "name the controller, route constant, service entry, or test entry",
            "the reader can jump from the document to the exact Java entry point",
            2),
        rubric(
            "响应模型",
            "name the response record and the fields that prove read-only behavior",
            "the explanation says which counters, flags, and lists are operationally meaningful",
            3),
        rubric(
            "上游证据配置",
            "name the Node plan, Java archive, or static contract consumed by the version",
            "the explanation says why no new upstream runtime is required",
            2),
        rubric(
            "服务层核心流程",
            "summarize the call order from catalog to renderer to support to response",
            "the explanation shows where to extend the flow without creating a large file",
            3),
        rubric(
            "Java 证据检查",
            "list the tests, catalogs, route constants, or docs that prove the version",
            "the explanation distinguishes compile-time structure from runtime behavior",
            3),
        rubric(
            "mini-kv 证据检查",
            "say whether mini-kv evidence is consumed or intentionally untouched",
            "the explanation names the mini-kv boundary rather than omitting the section",
            1),
        rubric(
            "阻断与安全边界",
            "name every forbidden action that stays closed",
            "the explanation connects each boundary to response flags or tests",
            4),
        rubric(
            "测试覆盖",
            "name targeted tests and whether broader verification was run",
            "the explanation lets a maintainer reproduce the confidence level",
            2));
  }

  private static OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric
      rubric(String section, String mustExplain, String standoutSignal, int minimumEvidencePoints) {
    return new OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric(
        section, mustExplain, standoutSignal, minimumEvidencePoints);
  }
}
