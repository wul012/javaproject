package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MarkerEvidenceTests {

  @Test
  void snapshotsMetadataAndFormatsWarnings() {
    var boundaryInputs = new ArrayList<>(List.of("markerDigest"));
    var proofClaims = new ArrayList<>(List.of("executionAllowed=false"));
    var nodeActions = new ArrayList<>(List.of("Keep execution disabled"));
    var evidence = new MarkerEvidence("markerWarnings", boundaryInputs, proofClaims, nodeActions);

    boundaryInputs.add("lateBoundary");
    proofClaims.clear();
    nodeActions.add("lateAction");

    assertThat(evidence.warningInputNames()).containsExactly("markerWarnings");
    assertThat(evidence.boundaryInputNames()).containsExactly("markerDigest");
    assertThat(evidence.proofClaims()).containsExactly("executionAllowed=false");
    assertThat(evidence.nodeActions()).containsExactly("Keep execution disabled");
    assertThat(evidence.warningLines(List.of("SOURCE_NOT_READY")))
        .containsExactly("markerWarnings=[SOURCE_NOT_READY]");
  }
}
