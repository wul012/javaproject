package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService
            identitySignatureService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService
            timestampWindowService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
            redactionDigestService;

    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService
                    identitySignatureService,
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService
                    timestampWindowService,
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService
                    redactionDigestService
    ) {
        this.catalogService = catalogService;
        this.identitySignatureService = identitySignatureService;
        this.timestampWindowService = timestampWindowService;
        this.redactionDigestService = redactionDigestService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IDENTITY_SIGNATURE)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse identitySignature() {
        return identitySignatureService.signature();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_TIMESTAMP_WINDOW)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse timestampWindow() {
        return timestampWindowService.window();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_REDACTION_DIGEST)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse redactionDigest() {
        return redactionDigestService.digest();
    }
}
