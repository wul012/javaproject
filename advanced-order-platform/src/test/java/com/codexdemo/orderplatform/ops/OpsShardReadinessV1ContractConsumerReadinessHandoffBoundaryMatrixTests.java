package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffBoundaryMatrixTests {

    @Test
    void keepsChecklistDigestAndReadinessHandoffFullyReadOnly() {
        assertThat(boundaryRows())
                .allSatisfy(row -> {
                    assertThat(row.readOnly()).as(row.version()).isTrue();
                    assertThat(row.executionAllowed()).as(row.version()).isFalse();
                    assertThat(row.shardEnabled()).as(row.version()).isFalse();
                    assertThat(row.probesAreGetOnly()).as(row.version()).isTrue();
                    assertThat(row.upstreamActionsAllowed()).as(row.version()).isFalse();
                    assertThat(row.startsJavaService()).as(row.version()).isFalse();
                    assertThat(row.startsMiniKvService()).as(row.version()).isFalse();
                    assertThat(row.writeRoutingAllowed()).as(row.version()).isFalse();
                    assertThat(row.activeShardRouterAllowed()).as(row.version()).isFalse();
                    assertThat(row.credentialValueRead()).as(row.version()).isFalse();
                    assertThat(row.rawEndpointParsed()).as(row.version()).isFalse();
                    assertThat(row.managedAuditConnectionAllowed()).as(row.version()).isFalse();
                    assertThat(row.deploymentOrRollbackAllowed()).as(row.version()).isFalse();
                    assertThat(row.nodeMayStartOrStopJavaOrMiniKv()).as(row.version()).isFalse();
                });
    }

    @Test
    void keepsChecklistDigestAndReadinessHandoffBlockedOperationsExact() {
        assertThat(boundaryRows())
                .extracting(BoundaryRow::blockedOperations)
                .allSatisfy(blockedOperations -> assertThat(blockedOperations)
                        .containsExactly(
                                "write-routing",
                                "active-shard-router",
                                "credential-value-read",
                                "raw-endpoint-parse",
                                "managed-audit-connection",
                                "deployment-or-rollback",
                                "node-start-or-stop-java-or-mini-kv"
                        ));
    }

    private static List<BoundaryRow> boundaryRows() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        return List.of(
                new BoundaryRow(
                        checklist.version(),
                        checklist.readOnly(),
                        checklist.executionAllowed(),
                        checklist.shardEnabled(),
                        checklist.probesAreGetOnly(),
                        checklist.upstreamActionsAllowed(),
                        checklist.startsJavaService(),
                        checklist.startsMiniKvService(),
                        checklist.writeRoutingAllowed(),
                        checklist.activeShardRouterAllowed(),
                        checklist.credentialValueRead(),
                        checklist.rawEndpointParsed(),
                        checklist.managedAuditConnectionAllowed(),
                        checklist.deploymentOrRollbackAllowed(),
                        checklist.nodeMayStartOrStopJavaOrMiniKv(),
                        checklist.blockedOperations()
                ),
                new BoundaryRow(
                        digest.version(),
                        digest.readOnly(),
                        digest.executionAllowed(),
                        digest.shardEnabled(),
                        digest.probesAreGetOnly(),
                        digest.upstreamActionsAllowed(),
                        digest.startsJavaService(),
                        digest.startsMiniKvService(),
                        digest.writeRoutingAllowed(),
                        digest.activeShardRouterAllowed(),
                        digest.credentialValueRead(),
                        digest.rawEndpointParsed(),
                        digest.managedAuditConnectionAllowed(),
                        digest.deploymentOrRollbackAllowed(),
                        digest.nodeMayStartOrStopJavaOrMiniKv(),
                        digest.blockedOperations()
                ),
                new BoundaryRow(
                        handoff.version(),
                        handoff.readOnly(),
                        handoff.executionAllowed(),
                        handoff.shardEnabled(),
                        handoff.probesAreGetOnly(),
                        handoff.upstreamActionsAllowed(),
                        handoff.startsJavaService(),
                        handoff.startsMiniKvService(),
                        handoff.writeRoutingAllowed(),
                        handoff.activeShardRouterAllowed(),
                        handoff.credentialValueRead(),
                        handoff.rawEndpointParsed(),
                        handoff.managedAuditConnectionAllowed(),
                        handoff.deploymentOrRollbackAllowed(),
                        handoff.nodeMayStartOrStopJavaOrMiniKv(),
                        handoff.blockedOperations()
                )
        );
    }

    private record BoundaryRow(
            String version,
            boolean readOnly,
            boolean executionAllowed,
            boolean shardEnabled,
            boolean probesAreGetOnly,
            boolean upstreamActionsAllowed,
            boolean startsJavaService,
            boolean startsMiniKvService,
            boolean writeRoutingAllowed,
            boolean activeShardRouterAllowed,
            boolean credentialValueRead,
            boolean rawEndpointParsed,
            boolean managedAuditConnectionAllowed,
            boolean deploymentOrRollbackAllowed,
            boolean nodeMayStartOrStopJavaOrMiniKv,
            List<String> blockedOperations
    ) {
    }
}
