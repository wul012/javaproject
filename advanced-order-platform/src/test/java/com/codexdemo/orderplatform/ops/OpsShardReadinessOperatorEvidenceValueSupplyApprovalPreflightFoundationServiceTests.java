package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationServiceTests {

    @Test
    void buildsCatalogWithAllItemsAndPoliciesButNoApprovalCapture() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService()
                        .catalog();

        assertThat(catalog.version()).isEqualTo("Java v688");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-catalog.v1");
        assertThat(catalog.sourcePlan()).isEqualTo("Node v986");
        assertThat(catalog.sourceEnvelopeVersion()).isEqualTo("Node v961");
        assertThat(catalog.sourceValueSupplyVersion()).isEqualTo("Java v658");
        assertThat(catalog.sourceAdapterPreflightVersion()).isEqualTo("Java v684");
        assertThat(catalog.readyForApprovalPreflight()).isTrue();
        assertThat(catalog.readyForSignedApprovalCapture()).isFalse();
        assertThat(catalog.readyForApprovalGrant()).isFalse();
        assertThat(catalog.readyForOperatorValueSubmission()).isFalse();
        assertThat(catalog.readyForEvidenceImport()).isFalse();
        assertThat(catalog.readyForRuntimePayload()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(25);
        assertThat(catalog.passedItemCount()).isEqualTo(25);
        assertThat(catalog.policyCount()).isEqualTo(20);
        assertThat(catalog.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .startsWith("VALUE_SUPPLY_APPROVAL_PACKET_01_PACKET_ID")
                .endsWith("VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD");
        assertThat(catalog.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .startsWith("APPROVAL_PREFLIGHT_POLICY_01_IDENTITY_ALIAS_ONLY")
                .endsWith("APPROVAL_PREFLIGHT_POLICY_20_CLOSEOUT_LOCK_SUMMARY_REQUIRED");
        assertThat(catalog.checks()).contains(
                "value-supply-approval-preflight-catalog-item-count-25",
                "value-supply-approval-preflight-catalog-policy-count-20",
                "value-supply-approval-preflight-catalog-source-node-v986",
                "value-supply-approval-preflight-approval-not-captured",
                "value-supply-approval-preflight-values-not-accepted",
                "value-supply-approval-preflight-import-locked"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
