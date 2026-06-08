package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentRequestPackageController {

    private final OpsShardReadinessCandidateDocumentRequestPackageService service;

    public OpsShardReadinessCandidateDocumentRequestPackageController(
            OpsShardReadinessCandidateDocumentRequestPackageService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE)
    public OpsShardReadinessCandidateDocumentRequestPackageResponse packageCatalog() {
        return service.packageCatalog();
    }
}
