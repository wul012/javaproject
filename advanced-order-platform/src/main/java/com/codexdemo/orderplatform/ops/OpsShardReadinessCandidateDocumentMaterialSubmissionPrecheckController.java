package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckController {

  private final OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService service;

  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckController(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK)
  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
      materialSubmissionPrecheck() {
    return service.materialSubmissionPrecheck();
  }
}
