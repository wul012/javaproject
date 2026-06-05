package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_HANDOFF_MATRIX;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-consumer-handoff-matrix.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse matrix() {
        List<OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse.MatrixEntry> matrix =
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
                        .map(this::entry)
                        .toList();
        List<String> forbiddenOperations = OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations();
        List<String> checks = List.of(
                "matrix-entry-count-" + matrix.size(),
                "every-upkeep-item-has-consumer",
                "every-upkeep-item-has-boundary",
                "forbidden-operations-remain-explicit",
                "handoff-matrix-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse(
                "advanced-order-platform",
                "Java v491",
                true,
                false,
                ENDPOINT,
                PROFILE,
                matrix.size(),
                (int) matrix.stream()
                        .map(OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse
                                .MatrixEntry::consumer)
                        .distinct()
                        .count(),
                forbiddenOperations.size(),
                matrix,
                forbiddenOperations,
                checks,
                status(matrix, forbiddenOperations)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse.MatrixEntry entry(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse.MatrixEntry(
                item.name(),
                item.consumer(),
                item.boundary(),
                item.endpoint(),
                "review-" + item.boundary() + "-from-java-v" + item.serviceVersion(),
                item.status()
        );
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse.MatrixEntry> matrix,
            List<String> forbiddenOperations
    ) {
        boolean passed = matrix.size() == OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().size()
                && matrix.stream().allMatch(entry -> !entry.consumer().isBlank())
                && matrix.stream().allMatch(entry -> !entry.boundary().isBlank())
                && forbiddenOperations.contains("managed-audit-connection")
                && matrix.stream().allMatch(entry -> "passed".equals(entry.handoffStatus()));
        return passed ? "passed" : "blocked";
    }
}
