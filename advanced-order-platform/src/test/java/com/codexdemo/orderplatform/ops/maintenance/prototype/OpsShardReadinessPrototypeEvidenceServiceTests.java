package com.codexdemo.orderplatform.ops.maintenance.prototype;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanupPostCompletionServiceFixtures;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessHardeningService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1Contract;
import org.junit.jupiter.api.Test;

class OpsShardReadinessPrototypeEvidenceServiceTests {

  @Test
  void buildsShardReadinessPrototypeCatalog() {
    OpsShardReadinessPrototypeCatalogResponse catalog = service().catalog();

    assertThat(catalog.project()).isEqualTo("advanced-order-platform");
    assertThat(catalog.version()).isEqualTo(catalog.entries().getLast().version());
    assertThat(catalog.readOnly()).isTrue();
    assertThat(catalog.executionAllowed()).isFalse();
    assertThat(catalog.endpoint()).isEqualTo("/api/v1/ops/shard-readiness/prototype-catalog");
    assertThat(catalog.profile()).isEqualTo("java-shard-readiness-prototype-catalog.v1");
    assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
    assertThat(catalog.entryCount()).isEqualTo(catalog.entries().size());
    assertThat(catalog.requiredFields())
        .containsExactlyElementsOf(OpsShardReadinessV1Contract.minimalFields());
    assertThat(catalog.forbiddenOperations()).contains("write-routing", "managed-audit-connection");
    assertThat(catalog.entries())
        .first()
        .satisfies(
            entry -> {
              assertThat(entry.javaVersion()).isEqualTo(409);
              assertThat(entry.key()).isEqualTo("prototype-catalog");
              assertThat(entry.nodePlanVersion()).isEqualTo("Node v368");
              assertThat(entry.checks()).contains("reuse-route-cleanup-v408-closeout");
            });
    assertThat(catalog.status()).isEqualTo("passed");
  }

  @Test
  void buildsPrototypeFixtureEchoEvidence() {
    OpsShardReadinessPrototypeEvidenceResponse evidence = service().fixtureEcho();

    assertThat(evidence.project()).isEqualTo("advanced-order-platform");
    assertThat(evidence.version()).isEqualTo("Java v411");
    assertThat(evidence.readOnly()).isTrue();
    assertThat(evidence.executionAllowed()).isFalse();
    assertThat(evidence.endpoint()).isEqualTo("/api/v1/ops/shard-readiness/prototype-fixture-echo");
    assertThat(evidence.profile()).isEqualTo("java-shard-readiness-prototype-fixture-echo.v1");
    assertThat(evidence.entryKey()).isEqualTo("prototype-fixture-echo");
    assertThat(evidence.contractName()).isEqualTo("shard-readiness.v1");
    assertThat(evidence.shardEnabled()).isFalse();
    assertThat(evidence.shardCount()).isZero();
    assertThat(evidence.slotCount()).isZero();
    assertThat(evidence.routingMode()).isEqualTo("fixture");
    assertThat(evidence.rootReadinessVersion()).isEqualTo("Java v153");
    assertThat(evidence.echoVersion()).isEqualTo("Java v174");
    assertThat(evidence.routeCleanupCloseoutVersion()).isEqualTo("Java v408");
    assertThat(evidence.checks()).contains("echo-status-passed");
    assertThat(evidence.forbiddenOperations()).contains("active-shard-router");
    assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
    assertThat(evidence.evidencePath())
        .isEqualTo("e/411/evidence/java-shard-readiness-prototype-fixture-echo-v411.json");
    assertThat(evidence.status()).isEqualTo("passed");
  }

  @Test
  void allCatalogEntriesProducePassedReadOnlyEvidence() {
    OpsShardReadinessPrototypeEvidenceService service = service();

    assertThat(OpsShardReadinessPrototypeEvidenceCatalog.entries())
        .allSatisfy(
            entry -> {
              OpsShardReadinessPrototypeEvidenceResponse evidence = service.evidence(entry.key());

              assertThat(evidence.version()).isEqualTo(entry.version());
              assertThat(evidence.endpoint()).isEqualTo(entry.endpoint());
              assertThat(evidence.profile()).isEqualTo(entry.profile());
              assertThat(evidence.readOnly()).isTrue();
              assertThat(evidence.executionAllowed()).isFalse();
              assertThat(evidence.requiredFields())
                  .containsExactlyElementsOf(OpsShardReadinessV1Contract.minimalFields());
              assertThat(evidence.status()).isEqualTo("passed");
            });
  }

  @Test
  void closesOutPrototypeEvidenceRunForConsumerGate() {
    OpsShardReadinessPrototypeEvidenceService service = service();
    OpsShardReadinessPrototypeCatalogResponse catalog = service.catalog();
    OpsShardReadinessPrototypeEvidenceResponse closeout = service.closeout();

    assertThat(catalog.entryCount()).isEqualTo(10);
    assertThat(catalog.entries().getFirst().javaVersion()).isEqualTo(409);
    assertThat(catalog.entries().getLast().javaVersion()).isEqualTo(427);
    assertThat(closeout.version()).isEqualTo("Java v427");
    assertThat(closeout.entryKey()).isEqualTo("prototype-closeout");
    assertThat(closeout.checks())
        .contains("closeout-entry-count-10", "closeout-ready-for-node-consumer-gate");
    assertThat(closeout.digestValue()).matches("[0-9a-f]{64}");
    assertThat(closeout.status()).isEqualTo("passed");
  }

  private OpsShardReadinessPrototypeEvidenceService service() {
    OpsShardReadinessEvidenceIndexService evidenceIndexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService evidenceVerificationService =
        new OpsShardReadinessEvidenceVerificationService(evidenceIndexService);
    OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
        new OpsShardReadinessEvidenceHandoffService(
            evidenceIndexService, evidenceVerificationService);
    OpsShardReadinessEchoService echoService =
        new OpsShardReadinessEchoService(
            new OpsShardReadinessService(),
            new OpsShardReadinessHardeningService(),
            evidenceIndexService,
            evidenceHandoffService);
    return new OpsShardReadinessPrototypeEvidenceService(
        new OpsShardReadinessService(),
        echoService,
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.postCompletionCloseoutService());
  }
}
