package com.codexdemo.orderplatform.ops;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessPrototypeHandoffService {

    static final String CATALOG_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_CATALOG;
    static final String ENDPOINT_INVENTORY_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_ENDPOINT_INVENTORY;
    static final String BOUNDARY_MATRIX_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_BOUNDARY_MATRIX;
    static final String CONSUMER_VERIFICATION_CHECKLIST_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                            .SHARD_READINESS_PROTOTYPE_HANDOFF_CONSUMER_VERIFICATION_CHECKLIST;
    static final String READ_WINDOW_CHECKLIST_ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SHARD_READINESS_PROTOTYPE_HANDOFF_READ_WINDOW_CHECKLIST;

    private static final String PROJECT = "advanced-order-platform";

    private static final String CATALOG_PROFILE = "java-shard-readiness-prototype-handoff-catalog.v1";

    private final OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService;

    public OpsShardReadinessPrototypeHandoffService(
            OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService
    ) {
        this.prototypeEvidenceService = prototypeEvidenceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessPrototypeHandoffCatalogResponse catalog() {
        OpsShardReadinessPrototypeCatalogResponse sourceCatalog = prototypeEvidenceService.catalog();
        OpsShardReadinessPrototypeEvidenceResponse sourceCloseout = prototypeEvidenceService.closeout();
        List<OpsShardReadinessPrototypeHandoffEvidenceCatalog.Entry> entries =
                OpsShardReadinessPrototypeHandoffEvidenceCatalog.entries();
        return new OpsShardReadinessPrototypeHandoffCatalogResponse(
                PROJECT,
                entries.getLast().version(),
                true,
                false,
                CATALOG_ENDPOINT,
                CATALOG_PROFILE,
                sourceCloseout.version(),
                sourceCloseout.endpoint(),
                OpsShardReadinessV1Contract.CONTRACT_NAME,
                entries.size(),
                entries,
                forbiddenOperations(),
                catalogStatus(sourceCatalog, sourceCloseout, entries)
        );
    }

    public OpsShardReadinessPrototypeHandoffEvidenceResponse endpointInventory() {
        return evidence("handoff-endpoint-inventory");
    }

    public OpsShardReadinessPrototypeHandoffEvidenceResponse boundaryMatrix() {
        return evidence("handoff-boundary-matrix");
    }

    public OpsShardReadinessPrototypeHandoffEvidenceResponse consumerVerificationChecklist() {
        return evidence("handoff-consumer-verification-checklist");
    }

    public OpsShardReadinessPrototypeHandoffEvidenceResponse readWindowChecklist() {
        return evidence("handoff-read-window-checklist");
    }

    OpsShardReadinessPrototypeHandoffEvidenceResponse evidence(String key) {
        OpsShardReadinessPrototypeHandoffEvidenceCatalog.Entry entry =
                OpsShardReadinessPrototypeHandoffEvidenceCatalog.entryFor(key);
        OpsShardReadinessPrototypeCatalogResponse sourceCatalog = prototypeEvidenceService.catalog();
        OpsShardReadinessPrototypeEvidenceResponse sourceCloseout = prototypeEvidenceService.closeout();
        List<String> evidenceRefs = List.of(
                "prototype-catalog:" + sourceCatalog.endpoint(),
                "prototype-closeout:" + sourceCloseout.endpoint(),
                "prototype-closeout-evidence:" + sourceCloseout.evidencePath()
        );
        return new OpsShardReadinessPrototypeHandoffEvidenceResponse(
                PROJECT,
                entry.version(),
                true,
                false,
                entry.endpoint(),
                entry.profile(),
                entry.key(),
                entry.phase(),
                entry.nodePlanVersion(),
                sourceCatalog.version(),
                sourceCloseout.version(),
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
            OpsShardReadinessPrototypeCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeEvidenceResponse sourceCloseout,
            List<OpsShardReadinessPrototypeHandoffEvidenceCatalog.Entry> entries
    ) {
        boolean passed = "passed".equals(sourceCatalog.status())
                && "passed".equals(sourceCloseout.status())
                && !entries.isEmpty()
                && entries.stream().allMatch(entry -> !entry.checks().isEmpty());
        return passed ? "passed" : "blocked";
    }

    private String evidenceStatus(
            OpsShardReadinessPrototypeCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeEvidenceResponse sourceCloseout
    ) {
        boolean passed = "passed".equals(sourceCatalog.status())
                && sourceCatalog.readOnly()
                && !sourceCatalog.executionAllowed()
                && "passed".equals(sourceCloseout.status())
                && sourceCloseout.readOnly()
                && !sourceCloseout.executionAllowed();
        return passed ? "passed" : "blocked";
    }

    private String digest(
            OpsShardReadinessPrototypeHandoffEvidenceCatalog.Entry entry,
            OpsShardReadinessPrototypeCatalogResponse sourceCatalog,
            OpsShardReadinessPrototypeEvidenceResponse sourceCloseout
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
