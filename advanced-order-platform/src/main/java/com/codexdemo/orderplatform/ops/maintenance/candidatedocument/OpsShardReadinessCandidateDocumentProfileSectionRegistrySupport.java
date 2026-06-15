package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentProfileSectionRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1481";
  static final String SOURCE_NODE_PROFILE_RENDERER_VERSION = "Node v1481";
  static final String REGISTRY_STATE =
      "candidate-document-profile-sections-extracted-with-route-output-stable";
  static final int EXPECTED_MODULE_COUNT = 5;
  static final int EXPECTED_SOURCE_ROUTE_COUNT = 5;
  static final int EXPECTED_SECTION_COUNT = 5;
  static final int EXPECTED_FIELD_ENTRY_COUNT = 25;
  static final int EXPECTED_ROUTE_FIELD_LOCK_COUNT = 5;
  static final int EXPECTED_LOCKED_ROUTE_FIELD_COUNT = 15;
  static final int EXPECTED_GATE_COUNT = 43;

  private OpsShardReadinessCandidateDocumentProfileSectionRegistrySupport() {}

  static OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry> modules,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource> sources,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection>
          sections,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry>
          fieldEntries,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection>
          renderedSections,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock>
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
                OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock
                    ::lockedFieldCount)
            .sum();
    List<String> checks = new ArrayList<>();
    checks.add("candidate-document-profile-section-registry-source-plan-" + SOURCE_PLAN);
    checks.add(
        "candidate-document-profile-section-registry-source-node-"
            + SOURCE_NODE_PROFILE_RENDERER_VERSION);
    checks.add("candidate-document-profile-section-registry-module-count-" + moduleCopy.size());
    checks.add(
        "candidate-document-profile-section-registry-source-route-count-" + sourceCopy.size());
    checks.add("candidate-document-profile-section-registry-section-count-" + sectionCopy.size());
    checks.add(
        "candidate-document-profile-section-registry-rendered-section-count-"
            + renderedCopy.size());
    checks.add("candidate-document-profile-section-registry-field-entry-count-" + fieldCopy.size());
    checks.add(
        "candidate-document-profile-section-registry-route-field-lock-count-" + lockCopy.size());
    checks.add(
        "candidate-document-profile-section-registry-locked-route-field-count-"
            + lockedRouteFieldCount);
    checks.add("candidate-document-profile-section-registry-gate-count-" + gateCopy.size());
    checks.add("candidate-document-profile-section-registry-zero-documents");
    checks.add("candidate-document-profile-section-registry-zero-payloads");
    checks.add("candidate-document-profile-section-registry-material-submission-disabled");
    checks.add("candidate-document-profile-section-registry-import-disabled");
    checks.add("candidate-document-profile-section-registry-evaluation-disabled");
    checks.add("candidate-document-profile-section-registry-approval-disabled");
    checks.add("candidate-document-profile-section-registry-signature-capture-disabled");
    checks.add("candidate-document-profile-section-registry-runtime-disabled");
    checks.add("candidate-document-profile-section-registry-write-disabled");
    checks.add("candidate-document-profile-section-registry-sibling-mutation-disabled");
    checks.addAll(additionalChecks);

    return new OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse(
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
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ModuleEntry> modules,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.SectionSource> sources,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection>
          sections,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.FieldEntry>
          fieldEntries,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection>
          renderedSections,
      List<OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RouteFieldLock>
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
