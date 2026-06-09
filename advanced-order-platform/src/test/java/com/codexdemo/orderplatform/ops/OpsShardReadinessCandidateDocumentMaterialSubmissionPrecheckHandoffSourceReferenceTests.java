package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSourceReferenceTests {

    @Test
    void sourceLineageReferencesRemainHumanAuditable() {
        var lineage = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSourceCatalog.sourceLineage(
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport.sourcePrecheck());

        assertThat(lineage.get(0).reference())
                .endsWith("v1456-controlled-read-only-shard-preview-candidate-document-material-"
                        + "submission-precheck-roadmap.md");
        assertThat(lineage.get(1).source()).isEqualTo("Java v1162");
        assertThat(lineage.get(1).reference())
                .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck");
        assertThat(lineage.get(5).source()).isEqualTo("8/41");
    }
}
