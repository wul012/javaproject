package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService {

  static final String RESPONSE_VERSION = "Java v1187";
  static final String ENDPOINT =
      OpsShardReadinessCandidateDocumentRoutePaths.BASE_PATH
          + OpsShardReadinessCandidateDocumentRoutePaths
              .CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF;
  static final String PROFILE =
      "java-shard-readiness-candidate-document-material-submission-precheck-handoff.v1";

  private final OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
      sourcePrecheckService;

  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
      OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService sourcePrecheckService) {
    this.sourcePrecheckService = sourcePrecheckService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse handoff() {
    var sourcePrecheck = sourcePrecheckService.materialSubmissionPrecheck();
    var evidence = PrecheckHandoffCatalog.from(sourcePrecheck);
    return OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        sourcePrecheck,
        evidence.sourceLineage(),
        evidence.modules(),
        evidence.archiveHandles(),
        evidence.policyLocks(),
        evidence.artifactReferences(),
        evidence.consumerRules(),
        evidence.gates(),
        List.of(
            "candidate-document-material-submission-precheck-handoff-service-assembled-from-java-v1162"));
  }
}
