package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalogTests {

  @Test
  void catalogsTwentyFiveApprovalPacketItemsMappedToValueSupplySlots() {
    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems())
        .hasSize(25)
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
                ::code)
        .startsWith(
            "VALUE_SUPPLY_APPROVAL_PACKET_01_PACKET_ID",
            "VALUE_SUPPLY_APPROVAL_PACKET_02_OPERATOR_IDENTITY_ALIAS")
        .endsWith("VALUE_SUPPLY_APPROVAL_PACKET_25_CLOSEOUT_LOCKS_HELD");

    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
                ::sourceEnvelopeSlot)
        .startsWith("VALUE_SUPPLY_01_ENVELOPE_ID")
        .endsWith("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");

    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems())
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
                ::sourceEndpoint)
        .allMatch(
            endpoint ->
                endpoint.startsWith(
                    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
                        .BASE_PATH));
  }

  @Test
  void carriesEvidenceFileAndSnippetIdsForEveryApprovalPacketItem() {
    assertThat(OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems())
        .allSatisfy(
            item -> {
              assertThat(item.evidenceFileId()).isNotBlank();
              assertThat(item.evidenceSnippetId()).isNotBlank();
              assertThat(item.blockedReason()).isNotBlank();
            });
  }

  @Test
  void returnsStageSlicesForFocusedApprovalPreflightServices() {
    assertThat(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(8, 12))
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
                ::packetStage)
        .containsOnly("redaction");

    assertThat(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(19, 22))
        .extracting(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse.ApprovalItem
                ::packetStage)
        .containsOnly("zero-count");
  }
}
