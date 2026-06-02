package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffAuditDeploymentBoundaryTests {

    @Test
    void keepsManagedAuditAndDeploymentRollbackDisabledInReadinessHandoff() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
        assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
        assertThat(handoff.blockedOperations())
                .contains("managed-audit-connection", "deployment-or-rollback");
    }

    @Test
    void keepsAuditDeploymentBoundaryEvidencePathVersionedToV250() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_AUDIT_DEPLOYMENT_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/250/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "audit-deployment-boundary-v250.json"
                );
    }
}
