package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffControllerTests {

  @Test
  void handoffRouteExposesReadOnlySignedApprovalDraftProfileSectionHandoff() {
    assertThat(
            OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths
                .SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF)
        .isEqualTo("/signed-approval-draft-profile-section-handoff");

    var response =
        new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffController(
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.service())
            .handoff();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/signed-approval-draft-profile-section-handoff");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-signed-approval-draft-profile-section-handoff.v1");
    assertThat(response.version()).isEqualTo("Java v1262");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
  }
}
