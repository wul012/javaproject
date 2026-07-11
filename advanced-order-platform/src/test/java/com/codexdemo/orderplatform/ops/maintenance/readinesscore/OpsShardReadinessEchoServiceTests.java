package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEchoServiceTests {

  @Test
  void buildsReadOnlyShardReadinessEchoWithoutChangingRootSchema() {
    OpsShardReadinessEvidenceIndexService evidenceIndexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService evidenceVerificationService =
        new OpsShardReadinessEvidenceVerificationService(evidenceIndexService);
    OpsShardReadinessEchoService service =
        new OpsShardReadinessEchoService(
            new OpsShardReadinessService(),
            new OpsShardReadinessHardeningService(),
            evidenceIndexService,
            new OpsShardReadinessEvidenceHandoffService(
                evidenceIndexService, evidenceVerificationService));

    OpsShardReadinessEchoResponse echo = service.echo();

    assertThat(echo.project()).isEqualTo("advanced-order-platform");
    assertThat(echo.version()).isEqualTo("Java v174");
    assertThat(echo.readOnly()).isTrue();
    assertThat(echo.executionAllowed()).isFalse();
    assertThat(echo.shardEnabled()).isFalse();
    assertThat(echo.sourceReadinessVersion()).isEqualTo("Java v153");
    assertThat(echo.sourceHardeningVersion()).isEqualTo("Java v154");
    assertThat(echo.sourceEvidenceIndexVersion()).isEqualTo("Java v155");
    assertThat(echo.sourceEvidenceHandoffVersion()).isEqualTo("Java v157");
    assertThat(echo.schemaCompatibilityMode())
        .isEqualTo("append-only-new-echo-endpoint-preserves-v153-root-schema");
    assertThat(echo.receiptId()).isEqualTo("java-shard-readiness-echo-receipt-v174");
    assertThat(echo.preservedRootFields())
        .containsExactly(
            "project",
            "version",
            "readOnly",
            "executionAllowed",
            "shardEnabled",
            "shardCount",
            "slotCount",
            "routingMode",
            "evidencePath",
            "status");
    assertThat(echo.controllerSplitReceipts())
        .containsExactly(
            "Java v171:runtime-execution-controller-split",
            "Java v172:lifecycle-plan-controller-split",
            "Java v173:evidence-controller-split");
    assertThat(echo.forbiddenOperations())
        .contains(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "raw-endpoint-parse",
            "managed-audit-connection",
            "deployment-or-rollback",
            "node-start-or-stop-java-or-mini-kv");
    assertThat(echo.evidencePath()).isEqualTo("e/174/evidence/java-shard-readiness-echo-v174.json");
    assertThat(echo.status()).isEqualTo("passed");
  }
}
