# 最小只读门执行闭包提取 v1843

v1843 把 MinimalReadOnlyGateExecution 基础注册表与归档验证注册表的三十一个
非控制器生产类，以及十三个包内测试/测试支持类，迁入：

```text
com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution
```

两个 Spring 控制器和两个控制器测试继续留在根包。迁移保持两个 HTTP 路由、
响应字段、版本锚点、只读事务和阻断逻辑不变。

## Requirement Evidence Matrix

| 要求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 完整提取 Execution 闭包 | 31 个生产实现和 13 个包内测试进入同一维护包 | v1843 精确文件清单守卫 | 完成 |
| 保持两个 HTTP 入口 | 2 个控制器留根并导入对应公开服务/响应 | 编译、控制器测试、源码守卫 | 完成 |
| 保持两级数据链 | 归档验证服务只读取基础注册表服务/响应 | 服务测试与依赖守卫 | 完成 |
| 解锁 Operator-CI 上游 | 4 个核心消费者显式导入归档验证服务/响应 | 编译与消费者导入守卫 | 完成 |
| 保持路由所有权 | 移动服务使用现有公开 `OpsShardReadinessReleaseAcceptanceRoutePaths` | 路由测试与源码守卫 | 完成 |
| 保持静态分析覆盖 | 8 个响应及嵌套章节镜像迁到新全限定名 | SpotBugs 配置守卫 | 完成 |
| 收紧根包 | root 696 -> 665；movable 591 -> 560；execution bucket 31 -> 0 | census 与 ratchet 测试 | 完成 |
| 保持只读边界 | 无写路由、凭据读取、原始端点、审计连接、部署、回滚或兄弟进程控制 | 服务测试与全量验证 | 完成 |

## 数据流

基础注册表服务从静态来源计划、读取目标、门检查、边界策略、持续集成批次
和操作员交接目录生成只读响应。归档验证服务只读取该基础响应，再形成来源
快照、制品验证、读取目标验证、门验证、边界验证、持续集成验证、操作员交接
验证和归档评分。两层都复制集合并以 `passed` 或 `blocked` 表达证据完整性，
不会执行响应中描述的命令。

v1844 的 Operator-CI 核心只消费归档验证服务与响应。v1843 因而是后续
ArchiveVerification、ArchiveDigest、ConsumerPackage 和 VerificationDossier
依赖链的被依赖者先迁步骤。

## 验证命令

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessMinimalReadOnlyGateExecution*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1842Tests,ReadabilityUpkeepOpsConsolidationExtractionV1843Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests test
.\mvnw.cmd verify
```

版本契约是：直接根包 665，保留根包 105，仍待迁移 560，未归类 0，
MinimalReadOnlyGateExecution 桶 0，全部 `ops` 生产 Java 文件不超过 1,352。

## 安全边界

本次只改变包归属与显式导入。接口不会启动门执行、写入路由、读取凭据值、
解析原始端点、连接受管审计环境、执行数据库语句、部署、回滚或控制 Node、
Java、mini-kv 进程。
