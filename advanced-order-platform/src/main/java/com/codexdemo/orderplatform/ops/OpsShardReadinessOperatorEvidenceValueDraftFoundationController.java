package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftFoundationController {

  private final OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService;
  private final OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService;
  private final OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService
      valueBoundaryService;
  private final OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService
      instructionSetService;
  private final OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService
      safetyGateMatrixService;
  private final OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService
      sourceMappingRegistryService;

  public OpsShardReadinessOperatorEvidenceValueDraftFoundationController(
      OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService,
      OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService,
      OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService valueBoundaryService,
      OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService instructionSetService,
      OpsShardReadinessOperatorEvidenceValueDraftSafetyGateMatrixService safetyGateMatrixService,
      OpsShardReadinessOperatorEvidenceValueDraftSourceMappingRegistryService
          sourceMappingRegistryService) {
    this.catalogService = catalogService;
    this.slotTemplateService = slotTemplateService;
    this.valueBoundaryService = valueBoundaryService;
    this.instructionSetService = instructionSetService;
    this.safetyGateMatrixService = safetyGateMatrixService;
    this.sourceMappingRegistryService = sourceMappingRegistryService;
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse slotTemplate() {
    return slotTemplateService.template();
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse valueBoundary() {
    return valueBoundaryService.boundary();
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse instructionSet() {
    return instructionSetService.instructions();
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SAFETY_GATE_MATRIX)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse safetyGateMatrix() {
    return safetyGateMatrixService.matrix();
  }

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SOURCE_MAPPING_REGISTRY)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse sourceMappingRegistry() {
    return sourceMappingRegistryService.registry();
  }
}
