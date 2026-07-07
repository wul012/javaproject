package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalogTests {

  @Test
  void catalogsTwentyFiveAdapterPreflightSlotsMappedToSupplySlots() {
    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots())
        .hasSize(25)
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
        .startsWith(
            "ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY",
            "ADAPTER_PREFLIGHT_02_OPERATOR_REFERENCE_COMPATIBILITY")
        .endsWith("ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot
                ::sourceSupplySlot)
        .startsWith("VALUE_SUPPLY_01_ENVELOPE_ID")
        .endsWith("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot
                ::sourceEndpoint)
        .allMatch(
            endpoint ->
                endpoint.startsWith(
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths
                        .BASE_PATH));
  }

  @Test
  void returnsStageSlicesForFocusedServices() {
    assertThat(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(12, 16))
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot
                ::adapterStage)
        .containsOnly("provenance");
  }
}
