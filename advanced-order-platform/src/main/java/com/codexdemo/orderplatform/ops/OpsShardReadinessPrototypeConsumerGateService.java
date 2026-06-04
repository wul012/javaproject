package com.codexdemo.orderplatform.ops;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessPrototypeConsumerGateService {

    static final String CATALOG_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_CONSUMER_GATE_CATALOG;

    private static final String PROJECT = "advanced-order-platform";

    private static final String CATALOG_PROFILE =
            "java-shard-readiness-prototype-consumer-gate-catalog.v1";

    private final OpsShardReadinessPrototypeHandoffService handoffService;

    public OpsShardReadinessPrototypeConsumerGateService(
            OpsShardReadinessPrototypeHandoffService handoffService
    ) {
        this.handoffService = handoffService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeConsumerGateCatalogResponse catalog() {
        OpsShardReadinessPrototypeHandoffCatalogResponse sourceCatalog = handoffService.catalog();
        OpsShardReadinessPrototypeHandoffEvidenceResponse sourceCloseout = handoffService.closeout();
        List<OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.Entry> entries =
                OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.entries();
        return new OpsShardReadinessPrototypeConsumerGateCatalogResponse(
                PROJECT,
                entries.getLast().version(),
                true,
                false,
                CATALOG_ENDPOINT,
                CATALOG_PROFILE,
                sourceCloseout.version(),
                sourceCloseout.endpoint(),
                sourceCatalog.entryCount(),
                OpsShardReadinessV1Contract.CONTRACT_NAME,
                entries.size(),
                entries,
                forbiddenOperations(),
                catalogStatus(sourceCatalog, sourceCloseout, entries)
        );
    }

    OpsShardReadinessPrototypeConsumerGateEvidenceResponse evidence(String key) {
        OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.Entry entry =
                OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.entryFor(key);
        OpsShardReadinessPrototypeHandoffCatalogResponse sourceCatalog = handoffService.catalog();
        OpsShardReadinessPrototypeHandoffEvidenceResponse sourceCloseout = handoffService.closeout();
        List<String> evidenceRefs = List.of(
                "prototype-handoff-catalog:" + sourceCatalog.endpoint(),
                "prototype-handoff-closeout:" + sourceCloseout.endpoint(),
                "prototype-handoff-closeout-evidence:" + sourceCloseout.evidencePath()
        );
        return new OpsShardReadinessPrototypeConsumerGateEvidenceResponse(
                PROJECT,
                entry.version(),
                true,
                false,
                entry.endpoint(),
                entry.profile(),
                entry.key(),
                entry.phase(),
                entry.nodePlanVersion(),
                sourceCloseout.version(),
                sourceCloseout.endpoint(),
                sourceCloseout.evidencePath(),
                OpsShardReadinessV1Contract.CONTRACT_NAME,
                evidenceRefs.size(),
                evidenceRefs,
                entry.checks(),
                forbiddenOperations(),
                digest(entry, sourceCatalog, sourceCloseout),
                entry.evidencePath(),
                evidenceStatus(sourceCatalog, sourceCloseout)
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

    private String catalogStatus(
            OpsShardReadinessPrototypeHandoffCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeHandoffEvidenceResponse sourceCloseout,
            List<OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.Entry> entries
    ) {
        boolean passed = "passed".equals(sourceCatalog.status())
                && sourceCatalog.readOnly()
                && !sourceCatalog.executionAllowed()
                && sourceCatalog.entryCount() == 10
                && "passed".equals(sourceCloseout.status())
                && !entries.isEmpty()
                && entries.stream().allMatch(entry -> !entry.checks().isEmpty());
        return passed ? "passed" : "blocked";
    }

    private String evidenceStatus(
            OpsShardReadinessPrototypeHandoffCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeHandoffEvidenceResponse sourceCloseout
    ) {
        boolean passed = "passed".equals(sourceCatalog.status())
                && sourceCatalog.readOnly()
                && !sourceCatalog.executionAllowed()
                && "passed".equals(sourceCloseout.status())
                && sourceCloseout.readOnly()
                && !sourceCloseout.executionAllowed()
                && "handoff-closeout".equals(sourceCloseout.entryKey());
        return passed ? "passed" : "blocked";
    }

    private String digest(
            OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.Entry entry,
            OpsShardReadinessPrototypeHandoffCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeHandoffEvidenceResponse sourceCloseout
    ) {
        String material = String.join("|",
                entry.version(),
                entry.key(),
                entry.profile(),
                sourceCatalog.version(),
                sourceCloseout.version(),
                sourceCloseout.digestValue(),
                entry.evidencePath());
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(material.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest is not available", ex);
        }
    }
}
