# 发布验收包提取 v1842

v1842 完成三版 ReleaseAcceptanceRoutePathSplit 拆分轨道。它把主验收包、
关闭回执和关闭归档索引的三十六个非控制器生产类，以及八个包内测试/测试
支持类，迁入：

```text
com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage
```

三个 Spring 控制器和三个控制器导向测试留在根包。迁移不改变路由字符串、
响应字段、来源版本、只读事务、状态计算或任何运行边界。

## Requirement Evidence Matrix

| 要求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 完整提取验收闭包 | 36 个生产实现和 8 个包内测试进入同一维护包 | v1842 精确文件清单守卫 | 完成 |
| 保持 HTTP 入口 | 3 个控制器留根并显式导入对应服务/响应 | 编译、控制器测试、源码守卫 | 完成 |
| 保持上游单向依赖 | 主验收服务只读取 v1841 持续维护服务/响应 | 编译与来源导入守卫 | 完成 |
| 保持内部交接链 | 主验收包 -> 关闭回执 -> 关闭归档索引 | 聚焦测试与服务链守卫 | 完成 |
| 保持静态分析覆盖 | 10 个响应及嵌套记录镜像迁到新全限定名 | SpotBugs 配置守卫 | 完成 |
| 收紧根包 | root 732 -> 696；movable 627 -> 591；split bucket 36 -> 0 | census 与 ratchet 测试 | 完成 |
| 保持只读边界 | 无写路由、凭据、原始端点、审计连接、部署、回滚或兄弟进程控制 | 服务测试与全量验证 | 完成 |

## 依赖与输出

主验收服务读取 v1841 的持续维护响应，形成来源快照、谱系、决定、归档项、
复核项、持续集成证据、运行边界、下一变更规则和评分卡。关闭回执只读取主
验收响应并核对七项接受标准。关闭归档索引只读取关闭回执，再形成来源、
标准回显、归档项、验证门和交接说明。每一层都是只读事务，并以 `passed`
或 `blocked` 表达证据完整性，不产生执行授权。

## 路径长度偏差

初始机械移动尝试使用更深的
`releaseacceptanceroutepathsplit.sustainment.acceptancepackage` 目录。Windows
在第一个超过传统路径上限的类名处拒绝移动；当时尚未改写包声明。随后对账
四个已移动文件，并统一改用更短的 `releaseacceptancepackage`。该名称仍准确
描述职责，同时避免把既有超长类名继续叠加到深目录。最终编译和精确文件
清单证明没有遗留双包或漏移文件。

## 验证命令

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -Dtest=OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackage*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1841Tests,ReadabilityUpkeepOpsConsolidationExtractionV1842Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests test
.\mvnw.cmd verify
```

版本契约是：直接根包 696，保留根包 105，仍待迁移 591，未归类 0，
ReleaseAcceptanceRoutePathSplit 桶 0，全部 `ops` 生产 Java 文件不超过
1,352。

## 安全边界

本次提取只改变源码归属和显式导入。接口不会写订单、库存、支付、路由或
归档，不会读取凭据值或解析原始端点，不会建立受管审计连接，不会部署或
回滚，也不会启动或停止 Node、Java、mini-kv 进程。
