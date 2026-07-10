# Release acceptance route-path split sustainment extraction v1841

v1841 moves the nineteen non-controller sustainment implementation files and
four package-local tests into:

```text
com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment
```

The sustainment controller and its controller test remain in root. This is the
middle layer of the three-version split track: it consumes the v1840 closeout
boundary and publishes the narrow input used by the v1842 acceptance package.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | Status |
| --- | --- | --- | --- |
| Extract sustainment as one layer | 19 implementation files and 4 package-local tests move | v1841 exact placement guard | Complete |
| Preserve its HTTP route | root controller imports the moved service/response and shared v1840 route owner | controller and route tests | Complete |
| Preserve its input | moved service/source catalogs import v1840 closeout service/response | compile and v1841 source guard | Complete |
| Prepare its consumer | acceptance-package service/catalogs import moved sustainment service/response | compile and reader guard | Complete |
| Preserve response analysis | four SpotBugs mirror entries follow the sustainment response FQN | v1841 config guard | Complete |
| Tighten structure | root 751 -> 732; movable 646 -> 627; split bucket 55 -> 36 | census and ratchet tests | Complete |

## Layer mechanics

The sustainment service reads the v1840 closeout response, derives source
snapshots, ownership rules, drift guards, boundary guards, CI gates, consumer
handoffs, and a scorecard, then renders and copies those structures into an
immutable response. It remains a read-only maintenance contract. The moved
package exposes only its public service and response to the next layer; catalogs,
renderers, and support remain package-private.

## Mechanical evidence

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainment*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1841Tests test
.\mvnw.cmd verify
```

The version contract is 732 direct-root files, 105 retained files, 627 movable
files, zero unassigned files, and 36 files remaining in the split bucket. Total
`ops` Java remains <= 1,352.

## Safety boundary

Sustainment evidence does not authorize route writes, runtime routing,
credential access, managed-audit connections, deployment, rollback, SQL,
Node control, or mini-kv control. CI gates in the response describe required
verification; they do not trigger CI or execute any external process.
