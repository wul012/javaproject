# 219. Java v217 v215 consumer verification checklist historical compatibility

This version adds a historical compatibility guard for the v215 checklist.

The new test class verifies three things:

- older v179 and v184 endpoint snapshots are not backfilled with the v215 checklist endpoint;
- the current rolling registry keeps the checklist in the intended position after the v211 bundle and before read-only catalog endpoints;
- the frozen v215 checklist does not pull in v216/v217 self evidence as required historical input.

The service now also carries named evidence constants for the v216 snapshot freeze and v217 historical compatibility receipt, which later versions can reference without hard-coding paths.
