package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SplitImmutabilityTests {

  @Test
  void responseCollectionsAreImmutable() {
    var response = SplitTestData.registry();

    assertThatThrownBy(() -> response.routePaths().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.compatibilityChecks().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.boundaryGuards().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.consumerHandoffs().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.checks().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().getFirst().lines().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
