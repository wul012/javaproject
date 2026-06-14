package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRegistryServiceTests {

  @Test
  void buildsQualityAuditRegistryForRecentMediumGranularityBatch() {
    var response = OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1758");
    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-code-walkthrough-quality-audit-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367 / Java v1754-v1758");
    assertThat(response.auditedBatch()).isEqualTo("Java v1748-v1753");
    assertThat(response.qualityGateRegistry())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
    assertThat(response.registryState())
        .isEqualTo("quality-gate-batch-audited-with-medium-granularity-evidence");
    assertThat(response.batchAssessmentCount()).isEqualTo(2);
    assertThat(response.versionAuditCount()).isEqualTo(6);
    assertThat(response.mediumGranularityVersionCount()).isEqualTo(6);
    assertThat(response.rubricScoreCount()).isEqualTo(8);
    assertThat(response.passedRubricScoreCount()).isEqualTo(8);
    assertThat(response.reviewFindingCount()).isEqualTo(4);
    assertThat(response.blockingReviewFindingCount()).isZero();
    assertThat(response.boundaryAuditCount()).isEqualTo(8);
    assertThat(response.deniedBoundaryAuditCount()).isEqualTo(8);
    assertThat(response.verificationStepCount()).isEqualTo(5);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void auditsEveryQualityGateVersionAsMediumGranularity() {
    var response = OpsShardReadinessCodeWalkthroughQualityAuditRegistryTestSupport.registry();

    assertThat(response.versionAudits())
        .extracting(
            OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VersionAudit::javaVersion)
        .containsExactly(
            "Java v1748", "Java v1749", "Java v1750", "Java v1751", "Java v1752", "Java v1753");
    assertThat(response.versionAudits())
        .allSatisfy(
            audit -> {
              assertThat(audit.mediumGranularity()).isTrue();
              assertThat(audit.explanationEvidencePoints()).isGreaterThanOrEqualTo(11);
              assertThat(audit.namedTestCount()).isGreaterThanOrEqualTo(2);
            });
  }
}
