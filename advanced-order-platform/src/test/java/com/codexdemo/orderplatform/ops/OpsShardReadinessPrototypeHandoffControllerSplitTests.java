package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeHandoffControllerSplitTests {

  @Test
  void keepsPrototypeHandoffRoutesInDedicatedController() {
    assertThat(OpsShardReadinessPrototypeHandoffController.class)
        .hasAnnotation(RestController.class);

    assertThat(getMappings(OpsShardReadinessPrototypeHandoffController.class))
        .containsExactlyInAnyOrder(
            PrototypeRoutes.HANDOFF_CATALOG,
            PrototypeRoutes.HANDOFF_ENDPOINT_INVENTORY,
            PrototypeRoutes.HANDOFF_BOUNDARY_MATRIX,
            PrototypeRoutes.HANDOFF_CONSUMER_CHECKLIST,
            PrototypeRoutes.HANDOFF_READ_WINDOW_CHECKLIST,
            PrototypeRoutes.HANDOFF_DIGEST_MANIFEST,
            PrototypeRoutes.HANDOFF_CI_MANIFEST,
            PrototypeRoutes.HANDOFF_ARCHIVE_MANIFEST,
            PrototypeRoutes.HANDOFF_OPERATOR_SIGNOFF,
            PrototypeRoutes.HANDOFF_CLOSEOUT);
    assertThat(getMappings(OpsShardReadinessController.class))
        .doesNotContain(
            "/shard-readiness/prototype-handoff-catalog",
            "/shard-readiness/prototype-handoff-endpoint-inventory",
            "/shard-readiness/prototype-handoff-boundary-matrix",
            "/shard-readiness/prototype-handoff-consumer-verification-checklist",
            "/shard-readiness/prototype-handoff-read-window-checklist",
            "/shard-readiness/prototype-handoff-digest-manifest",
            "/shard-readiness/prototype-handoff-ci-manifest",
            "/shard-readiness/prototype-handoff-archive-manifest",
            "/shard-readiness/prototype-handoff-operator-signoff-packet",
            "/shard-readiness/prototype-handoff-closeout");
    assertThat(getMappings(OpsShardReadinessPrototypeController.class))
        .doesNotContain(
            PrototypeRoutes.HANDOFF_CATALOG,
            PrototypeRoutes.HANDOFF_ENDPOINT_INVENTORY,
            PrototypeRoutes.HANDOFF_BOUNDARY_MATRIX,
            PrototypeRoutes.HANDOFF_CONSUMER_CHECKLIST,
            PrototypeRoutes.HANDOFF_READ_WINDOW_CHECKLIST,
            PrototypeRoutes.HANDOFF_DIGEST_MANIFEST,
            PrototypeRoutes.HANDOFF_CI_MANIFEST,
            PrototypeRoutes.HANDOFF_ARCHIVE_MANIFEST,
            PrototypeRoutes.HANDOFF_OPERATOR_SIGNOFF,
            PrototypeRoutes.HANDOFF_CLOSEOUT);
  }

  private static String[] getMappings(Class<?> controllerClass) {
    return Arrays.stream(controllerClass.getDeclaredMethods())
        .map(method -> method.getAnnotation(GetMapping.class))
        .filter(mapping -> mapping != null && mapping.value().length > 0)
        .map(mapping -> mapping.value()[0])
        .toArray(String[]::new);
  }
}
