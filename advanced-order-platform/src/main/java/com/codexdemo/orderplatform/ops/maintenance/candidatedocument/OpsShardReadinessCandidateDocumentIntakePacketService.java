package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentIntakePacketService {

  static final String RESPONSE_VERSION = "Java v1142";
  static final String ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET;
  static final String PROFILE = "java-shard-readiness-candidate-document-intake-packet.v1";

  private final OpsShardReadinessCandidateDocumentSubmissionPrecheckService sourcePrecheckService;

  public OpsShardReadinessCandidateDocumentIntakePacketService(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckService sourcePrecheckService) {
    this.sourcePrecheckService = sourcePrecheckService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket() {
    var sourcePrecheck = sourcePrecheckService.precheck();
    var evidence = IntakeCatalog.from(sourcePrecheck);
    return OpsShardReadinessCandidateDocumentIntakePacketSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourcePrecheck,
        evidence.sourceLineage(),
        evidence.modules(),
        evidence.slots(),
        evidence.guards(),
        evidence.artifacts(),
        evidence.gates(),
        List.of("candidate-document-intake-packet-service-assembled-from-submission-precheck"));
  }
}
