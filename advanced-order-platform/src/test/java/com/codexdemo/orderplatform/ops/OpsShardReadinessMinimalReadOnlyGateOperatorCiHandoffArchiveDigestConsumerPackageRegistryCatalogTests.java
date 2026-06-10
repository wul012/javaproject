package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryCatalogTests {

    @Test
    void carriesManifestAudiencesAndSections() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
                        .registry();

        assertThat(response.sourceDigestSnapshotCount()).isEqualTo(1);
        assertThat(response.manifestEntryCount()).isEqualTo(5);
        assertThat(response.passedManifestEntryCount()).isEqualTo(5);
        assertThat(response.manifest())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .ManifestEntry::name)
                .containsExactly(
                        "source-digest-version",
                        "source-archive-version",
                        "source-digest-state",
                        "source-endpoint",
                        "source-profile"
                );
        assertThat(response.consumerAudienceCount()).isEqualTo(4);
        assertThat(response.readyConsumerAudienceCount()).isEqualTo(4);
        assertThat(response.packageSectionCount()).isEqualTo(5);
        assertThat(response.readyPackageSectionCount()).isEqualTo(5);
    }

    @Test
    void carriesFocusedGroupedBuildSmokeCiMatrix() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport
                        .registry();

        assertThat(response.ciMatrixEntryCount()).isEqualTo(5);
        assertThat(response.readOnlyCiMatrixEntryCount()).isEqualTo(5);
        assertThat(response.ciMatrix())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                        .CiMatrixEntry::commandFamily)
                .containsExactly("focused", "focused", "grouped", "build", "smoke");
    }
}
