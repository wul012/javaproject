# 224. Java v222 v220 consumer evidence digest historical compatibility

This version adds a historical compatibility guard for the v220 digest.

The test verifies that:

- older v179 and v184 endpoint snapshots do not include the digest endpoint or fixture;
- the current rolling registry keeps the digest after the v215 checklist and before read-only catalog endpoints;
- the frozen v220 digest evidence excludes v221/v222 self evidence.

The service now exposes a named v222 historical compatibility evidence path constant.
