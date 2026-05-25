# Java v151 release approval evidence export hint

## Scope

Java v151 follows the active Node roadmap in:

```text
D:\nodeproj\orderops-node\docs\plans2\v328-post-final-prerequisite-closure-roadmap.md
```

Node v329 has not been generated yet, so this version deliberately avoids adding another large echo support family. The Java side instead adds a stable read-only export hint to the current release approval rehearsal response, giving the next Node step a predictable JSON artifact target.

## Evidence Export Hint

The rehearsal response schema advances to:

```text
java-release-approval-rehearsal-response-schema.v51
```

The new `evidenceExportHint` block declares:

```text
currentJsonEndpoint: /api/v1/ops/release-approval-rehearsal
sourceEvidenceEndpoint: /api/v1/ops/evidence
preferredArtifactName: release-approval-rehearsal-current.json
exportMode: stable-read-only-current-response
```

It also records that the export is read-only, stable for current-response consumption, and still allows a historical fallback. The prohibited actions explicitly reject credential value reads, raw endpoint URL parsing, HTTP/TCP execution, approval ledger writes, schema migrations, deployment/rollback execution, and automatic upstream startup.

## Code Shape

The change is intentionally small:

```text
OpsEvidenceService.java: 1457 lines
ReleaseApprovalRehearsalResponseBuilder.java: 559 lines
ReleaseApprovalRehearsalEvidenceExportHintBuilder.java: 67 lines
ReleaseApprovalRehearsalResponseRecords.java: 673 lines
```

No new 600+ line echo support file was added. The response record owns the typed contract, the small export hint builder owns the constant assembly and named boundary flags, and the verification hint catalog exposes `evidenceExportHint` as a top-level schema field.

## Tests

Updated the rehearsal summary, verification hint, and live aggregation verification coverage so the new block is checked through both service-level and HTTP JSON response surfaces. Existing response schema assertions now expect v51 while the v150 abort/rollback receipt keeps its historical v50 source schema.

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
d/151/解释/说明.md
d/151/图片/mcp-v151-release-approval-evidence-export-hint.png
```
