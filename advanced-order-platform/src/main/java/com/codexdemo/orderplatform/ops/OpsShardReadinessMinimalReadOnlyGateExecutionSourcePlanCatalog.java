package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry>
            sourcePlans() {
        return List.of(
                entry(
                        "Node v349",
                        "minimal read-only integration smoke rerun archive",
                        "previous smoke lane",
                        "5/5 read targets passed",
                        5,
                        5
                ),
                entry(
                        "Node v364",
                        "minimal read-only integration regular gate",
                        "regular gate source",
                        "34/34 checks passed",
                        34,
                        34
                ),
                entry(
                        "Node v365",
                        "regular gate archive and CI/operator check",
                        "CI batch source",
                        "40/40 checks passed",
                        40,
                        40
                ),
                entry(
                        "Node v366",
                        "explicit read-window gate execution decision",
                        "external read-window decision",
                        "22/22 checks passed",
                        22,
                        22
                ),
                entry(
                        "Node v367",
                        "minimal read-only integration gate execution",
                        "current execution evidence",
                        "5/5 read targets and 20/20 checks passed",
                        20,
                        20
                )
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry
            entry(
                    String nodeVersion,
                    String title,
                    String role,
                    String result,
                    int expectedChecks,
                    int passedChecks
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.SourcePlanEntry(
                nodeVersion,
                title,
                role,
                result,
                expectedChecks,
                passedChecks
        );
    }
}
