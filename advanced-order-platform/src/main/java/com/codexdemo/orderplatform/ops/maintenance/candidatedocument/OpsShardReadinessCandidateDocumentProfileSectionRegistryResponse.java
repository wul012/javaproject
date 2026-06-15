package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import java.util.List;

public record OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForProfileSectionRegistry,
    String sourcePlan,
    String sourceNodeProfileRendererVersion,
    String registryState,
    String endpoint,
    String profile,
    int moduleCount,
    int sourceRouteCount,
    int sectionCount,
    int renderedSectionCount,
    int fieldEntryCount,
    int routeFieldLockCount,
    int lockedRouteFieldCount,
    int gateCount,
    int realDocumentCount,
    int syntheticDocumentCount,
    int stagedDocumentCount,
    int importedDocumentCount,
    int evaluatedDocumentCount,
    int acceptedDocumentCount,
    int rejectedDocumentCount,
    int payloadCount,
    boolean materialSubmissionAccepted,
    boolean importAllowed,
    boolean evaluationAllowed,
    boolean approvalGrantAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean runtimePayloadAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<ModuleEntry> modules,
    List<SectionSource> sources,
    List<ProfileSection> sections,
    List<FieldEntry> fieldEntries,
    List<RenderedSection> renderedSections,
    List<RouteFieldLock> routeFieldLocks,
    List<String> gates,
    List<String> checks,
    String status) {
  public record ModuleEntry(int order, String code, String responsibility, String status) {}

  public record SectionSource(
      int order,
      String code,
      String sourceVersion,
      String endpoint,
      String profile,
      String sourceStatus,
      String status) {}

  public record ProfileSection(
      int order,
      String code,
      String heading,
      String sourceVersion,
      String endpoint,
      String profile,
      int fieldEntryCount,
      String rendererOwner,
      String status) {}

  public record FieldEntry(
      int order,
      String sectionCode,
      String fieldName,
      String fieldValue,
      boolean routeFacing,
      String status) {}

  public record RenderedSection(
      int order, String sectionCode, String markdownHeading, String markdownBody, String status) {}

  public record RouteFieldLock(
      String sectionCode,
      String endpoint,
      String profile,
      String sourceVersion,
      int lockedFieldCount,
      String enforcement,
      String status) {}
}
