# Java v1770 f layout segment docs

## 入口路由

The runtime route remains unchanged. The documentation update supports the registry by making the current f segment visible in the archive itself.

## 响应模型

The response's segment plan is mirrored by `f/README.md`: v1764-v1768 is the previous segment, v1769-v1773 is current, and v1774-v1785 is reserved.

## 上游证据配置

No upstream setting changes are introduced. Node v367 remains a planning reference only.

## 服务层核心流程

The service continues to render markdown from static catalogs. The new segment README gives the file-system side of the same inventory.

## Java 证据检查

`f/v1769-v1773/README.md` declares the required per-version layout with separate `images` and `explanations` directories.

## mini-kv 证据检查

mini-kv is outside scope. No keyspace, command, or health response is needed for archive layout documentation.

## 阻断与安全边界

The segment README says no runtime screenshots were captured by this batch. That keeps folder creation separate from evidence capture.

## 测试覆盖

The documentation test now requires both `f/v1764-v1768` and `f/v1769-v1773` segment READMEs.

## 一句话总结

v1770 gives the new f segment a first-class README instead of relying on a root note.
