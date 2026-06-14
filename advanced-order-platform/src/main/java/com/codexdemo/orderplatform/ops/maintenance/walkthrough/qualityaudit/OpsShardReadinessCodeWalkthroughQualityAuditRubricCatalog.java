package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditRubricCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditRubricCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore>
      rubricScores() {
    return List.of(
        score("入口路由", 2, 3, "route owner, shared route delegate, and controller entry are named"),
        score("响应模型", 3, 5, "response records, counters, flags, lists, and status are named"),
        score("上游证据配置", 2, 3, "Node v367 boundary and Java quality registries are cited"),
        score(
            "服务层核心流程",
            3,
            5,
            "catalog, renderer, support, service, and controller flow is explained"),
        score(
            "Java 证据检查",
            3,
            5,
            "route, service, renderer, boundary, controller, immutability, and docs tests are named"),
        score(
            "mini-kv 证据检查", 1, 2, "mini-kv is explicitly recorded as untouched read-only boundary"),
        score(
            "阻断与安全边界",
            4,
            6,
            "write routing, credentials, raw endpoint, audit, deployment, and autostart remain denied"),
        score("测试覆盖", 2, 4, "targeted Maven, full Maven, archive compliance, and CI are named"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore score(
      String section, int requiredEvidencePoints, int observedEvidencePoints, String rationale) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.RubricScore(
        section,
        requiredEvidencePoints,
        observedEvidencePoints,
        observedEvidencePoints >= requiredEvidencePoints,
        rationale);
  }
}
