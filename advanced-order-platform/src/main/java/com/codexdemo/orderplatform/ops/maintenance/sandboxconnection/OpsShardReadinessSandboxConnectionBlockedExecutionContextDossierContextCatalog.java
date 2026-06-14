package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.ReleaseApprovalRehearsalResponse;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog() {}

  static List<OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ContextField>
      fields(ReleaseApprovalRehearsalResponse rehearsal) {
    var context = rehearsal.requestContext();
    return List.of(
        field("requestId", context.requestId(), context.requestIdSource()),
        field("operatorIdentity", context.operatorIdentity(), context.operatorIdentitySource()),
        field(
            "auditCorrelationId", context.auditCorrelationId(), context.auditCorrelationSource()));
  }

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
              .NormalizationRule>
      rules() {
    return List.of(
        rule("trim-blank-inputs", "ContextHeaderField.normalizeValue trims supplied values."),
        rule(
            "blank-becomes-placeholder",
            "Blank or null context values become explicit placeholders."),
        rule("source-labels-preserved", "Header source labels stay visible for Node comparison."),
        rule("missing-warnings-archived", "Missing context warnings are surfaced, not suppressed."),
        rule(
            "read-only-flags",
            "Context normalization does not authenticate, persist, or write approvals."));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .ContextField
      field(String name, String value, String source) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .ContextField(
        name, value, source, !"NOT_SUPPLIED".equals(source), value != null && !value.isBlank());
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .NormalizationRule
      rule(String name, String evidence) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .NormalizationRule(name, evidence, true);
  }
}
