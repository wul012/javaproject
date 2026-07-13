package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentHandoffController {

  private final OpsShardReadinessCandidateDocumentHandoffService service;

  public OpsShardReadinessCandidateDocumentHandoffController(
      OpsShardReadinessCandidateDocumentHandoffService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF)
  public OpsShardReadinessCandidateDocumentHandoffResponse handoff() {
    return service.handoff();
  }
}
