package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalogTests {

    @Test
    void combinesFoundationAndAssuranceItemsWithoutCreatingDraft() {
        var items = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog
                .allItems();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationItemCatalog
                .foundationItems()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceItemCatalog
                .assuranceItems()).hasSize(12);
        assertThat(items).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog
                        .ITEM_COUNT);
        assertThat(items.stream().map(item -> item.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(items).allSatisfy(item -> {
            assertThat(item.status()).isEqualTo("passed");
            assertThat(item.ownershipCode()).startsWith("OWNERSHIP_");
            assertThat(item.blockedReason()).isNotBlank();
            assertThat(item.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
        assertThat(items.get(0).code()).contains("REQUEST_ID");
        assertThat(items.get(24).code()).contains("CLOSEOUT_BOUNDARY");
    }
}
