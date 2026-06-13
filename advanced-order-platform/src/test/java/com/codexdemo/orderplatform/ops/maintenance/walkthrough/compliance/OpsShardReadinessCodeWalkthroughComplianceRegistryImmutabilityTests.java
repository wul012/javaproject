package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests {

  @Test
  void exposesImmutableResponseLists() {
    var response = OpsShardReadinessCodeWalkthroughComplianceRegistryTestSupport.registry();

    assertThatThrownBy(() -> response.versions().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.requiredHeadings().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.archiveRanges().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.documentationRules().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.boundaryRules().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.testCoverages().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.markdownSections().clear())
        .isInstanceOf(UnsupportedOperationException.class);
    assertThatThrownBy(() -> response.checks().clear())
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
