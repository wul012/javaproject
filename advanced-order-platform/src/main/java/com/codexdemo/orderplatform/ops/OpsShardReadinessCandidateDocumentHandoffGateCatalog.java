package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentHandoffGateCatalog {

    private OpsShardReadinessCandidateDocumentHandoffGateCatalog() {
    }

    static List<String> gates() {
        return IntStream.rangeClosed(1, OpsShardReadinessCandidateDocumentHandoffSupport.EXPECTED_GATE_COUNT)
                .mapToObj(index -> "candidate-document-handoff-read-only-gate-" + index)
                .toList();
    }
}
