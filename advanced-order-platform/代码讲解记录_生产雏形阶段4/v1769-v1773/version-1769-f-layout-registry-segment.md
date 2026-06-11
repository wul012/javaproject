# Java v1769 f layout registry segment

## 入口路由

The existing screenshot/explanation archive registry remains the entry point. v1769 changes the registry content instead of adding a new URL, because the operator question is still the same: which archive segment is current?

## 响应模型

The response keeps `nextRoot=f` and expands segment plans from four to five entries. `f/v1764-v1768` becomes a closed f segment, `f/v1769-v1773` becomes the active segment, and `f/v1774-v1785` is reserved.

## 上游证据配置

The Node v367 plan says Java does not need a runtime feature. This version therefore records a Java-local archive inventory step and does not request Java or mini-kv startup.

## 服务层核心流程

The support class now requires the active segment to contain `f/v1769-v1773`. The catalog status cannot pass if the registry still points at the previous f segment.

## Java 证据检查

Java evidence checks include the canonical root `f`, five segment plans, and a current segment under `f/v1769-v1773`.

## mini-kv 证据检查

mini-kv is not queried. The change is repository archive governance, not read-path integration.

## 阻断与安全边界

Screenshot capture, historical archive moves, write routing, credential values, raw endpoint URLs, managed audit HTTP, deployment, rollback, Java autostart, and mini-kv autostart remain blocked.

## 测试覆盖

Service, renderer, controller, closeout, and f-root policy tests are updated around the five-segment plan.

## 一句话总结

v1769 advances the active screenshot/explanation archive segment to `f/v1769-v1773`.
