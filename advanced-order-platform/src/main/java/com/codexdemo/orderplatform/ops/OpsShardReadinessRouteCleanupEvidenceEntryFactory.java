package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanupEvidenceResponse.Entry;

final class OpsShardReadinessRouteCleanupEvidenceEntryFactory {

    private OpsShardReadinessRouteCleanupEvidenceEntryFactory() {
    }

    static Entry entry(
            int javaVersion,
            String sourceNodePlan,
            String phase,
            String evidenceType,
            String evidenceSlug
    ) {
        return new Entry(
                javaVersion,
                sourceNodePlan,
                phase,
                evidenceType,
                "e/" + javaVersion + "/evidence/" + evidenceSlug + ".json",
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "passed"
        );
    }
}