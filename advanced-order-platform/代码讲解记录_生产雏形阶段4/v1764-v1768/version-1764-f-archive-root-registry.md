# Java v1764 f archive root registry

## 入口路由

The public read-only evidence route remains `/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry`. v1764 does not add a second endpoint because the operator-facing question is still one question: where should screenshot explanations go next?

## 响应模型

The existing registry response keeps `legacyRoot=d` and changes `nextRoot=f`. The current archive assessment list now has three roots: historical `d`, transitional `d_runtime_screenshot_archive_next`, and active `f`.

## 上游证据配置

The source plan stays tied to Node v367, but the Java batch marker advances to `Java v1764-v1768`. Node v367 does not require a new Java runtime feature, so this version only corrects Java archive governance.

## 服务层核心流程

`OpsShardReadinessScreenshotExplanationArchiveRegistryService` still assembles catalogs through the same service path. The meaningful change is in the catalog constants: `NEXT_ROOT` becomes `f`, and the status check requires an active `f/v1764-v1768` segment.

## Java 证据检查

Java now reports `d` as closed historical evidence, `d_runtime_screenshot_archive_next` as a closed transition, and `f` as the canonical active root. The registry status cannot pass unless those roots and the active f segment are present.

## mini-kv 证据检查

mini-kv is not queried. This is an archive-path decision inside Java documentation and evidence metadata, not a key-value runtime dependency.

## 阻断与安全边界

The version keeps screenshot capture disabled and keeps historical archive movement disabled. It also keeps write routing, credential values, raw endpoint URLs, managed audit HTTP, deployment, rollback, Java autostart, and mini-kv autostart outside the batch.

## 测试覆盖

The service tests now expect `nextRoot=f`, three current archive assessments, four segment plans, and the canonical f-root registry state.

## 一句话总结

v1764 turns `f/` into the canonical screenshot/explanation root without moving historical evidence.
