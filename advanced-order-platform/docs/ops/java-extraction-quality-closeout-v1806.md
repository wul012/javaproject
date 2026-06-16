# v1806 Java extraction quality closeout

This note closes the first Java ops extraction wave after v1805. It is a
documentation and guard-test version only: no route, response, controller,
service, archive, runtime profile, deployment, rollback, or evidence schema is
changed.

## Quality Verdict

Current head is usable as the green Java extraction baseline:

- Current exact tag: `v1805-order-platform-production-excellence-ops-candidate-document-extraction`
- Current direct root `ops` Java file count: `1,183`
- Current ratchet: `MAX_ROOT_OPS_MAIN_JAVA_FILES = 1183`
- Current local lightweight closeout suite: `41 tests, 0 failures`
- Current formatting gate: `spotless:check` passed
- Current remote CI: GitHub Actions run `27524935139` passed for v1805

## Historical Tag Exception

The v1798 tag is a historical exception, not the recommended green baseline.
`v1798-order-platform-production-excellence-ops-quality-gate-registry-extraction`
points at commit `a8d8f93881407b0bc15bd25b61e463215fcac069`, whose Java Maven
CI run failed. The failure was not a route or runtime regression; the following
v1799 version records and fixes the latent walkthrough gate problem.

The correction policy is intentionally conservative:

- Do not rewrite or force-move historical tags.
- Do not describe v1798 as the preferred release baseline.
- Treat v1799 and newer tags as the remediated extraction line.
- Use v1805 as the current green baseline until a newer version passes CI.

## Evidence Chain

The v1797-v1805 extraction wave reduced direct root `ops` Java files from
1,330 to 1,183 while keeping total ops source file count stable at 1,352. The
wave moved mature implementation families into narrow maintenance subpackages,
left public controllers and route aggregation in root when needed, preserved
archive roots, and kept read-only runtime boundaries closed.

The important quality lesson is not merely "CI passed later." The project now
records the exact historical exception and the remediation boundary in source
control, so future maintainers do not have to rediscover that v1798's tag is not
the same thing as the current green extraction line.

## Stop Line

This closeout does not rename tags, move archive directories, start Java,
mini-kv, Node, Docker, browsers, or managed audit connections. It does not read
credentials, resolve raw endpoint URLs, open write routing, deploy, roll back,
or mutate business state.
