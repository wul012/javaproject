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

class ManifestResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void manifestOutputIsFrozen() throws Exception {
    var response = ManifestTestData.manifest();

    assertThat(
            List.of(
                response.sourceReceipts().size(),
                response.splitModules().size(),
                response.evidenceReferences().size(),
                response.precheckFields().size(),
                response.boundaryGuards().size(),
                response.codeHealthGates().size(),
                response.verificationGates().size(),
                response.handoffNotes().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 12, 5, 7, 17, 6, 10, 4, 8, 22);
    assertThat(sha256(response))
        .isEqualTo("03541a7ae5e46684151a3829458dde56453a4acc5ff1f397ad343892fc7656e2");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
