package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentSubmissionPrecheckController {

  private final OpsShardReadinessCandidateDocumentSubmissionPrecheckService service;

  public OpsShardReadinessCandidateDocumentSubmissionPrecheckController(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK)
  public OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse precheck() {
    return service.precheck();
  }
}
