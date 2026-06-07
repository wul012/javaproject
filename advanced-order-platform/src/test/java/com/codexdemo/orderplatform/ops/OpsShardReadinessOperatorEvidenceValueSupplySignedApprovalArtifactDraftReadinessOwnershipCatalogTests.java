package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalogTests {

    @Test
    void listsOwnershipRulesForReadinessAndClosedExecutionBoundaries() {
        var ownershipRules =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                        .allOwnershipRules();

        assertThat(ownershipRules).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                        .OWNERSHIP_COUNT);
        assertThat(ownershipRules.stream().map(rule -> rule.code()).collect(Collectors.toSet())).hasSize(20);
        assertThat(ownershipRules).allSatisfy(rule -> {
            assertThat(rule.owner()).isNotBlank();
            assertThat(rule.responsibility()).isNotBlank();
            assertThat(rule.enforcement()).isNotBlank();
        });
        assertThat(ownershipRules).anySatisfy(rule -> assertThat(rule.code()).contains("NO_ARTIFACT_DRAFT"));
        assertThat(ownershipRules).anySatisfy(rule -> assertThat(rule.code()).contains("NO_SIBLING_MUTATION"));
    }
}
