package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestImmutabilityTests {

  @Test
  void codeHealthVerificationAndHandoffStayReady() {
    var response =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport
            .manifest();

    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.codeHealthGates()).allSatisfy(gate -> assertThat(gate.passed()).isTrue());
    assertThat(response.verificationGates()).allSatisfy(gate -> assertThat(gate.passed()).isTrue());
    assertThat(response.handoffNotes()).allSatisfy(note -> assertThat(note.ready()).isTrue());
  }

  @Test
  void listsAndRenderedLinesAreImmutable() {
    var response =
        OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport
            .manifest();

    assertThatThrownBy(() -> response.checks().add("mutated"))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.boundaryGuards().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().getFirst().lines().add("mutated"))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
