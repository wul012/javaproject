package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ManifestEvidenceTests {

  @Test
  void splitModulesCoverTheNodeV1983ThroughV1994Refactor() {
    var response = ManifestTestData.manifest();

    assertThat(response.splitModules())
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .SplitModule
                ::version)
        .containsExactly(
            "v1983", "v1984", "v1985", "v1986", "v1987", "v1988", "v1989", "v1990", "v1991",
            "v1992", "v1993", "v1994");
    assertThat(response.splitModules())
        .allSatisfy(
            module -> {
              assertThat(module.publicContractPreserved()).isTrue();
              assertThat(module.consumesFrozenJavaV99Only()).isTrue();
              assertThat(module.runtimeExecutionAllowed()).isFalse();
            });
  }

  @Test
  void evidenceReferencesStayFrozenAndPointAtJavaV99AndMiniKvV108() {
    var response = ManifestTestData.manifest();

    assertThat(response.evidenceReferences())
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .EvidenceReference
                ::id)
        .containsExactly(
            "node-v1983-v2002-roadmap",
            "node-v245-precheck-packet",
            "java-v99-precheck-packet-echo",
            "mini-kv-v108-non-participation",
            "node-v247-verification-report");
    assertThat(response.evidenceReferences())
        .allSatisfy(
            reference -> {
              assertThat(reference.accepted()).isTrue();
              assertThat(reference.frozen()).isTrue();
            });
  }
}
