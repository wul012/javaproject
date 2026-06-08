package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog {

    private OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection> allSections() {
        List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection> sections =
                new ArrayList<>();
        sections.addAll(OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog.sourceSections());
        sections.addAll(OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog.comparisonSections());
        sections.addAll(OpsShardReadinessComparedEvidenceCandidateBlueprintPolicySectionCatalog.policySections());
        sections.addAll(OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog.closeoutSections());
        return List.copyOf(sections);
    }
}
