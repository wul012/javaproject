package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

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

class DigestResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void digestRegistryOutputIsFrozen() throws Exception {
    var response = ArchiveDigestTestData.registry();

    assertThat(
            List.of(
                response.sourceArchiveSnapshots().size(),
                response.digestSections().size(),
                response.consumerPackets().size(),
                response.replayInstructions().size(),
                response.boundaryLocks().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 6, 4, 5, 8, 6, 6, 22);
    assertThat(sha256(response))
        .isEqualTo("2c0d238ec99c234a1c679eb4b7de2d37174c0a088f31b61d6d516949a5581ba4");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
