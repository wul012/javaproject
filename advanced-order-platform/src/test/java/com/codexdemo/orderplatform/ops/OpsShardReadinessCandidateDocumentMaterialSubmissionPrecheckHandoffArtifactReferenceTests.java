package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactReferenceTests {

    @Test
    void artifactReferencesPreserveSourceArchiveBoundary() {
        var sourcePrecheck = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
                .sourcePrecheck();
        var references = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactCatalog
                .artifactReferences(sourcePrecheck);

        assertThat(references)
                .allSatisfy(reference -> {
                    assertThat(reference.code()).startsWith("handoff-");
                    assertThat(reference.sourceReference()).startsWith("e/1162/");
                    assertThat(reference.archiveReference()).startsWith("e/1187/artifacts/");
                    assertThat(reference.purpose()).contains("archive reference");
                });
    }
}
