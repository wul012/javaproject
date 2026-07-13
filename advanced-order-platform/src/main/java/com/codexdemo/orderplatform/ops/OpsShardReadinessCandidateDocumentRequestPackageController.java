package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentRequestPackageController {

  private final OpsShardReadinessCandidateDocumentRequestPackageService service;

  public OpsShardReadinessCandidateDocumentRequestPackageController(
      OpsShardReadinessCandidateDocumentRequestPackageService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCandidateDocumentRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE)
  public OpsShardReadinessCandidateDocumentRequestPackageResponse packageCatalog() {
    return service.packageCatalog();
  }
}
