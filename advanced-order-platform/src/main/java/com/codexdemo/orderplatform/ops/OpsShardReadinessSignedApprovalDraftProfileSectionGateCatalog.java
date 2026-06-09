package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessSignedApprovalDraftProfileSectionGateCatalog {

    private OpsShardReadinessSignedApprovalDraftProfileSectionGateCatalog() {
    }

    static List<String> gates() {
        return IntStream.rangeClosed(
                        1,
                        OpsShardReadinessSignedApprovalDraftProfileSectionRegistrySupport.EXPECTED_GATE_COUNT)
                .mapToObj(index -> "signed-approval-draft-profile-section-registry-no-runtime-gate-" + index)
                .toList();
    }
}
