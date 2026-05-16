package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ops")
public class OpsOverviewController {

    private final OpsOverviewService opsOverviewService;

    private final OpsEvidenceService opsEvidenceService;

    public OpsOverviewController(OpsOverviewService opsOverviewService, OpsEvidenceService opsEvidenceService) {
        this.opsOverviewService = opsOverviewService;
        this.opsEvidenceService = opsEvidenceService;
    }

    @GetMapping("/overview")
    public OpsOverviewResponse overview() {
        return opsOverviewService.overview();
    }

    @GetMapping("/evidence")
    public OpsEvidenceResponse evidence() {
        return opsEvidenceService.evidence();
    }

    @GetMapping("/release-approval-rehearsal")
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            @RequestHeader(name = "X-Rehearsal-Request-Id", required = false) String requestId,
            @RequestHeader(name = "X-Operator-Identity", required = false) String operatorIdentity,
            @RequestHeader(name = "X-Audit-Correlation-Id", required = false) String auditCorrelationId,
            @RequestHeader(name = "x-orderops-operator-id", required = false) String operatorWindowOperatorId,
            @RequestHeader(name = "x-orderops-roles", required = false) String operatorWindowRoles,
            @RequestHeader(name = "x-orderops-operator-verified", required = false) String operatorWindowVerifiedClaim,
            @RequestHeader(name = "x-orderops-approval-correlation-id", required = false)
            String operatorWindowApprovalCorrelationId,
            @RequestHeader(name = "x-orderops-ci-manifest-version", required = false) String ciManifestVersion,
            @RequestHeader(name = "x-orderops-ci-manifest-digest", required = false) String ciManifestDigest,
            @RequestHeader(name = "x-orderops-ci-manifest-endpoint", required = false) String ciManifestEndpoint,
            @RequestHeader(name = "x-orderops-ci-artifact-record-count", required = false)
            String ciArtifactRecordCount,
            @RequestHeader(name = "x-orderops-ci-approval-correlation-id", required = false)
            String ciApprovalCorrelationId,
            @RequestHeader(name = "x-orderops-ci-upload-contract-version", required = false)
            String ciUploadContractVersion,
            @RequestHeader(name = "x-orderops-ci-upload-contract-digest", required = false)
            String ciUploadContractDigest,
            @RequestHeader(name = "x-orderops-ci-artifact-name", required = false) String ciArtifactName,
            @RequestHeader(name = "x-orderops-ci-artifact-root", required = false) String ciArtifactRoot,
            @RequestHeader(name = "x-orderops-ci-retention-days", required = false) String ciRetentionDays,
            @RequestHeader(name = "x-orderops-ci-upload-mode", required = false) String ciUploadMode,
            @RequestHeader(name = "x-orderops-runtime-preflight-version", required = false)
            String runtimePreflightVersion,
            @RequestHeader(name = "x-orderops-runtime-preflight-digest", required = false)
            String runtimePreflightDigest,
            @RequestHeader(name = "x-orderops-runtime-smoke-session-id", required = false)
            String runtimeSmokeSessionId,
            @RequestHeader(name = "x-orderops-runtime-read-target-id", required = false)
            String runtimeReadTargetId,
            @RequestHeader(name = "x-orderops-runtime-window-mode", required = false)
            String runtimeWindowMode,
            @RequestHeader(name = "x-orderops-managed-audit-candidate-version", required = false)
            String managedAuditCandidateVersion,
            @RequestHeader(name = "x-orderops-managed-audit-candidate-digest", required = false)
            String managedAuditCandidateDigest,
            @RequestHeader(name = "x-orderops-managed-audit-sink-mode", required = false)
            String managedAuditSinkMode,
            @RequestHeader(name = "x-orderops-managed-audit-retention-days", required = false)
            String managedAuditRetentionDays,
            @RequestHeader(name = "x-orderops-managed-audit-rotation-policy", required = false)
            String managedAuditRotationPolicy
    ) {
        return opsEvidenceService.releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                operatorWindowOperatorId,
                operatorWindowRoles,
                operatorWindowVerifiedClaim,
                operatorWindowApprovalCorrelationId,
                ciManifestVersion,
                ciManifestDigest,
                ciManifestEndpoint,
                ciArtifactRecordCount,
                ciApprovalCorrelationId,
                ciUploadContractVersion,
                ciUploadContractDigest,
                ciArtifactName,
                ciArtifactRoot,
                ciRetentionDays,
                ciUploadMode,
                runtimePreflightVersion,
                runtimePreflightDigest,
                runtimeSmokeSessionId,
                runtimeReadTargetId,
                runtimeWindowMode,
                managedAuditCandidateVersion,
                managedAuditCandidateDigest,
                managedAuditSinkMode,
                managedAuditRetentionDays,
                managedAuditRotationPolicy
        );
    }
}
