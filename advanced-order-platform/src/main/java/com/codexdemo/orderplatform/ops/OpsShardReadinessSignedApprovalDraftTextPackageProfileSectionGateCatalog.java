package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionGateCatalog {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionGateCatalog() {
    }

    static List<String> gates() {
        return IntStream.rangeClosed(
                        1,
                        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistrySupport
                                .EXPECTED_GATE_COUNT)
                .mapToObj(index -> "signed-approval-draft-text-package-profile-section-registry-no-runtime-gate-"
                        + index)
                .toList();
    }
}
