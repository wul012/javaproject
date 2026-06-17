package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_PROVENANCE_REQUIREMENT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-provenance-requirement.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse requirement() {
    return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
        "Java v644",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(12, 16),
        List.of(
            "value-supply-provenance-requirement-slice-13-16",
            "value-supply-provenance-source-id-required",
            "value-supply-provenance-evidence-file-required",
            "value-supply-provenance-raw-endpoint-alias-only"));
  }
}
