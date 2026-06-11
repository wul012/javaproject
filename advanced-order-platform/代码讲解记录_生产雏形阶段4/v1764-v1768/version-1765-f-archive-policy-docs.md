# Java v1765 f archive policy docs

## 入口路由

The route remains unchanged because v1765 is a documentation and policy hardening step. Operators still use the same screenshot/explanation archive registry endpoint to see the effective root.

## 响应模型

The response model is unchanged in shape. Its content now distinguishes three roles: `d` as historical, `d_runtime_screenshot_archive_next` as a preserved transition, and `f` as the continuing root.

## 上游证据配置

The Node v367 plan says Java is not required for a runtime feature. The Java work therefore focuses on local archive policy and avoids any upstream probe or action flag.

## 服务层核心流程

The service consumes the updated catalogs and emits markdown sections through the existing renderer. No new service branch or conditional runtime path was added.

## Java 证据检查

The new `f/README.md` defines the canonical shape `f/v<start>-v<end>/<version>/images` and `f/v<start>-v<end>/<version>/explanations`. The segment README records the v1764-v1768 range and explicitly states that no runtime screenshots were captured by this batch.

## mini-kv 证据检查

mini-kv remains out of scope. No mini-kv command, TCP probe, or state mutation is needed to prove a Java archive folder rule.

## 阻断与安全边界

The updated AGENTS and `d/README.md` both forbid direct root dumping under `d`, `d_runtime_screenshot_archive_next`, or `f`. The transition root is preserved for v1759-v1763 only.

## 测试覆盖

Document tests check that `f/README.md` and `f/v1764-v1768/README.md` exist and that the transition README tells future work to continue in `f/`.

## 一句话总结

v1765 makes the `f/` archive rule visible in the files maintainers actually read.
