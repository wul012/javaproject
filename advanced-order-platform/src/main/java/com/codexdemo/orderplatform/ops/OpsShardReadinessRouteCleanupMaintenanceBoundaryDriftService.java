package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_DRIFT;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-boundary-drift.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse audit() {
        List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries();
        int readOnlyViolations = (int) entries.stream().filter(entry -> !entry.readOnly()).count();
        int executionViolations = (int) entries.stream().filter(
                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::executionAllowed).count();
        int startupViolations = (int) entries.stream().filter(entry ->
                entry.startsJavaService() || entry.startsMiniKvService()).count();
        int credentialViolations = (int) entries.stream().filter(
                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::credentialValueRead).count();
        int rawEndpointViolations = (int) entries.stream().filter(
                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::rawEndpointParsed).count();
        int managedAuditViolations = (int) entries.stream().filter(
                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::managedAuditConnectionOpened).count();
        int writeRoutingViolations = (int) entries.stream().filter(
                OpsShardReadinessRouteCleanupEvidenceResponse.Entry::writeRoutingChanged).count();
        List<String> checks = List.of(
                "read-only-violation-count-" + readOnlyViolations,
                "execution-allowed-violation-count-" + executionViolations,
                "upstream-startup-violation-count-" + startupViolations,
                "managed-audit-violation-count-" + managedAuditViolations,
                "write-routing-violation-count-" + writeRoutingViolations
        );
        return new OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse(
                "advanced-order-platform",
                "Java v479",
                true,
                false,
                ENDPOINT,
                PROFILE,
                entries.size(),
                readOnlyViolations,
                executionViolations,
                startupViolations,
                credentialViolations,
                rawEndpointViolations,
                managedAuditViolations,
                writeRoutingViolations,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations(),
                checks,
                status(readOnlyViolations, executionViolations, startupViolations, credentialViolations,
                        rawEndpointViolations, managedAuditViolations, writeRoutingViolations)
        );
    }

    private String status(int... violationCounts) {
        for (int violationCount : violationCounts) {
            if (violationCount != 0) {
                return "blocked";
            }
        }
        return "passed";
    }
}
