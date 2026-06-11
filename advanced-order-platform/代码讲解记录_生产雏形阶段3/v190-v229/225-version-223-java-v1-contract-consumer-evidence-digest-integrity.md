> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 225. Java v223 v1 contract consumer evidence digest integrity

This version adds an integrity guard around the v220 consumer evidence digest.

The new test checks:

- v1 endpoint pair registry remains focused at ten pairs;
- consumer bundle, checklist, and digest stay adjacent;
- digest references match the frozen v215 checklist;
- digest counts and blocked operations match the checklist;
- digest evidence excludes digest self and later receipts;
- digest checks match the frozen digest snapshot.

The service now exposes a named v223 integrity evidence path constant for final handoff guard work.
