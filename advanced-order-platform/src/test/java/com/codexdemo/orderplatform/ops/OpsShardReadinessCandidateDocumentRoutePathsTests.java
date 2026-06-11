package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentRoutePathsTests {

    @Test
    void candidateDocumentAndSignedApprovalProfileRoutesDelegateToSplitOwner() {
        assertThat(Map.ofEntries(
                Map.entry(
                        OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE,
                        OpsShardReadinessCandidateDocumentRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_CANDIDATE_DOCUMENT_REQUEST_PACKAGE
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF,
                        OpsShardReadinessCandidateDocumentRoutePaths
                                .CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY,
                        OpsShardReadinessCandidateDocumentRoutePaths.CANDIDATE_DOCUMENT_PROFILE_SECTION_REGISTRY
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY,
                        OpsShardReadinessCandidateDocumentRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF,
                        OpsShardReadinessCandidateDocumentRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF
                ),
                Map.entry(
                        OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY,
                        OpsShardReadinessCandidateDocumentRoutePaths
                                .SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY
                )
        )).allSatisfy((legacy, split) -> assertThat(legacy).isEqualTo(split));
    }
}
