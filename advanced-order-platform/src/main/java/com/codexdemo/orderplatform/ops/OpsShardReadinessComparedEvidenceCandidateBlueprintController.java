package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessComparedEvidenceCandidateBlueprintController {

    private final OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService catalogService;
    private final OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService sourceService;
    private final OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService comparisonService;
    private final OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService policyService;
    private final OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService closeoutService;

    public OpsShardReadinessComparedEvidenceCandidateBlueprintController(
            OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService catalogService,
            OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService sourceService,
            OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService comparisonService,
            OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService policyService,
            OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService closeoutService
    ) {
        this.catalogService = catalogService;
        this.sourceService = sourceService;
        this.comparisonService = comparisonService;
        this.policyService = policyService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse source() {
        return sourceService.source();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_COMPARISON)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse comparison() {
        return comparisonService.comparison();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_POLICY)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse policy() {
        return policyService.policy();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT)
    public OpsShardReadinessComparedEvidenceCandidateBlueprintResponse closeout() {
        return closeoutService.closeout();
    }
}
