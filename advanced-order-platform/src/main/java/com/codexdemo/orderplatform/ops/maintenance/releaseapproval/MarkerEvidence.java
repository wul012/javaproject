package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.List;
import java.util.Objects;

record MarkerEvidence(
    String warningInputName,
    List<String> boundaryInputNames,
    List<String> proofClaims,
    List<String> nodeActions) {

  MarkerEvidence {
    warningInputName = Objects.requireNonNull(warningInputName);
    boundaryInputNames = List.copyOf(boundaryInputNames);
    proofClaims = List.copyOf(proofClaims);
    nodeActions = List.copyOf(nodeActions);
  }

  List<String> warningInputNames() {
    return ReleaseApprovalDigestSupport.warningInputNames(warningInputName);
  }

  List<String> warningLines(List<String> warnings) {
    return ReleaseApprovalDigestSupport.warningLines(warningInputName, warnings);
  }
}
