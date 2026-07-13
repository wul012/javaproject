package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessCandidateDocumentProfileSectionRegistryController {

  private final OpsShardReadinessCandidateDocumentProfileSectionRegistryService service;

  public OpsShardReadinessCandidateDocumentProfileSectionRegistryController(
      OpsShardReadinessCandidateDocumentProfileSectionRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY)
  public OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse registry() {
    return service.registry();
  }
}
