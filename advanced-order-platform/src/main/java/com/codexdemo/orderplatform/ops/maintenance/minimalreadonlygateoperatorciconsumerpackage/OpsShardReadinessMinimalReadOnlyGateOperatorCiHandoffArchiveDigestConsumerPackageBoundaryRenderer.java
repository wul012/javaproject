package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .BoundaryLock>
              locks) {
    List<String> lines = new ArrayList<>();
    lines.add("boundary-lock-count=" + locks.size());
    locks.forEach(
        lock -> lines.add(lock.code() + " | locked=" + lock.locked() + " | " + lock.reason()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
        .section("Boundary Locks", lines);
  }
}
