package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

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

class PackageResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void consumerPackageOutputIsFrozen() throws Exception {
    var response = ConsumerPackageTestData.registry();

    assertThat(
            List.of(
                response.sourceDigestSnapshots().size(),
                response.manifest().size(),
                response.consumerAudiences().size(),
                response.packageSections().size(),
                response.acceptanceCriteria().size(),
                response.ciMatrix().size(),
                response.boundaryLocks().size(),
                response.handoffChecklist().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 5, 4, 5, 5, 5, 8, 5, 8, 9, 28);
    assertThat(sha256(response))
        .isEqualTo("1ae92cfe8926ecb9ae772c8eec70dd8cddfbc1b0654e11685ef6304249803c60");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
