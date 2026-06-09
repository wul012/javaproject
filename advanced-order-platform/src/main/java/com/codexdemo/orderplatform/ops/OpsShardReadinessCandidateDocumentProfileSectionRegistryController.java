package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentProfileSectionRegistryController {

    private final OpsShardReadinessCandidateDocumentProfileSectionRegistryService service;

    public OpsShardReadinessCandidateDocumentProfileSectionRegistryController(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY)
    public OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse registry() {
        return service.registry();
    }
}
