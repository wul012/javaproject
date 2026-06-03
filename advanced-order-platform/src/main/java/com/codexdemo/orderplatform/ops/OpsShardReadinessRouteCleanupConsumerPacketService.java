package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupConsumerPacketService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-consumer-packet.v1";

    private final OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService;

    private final OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService;

    public OpsShardReadinessRouteCleanupConsumerPacketService(
            OpsShardReadinessRouteCleanupReadOnlyGateService readOnlyGateService,
            OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService
    ) {
        this.readOnlyGateService = readOnlyGateService;
        this.archiveVerificationService = archiveVerificationService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupConsumerPacketResponse packet() {
        OpsShardReadinessRouteCleanupReadOnlyGateResponse gate = readOnlyGateService.gate();
        OpsShardReadinessRouteCleanupArchiveVerificationResponse archiveVerification =
                archiveVerificationService.verification();
        List<String> endpoints = List.of(
                OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT,
                OpsShardReadinessRouteCleanupArchiveVerificationService.ENDPOINT,
                OpsShardReadinessRouteCleanupSuiteCloseoutService.ENDPOINT
        );
        boolean passed = gate.status().equals("passed") && archiveVerification.status().equals("passed");
        return new OpsShardReadinessRouteCleanupConsumerPacketResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                OpsShardReadinessRouteCleanupReadOnlyGateService.ENDPOINT,
                OpsShardReadinessRouteCleanupArchiveVerificationService.ENDPOINT,
                OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                endpoints.size(),
                endpoints,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations(),
                passed ? "consumer-may-read-handoff-evidence" : "blocked",
                passed ? "passed" : "blocked"
        );
    }
}
