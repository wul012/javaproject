package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalogTests {

    @Test
    void catalogsTwentyFailClosedApprovalPreflightPolicies() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies())
                .hasSize(20)
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .startsWith(
                        "APPROVAL_PREFLIGHT_POLICY_01_IDENTITY_ALIAS_ONLY",
                        "APPROVAL_PREFLIGHT_POLICY_02_REVIEWER_ROLE_REQUIRED"
                )
                .endsWith("APPROVAL_PREFLIGHT_POLICY_20_CLOSEOUT_LOCK_SUMMARY_REQUIRED");

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies())
                .extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::category)
                .contains(
                        "identity",
                        "approval",
                        "timestamp",
                        "redaction",
                        "provenance",
                        "value-envelope",
                        "rejection",
                        "zero-count",
                        "receipt",
                        "import",
                        "runtime",
                        "closeout"
                );
    }

    @Test
    void returnsPolicySlicesForFocusedApprovalPreflightServices() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(5, 8))
                .extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::category)
                .containsOnly("timestamp");

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(13, 15))
                .extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::category)
                .containsOnly("rejection");
    }
}
