package com.codexdemo.orderplatform.ops;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessPrototypeEvidenceService {

    static final String CATALOG_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CATALOG;
    static final String FIXTURE_ECHO_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_FIXTURE_ECHO;
    static final String FIELD_ALIGNMENT_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_FIELD_ALIGNMENT;
    static final String READ_ONLY_INTEGRATION_BRIDGE_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_READ_ONLY_INTEGRATION_BRIDGE;
    static final String ROUTE_CLEANUP_BRIDGE_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_ROUTE_CLEANUP_BRIDGE;
    static final String READ_WINDOW_HANDOFF_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_READ_WINDOW_HANDOFF;
    static final String CONSUMER_GATE_PACKET_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_PACKET;

    private static final String PROJECT = "advanced-order-platform";

    private static final String CATALOG_PROFILE = "java-shard-readiness-prototype-catalog.v1";

    private final OpsShardReadinessService readinessService;

    private final OpsShardReadinessEchoService echoService;

    private final OpsShardReadinessRouteCleanupPostCompletionCloseoutService routeCleanupCloseoutService;

    public OpsShardReadinessPrototypeEvidenceService(
            OpsShardReadinessService readinessService,
            OpsShardReadinessEchoService echoService,
            OpsShardReadinessRouteCleanupPostCompletionCloseoutService routeCleanupCloseoutService
    ) {
        this.readinessService = readinessService;
        this.echoService = echoService;
        this.routeCleanupCloseoutService = routeCleanupCloseoutService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeCatalogResponse catalog() {
        List<OpsShardReadinessPrototypeEvidenceCatalog.Entry> entries =
                OpsShardReadinessPrototypeEvidenceCatalog.entries();
        return new OpsShardReadinessPrototypeCatalogResponse(
                PROJECT,
                entries.getLast().version(),
                true,
                false,
                CATALOG_ENDPOINT,
                CATALOG_PROFILE,
                OpsShardReadinessV1Contract.CONTRACT_NAME,
                entries.size(),
                entries,
                OpsShardReadinessV1Contract.minimalFields(),
                forbiddenOperations(),
                entries.stream().allMatch(entry -> !entry.checks().isEmpty()) ? "passed" : "blocked"
        );
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse fixtureEcho() {
        return evidence("prototype-fixture-echo");
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse fieldAlignment() {
        return evidence("prototype-field-alignment");
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse readOnlyIntegrationBridge() {
        return evidence("prototype-read-only-integration-bridge");
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse routeCleanupBridge() {
        return evidence("prototype-route-cleanup-bridge");
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse readWindowHandoff() {
        return evidence("prototype-read-window-handoff");
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeEvidenceResponse consumerGatePacket() {
        return evidence("prototype-consumer-gate-packet");
    }

    OpsShardReadinessPrototypeEvidenceResponse evidence(String key) {
        OpsShardReadinessPrototypeEvidenceCatalog.Entry entry =
                OpsShardReadinessPrototypeEvidenceCatalog.entryFor(key);
        OpsShardReadinessResponse readiness = readinessService.readiness();
        OpsShardReadinessEchoResponse echo = echoService.echo();
        OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout =
                routeCleanupCloseoutService.closeout();
        List<String> evidenceRefs = List.of(
                "root-readiness:" + readiness.evidencePath(),
                "echo:" + echo.evidencePath(),
                "route-cleanup-closeout:" + closeout.postCompletionCloseoutEndpoint()
        );
        String status = evidenceStatus(readiness, echo, closeout);
        return new OpsShardReadinessPrototypeEvidenceResponse(
                PROJECT,
                entry.version(),
                true,
                false,
                entry.endpoint(),
                entry.profile(),
                entry.key(),
                entry.phase(),
                entry.nodePlanVersion(),
                OpsShardReadinessV1Contract.CONTRACT_NAME,
                readiness.shardEnabled(),
                readiness.shardCount(),
                readiness.slotCount(),
                readiness.routingMode(),
                readiness.version(),
                echo.version(),
                closeout.version(),
                OpsShardReadinessV1Contract.minimalFields(),
                evidenceRefs,
                entry.checks(),
                forbiddenOperations(),
                digest(entry, readiness, echo, closeout),
                entry.evidencePath(),
                status
        );
    }

    private List<String> forbiddenOperations() {
        return List.of(
                "write-routing",
                "active-shard-router",
                "credential-value-read",
                "raw-endpoint-parse",
                "managed-audit-connection",
                "deployment-or-rollback",
                "node-start-or-stop-java-or-mini-kv"
        );
    }

    private String evidenceStatus(
            OpsShardReadinessResponse readiness,
            OpsShardReadinessEchoResponse echo,
            OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout
    ) {
        boolean passed = OpsShardReadinessV1Contract.alignsWithReadOnlyContract(readiness)
                && "passed".equals(echo.status())
                && "passed".equals(closeout.status())
                && !echo.executionAllowed()
                && !closeout.executionAllowed();
        return passed ? "passed" : "blocked";
    }

    private String digest(
            OpsShardReadinessPrototypeEvidenceCatalog.Entry entry,
            OpsShardReadinessResponse readiness,
            OpsShardReadinessEchoResponse echo,
            OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse closeout
    ) {
        String material = String.join("|",
                entry.version(),
                entry.key(),
                entry.profile(),
                readiness.version(),
                echo.version(),
                closeout.version(),
                entry.evidencePath());
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(material.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest is not available", ex);
        }
    }
}
