package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessPrototypeConsumerGateControllerSplitTests {

  @Test
  void keepsPrototypeConsumerGateRoutesInDedicatedController() {
    assertThat(OpsShardReadinessPrototypeConsumerGateController.class)
        .hasAnnotation(RestController.class);

    assertThat(getMappings(OpsShardReadinessPrototypeConsumerGateController.class))
        .containsExactlyInAnyOrder(
            PrototypeRoutes.CONSUMER_CATALOG,
            PrototypeRoutes.CONSUMER_SOURCE_INVENTORY,
            PrototypeRoutes.CONSUMER_FIELD_CHECKLIST,
            PrototypeRoutes.CONSUMER_ROUTE_PREVIEW,
            PrototypeRoutes.CONSUMER_BOUNDARY_MATRIX,
            PrototypeRoutes.CONSUMER_DIGEST_ACCEPTANCE,
            PrototypeRoutes.CONSUMER_CI_PLAN,
            PrototypeRoutes.CONSUMER_ARCHIVE_MANIFEST,
            PrototypeRoutes.CONSUMER_OPERATOR_SIGNOFF,
            PrototypeRoutes.CONSUMER_CLOSEOUT);
    assertThat(getMappings(OpsShardReadinessController.class))
        .doesNotContain(
            "/shard-readiness/prototype-consumer-gate-catalog",
            "/shard-readiness/prototype-consumer-gate-source-inventory",
            "/shard-readiness/prototype-consumer-gate-minimal-field-checklist",
            "/shard-readiness/prototype-consumer-gate-route-topology-preview",
            "/shard-readiness/prototype-consumer-gate-boundary-matrix",
            "/shard-readiness/prototype-consumer-gate-digest-acceptance",
            "/shard-readiness/prototype-consumer-gate-ci-batch-plan",
            "/shard-readiness/prototype-consumer-gate-archive-manifest",
            "/shard-readiness/prototype-consumer-gate-operator-signoff",
            "/shard-readiness/prototype-consumer-gate-closeout");
    assertThat(getMappings(OpsShardReadinessPrototypeHandoffController.class))
        .doesNotContain(
            PrototypeRoutes.CONSUMER_CATALOG,
            PrototypeRoutes.CONSUMER_SOURCE_INVENTORY,
            PrototypeRoutes.CONSUMER_FIELD_CHECKLIST,
            PrototypeRoutes.CONSUMER_ROUTE_PREVIEW,
            PrototypeRoutes.CONSUMER_BOUNDARY_MATRIX,
            PrototypeRoutes.CONSUMER_DIGEST_ACCEPTANCE,
            PrototypeRoutes.CONSUMER_CI_PLAN,
            PrototypeRoutes.CONSUMER_ARCHIVE_MANIFEST,
            PrototypeRoutes.CONSUMER_OPERATOR_SIGNOFF,
            PrototypeRoutes.CONSUMER_CLOSEOUT);
  }

  private static String[] getMappings(Class<?> controllerClass) {
    return Arrays.stream(controllerClass.getDeclaredMethods())
        .map(method -> method.getAnnotation(GetMapping.class))
        .filter(mapping -> mapping != null && mapping.value().length > 0)
        .map(mapping -> mapping.value()[0])
        .toArray(String[]::new);
  }
}
