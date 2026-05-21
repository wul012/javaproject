# Java Workspace Agent Rules

## Node Plan Source Rule

When advancing Java from a Node-driven roadmap, read the active Node plan directly from:

```text
D:\nodeproj\orderops-node\docs\plans2
D:\nodeproj\orderops-node\docs\plans
```

Prefer `plans2` when both exist. Do not rely only on Java-side mentions of a Node version. If the user names a Node version such as `v282`, locate and read that Node plan before deciding the Java next step.

## Completion Cleanup Gate

Before sending the final response for any task, clean files and processes created during that task.

- Delete temporary files and folders created only for intermediate editing, rendering, testing, debugging, or validation.
- Keep user-provided source files, final deliverables, requested source changes, useful logs, `.git`, and Codex session/state files.
- Stop background processes started during the task unless the user explicitly needs them running.
- State meaningful cleanup results in the final response.
