package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1506";
  static final String HANDOFF_STATE =
      "signed-approval-draft-profile-section-registry-handoff-ready";
  static final int EXPECTED_MODULE_COUNT = 8;
  static final int EXPECTED_SOURCE_COUNT = 1;
  static final int EXPECTED_SECTION_HANDOFF_COUNT = 5;
  static final int EXPECTED_ROUTE_CONTRACT_COUNT = 5;
  static final int EXPECTED_BOUNDARY_DECISION_COUNT = 7;
  static final int EXPECTED_RENDERED_HANDOFF_COUNT = 5;
  static final int EXPECTED_GATE_COUNT = 52;
  static final int EXPECTED_TRANSFERRED_ROUTE_FIELD_LOCK_COUNT = 5;
  static final int EXPECTED_TRANSFERRED_LOCKED_ROUTE_FIELD_COUNT = 25;

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSupport() {}

  static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse response(
      String version,
      String endpoint,
      String profile,
      OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry> modules,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.HandoffSource> sources,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff>
          sectionHandoffs,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract>
          routeContracts,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.BoundaryDecision>
          boundaryDecisions,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff>
          renderedHandoffs,
      List<String> gates,
      List<String> additionalChecks) {
    var moduleCopy = List.copyOf(modules);
    var sourceCopy = List.copyOf(sources);
    var handoffCopy = List.copyOf(sectionHandoffs);
    var contractCopy = List.copyOf(routeContracts);
    var decisionCopy = List.copyOf(boundaryDecisions);
    var renderedCopy = List.copyOf(renderedHandoffs);
    var gateCopy = List.copyOf(gates);
    int transferredLockedRouteFieldCount =
        contractCopy.stream()
            .mapToInt(
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract
                    ::lockedFieldCount)
            .sum();
    List<String> checks = new ArrayList<>();
    checks.add("signed-approval-draft-profile-section-handoff-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-draft-profile-section-handoff-source-registry-version-"
            + registry.version());
    checks.add("signed-approval-draft-profile-section-handoff-module-count-" + moduleCopy.size());
    checks.add("signed-approval-draft-profile-section-handoff-source-count-" + sourceCopy.size());
    checks.add("signed-approval-draft-profile-section-handoff-section-count-" + handoffCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-handoff-route-contract-count-"
            + contractCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-handoff-boundary-decision-count-"
            + decisionCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-handoff-rendered-count-" + renderedCopy.size());
    checks.add("signed-approval-draft-profile-section-handoff-gate-count-" + gateCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-handoff-transferred-route-lock-count-"
            + contractCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-handoff-transferred-locked-field-count-"
            + transferredLockedRouteFieldCount);
    checks.add("signed-approval-draft-profile-section-handoff-zero-draft-artifacts");
    checks.add("signed-approval-draft-profile-section-handoff-zero-signed-approvals");
    checks.add("signed-approval-draft-profile-section-handoff-zero-runtime-payloads");
    checks.add("signed-approval-draft-profile-section-handoff-zero-write-operations");
    checks.add("signed-approval-draft-profile-section-handoff-draft-materialization-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-signature-capture-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-approval-grant-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-value-import-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-runtime-payload-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-write-disabled");
    checks.add("signed-approval-draft-profile-section-handoff-sibling-mutation-disabled");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        registry.version(),
        registry.endpoint(),
        registry.profile(),
        HANDOFF_STATE,
        endpoint,
        profile,
        moduleCopy.size(),
        sourceCopy.size(),
        handoffCopy.size(),
        contractCopy.size(),
        decisionCopy.size(),
        renderedCopy.size(),
        gateCopy.size(),
        registry.sectionCount(),
        registry.routeFieldLockCount(),
        transferredLockedRouteFieldCount,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        moduleCopy,
        sourceCopy,
        handoffCopy,
        contractCopy,
        decisionCopy,
        renderedCopy,
        gateCopy,
        List.copyOf(checks),
        isComplete(
                moduleCopy,
                sourceCopy,
                handoffCopy,
                contractCopy,
                decisionCopy,
                renderedCopy,
                gateCopy,
                registry,
                transferredLockedRouteFieldCount)
            ? "passed"
            : "blocked");
  }

  private static boolean isComplete(
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry> modules,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.HandoffSource> sources,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.SectionHandoff>
          handoffs,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract>
          contracts,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.BoundaryDecision>
          decisions,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RenderedHandoff>
          rendered,
      List<String> gates,
      OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry,
      int transferredLockedRouteFieldCount) {
    return modules.size() == EXPECTED_MODULE_COUNT
        && sources.size() == EXPECTED_SOURCE_COUNT
        && handoffs.size() == EXPECTED_SECTION_HANDOFF_COUNT
        && contracts.size() == EXPECTED_ROUTE_CONTRACT_COUNT
        && decisions.size() == EXPECTED_BOUNDARY_DECISION_COUNT
        && rendered.size() == EXPECTED_RENDERED_HANDOFF_COUNT
        && gates.size() == EXPECTED_GATE_COUNT
        && registry.sectionCount() == EXPECTED_SECTION_HANDOFF_COUNT
        && registry.routeFieldLockCount() == EXPECTED_TRANSFERRED_ROUTE_FIELD_LOCK_COUNT
        && transferredLockedRouteFieldCount == EXPECTED_TRANSFERRED_LOCKED_ROUTE_FIELD_COUNT;
  }
}
