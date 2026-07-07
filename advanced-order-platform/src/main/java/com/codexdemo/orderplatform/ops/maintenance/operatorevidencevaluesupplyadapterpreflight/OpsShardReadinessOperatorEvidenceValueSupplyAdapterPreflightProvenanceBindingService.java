package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PROVENANCE_BINDING;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-provenance-binding.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse binding() {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
        "Java v668",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(12, 16),
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.rules(7, 10),
        List.of(
            "value-supply-adapter-preflight-provenance-slice-13-16",
            "value-supply-adapter-preflight-provenance-source-id-required",
            "value-supply-adapter-preflight-provenance-evidence-file-required",
            "value-supply-adapter-preflight-provenance-snippet-required",
            "value-supply-adapter-preflight-provenance-endpoint-alias-only"));
  }
}
