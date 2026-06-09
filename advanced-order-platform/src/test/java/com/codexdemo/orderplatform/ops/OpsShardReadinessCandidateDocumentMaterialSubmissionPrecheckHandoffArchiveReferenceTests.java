package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveReferenceTests {

    @Test
    void archiveHandlesAreUniqueAndCheckpointAddressable() {
        var sourcePrecheck = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
                .sourcePrecheck();
        var archiveHandles = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveCatalog
                .archiveHandles(sourcePrecheck);

        assertThat(archiveHandles)
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle::code)
                .doesNotHaveDuplicates();
        assertThat(archiveHandles)
                .allSatisfy(handle -> {
                    assertThat(handle.code()).isEqualTo("archive-" + handle.checkpointCode());
                    assertThat(handle.reference()).endsWith(handle.checkpointCode() + ".json");
                    assertThat(handle.retention()).contains("read-only");
                });
    }
}
