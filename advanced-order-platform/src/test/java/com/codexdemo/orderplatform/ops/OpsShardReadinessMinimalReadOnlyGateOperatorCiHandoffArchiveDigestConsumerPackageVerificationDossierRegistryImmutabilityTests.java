package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryImmutabilityTests {

    @Test
    void responseCollectionsAreImmutableCopies() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryTestSupport
                        .registry();

        assertThatThrownBy(() -> response.checks().add("late-mutation"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.sourcePackageSnapshots().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().add(
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                        .MarkdownSection("late", java.util.List.of("mutation"))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
