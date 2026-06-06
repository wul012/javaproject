package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySlotCatalogTests {

    @Test
    void catalogsTwentyFiveDisabledValueSupplySlots() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots())
                .hasSize(25)
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .startsWith(
                        "VALUE_SUPPLY_01_ENVELOPE_ID",
                        "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
                        "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT"
                )
                .endsWith("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::status)
                .containsOnly("passed");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::evidenceSource)
                .allMatch(source -> source.startsWith(OpsShardReadinessRoutePaths.BASE_PATH));
    }

    @Test
    void returnsDefensiveSlotSlices() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(4, 8))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_05_REDACTION_CLASSIFICATION",
                        "VALUE_SUPPLY_06_CREDENTIAL_VALUE_BLOCK",
                        "VALUE_SUPPLY_07_RAW_ENDPOINT_BLOCK",
                        "VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK"
                );
    }
}
