package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

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
  void acceptancePackageOutputIsFrozen() throws Exception {
    var response = PackageTestData.registry();

    assertThat(
            List.of(
                response.sourceSnapshots().size(),
                response.lineage().size(),
                response.decisions().size(),
                response.archiveItems().size(),
                response.reviewItems().size(),
                response.ciEvidence().size(),
                response.runtimeBoundaries().size(),
                response.nextChangeRules().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 3, 6, 5, 5, 5, 7, 6, 9, 9, 40);
    assertThat(sha256(response))
        .isEqualTo("2679ebdc83c27789a17d52d8d255f96ebda0cb081e9f37295b9953613ecca51a");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
