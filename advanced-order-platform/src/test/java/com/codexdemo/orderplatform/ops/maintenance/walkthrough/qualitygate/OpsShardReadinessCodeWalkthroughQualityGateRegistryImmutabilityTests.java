package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRegistryImmutabilityTests {

  @Test
  void exposesImmutableResponseLists() {
    var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

    assertThatThrownBy(() -> response.versionRules().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.explanationRubrics().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.evidenceAnchors().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.reviewChecklists().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.boundaryRules().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.checks().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
