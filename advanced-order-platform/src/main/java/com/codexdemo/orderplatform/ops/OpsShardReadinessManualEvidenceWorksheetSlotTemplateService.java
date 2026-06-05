package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessManualEvidenceWorksheetSlotTemplateService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE;
    static final String PROFILE =
            "java-shard-readiness-manual-evidence-worksheet-slot-template.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessManualEvidenceWorksheetResponse template() {
        return OpsShardReadinessManualEvidenceWorksheetSupport.response(
                "Java v562",
                ENDPOINT,
                PROFILE,
                List.of(
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "slot-identity",
                                "operator-worksheet-maintainer",
                                "slot id, label, owner, and target scope are structural fields",
                                OpsShardReadinessManualEvidenceWorksheetCatalogService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "blank-value-state",
                                "runtime-boundary-reviewer",
                                "manual value and imported value remain empty",
                                OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "operator-note",
                                "operator-worksheet-maintainer",
                                "note field is descriptive and cannot unlock execution",
                                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.ENDPOINT
                        ),
                        OpsShardReadinessManualEvidenceWorksheetSupport.item(
                                "validation-hint",
                                "validation-reviewer",
                                "expected format is a hint, not a value",
                                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT
                        )
                ),
                List.of(
                        "slot-template-blank-values-only",
                        "slot-template-no-secret-placeholder",
                        "slot-template-target-scope-required"
                )
        );
    }
}
