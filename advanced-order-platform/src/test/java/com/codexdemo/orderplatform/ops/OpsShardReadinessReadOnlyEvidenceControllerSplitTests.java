package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class OpsShardReadinessReadOnlyEvidenceControllerSplitTests {

    @Test
    void keepsReadOnlyCatalogRoutesInDedicatedController() {
        assertThat(OpsShardReadinessReadOnlyEvidenceController.class)
                .hasAnnotation(RestController.class);

        assertThat(getMappings(OpsShardReadinessReadOnlyEvidenceController.class))
                .containsExactlyInAnyOrder(
                        "/read-only-evidence-catalog",
                        "/read-only-evidence-catalog-handoff",
                        "/read-only-evidence-catalog-handoff-verification"
                );
        assertThat(getMappings(OpsShardReadinessEvidenceController.class))
                .doesNotContain(
                        "/read-only-evidence-catalog",
                        "/read-only-evidence-catalog-handoff",
                        "/read-only-evidence-catalog-handoff-verification"
                );
    }

    private static String[] getMappings(Class<?> controllerClass) {
        return Arrays.stream(controllerClass.getDeclaredMethods())
                .map(method -> method.getAnnotation(GetMapping.class))
                .filter(mapping -> mapping != null && mapping.value().length > 0)
                .map(mapping -> mapping.value()[0])
                .toArray(String[]::new);
    }
}
