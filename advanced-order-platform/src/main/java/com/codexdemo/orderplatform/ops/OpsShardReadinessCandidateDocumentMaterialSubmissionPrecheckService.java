package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService {

    static final String RESPONSE_VERSION = "Java v1162";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK;
    static final String PROFILE =
            "java-shard-readiness-candidate-document-material-submission-precheck.v1";

    private final OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService;

    public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
            OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService
    ) {
        this.materialRequestService = materialRequestService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse materialSubmissionPrecheck() {
        var sourceRequest = materialRequestService.materialRequest();
        var checkpoints = OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.checkpoints(
                sourceRequest);
        return OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                sourceRequest,
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.modules(),
                checkpoints,
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.validators(checkpoints),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.artifacts(),
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.gates(),
                List.of("candidate-document-material-submission-precheck-service-assembled-from-material-request"));
    }
}
