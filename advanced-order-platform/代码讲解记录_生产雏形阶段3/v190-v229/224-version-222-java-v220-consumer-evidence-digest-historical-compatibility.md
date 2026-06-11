> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 224. Java v222 v220 consumer evidence digest historical compatibility

This version adds a historical compatibility guard for the v220 digest.

The test verifies that:

- older v179 and v184 endpoint snapshots do not include the digest endpoint or fixture;
- the current rolling registry keeps the digest after the v215 checklist and before read-only catalog endpoints;
- the frozen v220 digest evidence excludes v221/v222 self evidence.

The service now exposes a named v222 historical compatibility evidence path constant.
