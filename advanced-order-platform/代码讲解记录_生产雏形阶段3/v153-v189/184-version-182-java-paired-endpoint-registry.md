# 184. Java v182 paired endpoint registry

## 目标

`OpsShardReadinessEvidenceEndpoints` 之前分别维护 live endpoint 列表和 fixture endpoint 列表。两份列表必须同步插入，后续版本继续增加 endpoint 时容易出现顺序错位。v182 把 registry 改为成对维护。

## 代码变更

- `EndpointPair`
  - 保存一个 live endpoint 和对应 fixture endpoint。
- `endpointPairs()`
  - 维护 22 对 endpoint，作为 registry 单一源。
- `liveEndpoints()` / `fixtureEndpoints()`
  - 从 `endpointPairs()` 派生。
- `liveProbeEndpoints()` / `fixtureProbeEndpoints()`
  - 继续从派生列表生成，不改变输出。
- `OpsShardReadinessEvidenceEndpointsTests`
  - 增加 pair live/fixture 顺序和值断言。

## 验证与边界

- focused 测试覆盖 endpoint registry、ops evidence service、v175 catalog、v179 verification。
- endpoint registry 不扩张，仍为 22 live / 22 fixture / 22 pair。
- 本版没有新增执行入口，没有打开 write routing、active shard router、credential 读取、raw endpoint parse、managed audit connection、deployment / rollback。
