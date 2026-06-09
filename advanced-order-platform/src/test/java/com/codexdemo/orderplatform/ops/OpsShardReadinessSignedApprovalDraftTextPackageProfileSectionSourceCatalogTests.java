package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalogTests {

    @Test
    void sourceCatalogKeepsNineTextPackageRouteOrderStable() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.sources())
                .extracting(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                        .TextPackageSectionSource::code)
                .containsExactly(
                        "signed-approval-artifact-draft-text-package-intake",
                        "signed-approval-artifact-draft-text-package-review-preflight",
                        "signed-approval-artifact-draft-text-package-submission-preflight",
                        "signed-approval-artifact-draft-text-package-comparison-preflight",
                        "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck",
                        "signed-approval-artifact-draft-text-package-compared-package-evidence-intake",
                        "signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight",
                        "signed-approval-artifact-draft-text-package-compared-evidence-candidate",
                        "signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake");
    }

    @Test
    void sourceCatalogLocksNodeVersionMarkersFromNodeV1531Plan() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.sources())
                .extracting(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                        .TextPackageSectionSource::nodeVersionMarker)
                .containsExactly(
                        "Node v1236",
                        "Node v1261",
                        "Node v1286",
                        "Node v1311",
                        "Node v1321",
                        "Node v1331",
                        "Node v1351",
                        "Node v1361",
                        "Node v1371");
    }
}
