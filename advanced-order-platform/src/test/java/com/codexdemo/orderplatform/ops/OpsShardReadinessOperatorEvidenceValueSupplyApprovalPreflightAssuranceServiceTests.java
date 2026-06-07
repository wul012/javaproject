package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceServiceTests {

    @Test
    void buildsValueRejectionWithoutAcceptingMalformedMissingOrTypedValueBodies() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse rejection =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService()
                        .rejection();

        assertThat(rejection.version()).isEqualTo("Java v698");
        assertThat(rejection.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-value-rejection");
        assertThat(rejection.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-value-rejection.v1");
        assertThat(rejection.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(rejection.malformedValueState()).isEqualTo("rejected");
        assertThat(rejection.readyForOperatorValueSubmission()).isFalse();
        assertThat(rejection.readyForEvidenceImport()).isFalse();
        assertThat(rejection.itemCount()).isEqualTo(3);
        assertThat(rejection.policyCount()).isEqualTo(3);
        assertThat(rejection.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_17_TYPED_VALUE_ENVELOPE_REFERENCE",
                        "VALUE_SUPPLY_APPROVAL_PACKET_18_MALFORMED_VALUE_REJECTION",
                        "VALUE_SUPPLY_APPROVAL_PACKET_19_MISSING_VALUE_REJECTION"
                );
        assertThat(rejection.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_13_TYPED_VALUE_ENVELOPE_REFERENCE_ONLY",
                        "APPROVAL_PREFLIGHT_POLICY_14_MALFORMED_VALUES_REJECTED",
                        "APPROVAL_PREFLIGHT_POLICY_15_MISSING_VALUES_REJECTED"
                );
        assertThat(rejection.checks()).contains(
                "value-supply-approval-preflight-value-envelope-reference-only",
                "value-supply-approval-preflight-malformed-values-rejected",
                "value-supply-approval-preflight-missing-values-rejected",
                "value-supply-approval-preflight-value-import-still-locked"
        );
        assertThat(rejection.status()).isEqualTo("passed");
    }
}
