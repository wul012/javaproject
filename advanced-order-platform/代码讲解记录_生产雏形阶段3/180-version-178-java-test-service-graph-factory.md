# 180. Java v178 test service graph factory

## Background

The v175 and v177 service tests repeated a long shard-readiness runtime service graph. That duplication made future versions harder to maintain and easier to accidentally skew.

## Implementation

- Added package-private `OpsShardReadinessServiceGraphTestFactory`.
- Exposed shared builders for catalog, catalog handoff, and pass-evidence closeout tests.
- Refactored three test classes to use the factory.
- Added `e/178/` evidence archive files.

## Boundary

This is test-only refactoring. It does not change production source behavior, endpoint paths, fixture endpoints, or the v153 root readiness schema.

## Verification

- Compile.
- Focused tests for the refactored service graph users.
- Full `mvn -q test`.
- Static JSON parse check.
- Browser snapshot and screenshot for the v178 archive page.
