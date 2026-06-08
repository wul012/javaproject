package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceAcceptanceService sourceAcceptanceService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService submissionComparisonService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeIdentityDigestSignatureService identityDigestSignatureService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceCloseoutService assuranceCloseoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceAcceptanceService sourceAcceptanceService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService submissionComparisonService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeIdentityDigestSignatureService identityDigestSignatureService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceCloseoutService assuranceCloseoutService
    ) {
        this.catalogService = catalogService;
        this.sourceAcceptanceService = sourceAcceptanceService;
        this.submissionComparisonService = submissionComparisonService;
        this.identityDigestSignatureService = identityDigestSignatureService;
        this.assuranceCloseoutService = assuranceCloseoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ACCEPTANCE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    sourceAcceptance() {
        return sourceAcceptanceService.sourceAcceptance();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_SUBMISSION_COMPARISON)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    submissionComparison() {
        return submissionComparisonService.submissionComparison();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_IDENTITY_DIGEST_SIGNATURE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    identityDigestSignature() {
        return identityDigestSignatureService.identityDigestSignature();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_ASSURANCE_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
    assuranceCloseout() {
        return assuranceCloseoutService.assuranceCloseout();
    }
}

