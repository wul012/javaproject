package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffController {

  private final OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService service;

  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffController(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF)
  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse handoff() {
    return service.handoff();
  }
}
