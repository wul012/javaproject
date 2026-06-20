package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

final class OpsShardReadinessSignedApprovalDraftProfileSectionRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1506";
  static final String SOURCE_NODE_PROFILE_RENDERER_VERSION = "Node v1506";
  static final String REGISTRY_STATE =
      "signed-approval-draft-profile-sections-extracted-with-route-output-stable";
  static final int EXPECTED_MODULE_COUNT = 8;
  static final int EXPECTED_SOURCE_ROUTE_COUNT = 5;
  static final int EXPECTED_SECTION_COUNT = 5;
  static final int EXPECTED_FIELD_ENTRY_COUNT = 30;
  static final int EXPECTED_ROUTE_FIELD_LOCK_COUNT = 5;
  static final int EXPECTED_LOCKED_ROUTE_FIELD_COUNT = 25;
  static final int EXPECTED_GATE_COUNT = 46;

  private OpsShardReadinessSignedApprovalDraftProfileSectionRegistrySupport() {}

  static List<String> gates() {
    return IntStream.rangeClosed(1, EXPECTED_GATE_COUNT)
        .mapToObj(
            index -> "signed-approval-draft-profile-section-registry-no-runtime-gate-" + index)
        .toList();
  }

  static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry> modules,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource>
          sources,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection>
          sections,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry>
          fieldEntries,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RenderedSection>
          renderedSections,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock>
          routeFieldLocks,
      List<String> gates,
      List<String> additionalChecks) {
    var moduleCopy = List.copyOf(modules);
    var sourceCopy = List.copyOf(sources);
    var sectionCopy = List.copyOf(sections);
    var fieldCopy = List.copyOf(fieldEntries);
    var renderedCopy = List.copyOf(renderedSections);
    var lockCopy = List.copyOf(routeFieldLocks);
    var gateCopy = List.copyOf(gates);
    int lockedRouteFieldCount =
        lockCopy.stream()
            .mapToInt(
                OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock
                    ::lockedFieldCount)
            .sum();
    List<String> checks = new ArrayList<>();
    checks.add("signed-approval-draft-profile-section-registry-source-plan-" + SOURCE_PLAN);
    checks.add(
        "signed-approval-draft-profile-section-registry-source-node-"
            + SOURCE_NODE_PROFILE_RENDERER_VERSION);
    checks.add("signed-approval-draft-profile-section-registry-module-count-" + moduleCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-source-route-count-" + sourceCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-section-count-" + sectionCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-rendered-section-count-"
            + renderedCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-field-entry-count-" + fieldCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-route-field-lock-count-" + lockCopy.size());
    checks.add(
        "signed-approval-draft-profile-section-registry-locked-route-field-count-"
            + lockedRouteFieldCount);
    checks.add("signed-approval-draft-profile-section-registry-gate-count-" + gateCopy.size());
    checks.add("signed-approval-draft-profile-section-registry-zero-draft-artifacts");
    checks.add("signed-approval-draft-profile-section-registry-zero-signed-approvals");
    checks.add("signed-approval-draft-profile-section-registry-zero-runtime-payloads");
    checks.add("signed-approval-draft-profile-section-registry-zero-write-operations");
    checks.add("signed-approval-draft-profile-section-registry-zero-sibling-mutations");
    checks.add("signed-approval-draft-profile-section-registry-draft-materialization-disabled");
    checks.add("signed-approval-draft-profile-section-registry-signature-capture-disabled");
    checks.add("signed-approval-draft-profile-section-registry-approval-grant-disabled");
    checks.add("signed-approval-draft-profile-section-registry-value-import-disabled");
    checks.add("signed-approval-draft-profile-section-registry-runtime-payload-disabled");
    checks.add("signed-approval-draft-profile-section-registry-write-disabled");
    checks.add("signed-approval-draft-profile-section-registry-sibling-mutation-disabled");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        SOURCE_NODE_PROFILE_RENDERER_VERSION,
        REGISTRY_STATE,
        endpoint,
        profile,
        moduleCopy.size(),
        sourceCopy.size(),
        sectionCopy.size(),
        renderedCopy.size(),
        fieldCopy.size(),
        lockCopy.size(),
        lockedRouteFieldCount,
        gateCopy.size(),
        0,
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
        sectionCopy,
        fieldCopy,
        renderedCopy,
        lockCopy,
        gateCopy,
        List.copyOf(checks),
        isComplete(
                moduleCopy,
                sourceCopy,
                sectionCopy,
                fieldCopy,
                renderedCopy,
                lockCopy,
                lockedRouteFieldCount,
                gateCopy)
            ? "passed"
            : "blocked");
  }

  private static boolean isComplete(
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.ModuleEntry> modules,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftSectionSource>
          sources,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection>
          sections,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.FieldEntry>
          fieldEntries,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RenderedSection>
          renderedSections,
      List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock>
          routeFieldLocks,
      int lockedRouteFieldCount,
      List<String> gates) {
    return modules.size() == EXPECTED_MODULE_COUNT
        && sources.size() == EXPECTED_SOURCE_ROUTE_COUNT
        && sections.size() == EXPECTED_SECTION_COUNT
        && fieldEntries.size() == EXPECTED_FIELD_ENTRY_COUNT
        && renderedSections.size() == EXPECTED_SECTION_COUNT
        && routeFieldLocks.size() == EXPECTED_ROUTE_FIELD_LOCK_COUNT
        && lockedRouteFieldCount == EXPECTED_LOCKED_ROUTE_FIELD_COUNT
        && gates.size() == EXPECTED_GATE_COUNT;
  }
}
