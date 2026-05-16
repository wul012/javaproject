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
            String ciApprovalCorrelationId
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
                ciApprovalCorrelationId
        );
    }
}
