# Java 三分优雅度提升路线图

## 背景与授权

用户于 2026-07-20 明确授权持续创造、重构和优化 Java 项目，并允许后续再对关联项目做
对齐。本路线图只修改 `D:\javaproj`；Node、mini-kv、aiproj 工作区保持只读。历史归档目录、
已发布 tag 和 Node 已固定引用的证据路径不移动、不改写。外部路由若需迁移，必须先保留兼容
入口并形成独立对齐清单，不得在内部重构中静默改变。

## 起点核对

v1872 tag、本地 `master` 与 `javaproject/master` 同指 `07505752`，最终 receipt CI
`29695354262` 成功。生产 Java 1,484 个，其中 `ops` 1,352 个；Renderer 为 121 个、
5,355 行，119 个 Renderer 文件名超过 40 字符。生产长文件 stem 1,297 个、长标识符出现
21,167 次，超过 500 行的生产文件 32 个，最大 738 行。这些数字来自提交脚本或同口径
长路径安全 census，后续只能收紧。

## 三分目标

| 指标 | 起点 | 目标 | 机械证据 |
| --- | ---: | ---: | --- |
| `ops` 生产 Java 文件 | 1,352 | <= 650 | `ops-elegance-census.ps1` + Maven gate |
| Renderer 文件 | 121 | <= 30 | 同上 |
| 超长 Renderer 文件名 | 119 | 0 | 同上 |
| 生产长文件 stem | 1,297 | <= 550 | `java-maintainability-census.ps1` |
| 长标识符出现次数 | 21,167 | <= 9,000 | 同上 |
| 超过 500 行生产文件 | 32 | <= 8 | 同上 |
| 最大生产文件 | 738 | <= 600 | 同上 |

目标表达的是本轮计划的结构终点，不是允许一次批量放宽的预算。每个版本必须比前一版更严，
最终分数由外部评审判断，本仓库只报告可复现指标。

## 执行阶段

1. Renderer engine：建立共享 Markdown section engine，按依赖链把一次性 Renderer 类收敛
   为每个家族一个短名报告组合器，同时保留响应字节与只读路由。
2. Catalog engine：把只返回固定列表或字段投影的 Catalog 改成声明式规格和共享映射器，删除
   重复工厂类；第三个相似 Catalog 不再允许独立成文件。
3. Service boundary：将只负责拼接十余个 Catalog 的 Service 收敛为明确 aggregate pipeline，
   保留 Spring 入口与跨家族依赖方向。
4. Release approval：拆分剩余 500 行以上文件，提取短名领域概念，并删除被替代的长名构建器。
5. 收尾：重跑 exact census、完整 verify、远端 CI 和跨项目只读 capstone；请求外部评审，
   不自授“提高三分”结论。

## v1881 检查点

Renderer engine 已连续覆盖八个真实输出家族。当前 ops 为 1,266，Renderer 为 45 个、
3,616 行、30 个超长文件名；生产长 stem / 长标识符使用为 1,197 / 20,544。相对 v1872
起点，Renderer 已减少 76 个、1,739 行，超长 Renderer 名减少 89 个，但尚未达到
Renderer <=30、超长 Renderer=0、ops<=650 和全局长名目标，因此不能把阶段或九分目标写成
完成。下一步继续处理剩余高收益 Renderer 家族，再进入 Catalog engine；每个家族仍须先冻结
完整输出，且不能用合并成大文件代替抽象收敛。

## DONE 与失败条件

- 每版都有变更前后 census、行为测试、完整 `mvnw -B verify`、提交、tag、push 和绿色 CI。
- 讲解在最终 verify 前完成，中文不少于 3,000 字且严格使用十个标准章节。
- 删除实现形状测试时，必须以更严格的当前结构 ratchet 和行为 oracle 替代；不得只删测试。
- 修改测试期望、fixture 字节、路由字符串或响应文本来迁就重构，整版回退。
- Renderer/Catalog 数量、长名 baseline、文件大小或 SpotBugs 豁免上升，整版回退。
- 新共享引擎只被一个 case 使用、或共享引擎比被替代代码更难读，整版回退。
- 历史 archive 路径、已发布 tag、credential value、写路由和执行权限不属于本计划。

## 对抗性自审

最强质疑是“把很多小文件塞进一个大文件只是换一种难看”。因此每个组合器必须低于 300 行，
通过短名、类型导入和声明式 section mapping 降低总行数与长标识符次数；只降低文件数而不降低
总复杂度的版本不算有效进展。
