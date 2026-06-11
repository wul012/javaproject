# Java v1771 f layout inventory tests

## 入口路由

No new route is added. v1771 is a test-hardening version that validates the archive layout behind the existing registry.

## 响应模型

The model remains unchanged, but its assumptions become test-backed: active segments must stay under `f/`, while transition-root segments remain inactive.

## 上游证据配置

The tests do not require upstream services. They read local repository files only.

## 服务层核心流程

The registry service still produces the catalog response. The new inventory test independently checks the local `f/` root shape so the service and filesystem cannot drift silently.

## Java 证据检查

The inventory test requires `f/` to contain only `README.md` and segment directories. It also requires each segment README to mention `images`, `explanations`, and its segment name.

## mini-kv 证据检查

mini-kv is not involved. This guard protects repository organization rather than distributed runtime behavior.

## 阻断与安全边界

The test prevents direct artifact dumping in `f/`. It does not create or delete screenshot files.

## 测试覆盖

Coverage adds `OpsScreenshotExplanationFArchiveLayoutInventoryTests` alongside the existing f-root policy and documentation tests.

## 一句话总结

v1771 turns the `f/` folder layout into a machine-checkable invariant.
