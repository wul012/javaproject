package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController {

    private final OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService service;

    public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY)
    public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse registry() {
        return service.registry();
    }
}
