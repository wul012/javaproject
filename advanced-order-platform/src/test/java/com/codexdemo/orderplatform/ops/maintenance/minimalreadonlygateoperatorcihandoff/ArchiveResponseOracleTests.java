package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

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

class ArchiveResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void archiveRegistryOutputIsFrozen() throws Exception {
    var response = ArchiveTestData.registry();

    assertThat(
            List.of(
                response.sourceHandoffSnapshots().size(),
                response.artifactVerifications().size(),
                response.operatorLaneVerifications().size(),
                response.ciBatchVerifications().size(),
                response.boundaryVerifications().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 6, 4, 5, 8, 6, 6, 21);
    assertThat(sha256(response))
        .isEqualTo("1b9fd78f3ac4d3905d027f2c5b3d04c15a768b0b17b45497d583606ead7a5321");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
