package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewSupportTests {

    @Test
    void buildsReadOnlyReviewResponseWithoutExecutionOrAcceptance() {
        var response = OpsShardReadinessComparedPackageReviewSupport.response(
                "Java v1025",
                "/ops/shard-readiness/test",
                "test-profile",
                OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog.sourceEvidenceSlots(),
                OpsShardReadinessComparedPackageReviewGuardCatalog.sourceGuards(),
                OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.sourceGroups(),
                List.of("unit-extra-check"));

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForEvidenceAcceptance()).isFalse();
        assertThat(response.readyForReviewDecision()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.checks()).contains("unit-extra-check",
                "compared-package-review-source-java-Java v1024");
    }
}
