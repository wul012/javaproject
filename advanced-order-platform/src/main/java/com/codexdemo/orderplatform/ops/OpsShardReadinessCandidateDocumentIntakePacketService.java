package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentIntakePacketService {

    static final String RESPONSE_VERSION = "Java v1139";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET;
    static final String PROFILE = "java-shard-readiness-candidate-document-intake-packet.v1";

    private final OpsShardReadinessCandidateDocumentSubmissionPrecheckService sourcePrecheckService;

    public OpsShardReadinessCandidateDocumentIntakePacketService(
            OpsShardReadinessCandidateDocumentSubmissionPrecheckService sourcePrecheckService
    ) {
        this.sourcePrecheckService = sourcePrecheckService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket() {
        var sourcePrecheck = sourcePrecheckService.precheck();
        var slots = OpsShardReadinessCandidateDocumentIntakePacketSlotCatalog.slots(sourcePrecheck);
        return OpsShardReadinessCandidateDocumentIntakePacketSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourcePrecheck,
                OpsShardReadinessCandidateDocumentIntakePacketSourceCatalog.sourceLineage(sourcePrecheck),
                OpsShardReadinessCandidateDocumentIntakePacketModuleCatalog.modules(),
                slots,
                OpsShardReadinessCandidateDocumentIntakePacketGuardCatalog.guards(slots),
                OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog.artifacts(),
                OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog.gates(),
                List.of("candidate-document-intake-packet-service-assembled-from-submission-precheck"));
    }
}
