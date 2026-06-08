package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateBlueprintServiceTests {

    @Test
    void exposesScopedCandidateBlueprintSlicesWithoutImports() {
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService().source(),
                "Java v1061", 3);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService().comparison(),
                "Java v1062", 3);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService().policy(),
                "Java v1063", 2);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService().closeout(),
                "Java v1064", 2);
    }

    private void assertSlice(
            OpsShardReadinessComparedEvidenceCandidateBlueprintResponse response,
            String version,
            int count
    ) {
        assertThat(response.version()).isEqualTo(version);
        assertThat(response.candidateSectionCount()).isEqualTo(count);
        assertThat(response.blockerCount()).isEqualTo(count);
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
    }
}
