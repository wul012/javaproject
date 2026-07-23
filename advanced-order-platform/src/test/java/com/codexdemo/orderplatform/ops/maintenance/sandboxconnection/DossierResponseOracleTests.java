package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

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

class DossierResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void dossierOutputIsFrozen() throws Exception {
    var response = DossierTestData.dossier();

    assertThat(
            List.of(
                response.sourceReceipts().size(),
                response.contextFields().size(),
                response.normalizationRules().size(),
                response.preconditionEvidence().size(),
                response.boundarySnapshots().size(),
                response.executionGuards().size(),
                response.warningEchoes().size(),
                response.downstreamIntakeGates().size(),
                response.verificationGates().size(),
                response.handoffNotes().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 3, 5, 6, 5, 12, 4, 5, 10, 4, 9, 21);
    assertThat(sha256(response))
        .isEqualTo("f4ff835d241fd99fd1113f926f542c6954ab22f409ff43ef78b6e34f4413fad2");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
