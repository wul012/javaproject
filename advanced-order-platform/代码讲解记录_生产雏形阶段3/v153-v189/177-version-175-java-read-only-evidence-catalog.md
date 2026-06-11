> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 177. Java v175 read-only evidence catalog

## Background

After v174 introduced a dedicated shard-readiness echo, Java needed a compact catalog that lets later consumers find the versioned evidence without depending on Node to advance each step.

## Implementation

- Added `OpsShardReadinessReadOnlyEvidenceCatalogService`.
- Added `OpsShardReadinessReadOnlyEvidenceCatalogResponse`.
- Added `GET /api/v1/ops/shard-readiness/read-only-evidence-catalog` to the evidence controller.
- Added the v175 fixture endpoint to `OpsShardReadinessEvidenceEndpoints`.
- Added service and integration tests for the catalog.
- Updated ops evidence endpoint assertions so `/api/v1/ops/evidence` exposes the new read-only catalog.
- Added `e/175/` JSON and HTML archive files.

## Boundaries

- No change to `/api/v1/ops/shard-readiness` v153 fields.
- No write routing.
- No active shard router.
- No credential or raw endpoint reads.
- No managed audit connection.
- No Java or mini-kv process start/stop.
- No deployment or rollback path.

## Verification

- Focused Maven tests for catalog, endpoint listing, and ops evidence.
- Static JSON parse check for the v175 fixture and evidence archive.
- Browser snapshot and screenshot for the v175 archive page.
