package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateCatalog {

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateCatalog() {
    }

    static List<String> gates() {
        return IntStream.rangeClosed(
                        1,
                        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport.EXPECTED_GATE_COUNT)
                .mapToObj(index -> "candidate-document-material-submission-precheck-handoff-no-material-gate-"
                        + index)
                .toList();
    }
}
