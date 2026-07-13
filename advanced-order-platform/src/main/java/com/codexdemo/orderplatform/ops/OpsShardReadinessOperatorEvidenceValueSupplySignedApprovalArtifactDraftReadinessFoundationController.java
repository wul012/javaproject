package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
      digestChainService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
      operatorWindowService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
      signatureStatementService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService
          digestChainService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService
          operatorWindowService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService
          signatureStatementService) {
    this.catalogService = catalogService;
    this.digestChainService = digestChainService;
    this.operatorWindowService = operatorWindowService;
    this.signatureStatementService = signatureStatementService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_DIGEST_CHAIN)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      digestChain() {
    return digestChainService.digestChain();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_OPERATOR_WINDOW)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      operatorWindow() {
    return operatorWindowService.operatorWindow();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_SIGNATURE_STATEMENT)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse
      signatureStatement() {
    return signatureStatementService.signatureStatement();
  }
}
