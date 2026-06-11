# Java v1766 f archive test guards

## 入口路由

The route test still delegates through the shared ops shard-readiness route table. That prevents the `f/` correction from creating an undocumented side route.

## 响应模型

The service, renderer, controller, and closeout tests now assert the same root story: `nextRoot=f`, `d_runtime_screenshot_archive_next` is transitional, and `f/v1764-v1768` is the active segment.

## 上游证据配置

No upstream execution setting changes. The registry remains a read-only Java evidence surface aligned with Node v367's boundary language.

## 服务层核心流程

The support class builds status from catalog counts, required naming rules, required verification steps, denied runtime boundaries, the canonical f root, and the active f segment.

## Java 证据检查

Checks now include `screenshot-explanation-archive-canonical-root-f` and `screenshot-explanation-archive-transition-root-closed-d_runtime_screenshot_archive_next`. These strings make the correction easy to audit without reading every catalog class.

## mini-kv 证据检查

The tests do not start mini-kv and do not require a mini-kv fixture. This is intentional: screenshot archive placement is Java repository evidence.

## 阻断与安全边界

The boundary tests continue to require `capturesScreenshot=false`, `movesHistoricalArchive=false`, `readsCredentialValue=false`, `resolvesRawEndpointUrl=false`, and `managedAuditHttpAllowed=false`.

## 测试覆盖

Coverage includes route delegation, service assembly, renderer markdown, controller response, immutability, boundary checks, documentation guards, and closeout checks.

## 一句话总结

v1766 turns the `f/` decision into failing tests rather than a loose convention.
