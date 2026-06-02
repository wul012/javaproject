package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogBoundarySchemaStrictnessTests {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Set<String> EXPECTED_BOUNDARY_FIELDS = Set.of(
            "writeRoutingAllowed",
            "activeShardRouterAllowed",
            "credentialValueRead",
            "rawEndpointParsed",
            "managedAuditConnectionAllowed",
            "deploymentOrRollbackAllowed",
            "nodeMayStartOrStopJavaOrMiniKv"
    );

    @Test
    void keepsEveryCatalogBoundaryObjectOnTheStrictReadOnlySchema() throws IOException {
        Path root = Path.of("").toAbsolutePath();

        for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt receipt
                : OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts()) {
            JsonNode boundary = OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile()).path("boundary");
            Set<String> boundaryFields = new TreeSet<>();
            Iterator<String> fieldNames = boundary.fieldNames();
            fieldNames.forEachRemaining(boundaryFields::add);

            assertThat(boundaryFields).as(receipt.evidencePath()).isEqualTo(EXPECTED_BOUNDARY_FIELDS);
            assertThat(boundaryFields).as(receipt.evidencePath())
                    .allSatisfy(field -> assertThat(boundary.path(field).asBoolean()).isFalse());
        }
    }

    @Test
    void keepsCatalogBoundarySchemaStrictnessPathVersionedToV283() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_BOUNDARY_SCHEMA_STRICTNESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/283/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-boundary-schema-strictness-v283.json"
                );
    }
}
