package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentHandoffController {

    private final OpsShardReadinessCandidateDocumentHandoffService service;

    public OpsShardReadinessCandidateDocumentHandoffController(
            OpsShardReadinessCandidateDocumentHandoffService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF)
    public OpsShardReadinessCandidateDocumentHandoffResponse handoff() {
        return service.handoff();
    }
}
