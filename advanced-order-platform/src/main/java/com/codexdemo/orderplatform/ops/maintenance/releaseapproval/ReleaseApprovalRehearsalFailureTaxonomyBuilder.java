package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import com.codexdemo.orderplatform.ops.OpsEvidenceResponse;
import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalRehearsalFailureTaxonomyBuilder {

  ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy build(
      OpsEvidenceResponse evidence,
      String normalizedRequestId,
      String normalizedOperatorIdentity,
      String normalizedAuditCorrelationId) {
    boolean upstreamReady =
        evidence.readOnlyWindow().readyForReadOnlyLiveProbe()
            && evidence.healthProbe().liveProbeRequiredForPass()
            && !evidence.healthProbe().staticSampleOnly()
            && evidence.readOnly();
    boolean authContextComplete = normalizedRequestId != null && normalizedOperatorIdentity != null;
    boolean auditCorrelationPresent = normalizedAuditCorrelationId != null;

    List<String> failureCategories = new ArrayList<>();
    List<String> taxonomyWarnings = new ArrayList<>();

    if (!upstreamReady) {
      failureCategories.add("UPSTREAM_READINESS_WARNING");
      taxonomyWarnings.add("JAVA_READ_ONLY_UPSTREAM_NOT_READY");
    }
    if (!authContextComplete) {
      failureCategories.add("AUTH_CONTEXT_WARNING");
      taxonomyWarnings.add("REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING");
    }
    if (!auditCorrelationPresent) {
      failureCategories.add("AUDIT_CORRELATION_WARNING");
      taxonomyWarnings.add("AUDIT_CORRELATION_ID_MISSING");
    }
    failureCategories.add("READ_ONLY_EXECUTION_BLOCKED");
    taxonomyWarnings.add("REHEARSAL_REMAINS_READ_ONLY");

    return new ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy(
        ReleaseApprovalContractConstants.RELEASE_APPROVAL_REHEARSAL_FAILURE_TAXONOMY_VERSION,
        readinessStatus(upstreamReady),
        readinessStatus(authContextComplete),
        readinessStatus(auditCorrelationPresent),
        upstreamReady,
        authContextComplete,
        auditCorrelationPresent,
        true,
        false,
        List.copyOf(failureCategories),
        List.copyOf(taxonomyWarnings));
  }

  private String readinessStatus(boolean ready) {
    if (ready) {
      return "READY";
    }
    return "WARNING";
  }
}
