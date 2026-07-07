package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffGateCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffGateCatalog() {}

  static List<String> gates() {
    return IntStream.rangeClosed(
            1, OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSupport.EXPECTED_GATE_COUNT)
        .mapToObj(index -> "signed-approval-draft-profile-section-handoff-no-runtime-gate-" + index)
        .toList();
  }
}
