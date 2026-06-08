package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateIntakePreflightServiceTests {

    @Test
    void exposesScopedCandidateIntakeSlicesWithoutRuntimeOrWrites() {
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService().source(),
                "Java v1076", 3);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService().comparison(),
                "Java v1077", 3);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService().policy(),
                "Java v1078", 2);
        assertSlice(new OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService().closeout(),
                "Java v1079", 2);
    }

    private void assertSlice(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse response,
            String version,
            int count
    ) {
        assertThat(response.version()).isEqualTo(version);
        assertThat(response.intakeSlotCount()).isEqualTo(count);
        assertThat(response.guardCount()).isEqualTo(count);
        assertThat(response.realDocumentCount()).isZero();
        assertThat(response.writeAllowed()).isFalse();
    }
}
