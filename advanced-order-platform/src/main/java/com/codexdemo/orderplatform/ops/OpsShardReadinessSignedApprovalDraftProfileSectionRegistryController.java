package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController {

    private final OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService service;

    public OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY)
    public OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry() {
        return service.registry();
    }
}
