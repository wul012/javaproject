package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupConsumerSignoffPacketService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_SIGNOFF_PACKET;

    static final String PROFILE = "java-shard-readiness-route-cleanup-consumer-signoff-packet.v1";

    private final OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService;

    private final OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService;

    private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

    public OpsShardReadinessRouteCleanupConsumerSignoffPacketService(
            OpsShardReadinessRouteCleanupReleaseEvidenceBundleService releaseEvidenceBundleService,
            OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService,
            OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService
    ) {
        this.releaseEvidenceBundleService = releaseEvidenceBundleService;
        this.policyGuardService = policyGuardService;
        this.acceptanceReceiptService = acceptanceReceiptService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse packet() {
        OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse bundle =
                releaseEvidenceBundleService.bundle();
        OpsShardReadinessRouteCleanupPolicyGuardResponse guard = policyGuardService.guard();
        OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt = acceptanceReceiptService.receipt();
        List<OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse.SignoffItem> items = List.of(
                item("release-evidence-bundle", bundle.status()),
                item("policy-guard", guard.decision()),
                item("acceptance-receipt", receipt.receipt()),
                item("read-only-boundary", OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
                item("execution-disabled", String.valueOf(!bundle.executionAllowed()
                        && !guard.executionAllowed()
                        && !receipt.executionAllowed()))
        );
        boolean passed = bundle.status().equals("passed")
                && guard.status().equals("passed")
                && receipt.status().equals("passed");
        return new OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupReleaseEvidenceBundleService.ENDPOINT,
                OpsShardReadinessRouteCleanupPolicyGuardService.ENDPOINT,
                items.size(),
                items,
                "consumer signoff can review evidence but must not open write routing or runtime execution",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse.SignoffItem item(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupConsumerSignoffPacketResponse.SignoffItem(
                name,
                evidence,
                "passed"
        );
    }
}
