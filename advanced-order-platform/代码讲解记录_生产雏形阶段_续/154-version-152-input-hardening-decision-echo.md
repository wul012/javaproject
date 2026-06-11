> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v152 input-hardening decision echo

## Scope

Java v152 consumes the Node v329 implementation candidate gate / input-hardening decision from:

```text
D:\nodeproj\orderops-node\d\329\evidence\implementation-candidate-gate-input-hardening-decision-v329-http.json
```

Node v329 requires stable Java evidence export before Node v330 can evaluate upstream alignment. Java v151 already added `evidenceExportHint`; v152 now echoes the formal Node v329 decision and connects that hint into the current rehearsal response.

## Echo Contract

The response schema advances to:

```text
java-release-approval-rehearsal-response-schema.v52
```

The new `inputHardeningDecisionEcho` block records:

```text
sourceNodeVersion: Node v329
sourceCandidateGateDecision: require-input-export-hardening-before-disabled-runtime-design
satisfiedJavaInputHardeningRequirements: java-stable-evidence-export
consumedEvidenceExportMode: stable-read-only-current-response
```

It preserves the Node v329 stop conditions: no credential value, no raw endpoint URL parsing, no provider/client, no HTTP/TCP or managed audit connection, no Java SQL/ledger/schema writes, no deployment or rollback, and no automatic upstream startup.

## Code Shape

The implementation is deliberately small:

```text
OpsEvidenceService.java: 1472 lines
ReleaseApprovalRehearsalResponseBuilder.java: 564 lines
ReleaseApprovalRehearsalInputHardeningDecisionEchoBuilder.java: 96 lines
ReleaseApprovalRehearsalResponseRecords.java: 704 lines
```

The main response builder only wires the new echo. The new builder reuses `RehearsalEvidenceExportHint`, so the v151 export hint remains the single source for the current stable JSON export mode.

## Tests

Updated coverage checks:

```text
OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests
OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests
OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests
```

Existing schema assertions were advanced to v52. The HTTP integration test verifies the new JSON field and confirms runtime shell design/implementation, credential, network, ledger, and upstream-start flags stay false.

## Verification

```powershell
mvn -q -DskipTests compile
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests,OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests" test
mvn -q test
```

Result: passed. The full test run emitted the existing Testcontainers Docker environment warning, but Maven exited with code 0.

## Archive

```text
d/152/解释/说明.md
d/152/图片/mcp-v152-input-hardening-decision-echo.png
```
