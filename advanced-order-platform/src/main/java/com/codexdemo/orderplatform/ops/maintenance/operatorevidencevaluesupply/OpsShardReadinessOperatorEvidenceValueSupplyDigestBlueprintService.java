package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_DIGEST_BLUEPRINT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-digest-blueprint.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse blueprint() {
    return OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.response(
        "Java v654",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots(),
        List.of(
            "value-supply-digest-blueprint-slot-count-25",
            "value-supply-digest-blueprint-no-value-hash",
            "value-supply-digest-blueprint-provenance-before-import",
            "value-supply-digest-blueprint-lock-flags-covered"));
  }
}
