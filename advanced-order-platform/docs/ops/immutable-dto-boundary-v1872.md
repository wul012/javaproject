# v1872 DTO 不可变集合边界

## Family design

- Abstraction: `ImmutableLists` 统一表达“保留 null，否则建立不可变快照”。
- Data boundary: 三个公开 record 的组件、顺序、JSON 名称与校验注解保持不变。
- Behavior boundary: 紧凑构造器隔离调用方列表，显式访问器只返回不可变视图。
- Test boundary: 一个泛型 `assertSnapshot` 驱动 page、request、response 三种场景。
- Debt boundary: 只有 SpotBugs 实跑无告警后，才删除对应六条精确豁免身份。

## 需求证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 构造后不受输入列表修改影响 | `ImmutableLists.copy` + 紧凑构造器 | `ImmutableListBoundaryTests` | 聚焦验证通过 |
| 调用方不能通过访问器改内部状态 | 显式列表访问器返回不可变快照 | 同一测试断言修改失败 | 聚焦验证通过 |
| null 校验语义保持 | helper 原样返回 null | helper 单测 + 现有订单边界回归 | 聚焦验证通过 |
| 不复制三段相同实现 | 三个 record 共享一个短名 helper | v1869 三次规则与源码增长门 | 聚焦验证通过 |
| 真实减少静态分析债务 | 删除三个 class 的 REP/REP2 六条 Match | v1871 身份门 + SpotBugs | 完整 verify 通过 |

## 已核实事实

- `PagedResponse.content`、`CreateOrderRequest.items`、`OrderResponse.lines` 都是公开
  `List` record 组件，各有 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2` 两条历史豁免。
- 现有生产与测试调用者没有对这三个访问器执行 add/remove/set/clear。
- `CreateOrderRequest.items` 允许 null 进入 Bean Validation，因此复制层不能提前抛 NPE。
- record 组件保持不变即可维持 Jackson 字段名和构造绑定，不需要自定义序列化器。

## 失败条件

- 调用方修改原列表后 DTO 内容随之变化，或能修改访问器返回值。
- 为消除 SpotBugs 而把 null 改成空列表、提前抛 NPE、修改 record 组件或 JSON 契约。
- 三个文件复制相同的 null/copy 判断，而不使用共享抽象。
- 在 SpotBugs 仍报告问题时直接删除豁免，或用 suppression 注解转移债务。
- 完整 verify、远端 CI、中文讲解、归档、提交、tag 或 push 任一未闭环。

## 验证计划

先运行不可变边界测试和 v1871 豁免身份门，再运行 SpotBugs 确认六条删除后仍为 0/0；随后
完成中文讲解与精确归档，最后执行完整 `mvnw -B verify`。外部评审结论不在本文自授。

## 聚焦验证记录

`mvnw -B '-Dtest=ImmutableListBoundaryTests,SpotBugsWaiverTests,JavaChangeGateTests,OrderIdempotencyBoundaryIntegrationTests' test spotbugs:check`
通过 12 个测试，零失败、零错误；SpotBugs 报告 `BugInstance=0`、`Error=0`。测试同时覆盖
null 列表、含 null 元素、构造输入别名隔离、访问器不可修改，以及三个公开 DTO 的共同边界。
`List.copyOf` 因会拒绝 null 元素而未采用，避免静态分析修复抢在 Bean Validation 前改变 400 契约。

## 完整验证结果

最终 `mvnw -B verify` 用时 11:57，执行 1,931 项测试，零失败、零错误、零跳过；JaCoCo
按 Maven 插件权威输出分析 2,231 个类并满足全部覆盖率下限，SpotBugs 保持
`BugInstance=0`、`Error=0`，Spotless、Spring Boot 重打包和可执行 jar 产出均通过。
远端 headless 与 Docker-tagged CI 必须在提交后独立通过，外部评审仍不由本文自授。
