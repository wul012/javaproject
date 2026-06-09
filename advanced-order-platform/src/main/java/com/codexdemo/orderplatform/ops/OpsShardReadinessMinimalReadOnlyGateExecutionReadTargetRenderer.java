package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection render(
            List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.ReadTarget> readTargets
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("read-target-count=" + readTargets.size());
        readTargets.forEach(target -> lines.add(String.join(
                " | ",
                target.target(),
                target.owner(),
                target.protocol(),
                target.addressHandle(),
                target.commandOrRoute(),
                "status=" + target.status()
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.section(
                "Read Targets",
                lines
        );
    }
}
