package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.security.MessageDigest;
import java.util.HexFormat;
import org.junit.jupiter.api.Test;

class RehearsalResponseOracleTests extends ReleaseApprovalRehearsalTestSupport {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .addModule(new JavaTimeModule())
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void defaultResponseIsFrozen() throws Exception {
    assertThat(sha256(defaultRehearsal()))
        .isEqualTo("48dc64dd2385de0ad0b98f114be157c98b19012abcfde8384ff6e237248b8550");
  }

  @Test
  void headerBackedResponseIsFrozen() throws Exception {
    var response =
        readOnlyFixtureService().releaseApprovalRehearsal(headerBackedRehearsalRequest());

    assertThat(sha256(response))
        .isEqualTo("c64e2fac8194ab2f70ef5bbd603a9a92dd0ea1a9ae75459f386c7fa6373258cc");
  }

  private static String sha256(ReleaseApprovalRehearsalResponse response) throws Exception {
    JsonNode snapshot = JSON.valueToTree(response);
    ((com.fasterxml.jackson.databind.node.ObjectNode) snapshot).remove("sampledAt");
    ((com.fasterxml.jackson.databind.node.ObjectNode) snapshot.get("liveReadinessHint"))
        .remove("serverTimestamp");
    byte[] json = JSON.writeValueAsBytes(snapshot);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
