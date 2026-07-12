package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupBoundaryMatrixResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupBoundaryMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupOperatorRunbookResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupOperatorRunbookService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReadOnlyGateResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupReadOnlyGateService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupGovernanceController {

  private final OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService;

  private final OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService;

  private final OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService;

  private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

  private final OpsShardReadinessRouteCleanupRegressionGuardService regressionGuardService;

  private final OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService;

  public OpsShardReadinessRouteCleanupGovernanceController(
      OpsShardReadinessRouteCleanupBoundaryMatrixService boundaryMatrixService,
      OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService,
      OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService,
      OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService,
      OpsShardReadinessRouteCleanupRegressionGuardService regressionGuardService,
      OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService) {
    this.boundaryMatrixService = boundaryMatrixService;
    this.operatorRunbookService = operatorRunbookService;
    this.readOnlyGateService = readOnlyGateService;
    this.ciEvidenceService = ciEvidenceService;
    this.regressionGuardService = regressionGuardService;
    this.policyGuardService = policyGuardService;
  }

  @GetMapping(RouteCleanupRoutes.BOUNDARY_MATRIX)
  public OpsShardReadinessRouteCleanupBoundaryMatrixResponse boundaryMatrix() {
    return boundaryMatrixService.matrix();
  }

  @GetMapping(RouteCleanupRoutes.OPERATOR_RUNBOOK)
  public OpsShardReadinessRouteCleanupOperatorRunbookResponse operatorRunbook() {
    return operatorRunbookService.runbook();
  }

  @GetMapping(RouteCleanupRoutes.READ_ONLY_GATE)
  public OpsShardReadinessRouteCleanupReadOnlyGateResponse readOnlyGate() {
    return readOnlyGateService.gate();
  }

  @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CI_EVIDENCE)
  public OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence() {
    return ciEvidenceService.evidence();
  }

  @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REGRESSION_GUARD)
  public OpsShardReadinessRouteCleanupRegressionGuardResponse regressionGuard() {
    return regressionGuardService.guard();
  }

  @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POLICY_GUARD)
  public OpsShardReadinessRouteCleanupPolicyGuardResponse policyGuard() {
    return policyGuardService.guard();
  }
}
