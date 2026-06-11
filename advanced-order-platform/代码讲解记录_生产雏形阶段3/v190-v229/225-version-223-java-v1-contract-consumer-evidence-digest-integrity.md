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
