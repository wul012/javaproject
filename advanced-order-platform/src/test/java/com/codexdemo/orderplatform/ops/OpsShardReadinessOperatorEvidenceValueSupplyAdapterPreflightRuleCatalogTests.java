package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalogTests {

    @Test
    void catalogsEighteenFailClosedAdapterPreflightRules() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules())
                .hasSize(18)
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
                .startsWith(
                        "ADAPTER_RULE_01_DISABLED_IMPLEMENTATION",
                        "ADAPTER_RULE_02_METADATA_ONLY_COMPATIBILITY"
                )
                .endsWith("ADAPTER_RULE_18_CLOSEOUT_LOCK_SUMMARY_REQUIRED");

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::category)
                .contains(
                        "implementation",
                        "approval",
                        "redaction",
                        "provenance",
                        "missing-policy",
                        "source-evidence",
                        "payload",
                        "side-effect",
                        "runtime",
                        "closeout"
                );
    }

    @Test
    void returnsRuleSlicesForFocusedAdapterPreflightServices() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(4, 7))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::category)
                .containsOnly("redaction");

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(12, 14))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::category)
                .containsOnly("source-evidence");
    }
}
