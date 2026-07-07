package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PAYLOAD_FIREWALL;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-payload-firewall.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse firewall() {
    return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
        "Java v674",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(19, 21),
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.rules(14, 16),
        List.of(
            "value-supply-adapter-preflight-payload-firewall-slice-20-21",
            "value-supply-adapter-preflight-payload-firewall-runtime-payload-blocked",
            "value-supply-adapter-preflight-payload-firewall-import-preview-locked",
            "value-supply-adapter-preflight-payload-firewall-no-state-write"));
  }
}
