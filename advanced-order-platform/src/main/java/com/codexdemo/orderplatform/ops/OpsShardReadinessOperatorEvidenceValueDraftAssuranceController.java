package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService blockedReasonLedgerService;
    private final OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService digestBlueprintService;
    private final OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService routeProfileSummaryService;
    private final OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService archivePlanService;

    public OpsShardReadinessOperatorEvidenceValueDraftAssuranceController(
            OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService blockedReasonLedgerService,
            OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService digestBlueprintService,
            OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService routeProfileSummaryService,
            OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService archivePlanService
    ) {
        this.blockedReasonLedgerService = blockedReasonLedgerService;
        this.digestBlueprintService = digestBlueprintService;
        this.routeProfileSummaryService = routeProfileSummaryService;
        this.archivePlanService = archivePlanService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse blockedReasonLedger() {
        return blockedReasonLedgerService.ledger();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse digestBlueprint() {
        return digestBlueprintService.blueprint();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse routeProfileSummary() {
        return routeProfileSummaryService.summary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse archivePlan() {
        return archivePlanService.plan();
    }
}
