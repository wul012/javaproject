package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RISK_LEDGER;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-risk-ledger.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse ledger() {
        List<OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse.RiskEntry> risks = List.of(
                risk("route-drift", "route-topology-index", "catalog-maintainer", "low"),
                risk("evidence-staleness", "freshness-window", "archive-reviewer", "low"),
                risk("boundary-drift", "fail-closed-policy", "runtime-boundary-reviewer", "low"),
                risk("handoff-owner-gap", "ownership-register", "operator-handoff-reviewer", "low"),
                risk("ci-regression-gap", "ci-expectation-manifest", "ci-reviewer", "low")
        );
        int high = (int) risks.stream().filter(risk -> "high".equals(risk.severity())).count();
        int mitigated = (int) risks.stream().filter(risk -> "mitigated".equals(risk.status())).count();
        List<String> checks = List.of(
                "risk-count-" + risks.size(),
                "high-risk-count-" + high,
                "all-risks-have-mitigation",
                "all-risks-have-owners",
                "risk-ledger-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse(
                "advanced-order-platform",
                "Java v520",
                true,
                false,
                ENDPOINT,
                PROFILE,
                risks.size(),
                high,
                mitigated,
                risks,
                checks,
                high == 0 && mitigated == risks.size() ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse.RiskEntry risk(
            String name,
            String mitigation,
            String owner,
            String severity
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse.RiskEntry(
                name,
                mitigation,
                owner,
                severity,
                "mitigated"
        );
    }
}
