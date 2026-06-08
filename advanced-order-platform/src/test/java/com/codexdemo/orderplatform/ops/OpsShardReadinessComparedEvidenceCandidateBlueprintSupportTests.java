package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateBlueprintSupportTests {

    @Test
    void buildsReadOnlyCandidateBlueprintWithoutRealCandidateImport() {
        var response = OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.response(
                "Java v1055",
                "/ops/shard-readiness/test",
                "test-profile",
                OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog.sourceSections(),
                OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.sourceBlockers(),
                List.of("unit-extra-check"));

        assertThat(response.sourcePlan()).isEqualTo("Node v1361");
        assertThat(response.realCandidateState()).isEqualTo("absent");
        assertThat(response.readyForRealCandidateIntake()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.checks()).contains("unit-extra-check",
                "compared-evidence-candidate-blueprint-source-java-Java v1054");
    }
}
