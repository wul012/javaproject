package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryImmutabilityTests {

    @Test
    void responseCollectionsAreImmutableCopies() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThatThrownBy(() -> response.checks().add("late-mutation"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.sourceDossierSnapshots().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().add(
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .MarkdownSection("late", java.util.List.of("mutation"))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
