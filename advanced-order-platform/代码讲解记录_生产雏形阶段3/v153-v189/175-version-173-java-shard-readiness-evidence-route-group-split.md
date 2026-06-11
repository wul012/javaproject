> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 175. Java v173 evidence route group split

## 背景

v171 拆出了 runtime execution controller，v172 拆出了 lifecycle/plan controller。剩余 `OpsShardReadinessController` 仍包含 evidence 三个 endpoint。Node v413 已完成 Java / mini-kv shard readiness evidence route group split，Java 侧可以做同类维护性拆分。

## 本版处理

- 新增 `OpsShardReadinessEvidenceController`。
- 迁出 3 个 endpoint：
  - `/api/v1/ops/shard-readiness/evidence-index`
  - `/api/v1/ops/shard-readiness/evidence-verification`
  - `/api/v1/ops/shard-readiness/evidence-handoff`
- `OpsShardReadinessController` 只保留：
  - `/api/v1/ops/shard-readiness`
  - `/api/v1/ops/shard-readiness/hardening`

## 保守边界

- 不改变 API path。
- 不改变 response record 或 service 输出。
- 不新增 evidence gate。
- 不启动 Java / mini-kv，不连接 managed audit，不允许写路径。

## 验证

- `mvn -q -DskipTests compile`
- evidence 相关集成测试
- `mvn -q test`
- v173 归档页截图和浏览器快照
