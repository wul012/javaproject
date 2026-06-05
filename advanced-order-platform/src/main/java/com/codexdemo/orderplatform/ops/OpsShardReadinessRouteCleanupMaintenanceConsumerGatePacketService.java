package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-consumer-gate-packet.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse packet() {
        return OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.response(
                "Java v547",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "contract-freeze",
                                "release-reviewer",
                                "read-only-integration-v1 and shard-readiness-v1 frozen",
                                OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "field-map",
                                "catalog-maintainer",
                                "minimal shard-readiness fields mapped",
                                OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "read-window",
                                "operator-handoff-reviewer",
                                "read targets documented without live probe",
                                OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.ENDPOINT
                        ),
                        OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.item(
                                "runtime-boundary",
                                "runtime-boundary-reviewer",
                                "forbidden actions remain explicit",
                                OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.ENDPOINT
                        )
                ),
                List.of(
                        "consumer-gate-packet-source-count-4",
                        "consumer-gate-packet-is-read-only",
                        "consumer-gate-packet-does-not-contact-node"
                )
        );
    }
}
