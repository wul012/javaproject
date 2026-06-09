package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentMaterialRequestService {

    static final String RESPONSE_VERSION = "Java v1146";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST;
    static final String PROFILE = "java-shard-readiness-candidate-document-material-request.v1";

    private final OpsShardReadinessCandidateDocumentIntakePacketService sourcePacketService;

    public OpsShardReadinessCandidateDocumentMaterialRequestService(
            OpsShardReadinessCandidateDocumentIntakePacketService sourcePacketService
    ) {
        this.sourcePacketService = sourcePacketService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest() {
        var sourcePacket = sourcePacketService.intakePacket();
        var requestItems = OpsShardReadinessCandidateDocumentMaterialRequestCatalog.requestItems(sourcePacket);
        return OpsShardReadinessCandidateDocumentMaterialRequestSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourcePacket,
                OpsShardReadinessCandidateDocumentMaterialRequestCatalog.modules(),
                requestItems,
                OpsShardReadinessCandidateDocumentMaterialRequestCatalog.acceptanceChecks(requestItems),
                OpsShardReadinessCandidateDocumentMaterialRequestCatalog.artifacts(),
                OpsShardReadinessCandidateDocumentMaterialRequestCatalog.gates(),
                List.of("candidate-document-material-request-service-assembled-from-intake-packet"));
    }
}
