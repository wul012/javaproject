package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessCandidateDocumentProfileSectionGateCatalog {

    private OpsShardReadinessCandidateDocumentProfileSectionGateCatalog() {
    }

    static List<String> gates() {
        return IntStream.rangeClosed(
                        1,
                        OpsShardReadinessCandidateDocumentProfileSectionRegistrySupport.EXPECTED_GATE_COUNT)
                .mapToObj(index -> "candidate-document-profile-section-registry-no-runtime-gate-" + index)
                .toList();
    }
}
