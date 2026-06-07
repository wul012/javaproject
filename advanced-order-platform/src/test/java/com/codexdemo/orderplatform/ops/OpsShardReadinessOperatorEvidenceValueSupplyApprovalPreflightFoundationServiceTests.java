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

    @Test
    void buildsIdentitySignaturePreflightWithoutApprovalCaptureOrGrant() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse signature =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService()
                        .signature();

        assertThat(signature.version()).isEqualTo("Java v690");
        assertThat(signature.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-identity-signature");
        assertThat(signature.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-identity-signature.v1");
        assertThat(signature.approvalCaptureState()).isEqualTo("not-captured");
        assertThat(signature.readyForSignedApprovalCapture()).isFalse();
        assertThat(signature.readyForApprovalGrant()).isFalse();
        assertThat(signature.readyForOperatorValueSubmission()).isFalse();
        assertThat(signature.itemCount()).isEqualTo(5);
        assertThat(signature.policyCount()).isEqualTo(5);
        assertThat(signature.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_01_PACKET_ID",
                        "VALUE_SUPPLY_APPROVAL_PACKET_02_OPERATOR_IDENTITY_ALIAS",
                        "VALUE_SUPPLY_APPROVAL_PACKET_03_REVIEWER_ROLE",
                        "VALUE_SUPPLY_APPROVAL_PACKET_04_APPROVAL_INTENT",
                        "VALUE_SUPPLY_APPROVAL_PACKET_05_SIGNED_HUMAN_POLICY"
                );
        assertThat(signature.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_01_IDENTITY_ALIAS_ONLY",
                        "APPROVAL_PREFLIGHT_POLICY_02_REVIEWER_ROLE_REQUIRED",
                        "APPROVAL_PREFLIGHT_POLICY_03_SIGNED_HUMAN_APPROVAL_REQUIRED",
                        "APPROVAL_PREFLIGHT_POLICY_04_NO_APPROVAL_CAPTURE",
                        "APPROVAL_PREFLIGHT_POLICY_05_NO_OPERATOR_VALUE_BODY"
                );
        assertThat(signature.checks()).contains(
                "value-supply-approval-preflight-identity-operator-alias-only",
                "value-supply-approval-preflight-signature-human-policy-required",
                "value-supply-approval-preflight-signature-capture-locked",
                "value-supply-approval-preflight-signature-grant-locked"
        );
        assertThat(signature.status()).isEqualTo("passed");
    }

    @Test
    void buildsTimestampWindowWithoutUnlockingCaptureOrRuntime() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse window =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService()
                        .window();

        assertThat(window.version()).isEqualTo("Java v692");
        assertThat(window.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-timestamp-window");
        assertThat(window.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-timestamp-window.v1");
        assertThat(window.readyForSignedApprovalCapture()).isFalse();
        assertThat(window.readyForRuntimePayload()).isFalse();
        assertThat(window.readyForLiveExecution()).isFalse();
        assertThat(window.itemCount()).isEqualTo(3);
        assertThat(window.policyCount()).isEqualTo(3);
        assertThat(window.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_06_ISSUED_AT_TIMESTAMP",
                        "VALUE_SUPPLY_APPROVAL_PACKET_07_EXPIRY_WINDOW",
                        "VALUE_SUPPLY_APPROVAL_PACKET_08_REPLAY_NONCE"
                );
        assertThat(window.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_06_ISSUED_AT_REQUIRED",
                        "APPROVAL_PREFLIGHT_POLICY_07_EXPIRY_WINDOW_REQUIRED",
                        "APPROVAL_PREFLIGHT_POLICY_08_REPLAY_NONCE_REQUIRED"
                );
        assertThat(window.checks()).contains(
                "value-supply-approval-preflight-timestamp-issued-at-required",
                "value-supply-approval-preflight-timestamp-expiry-window-required",
                "value-supply-approval-preflight-timestamp-replay-nonce-required",
                "value-supply-approval-preflight-timestamp-capture-still-locked"
        );
        assertThat(window.status()).isEqualTo("passed");
    }

    @Test
    void buildsRedactionDigestWithoutCredentialRawEndpointOrValueHash() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse digest =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService()
                        .digest();

        assertThat(digest.version()).isEqualTo("Java v694");
        assertThat(digest.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-redaction-digest");
        assertThat(digest.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-approval-preflight-redaction-digest.v1");
        assertThat(digest.redactionDigestState()).isEqualTo("required-before-capture");
        assertThat(digest.readyForSignedApprovalCapture()).isFalse();
        assertThat(digest.readyForOperatorValueSubmission()).isFalse();
        assertThat(digest.itemCount()).isEqualTo(4);
        assertThat(digest.policyCount()).isEqualTo(2);
        assertThat(digest.items())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem::code)
                .containsExactly(
                        "VALUE_SUPPLY_APPROVAL_PACKET_09_REDACTION_DIGEST_ID",
                        "VALUE_SUPPLY_APPROVAL_PACKET_10_REDACTION_DIGEST_ALGORITHM",
                        "VALUE_SUPPLY_APPROVAL_PACKET_11_CREDENTIAL_ABSENCE_PROOF",
                        "VALUE_SUPPLY_APPROVAL_PACKET_12_RAW_ENDPOINT_ABSENCE_PROOF"
                );
        assertThat(digest.policies())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalPolicy::code)
                .containsExactly(
                        "APPROVAL_PREFLIGHT_POLICY_09_REDACTION_DIGEST_REQUIRED",
                        "APPROVAL_PREFLIGHT_POLICY_10_NO_CREDENTIAL_OR_RAW_ENDPOINT"
                );
        assertThat(digest.checks()).contains(
                "value-supply-approval-preflight-redaction-digest-id-required",
                "value-supply-approval-preflight-redaction-credential-absence-proof",
                "value-supply-approval-preflight-redaction-raw-endpoint-absence-proof",
                "value-supply-approval-preflight-redaction-no-value-hash"
        );
        assertThat(digest.status()).isEqualTo("passed");
    }
}
