package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import java.util.List;

public record OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForTextPackageProfileSectionRegistry,
    String sourcePlan,
    String sourceNodeTextPackageRendererVersion,
    String registryState,
    String endpoint,
    String profile,
    int moduleCount,
    int sourceRouteCount,
    int sectionCount,
    int submissionRendererSectionCount,
    int comparedEvidenceRendererSectionCount,
    int renderedSectionCount,
    int fieldEntryCount,
    int routeFieldLockCount,
    int lockedRouteFieldCount,
    int gateCount,
    int packageAcceptedCount,
    int signedApprovalCount,
    int runtimePayloadCount,
    int secretValueCount,
    int writeOperationCount,
    boolean packageAcceptanceAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean approvalGrantAllowed,
    boolean valueImportAllowed,
    boolean runtimePayloadAllowed,
    boolean secretValueAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<ModuleEntry> modules,
    List<TextPackageSectionSource> sources,
    List<TextPackageProfileSection> sections,
    List<FieldEntry> fieldEntries,
    List<RenderedSection> renderedSections,
    List<RouteFieldLock> routeFieldLocks,
    List<String> gates,
    List<String> checks,
    String status) {
  public record ModuleEntry(int order, String code, String responsibility, String status) {}

  public record TextPackageSectionSource(
      int order,
      String code,
      String javaVersion,
      String nodeVersionMarker,
      String rendererGroup,
      String endpoint,
      String profile,
      String sourceStatus,
      String status) {}

  public record TextPackageProfileSection(
      int order,
      String code,
      String heading,
      String javaVersion,
      String nodeVersionMarker,
      String rendererGroup,
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
      int order,
      String sectionCode,
      String rendererGroup,
      String markdownHeading,
      String markdownBody,
      String status) {}

  public record RouteFieldLock(
      String sectionCode,
      String endpoint,
      String profile,
      String javaVersion,
      String nodeVersionMarker,
      String rendererGroup,
      int lockedFieldCount,
      String enforcement,
      String status) {}
}
