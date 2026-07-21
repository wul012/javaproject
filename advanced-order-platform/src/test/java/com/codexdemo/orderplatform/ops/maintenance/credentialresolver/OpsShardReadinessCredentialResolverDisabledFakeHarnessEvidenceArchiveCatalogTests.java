package com.codexdemo.orderplatform.ops.maintenance.credentialresolver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveCatalogTests {

  @Test
  void archivePinsNodeOwnerPlanAndFrozenSiblingEvidence() {
    var response = ArchiveTestData.archive();

    assertThat(response.version()).isEqualTo("Java v1667");
    assertThat(response.sourcePlan()).isEqualTo("Node v1967");
    assertThat(response.nodeOwnerPlan()).isEqualTo("Node v1953-v1967");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-credential-resolver-disabled-fake-harness-evidence-archive.v1");
    assertThat(response.sourceRehearsalSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v52");
    assertThat(response.sourceReceiptCount()).isEqualTo(1);
    assertThat(response.javaRequirementCount()).isEqualTo(4);
    assertThat(response.miniKvRequirementCount()).isEqualTo(4);
    assertThat(response.fakeHarnessBoundaryCount()).isEqualTo(1);
    assertThat(response.runtimeGuardCount()).isEqualTo(10);
    assertThat(response.verificationGateCount()).isEqualTo(8);
    assertThat(response.handoffNoteCount()).isEqualTo(4);
    assertThat(response.markdownSectionCount()).isEqualTo(6);
    assertThat(response.checks()).hasSize(19);
    assertThat(response.status()).isEqualTo("passed");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void archiveEchoesImplementationPlanFakeHarnessBoundary() {
    var response = ArchiveTestData.archive();

    assertThat(response.sourceReceipts())
        .singleElement()
        .satisfies(
            source -> {
              assertThat(source.consumedNodeVersion()).isEqualTo("Node v283");
              assertThat(source.nextJavaEchoVersion()).isEqualTo("Java v121");
              assertThat(source.nextMiniKvReceiptVersion()).isEqualTo("mini-kv v126");
              assertThat(source.nextNodeVerificationVersion()).isEqualTo("Node v284");
              assertThat(source.fakeHarnessDeferredUntil()).isEqualTo("Node v285");
              assertThat(source.nodeVerificationReady()).isTrue();
              assertThat(source.siblingEchoReady()).isTrue();
              assertThat(source.fakeHarnessPrecheckReady()).isFalse();
              assertThat(source.managedAuditResolverImplementationReady()).isFalse();
            });
    assertThat(response.fakeHarnessBoundaries())
        .singleElement()
        .satisfies(
            boundary -> {
              assertThat(boundary.code()).isEqualTo("TEST_ONLY_FAKE_HARNESS_CONTRACT");
              assertThat(boundary.requiredArtifacts()).contains("test-only-fake-harness-plan-id");
              assertThat(boundary.prohibitedActions()).contains("send-real-http-request");
              assertThat(boundary.archiveReady()).isTrue();
            });
  }
}
