# Release acceptance route-path split base and closeout extraction v1840

v1840 begins the three-version ReleaseAcceptanceRoutePathSplit extraction. It
moves the base registry, its closeout layer, and the shared
`OpsShardReadinessReleaseAcceptanceRoutePaths` owner into:

```text
com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit
```

Twenty-four production files and nine package-local test files move. The base
and closeout Spring controllers and their controller tests remain in root.

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | Status |
| --- | --- | --- | --- |
| Extract a coherent first layer | 17 base files, 6 closeout files, and 1 route owner move together | v1840 exact placement guard | Complete |
| Keep HTTP entry points visible | two controllers remain in direct root and import moved public boundaries | compile and controller tests | Complete |
| Preserve split comparison | moved RouteCatalog compares 11 retained global constants with 11 moved owner constants | compatibility/catalog tests | Complete |
| Avoid broad public implementation | global aggregator exposes only BASE_PATH and the 11 immutable comparison constants; services remain narrow | v1840 source guard | Complete |
| Keep later layers compilable | root sustainment imports the moved closeout service/response and route owner | test compile and reader guard | Complete |
| Preserve analysis and structure | eight SpotBugs mirror entries follow the two moved responses; root 775 -> 751 | v1840 config and ratchet guards | Complete |

## Package boundary

The base package owns source snapshots, route comparison, compatibility,
boundary, consumer, scorecard, rendering, response construction, and the
read-only service. The closeout layer owns closeout items, boundary assertions,
markdown rendering, response construction, and its service. Closeout consumes
the base public service/response, so moving them together avoids a temporary
cross-package edge.

The route owner is shared by the remaining sustainment layers, the archive
handoff, MinimalReadOnlyGate controllers, and the retained global route
aggregator. Its immutable suffixes are public after the move. The root
aggregator becomes a public retained contract but exposes only the base path and
the 11 constants that RouteCatalog must compare; unrelated fields remain
package-private.

## Mechanical evidence

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessReleaseAcceptanceRoutePathSplit*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1840Tests test
.\mvnw.cmd verify
```

The census contract is 751 direct-root files, 105 retained files, 646 movable
files, zero unassigned files, 55 remaining ReleaseAcceptanceRoutePathSplit
files, and zero root route-owner files. Total `ops` Java remains <= 1,352.
This is a direct-root reduction of 775 -> 751 and a movable-backlog reduction
of 670 -> 646.

## Safety boundary

The route split remains an evidence comparison, not a router. It does not
select an active shard, write route configuration, read credential values,
connect to managed audit, deploy, roll back, execute SQL, or control Node or
mini-kv. The services remain read-only and the response continues to describe
matched route strings, boundaries, consumer handoffs, and scorecard evidence.
