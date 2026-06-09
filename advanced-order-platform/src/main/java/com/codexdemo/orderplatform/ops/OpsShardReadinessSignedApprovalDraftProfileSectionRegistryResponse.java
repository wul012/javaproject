package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean readyForDraftProfileSectionRegistry,
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
        int draftArtifactCount,
        int signedApprovalCount,
        int runtimePayloadCount,
        int writeOperationCount,
        int siblingMutationCount,
        boolean draftArtifactMaterializationAllowed,
        boolean signedApprovalCaptureAllowed,
        boolean approvalGrantAllowed,
        boolean valueImportAllowed,
        boolean runtimePayloadAllowed,
        boolean writeAllowed,
        boolean siblingMutationAllowed,
        List<ModuleEntry> modules,
        List<DraftSectionSource> sources,
        List<DraftProfileSection> sections,
        List<FieldEntry> fieldEntries,
        List<RenderedSection> renderedSections,
        List<RouteFieldLock> routeFieldLocks,
        List<String> gates,
        List<String> checks,
        String status
) {
    public record ModuleEntry(
            int order,
            String code,
            String responsibility,
            String status
    ) {
    }

    public record DraftSectionSource(
            int order,
            String code,
            String javaVersion,
            String nodeVersionMarker,
            String endpoint,
            String profile,
            int sourceGateCount,
            String sourceStatus,
            String status
    ) {
    }

    public record DraftProfileSection(
            int order,
            String code,
            String heading,
            String javaVersion,
            String nodeVersionMarker,
            String endpoint,
            String profile,
            int sourceGateCount,
            String sourceStatus,
            int fieldEntryCount,
            String rendererOwner,
            String status
    ) {
    }

    public record FieldEntry(
            int order,
            String sectionCode,
            String fieldName,
            String fieldValue,
            boolean routeFacing,
            String status
    ) {
    }

    public record RenderedSection(
            int order,
            String sectionCode,
            String markdownHeading,
            String markdownBody,
            String status
    ) {
    }

    public record RouteFieldLock(
            String sectionCode,
            String endpoint,
            String profile,
            String javaVersion,
            String nodeVersionMarker,
            int lockedFieldCount,
            String enforcement,
            String status
    ) {
    }
}
