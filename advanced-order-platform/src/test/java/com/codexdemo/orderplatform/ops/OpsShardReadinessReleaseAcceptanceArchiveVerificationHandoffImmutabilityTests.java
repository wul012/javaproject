package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffImmutabilityTests {

    @Test
    void responseCollectionsAreImmutableCopies() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThatThrownBy(() -> response.checks().add("late-mutation"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.sourceArchiveSnapshots().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.verificationRequirements().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().add(
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .MarkdownSection("late", List.of("mutation"))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
