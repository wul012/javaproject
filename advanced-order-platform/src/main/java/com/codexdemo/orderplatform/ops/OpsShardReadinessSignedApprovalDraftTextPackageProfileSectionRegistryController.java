package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController {

  private final OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService
      service;

  public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController(
      OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService service) {
    this.service = service;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRoutePaths
          .SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY)
  public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse registry() {
    return service.registry();
  }
}
