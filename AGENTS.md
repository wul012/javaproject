# Java Workspace Agent Rules

## Node Plan Source Rule

When advancing Java from a Node-driven roadmap, read the active Node plan directly from:

```text
D:\nodeproj\orderops-node\docs\plans2
D:\nodeproj\orderops-node\docs\plans
```

Prefer `plans2` when both exist. Do not rely only on Java-side mentions of a Node version. If the user names a Node version such as `v282`, locate and read that Node plan before deciding the Java next step.

## Code Maintainability Rule

写代码的时候不要出现难于维护的巨型代码文件，要做必要拆分。

## Screenshot Automation Rule

When browser automation, page inspection, or runtime screenshots are needed, use `tool_search`
to discover Playwright MCP tools first. Prefer `mcp__playwright__` tools such as
`browser_tabs`, `browser_resize`, `browser_snapshot`, and `browser_take_screenshot`.
Run tab/open, snapshot, and screenshot calls sequentially so the session is established before
capture. Use Playwright CLI or the Playwright skill only when MCP is unavailable.

## Completion Cleanup Gate

Before sending the final response for any task, clean files and processes created during that task.

- Delete temporary files and folders created only for intermediate editing, rendering, testing, debugging, or validation.
- Keep user-provided source files, final deliverables, requested source changes, useful logs, `.git`, and Codex session/state files.
- Stop background processes started during the task unless the user explicitly needs them running.
- State meaningful cleanup results in the final response.
