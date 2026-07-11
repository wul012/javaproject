package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeControllerSplitTests {

  @Test
  void keepsPrototypeRoutesInDedicatedController() {
    assertThat(OpsShardReadinessPrototypeController.class).hasAnnotation(RestController.class);

    assertThat(getMappings(OpsShardReadinessPrototypeController.class))
        .containsExactlyInAnyOrder(
            PrototypeRoutes.CATALOG,
            PrototypeRoutes.FIXTURE_ECHO,
            PrototypeRoutes.FIELD_ALIGNMENT,
            PrototypeRoutes.READ_ONLY_BRIDGE,
            PrototypeRoutes.CLEANUP_BRIDGE,
            PrototypeRoutes.READ_WINDOW_HANDOFF,
            PrototypeRoutes.CONSUMER_GATE_PACKET,
            PrototypeRoutes.OPERATOR_CI_HANDOFF,
            PrototypeRoutes.AUDIT_DIGEST,
            PrototypeRoutes.CLOSEOUT);
    assertThat(getMappings(OpsShardReadinessController.class))
        .doesNotContain(
            "/shard-readiness/prototype-catalog",
            "/shard-readiness/prototype-fixture-echo",
            "/shard-readiness/prototype-field-alignment",
            "/shard-readiness/prototype-read-only-integration-bridge",
            "/shard-readiness/prototype-route-cleanup-bridge",
            "/shard-readiness/prototype-read-window-handoff",
            "/shard-readiness/prototype-consumer-gate-packet",
            "/shard-readiness/prototype-operator-ci-handoff",
            "/shard-readiness/prototype-audit-digest",
            "/shard-readiness/prototype-closeout");
  }

  private static String[] getMappings(Class<?> controllerClass) {
    return Arrays.stream(controllerClass.getDeclaredMethods())
        .map(method -> method.getAnnotation(GetMapping.class))
        .filter(mapping -> mapping != null && mapping.value().length > 0)
        .map(mapping -> mapping.value()[0])
        .toArray(String[]::new);
  }
}
