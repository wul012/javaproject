package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessComparedPackageReviewController {

    private final OpsShardReadinessComparedPackageReviewCatalogService catalogService;
    private final OpsShardReadinessComparedPackageReviewSourceEvidenceService sourceEvidenceService;
    private final OpsShardReadinessComparedPackageReviewComparisonOutcomeService comparisonOutcomeService;
    private final OpsShardReadinessComparedPackageReviewIdentityDigestService identityDigestService;
    private final OpsShardReadinessComparedPackageReviewPolicyArchiveService policyArchiveService;
    private final OpsShardReadinessComparedPackageReviewHandoffCloseoutService handoffCloseoutService;

    public OpsShardReadinessComparedPackageReviewController(
            OpsShardReadinessComparedPackageReviewCatalogService catalogService,
            OpsShardReadinessComparedPackageReviewSourceEvidenceService sourceEvidenceService,
            OpsShardReadinessComparedPackageReviewComparisonOutcomeService comparisonOutcomeService,
            OpsShardReadinessComparedPackageReviewIdentityDigestService identityDigestService,
            OpsShardReadinessComparedPackageReviewPolicyArchiveService policyArchiveService,
            OpsShardReadinessComparedPackageReviewHandoffCloseoutService handoffCloseoutService
    ) {
        this.catalogService = catalogService;
        this.sourceEvidenceService = sourceEvidenceService;
        this.comparisonOutcomeService = comparisonOutcomeService;
        this.identityDigestService = identityDigestService;
        this.policyArchiveService = policyArchiveService;
        this.handoffCloseoutService = handoffCloseoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_CATALOG)
    public OpsShardReadinessComparedPackageReviewResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_SOURCE_EVIDENCE)
    public OpsShardReadinessComparedPackageReviewResponse sourceEvidence() {
        return sourceEvidenceService.sourceEvidence();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME)
    public OpsShardReadinessComparedPackageReviewResponse comparisonOutcome() {
        return comparisonOutcomeService.comparisonOutcome();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_IDENTITY_DIGEST)
    public OpsShardReadinessComparedPackageReviewResponse identityDigest() {
        return identityDigestService.identityDigest();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE)
    public OpsShardReadinessComparedPackageReviewResponse policyArchive() {
        return policyArchiveService.policyArchive();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT)
    public OpsShardReadinessComparedPackageReviewResponse handoffCloseout() {
        return handoffCloseoutService.handoffCloseout();
    }
}
