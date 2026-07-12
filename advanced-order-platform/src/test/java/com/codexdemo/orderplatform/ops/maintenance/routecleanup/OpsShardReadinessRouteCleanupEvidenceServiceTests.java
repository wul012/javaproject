package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupEvidenceServiceTests {

  @Test
  void buildsReadOnlyRouteCleanupEvidenceCatalog() {
    OpsShardReadinessRouteCleanupEvidenceResponse catalog =
        new OpsShardReadinessRouteCleanupEvidenceService().catalog();

    assertThat(catalog.project()).isEqualTo("advanced-order-platform");
    assertThat(catalog.version()).isEqualTo("Java v" + catalog.entries().getLast().javaVersion());
    assertThat(catalog.readOnly()).isTrue();
    assertThat(catalog.executionAllowed()).isFalse();
    assertThat(catalog.catalogEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-evidence-catalog");
    assertThat(catalog.catalogProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-evidence-catalog.v1");
    assertThat(catalog.entryCount()).isEqualTo(catalog.entries().size());
    assertThat(catalog.entryCount()).isEqualTo(catalog.entries().getLast().javaVersion() - 305);
    assertThat(catalog.entries())
        .extracting(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::javaVersion)
        .containsExactlyElementsOf(
            IntStream.rangeClosed(306, catalog.entries().getLast().javaVersion()).boxed().toList());
    assertThat(catalog.entries().getLast().javaVersion()).isGreaterThanOrEqualTo(326);
    assertThat(catalog.entries())
        .allSatisfy(
            entry -> {
              assertThat(entry.readOnly()).isTrue();
              assertThat(entry.executionAllowed()).isFalse();
              assertThat(entry.startsJavaService()).isFalse();
              assertThat(entry.startsMiniKvService()).isFalse();
              assertThat(entry.credentialValueRead()).isFalse();
              assertThat(entry.rawEndpointParsed()).isFalse();
              assertThat(entry.managedAuditConnectionOpened()).isFalse();
              assertThat(entry.writeRoutingChanged()).isFalse();
              assertThat(entry.status()).isEqualTo("passed");
            });
    assertThat(catalog.entries().getFirst().phase())
        .isEqualTo("route-cleanup-catalog-contract-freeze");
    assertThat(catalog.entries().getFirst().sourceNodePlan()).isEqualTo("Node v549");
    assertThat(catalog.entries().getFirst().evidencePath())
        .isEqualTo(
            "e/306/evidence/java-shard-readiness-route-cleanup-catalog-contract-freeze-v306.json");
    assertThat(catalog.entries())
        .extracting(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::phase)
        .contains(
            "route-cleanup-catalog-contract-freeze",
            "latest-sibling-evidence-intake",
            "latest-sibling-evidence-report",
            "twenty-version-functional-run-closeout",
            "handoff-suite-evidence-analyzer");
    assertThat(catalog.entries())
        .allSatisfy(
            entry ->
                assertThat(entry.evidencePath())
                    .contains("/" + entry.javaVersion() + "/")
                    .endsWith("-v" + entry.javaVersion() + ".json"));
    assertThat(catalog.forbiddenOperations())
        .contains(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "raw-endpoint-parse",
            "managed-audit-connection",
            "node-start-or-stop-java-or-mini-kv");
    assertThat(catalog.status()).isEqualTo("passed");
  }
}
