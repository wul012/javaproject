> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 178. Java v176 read-only evidence catalog snapshot freeze

## Background

Java v175 introduced a versioned read-only evidence catalog. The first implementation read the shared endpoint registry directly. That was correct for v175, but it would make v175 drift once later versions add new endpoints.

## Implementation

- Added `OpsShardReadinessReadOnlyEvidenceCatalogSnapshot`.
- Froze the v175 live endpoint list at 20 entries.
- Froze the v175 fixture endpoint list at 20 entries.
- Updated `OpsShardReadinessReadOnlyEvidenceCatalogService` to read the frozen v175 snapshot.
- Added `OpsShardReadinessReadOnlyEvidenceCatalogSnapshotTests`.
- Added `e/176/` evidence archive files.

## Engineering Boundary

This is an internal stability hardening step, not a new execution surface. Future endpoint growth can happen in the global registry without mutating the already versioned v175 catalog response.

## Verification

- Compile.
- Focused catalog snapshot and catalog service tests.
- Full `mvn -q test`.
- Static JSON parse check.
- Browser snapshot and screenshot for the v176 archive page.
