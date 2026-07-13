# JaCoCo Baseline v1792

Source run:

```powershell
.\mvnw.cmd -B "-Dtest.jvm.argLine=-XX:TieredStopAtLevel=1 -Xmx512m" clean verify
```

Result: 1470 tests, 0 failures, 0 errors, 0 skipped. JaCoCo report generated at
`target/site/jacoco/index.html` and `target/site/jacoco/jacoco.xml`.

## Line Coverage Floors

The global line baseline is high because `com.codexdemo.orderplatform.ops`
dominates measured lines. J2 therefore keeps a global floor and package-specific
floors. Floors are set about two percentage points below the measured baseline
and should never be lowered.

Coverage checks run on the default headless suite. The `docker-tests` Maven
profile sets `jacoco.skip=true` because it intentionally runs only the four
Docker-tagged Testcontainers classes and is not a representative coverage run.

| Scope | Missed | Covered | Baseline line coverage | J2 floor |
| --- | ---: | ---: | ---: | ---: |
| GLOBAL | 559 | 30215 | 98.18% | 0.96 |
| `com.codexdemo.orderplatform` | 2 | 14 | 87.50% | 0.85 |
| `com.codexdemo.orderplatform.catalog` | 10 | 20 | 66.67% | 0.64 |
| `com.codexdemo.orderplatform.common` | 12 | 21 | 63.64% | 0.61 |
| `com.codexdemo.orderplatform.inventory` | 11 | 121 | 91.67% | 0.89 |
| `com.codexdemo.orderplatform.notification` | 357 | 1366 | 79.28% | 0.77 |
| `com.codexdemo.orderplatform.ops` | 92 | 27895 | 99.67% | 0.97 |
| `com.codexdemo.orderplatform.ops.maintenance.readability` | 0 | 335 | 100.00% | 0.98 |
| `com.codexdemo.orderplatform.order` | 33 | 296 | 89.97% | 0.87 |
| `com.codexdemo.orderplatform.outbox` | 38 | 97 | 71.85% | 0.69 |
| `com.codexdemo.orderplatform.payment` | 4 | 50 | 92.59% | 0.90 |

## Ratchet Proof

The outbox package floor was temporarily overridden to `0.99`:

```powershell
.\mvnw.cmd -B -DskipTests "-Djacoco.outbox.line.minimum=0.99" verify
```

The run failed with:

```text
Rule violated for package com.codexdemo.orderplatform.outbox: lines covered ratio is 0.71, but expected minimum is 0.99
```

The same command without the temporary override passed:

```powershell
.\mvnw.cmd -B -DskipTests verify
```

## v1867 Closeout Floors

Phase 2 raises every floor whose observed v1866 baseline had more than two percentage
points of headroom. The readability package remains at `0.98` because its floor was
already within two points of its exact coverage. These values are shrink-only release
contracts in `pom.xml`:

| Scope | v1867 floor |
| --- | ---: |
| GLOBAL | 0.97 |
| root package | 0.86 |
| catalog | 0.65 |
| common | 0.62 |
| inventory | 0.90 |
| notification | 0.78 |
| ops | 0.98 |
| readability | 0.98 |
| order | 0.88 |
| outbox | 0.70 |
| payment | 0.91 |

The final v1867 `mvnw -B verify` and remote headless CI run are the acceptance evidence;
lowering any property is a regression rather than a repair.

The final local v1867 verify measured:

| Scope | Missed | Covered | v1867 actual |
| --- | ---: | ---: | ---: |
| GLOBAL | 568 | 32895 | 98.30% |
| root package | 2 | 14 | 87.50% |
| catalog | 10 | 20 | 66.67% |
| common | 29 | 63 | 68.48% |
| inventory | 11 | 121 | 91.67% |
| notification | 379 | 1429 | 79.04% |
| ops | 0 | 1187 | 100.00% |
| readability | 0 | 335 | 100.00% |
| order | 34 | 295 | 89.67% |
| outbox | 38 | 97 | 71.85% |
| payment | 4 | 50 | 92.59% |
