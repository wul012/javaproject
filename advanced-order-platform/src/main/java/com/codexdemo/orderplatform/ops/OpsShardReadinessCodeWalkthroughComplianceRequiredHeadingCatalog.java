package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceRequiredHeadingCatalog {

    private OpsShardReadinessCodeWalkthroughComplianceRequiredHeadingCatalog() {
    }

    static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading>
            requiredHeadings() {
        return List.of(
                heading(1, "## 入口路由", "name the route, controller, or service entry"),
                heading(2, "## 响应模型", "explain the response fields and evidence payload"),
                heading(3, "## 上游证据配置", "tie the work to Node, Java, or mini-kv evidence"),
                heading(4, "## 服务层核心流程", "summarize the service and catalog flow"),
                heading(5, "## Java 证据检查", "show Java-side assertions and constraints"),
                heading(6, "## mini-kv 证据检查", "state whether mini-kv is inspected or intentionally untouched"),
                heading(7, "## 阻断与安全边界", "record forbidden write, credential, endpoint, and runtime actions"),
                heading(8, "## 测试覆盖", "list the tests that lock the version"),
                heading(9, "## 一句话总结", "close with a single operator-readable outcome")
        );
    }

    private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading
            heading(int order, String heading, String intent) {
        return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading(
                order,
                heading,
                intent
        );
    }
}
