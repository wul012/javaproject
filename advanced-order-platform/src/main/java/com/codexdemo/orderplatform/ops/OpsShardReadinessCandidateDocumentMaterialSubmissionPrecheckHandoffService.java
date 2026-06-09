package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService {

    static final String RESPONSE_VERSION = "Java v1187";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF;
    static final String PROFILE =
            "java-shard-readiness-candidate-document-material-submission-precheck-handoff.v1";

    private final OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService sourcePrecheckService;

    public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService sourcePrecheckService
    ) {
        this.sourcePrecheckService = sourcePrecheckService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse handoff() {
        var sourcePrecheck = sourcePrecheckService.materialSubmissionPrecheck();
        return OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourcePrecheck,
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSourceCatalog.sourceLineage(
                        sourcePrecheck),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffModuleCatalog.modules(),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveCatalog.archiveHandles(
                        sourcePrecheck),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyCatalog.policyLocks(
                        sourcePrecheck),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactCatalog.artifactReferences(
                        sourcePrecheck),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerCatalog.consumerRules(
                        sourcePrecheck),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateCatalog.gates(),
                List.of("candidate-document-material-submission-precheck-handoff-service-assembled-from-java-v1162"));
    }
}
