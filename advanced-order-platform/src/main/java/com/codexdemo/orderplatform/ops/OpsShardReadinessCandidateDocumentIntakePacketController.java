package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessCandidateDocumentIntakePacketController {

    private final OpsShardReadinessCandidateDocumentIntakePacketService service;

    public OpsShardReadinessCandidateDocumentIntakePacketController(
            OpsShardReadinessCandidateDocumentIntakePacketService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET)
    public OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket() {
        return service.intakePacket();
    }
}
