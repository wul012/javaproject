package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentSubmissionPrecheckController {

  private final OpsShardReadinessCandidateDocumentSubmissionPrecheckService service;

  public OpsShardReadinessCandidateDocumentSubmissionPrecheckController(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK)
  public OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse precheck() {
    return service.precheck();
  }
}
