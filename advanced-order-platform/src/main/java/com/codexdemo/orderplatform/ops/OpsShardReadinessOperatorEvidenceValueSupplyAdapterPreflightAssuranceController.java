package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService
            payloadFirewallService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService
            runtimeSubmissionLockService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService
            operatorRehearsalChecklistService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService
            digestBlueprintService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService archivePlanService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService closeoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService
                    payloadFirewallService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService
                    runtimeSubmissionLockService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService
                    operatorRehearsalChecklistService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService
                    digestBlueprintService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService archivePlanService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService closeoutService
    ) {
        this.payloadFirewallService = payloadFirewallService;
        this.runtimeSubmissionLockService = runtimeSubmissionLockService;
        this.operatorRehearsalChecklistService = operatorRehearsalChecklistService;
        this.digestBlueprintService = digestBlueprintService;
        this.archivePlanService = archivePlanService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PAYLOAD_FIREWALL)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse payloadFirewall() {
        return payloadFirewallService.firewall();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_RUNTIME_SUBMISSION_LOCK)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse runtimeSubmissionLock() {
        return runtimeSubmissionLockService.lock();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_OPERATOR_REHEARSAL_CHECKLIST)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse operatorRehearsalChecklist() {
        return operatorRehearsalChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_DIGEST_BLUEPRINT)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse digestBlueprint() {
        return digestBlueprintService.blueprint();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_ARCHIVE_PLAN)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse archivePlan() {
        return archivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse closeout() {
        return closeoutService.closeout();
    }
}
