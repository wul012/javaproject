package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionSourceCatalog {

    private OpsShardReadinessCandidateDocumentProfileSectionSourceCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource> sources(
            OpsShardReadinessCandidateDocumentRequestPackageResponse requestPackage,
            OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse submissionPrecheck,
            OpsShardReadinessCandidateDocumentIntakePacketResponse intakePacket,
            OpsShardReadinessCandidateDocumentMaterialRequestResponse materialRequest,
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse materialSubmissionPrecheck
    ) {
        return List.of(
                source(1, "candidate-document-request-package",
                        requestPackage.version(), requestPackage.endpoint(),
                        requestPackage.profile(), requestPackage.status()),
                source(2, "candidate-document-submission-precheck",
                        submissionPrecheck.version(), submissionPrecheck.endpoint(),
                        submissionPrecheck.profile(), submissionPrecheck.status()),
                source(3, "candidate-document-intake-packet",
                        intakePacket.version(), intakePacket.endpoint(),
                        intakePacket.profile(), intakePacket.status()),
                source(4, "candidate-document-material-request",
                        materialRequest.version(), materialRequest.endpoint(),
                        materialRequest.profile(), materialRequest.status()),
                source(5, "candidate-document-material-submission-precheck",
                        materialSubmissionPrecheck.version(), materialSubmissionPrecheck.endpoint(),
                        materialSubmissionPrecheck.profile(), materialSubmissionPrecheck.status())
        );
    }

    private static OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource source(
            int order,
            String code,
            String sourceVersion,
            String endpoint,
            String profile,
            String sourceStatus
    ) {
        return new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource(
                order,
                code,
                sourceVersion,
                endpoint,
                profile,
                sourceStatus,
                "passed"
        );
    }
}
