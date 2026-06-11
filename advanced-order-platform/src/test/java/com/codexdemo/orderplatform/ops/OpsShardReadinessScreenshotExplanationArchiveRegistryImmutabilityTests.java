package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryImmutabilityTests {

    @Test
    void exposesImmutableResponseLists() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThatThrownBy(() -> response.currentArchiveAssessments().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.segmentPlans().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.namingRules().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.boundaryRules().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.verificationSteps().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.checks().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
