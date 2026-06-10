package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryImmutabilityTests {

    @Test
    void responseCollectionsAreImmutableCopies() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
                        .registry();

        assertThatThrownBy(() -> response.checks().add("late-mutation"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.sourceArchiveSnapshots().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.artifactManifest().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> response.markdownSections().add(
                new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .MarkdownSection("late", List.of("mutation"))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
