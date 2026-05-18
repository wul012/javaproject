# Start Here: Java OrderOps Platform

## What this project does

This repository implements the Java backend of the multi-repo order operations platform. It focuses on release approval, rehearsal, and managed-audit receipt chains.

It contains:
- ReleaseApprovalRehearsalResponse and builder chains
- Managed-audit receipt creation and verification
- Release input normalization
- Response contract preservation
- Rehearsal and sandbox verification

## Why it matters

The repo demonstrates mature Java backend engineering:

- Complex builder chains for rehearsal and approval
- Contract-preserving refactoring (v97 split builder chain)
- Managed-audit and verification evidence preserved for each operation
- Read-only boundaries and HTTP contract safety

For outside readers, it shows how to manage complex backend approval flows safely while keeping all contracts and digests auditable.

## How to run it

Typical Maven commands:

```powershell
mvn -q -DskipTests compile
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests" test
mvn -q -DskipTests package
```

The README contains more instructions on specific rehearsal/approval scenarios.

## Top technical highlights

1. **Release approval rehearsal builder chain**
   - Splits large monolithic builder into `NormalizedRequest`, `RehearsalSections`, and `ManagedAuditReceiptChain`

2. **Contract preservation**
   - Response fields, digest, read-only boundaries, and HTTP header behavior remain unchanged

3. **Sandbox and dry-run verification**
   - Rehearsal and sandbox actions separate from production approval

## Latest version summary

Current focus: **v97 refactor release approval rehearsal builder chain**.

It normalizes inputs, splits sections, and separates managed-audit receipts from the main builder while keeping external contracts and digests unchanged.

## Where to look next

- `README.md` — detailed instructions and versioned changes
- `src/main/java/` — core classes for approval, builder chains, and audits
- `c/` — versioned explanation documents
- `tests/` — unit and integration tests
