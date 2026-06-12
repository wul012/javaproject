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
