package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_PAIR_AUDIT;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-handoff-pair-audit.v1";

    private static final Set<String> ALLOWED_ROUTE_ONLY_PHASES = Set.of(
            "handoff-suite-closeout-route",
            "handoff-suite-completion-certificate-route"
    );

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse audit() {
        List<String> phases = OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().stream()
                .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::phase)
                .filter(phase -> phase.startsWith("handoff-suite"))
                .toList();
        Set<String> serviceKeys = phases.stream()
                .filter(phase -> phase.endsWith("-service"))
                .map(phase -> phase.substring(0, phase.length() - "-service".length()))
                .collect(java.util.stream.Collectors.toSet());
        Set<String> routeKeys = phases.stream()
                .filter(phase -> phase.endsWith("-route"))
                .map(phase -> phase.substring(0, phase.length() - "-route".length()))
                .collect(java.util.stream.Collectors.toSet());
        List<String> routeOnly = routeKeys.stream()
                .filter(key -> !serviceKeys.contains(key))
                .map(key -> key + "-route")
                .sorted()
                .toList();
        List<String> unpairedServices = serviceKeys.stream()
                .filter(key -> !routeKeys.contains(key))
                .map(key -> key + "-service")
                .sorted()
                .toList();
        int pairedRouteCount = (int) routeKeys.stream()
                .filter(serviceKeys::contains)
                .count();
        List<String> checks = List.of(
                "handoff-suite-service-route-pairs-counted",
                "documented-route-only-exceptions-retained",
                "unpaired-service-entry-count-" + unpairedServices.size(),
                "paired-route-count-" + pairedRouteCount,
                "pair-audit-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse(
                "advanced-order-platform",
                "Java v477",
                true,
                false,
                ENDPOINT,
                PROFILE,
                phases.size(),
                serviceKeys.size(),
                routeKeys.size(),
                pairedRouteCount,
                routeOnly,
                unpairedServices,
                checks,
                status(routeOnly, unpairedServices)
        );
    }

    private String status(List<String> routeOnly, List<String> unpairedServices) {
        boolean passed = unpairedServices.isEmpty()
                && ALLOWED_ROUTE_ONLY_PHASES.containsAll(routeOnly)
                && routeOnly.containsAll(ALLOWED_ROUTE_ONLY_PHASES);
        return passed ? "passed" : "blocked";
    }
}
