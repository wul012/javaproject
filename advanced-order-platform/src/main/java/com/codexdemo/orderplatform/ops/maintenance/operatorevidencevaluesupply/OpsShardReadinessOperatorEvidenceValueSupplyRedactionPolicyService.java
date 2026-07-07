package com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_REDACTION_POLICY;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-redaction-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse policy() {
    return OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.response(
        "Java v640",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(4, 8),
        List.of(
            "value-supply-redaction-policy-slice-5-8",
            "value-supply-redaction-credential-values-blocked",
            "value-supply-redaction-raw-endpoints-blocked",
            "value-supply-redaction-secret-material-blocked"));
  }
}
