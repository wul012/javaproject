package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class OpsShardReadinessMinimalReadOnlyGateExecutionGateRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionGateRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection render(
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.GateCheck> gateChecks
    ) {
        Map<String, List<String>> grouped = new LinkedHashMap<>();
        gateChecks.forEach(check -> grouped.computeIfAbsent(check.group(), ignored -> new ArrayList<>())
                .add(check.code() + "=" + check.evidence()));
        List<String> lines = new ArrayList<>();
        lines.add("gate-check-count=" + gateChecks.size());
        grouped.forEach((group, checks) -> lines.add(group + ": " + String.join("; ", checks)));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "Gate Checks",
                lines
        );
    }
}
