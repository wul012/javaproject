package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import java.util.List;

public record OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    boolean readyForDraftProfileSectionHandoff,
    String sourcePlan,
    String sourceRegistryVersion,
    String sourceRegistryEndpoint,
    String sourceRegistryProfile,
    String handoffState,
    String endpoint,
    String profile,
    int moduleCount,
    int sourceCount,
    int sectionHandoffCount,
    int routeContractCount,
    int boundaryDecisionCount,
    int renderedHandoffCount,
    int gateCount,
    int transferredSectionCount,
    int transferredRouteFieldLockCount,
    int transferredLockedRouteFieldCount,
    int draftArtifactCount,
    int signedApprovalCount,
    int runtimePayloadCount,
    int writeOperationCount,
    boolean draftArtifactMaterializationAllowed,
    boolean signedApprovalCaptureAllowed,
    boolean approvalGrantAllowed,
    boolean valueImportAllowed,
    boolean runtimePayloadAllowed,
    boolean writeAllowed,
    boolean siblingMutationAllowed,
    List<ModuleEntry> modules,
    List<HandoffSource> sources,
    List<SectionHandoff> sectionHandoffs,
    List<RouteContract> routeContracts,
    List<BoundaryDecision> boundaryDecisions,
    List<RenderedHandoff> renderedHandoffs,
    List<String> gates,
    List<String> checks,
    String status) {
  public record ModuleEntry(int order, String code, String responsibility, String status) {}

  public record HandoffSource(
      int order,
      String code,
      String sourceVersion,
      String endpoint,
      String profile,
      String sourceState,
      int transferredCount,
      String status) {}

  public record SectionHandoff(
      int order,
      String sectionCode,
      String heading,
      String javaVersion,
      String nodeVersionMarker,
      String endpoint,
      String profile,
      int routeFieldCount,
      String handoffAction,
      String consumerBoundary,
      String status) {}

  public record RouteContract(
      String sectionCode,
      String endpoint,
      String profile,
      String javaVersion,
      String nodeVersionMarker,
      int lockedFieldCount,
      String consumerRule,
      String status) {}

  public record BoundaryDecision(
      String code, String boundary, String decision, String evidence, String status) {}

  public record RenderedHandoff(
      int order, String sectionCode, String markdownHeading, String markdownBody, String status) {}
}
