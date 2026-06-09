package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessCandidateDocumentSubmissionPrecheckService {

    static final String RESPONSE_VERSION = "Java v1117";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK;
    static final String PROFILE = "java-shard-readiness-candidate-document-submission-precheck.v1";

    private final OpsShardReadinessCandidateDocumentRequestPackageService requestPackageService;
    private final OpsShardReadinessCandidateDocumentHandoffService handoffService;

    public OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            OpsShardReadinessCandidateDocumentRequestPackageService requestPackageService,
            OpsShardReadinessCandidateDocumentHandoffService handoffService
    ) {
        this.requestPackageService = requestPackageService;
        this.handoffService = handoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse precheck() {
        var requestPackage = requestPackageService.packageCatalog();
        var handoff = handoffService.handoff();
        var checkpoints = OpsShardReadinessCandidateDocumentSubmissionCheckpointCatalog.checkpoints(
                requestPackage,
                handoff);
        return OpsShardReadinessCandidateDocumentSubmissionPrecheckSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                requestPackage,
                handoff,
                checkpoints,
                OpsShardReadinessCandidateDocumentSubmissionValidatorCatalog.validators(checkpoints),
                OpsShardReadinessCandidateDocumentSubmissionArtifactCatalog.artifacts(),
                OpsShardReadinessCandidateDocumentSubmissionArtifactCatalog.gates(),
                List.of("candidate-document-submission-precheck-service-assembled-from-request-package-and-handoff"));
    }
}
