package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController {

  private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService
      compatibilityMatrixService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService
      redactionBoundaryService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService
      provenanceBindingService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService
      missingValueRejectionService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService
      sourceEvidenceSnapshotService;

  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService
          compatibilityMatrixService,
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService
          redactionBoundaryService,
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService
          provenanceBindingService,
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService
          missingValueRejectionService,
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService
          sourceEvidenceSnapshotService) {
    this.catalogService = catalogService;
    this.compatibilityMatrixService = compatibilityMatrixService;
    this.redactionBoundaryService = redactionBoundaryService;
    this.provenanceBindingService = provenanceBindingService;
    this.missingValueRejectionService = missingValueRejectionService;
    this.sourceEvidenceSnapshotService = sourceEvidenceSnapshotService;
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_COMPATIBILITY_MATRIX)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse
      compatibilityMatrix() {
    return compatibilityMatrixService.matrix();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_REDACTION_BOUNDARY)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse redactionBoundary() {
    return redactionBoundaryService.boundary();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PROVENANCE_BINDING)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse provenanceBinding() {
    return provenanceBindingService.binding();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_MISSING_VALUE_REJECTION)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse
      missingValueRejection() {
    return missingValueRejectionService.rejection();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_SOURCE_EVIDENCE_SNAPSHOT)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse
      sourceEvidenceSnapshot() {
    return sourceEvidenceSnapshotService.snapshot();
  }
}
