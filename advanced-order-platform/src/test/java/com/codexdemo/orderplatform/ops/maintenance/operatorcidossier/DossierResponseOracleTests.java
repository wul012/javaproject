package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

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
    var response = DossierTestData.registry();

    assertThat(
            List.of(
                response.sourcePackageSnapshots().size(),
                response.provenance().size(),
                response.sectionDigests().size(),
                response.audienceRoutes().size(),
                response.ciLanes().size(),
                response.acceptanceGates().size(),
                response.boundaryAudits().size(),
                response.releaseChecklist().size(),
                response.handoffReceipts().size(),
                response.scorecard().size(),
                response.markdownSections().size(),
                response.checks().size()))
        .containsExactly(1, 6, 9, 4, 5, 5, 8, 5, 4, 10, 10, 34);
    assertThat(sha256(response))
        .isEqualTo("f9ee01616f66f941914558105fbf7fe2652deb82891058fde433a06dcaf92a92");
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
