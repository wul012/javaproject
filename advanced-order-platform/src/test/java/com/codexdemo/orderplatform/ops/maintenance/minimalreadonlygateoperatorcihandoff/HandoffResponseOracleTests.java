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

class HandoffResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void handoffRegistryOutputIsFrozen() throws Exception {
    var response = HandoffTestData.registry();

    assertThat(
            List.of(
                response.sourceArchiveSnapshots().size(),
                response.operatorLanes().size(),
                response.ciBatches().size(),
                response.boundaryLocks().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 4, 5, 8, 5, 5, 15);
    assertThat(sha256(response))
        .isEqualTo("4fc6dc6069cff5bc40ee0934bc1ed9133ff50bcfe7c3c5940429e83cf4287ab0");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
