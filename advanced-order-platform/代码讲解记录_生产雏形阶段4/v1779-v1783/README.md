# Java v1779-v1783 ops 可读性保养批次

本目录记录 Java 本项目的工程后期可读性保养五版。

## 范围

- v1779：新增 `docs/ops` 主题索引。
- v1780：新增 `ops.maintenance.readability` 子包和 registry 地基。
- v1781：补齐可读性保养 registry 的 renderer/support/service/controller/tests。
- v1782：新增 registry 模板和 docs/ops 门禁测试。
- v1783：完成长类名收敛试点、版本收口、CI 与 cleanup gate。

## 边界

本批只做 `advanced-order-platform`，不修改 Node、mini-kv、aiproj 或其他项目；不打开写路由、active shard router、credential value、raw endpoint、managed audit connection、deployment、rollback、Java autostart 或 mini-kv autostart。
