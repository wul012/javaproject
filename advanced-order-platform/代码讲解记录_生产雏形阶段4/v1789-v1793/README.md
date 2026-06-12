# v1789-v1793 Java ops governance consolidation 批次

本目录用于承接 Claude 指出的 Java ops 根包膨胀问题。当前批次只做 Java 本项目，优先建立 roadmap、playbook、ratchet、inventory 和 archive-safe consolidation 规则，不移动 `a/` 到 `f/`，不移动 `e/<version>/`，不改 evidence JSON，不触碰 Node、mini-kv 或其他项目工作区。

## 版本清单

| Version | Scope |
| --- | --- |
| v1789 | Java ops governance consolidation roadmap, Codex playbook, file-count ratchet |
| v1790+ | 待后续按 roadmap 做 root ops inventory 和小批量 contract-preserving consolidation |
