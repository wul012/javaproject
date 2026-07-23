package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DossierServiceTests {

  @Test
  void verificationAndDownstreamGatesPassWithoutOpeningRuntime() {
    var response = DossierTestData.dossier();

    assertThat(response.downstreamIntakeGates())
        .allSatisfy(gate -> assertThat(gate.ready()).isTrue());
    assertThat(response.verificationGates()).allSatisfy(gate -> assertThat(gate.passed()).isTrue());
    assertThat(response.handoffNotes()).allSatisfy(note -> assertThat(note.ready()).isTrue());
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }

  @Test
  void dossierListsAreImmutable() {
    var response = DossierTestData.dossier();

    assertThatThrownBy(() -> response.checks().add("mutate"))
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.executionGuards().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().get(0).lines().add("mutate"))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
