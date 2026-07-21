package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CloseoutImmutabilityTests {

  @Test
  void closeoutCollectionsAreImmutable() {
    var response = CloseoutTestData.closeout();

    assertThatThrownBy(() -> response.closeoutItems().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.boundaryAssertions().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.checks().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().getFirst().lines().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
