package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService;
import com.codexdemo.orderplatform.ops.maintenance.approvalpreflight.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController {

  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
      valueRejectionService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService
      zeroValueLedgerService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService
      cleanupReceiptService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
      importFirewallService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService
      digestBlueprintService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService
      archivePlanService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService
      closeoutService;

  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
          valueRejectionService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService
          zeroValueLedgerService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService
          cleanupReceiptService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService
          importFirewallService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService
          digestBlueprintService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService
          archivePlanService,
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService
          closeoutService) {
    this.valueRejectionService = valueRejectionService;
    this.zeroValueLedgerService = zeroValueLedgerService;
    this.cleanupReceiptService = cleanupReceiptService;
    this.importFirewallService = importFirewallService;
    this.digestBlueprintService = digestBlueprintService;
    this.archivePlanService = archivePlanService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse valueRejection() {
    return valueRejectionService.rejection();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ZERO_VALUE_LEDGER)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse zeroValueLedger() {
    return zeroValueLedgerService.ledger();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLEANUP_RECEIPT)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse cleanupReceipt() {
    return cleanupReceiptService.receipt();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IMPORT_FIREWALL)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse importFirewall() {
    return importFirewallService.firewall();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_DIGEST_BLUEPRINT)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse digestBlueprint() {
    return digestBlueprintService.blueprint();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ARCHIVE_PLAN)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLOSEOUT)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse closeout() {
    return closeoutService.closeout();
  }
}
