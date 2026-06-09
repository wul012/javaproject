package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentMaterialRequestController {

    private final OpsShardReadinessCandidateDocumentMaterialRequestService service;

    public OpsShardReadinessCandidateDocumentMaterialRequestController(
            OpsShardReadinessCandidateDocumentMaterialRequestService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST)
    public OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest() {
        return service.materialRequest();
    }
}
