package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffController {

  private final OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService service;

  public OpsShardReadinessSignedApprovalDraftProfileSectionHandoffController(
      OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths
          .SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF)
  public OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse handoff() {
    return service.handoff();
  }
}
