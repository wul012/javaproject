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
                response.sourceRegistrySnapshots().size(),
                response.artifactVerifications().size(),
                response.readTargetVerifications().size(),
                response.gateCheckVerifications().size(),
                response.boundaryVerifications().size(),
                response.ciBatchVerifications().size(),
                response.operatorHandoffVerifications().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 6, 5, 20, 10, 4, 5, 7, 6, 20);
    assertThat(sha256(response))
        .isEqualTo("d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
