package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegistryResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void registryOutputIsFrozen() throws Exception {
    var response = ExecutionTestData.registry();

    assertThat(
            List.of(
                response.sourcePlans().size(),
                response.readTargets().size(),
                response.gateChecks().size(),
                response.boundaryRules().size(),
                response.ciBatches().size(),
                response.archiveRequirements().size(),
                response.operatorHandoffs().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(5, 5, 20, 10, 4, 6, 5, 6, 20);
    assertThat(sha256(response))
        .isEqualTo("8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
