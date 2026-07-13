package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
      digestChainService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
      operatorWindowService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
      signatureStatementService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService
          digestChainService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService
          operatorWindowService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService
          signatureStatementService) {
    this.catalogService = catalogService;
    this.digestChainService = digestChainService;
    this.operatorWindowService = operatorWindowService;
    this.signatureStatementService = signatureStatementService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_DIGEST_CHAIN)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      digestChain() {
    return digestChainService.digestChain();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_OPERATOR_WINDOW)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      operatorWindow() {
    return operatorWindowService.operatorWindow();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_STATEMENT)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      signatureStatement() {
    return signatureStatementService.signatureStatement();
  }
}
