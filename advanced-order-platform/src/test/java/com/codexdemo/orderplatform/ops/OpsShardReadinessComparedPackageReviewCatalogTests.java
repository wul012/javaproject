package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewCatalogTests {

    @Test
    void catalogListsTwelveReviewSlotsAndFailClosedGuards() {
        var response = new OpsShardReadinessComparedPackageReviewCatalogService().catalog();

        assertThat(response.version()).isEqualTo("Java v1034");
        assertThat(response.reviewSlotCount()).isEqualTo(12);
        assertThat(response.passedReviewSlotCount()).isEqualTo(12);
        assertThat(response.guardCount()).isEqualTo(12);
        assertThat(response.passedGuardCount()).isEqualTo(12);
        assertThat(response.reviewerGroupCount()).isEqualTo(5);
        assertThat(response.guards()).allSatisfy(guard -> {
            assertThat(guard.enforcement()).isEqualTo("fail-closed");
            assertThat(guard.rejectionCode()).startsWith("reject-review");
        });
    }

    @Test
    void reviewSlotCodesStayUnique() {
        var codes = OpsShardReadinessComparedPackageReviewSlotCatalog.allSlots().stream()
                .map(OpsShardReadinessComparedPackageReviewResponse.ReviewSlot::code)
                .toList();

        assertThat(codes).doesNotHaveDuplicates();
        assertThat(codes).contains("source-intake-catalog-consistency",
                "archive-closeout-review");
    }
}
