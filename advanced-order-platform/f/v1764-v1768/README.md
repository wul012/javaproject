# Java v1764-v1768 f archive segment

This segment records the correction that makes `f/` the continuing root for new screenshot and explanation artifacts.

Expected per-version shape:

```text
f/v1764-v1768/<version>/images/*.png
f/v1764-v1768/<version>/explanations/summary.md
```

No runtime screenshots are captured by this batch. It only changes the archive policy, registry evidence, documentation, and tests so future screenshots and explanations have a clean root.

## Versions

```text
v1764: registry root correction from transitional root to f
v1765: f root documentation and historical-root policy update
v1766: f root guard tests and no-root-dumping checks
v1767: code walkthrough and archive index updates
v1768: closeout verification and tag handoff
```
