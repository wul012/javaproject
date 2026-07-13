package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit.OpsShardReadinessCodeWalkthroughQualityAuditRoutePaths;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRoutePathsTests {

  @Test
  void delegatesQualityAuditRouteThroughSharedRoutePaths() {
    assertThat(
            OpsShardReadinessCodeWalkthroughQualityAuditRoutePaths
                .CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY)
        .isEqualTo("/code-walkthrough-quality-audit-registry");
    assertThat(
            OpsShardReadinessCodeWalkthroughQualityAuditRoutePaths
                .CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY)
        .isEqualTo(
            OpsShardReadinessCodeWalkthroughQualityAuditRoutePaths
                .CODE_WALKTHROUGH_QUALITY_AUDIT_REGISTRY);
    assertThat(OpsShardReadinessCodeWalkthroughQualityAuditRegistryService.ENDPOINT)
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry");
  }
}
