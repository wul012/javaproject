package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

class DossierCatalogTests {

  @Test
  void dossierPinsLatestNodePlanAndFrozenJavaContextEvidence() {
    var response = DossierTestData.dossier();

    assertThat(response.version()).isEqualTo("Java v1687");
    assertThat(response.sourcePlan()).isEqualTo("Node v1982");
    assertThat(response.nodeOwnerPlan()).isEqualTo("Node v1968-v1982");
    assertThat(response.javaContextEvidenceVersion()).isEqualTo("Java v90");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-sandbox-connection-blocked-execution-context-normalization-dossier.v1");
    assertThat(response.sourcePreconditionReceiptVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-sandbox-connection-precondition-receipt.v1");
    assertThat(response.sourceReceiptCount()).isEqualTo(1);
    assertThat(response.contextFieldCount()).isEqualTo(3);
    assertThat(response.normalizationRuleCount()).isEqualTo(5);
    assertThat(response.preconditionEvidenceCount()).isEqualTo(6);
    assertThat(response.boundarySnapshotCount()).isEqualTo(5);
    assertThat(response.executionGuardCount()).isEqualTo(12);
    assertThat(response.warningEchoCount()).isEqualTo(4);
    assertThat(response.downstreamIntakeGateCount()).isEqualTo(5);
    assertThat(response.verificationGateCount()).isEqualTo(10);
    assertThat(response.handoffNoteCount()).isEqualTo(4);
    assertThat(response.markdownSectionCount()).isEqualTo(9);
    assertThat(response.checks()).hasSize(21);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void sourceReceiptKeepsBlockedExecutionAndPreconditionIntakeSeparate() {
    var response = DossierTestData.dossier();

    assertThat(response.sourceReceipts())
        .singleElement()
        .satisfies(
            source -> {
              assertThat(source.consumedNodeVersion()).isEqualTo("Node v234");
              assertThat(source.consumedNodeProfile())
                  .isEqualTo(
                      "managed-audit-manual-sandbox-connection-blocked-execution-rehearsal.v1");
              assertThat(source.nextNodeVersion()).isEqualTo("Node v235");
              assertThat(source.nodeMayConsume()).isTrue();
              assertThat(source.readyForManagedAuditSandboxAdapterConnection()).isFalse();
              assertThat(source.nodeMayTreatAsProductionAuditRecord()).isFalse();
            });
    assertThat(response.contextFields())
        .extracting(
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField
                ::name)
        .containsExactly("requestId", "operatorIdentity", "auditCorrelationId");
    assertThat(response.contextFields())
        .allSatisfy(field -> assertThat(field.normalized()).isTrue());
  }

  @Test
  @ResourceLock("default-locale")
  void evidenceIdsUseLocaleNeutralCaseFolding() {
    Locale previous = Locale.getDefault();
    try {
      Locale.setDefault(Locale.forLanguageTag("tr-TR"));
      assertThat(DossierCatalog.evidenceId("INPUT ID: supplied")).isEqualTo("input-id");
    } finally {
      Locale.setDefault(previous);
    }
  }
}
