package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialRequestResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialRequestService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentMaterialRequestController {

  private final OpsShardReadinessCandidateDocumentMaterialRequestService service;

  public OpsShardReadinessCandidateDocumentMaterialRequestController(
      OpsShardReadinessCandidateDocumentMaterialRequestService service) {
    this.service = service;
  }

  @GetMapping(OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST)
  public OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest() {
    return service.materialRequest();
  }
}
