package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_OPERATOR_REHEARSAL_CHECKLIST;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-operator-rehearsal-checklist.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse checklist() {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
        "Java v678",
        ENDPOINT,
        PROFILE,
        rehearsalSlots(),
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allRules(),
        List.of(
            "value-supply-adapter-preflight-rehearsal-envelope-metadata-reviewed",
            "value-supply-adapter-preflight-rehearsal-provenance-reviewed",
            "value-supply-adapter-preflight-rehearsal-runtime-locks-reviewed",
            "value-supply-adapter-preflight-rehearsal-no-approval-grant",
            "value-supply-adapter-preflight-rehearsal-no-adapter-implementation"));
  }

  private List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot>
      rehearsalSlots() {
    List<OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot> slots =
        new ArrayList<>();
    slots.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(0, 4));
    slots.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(12, 16));
    slots.addAll(
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(20, 25));
    return List.copyOf(slots);
  }
}
