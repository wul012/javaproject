> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第五十四版代码讲解：release verification manifest

本版目标是把 Java 发布前验证固化成一份可读、可测、可被 Node 消费的 manifest。v53 已经完成订单幂等存储抽象；v54 不继续改业务，而是开始进入“发布验证硬化”。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v161-post-controlled-idempotency-drill-roadmap.md
```

计划要求 Java v54 做：

```text
release verification manifest
固化 Maven 聚焦测试、非 Docker 回归、package、HTTP smoke、静态 contracts 清单
不改订单业务语义
不接生产数据库
不接 mini-kv
```

这说明当前主线已经从“订单幂等能力”转向“发布验证 / CI 可复现 / 三项目 release evidence gate”。

## 静态 manifest

新增文件：

```text
src/main/resources/static/contracts/release-verification-manifest.sample.json
```

manifest 版本：

```json
"manifestVersion": "java-release-verification-manifest.v1"
```

场景名：

```json
"scenario": "JAVA_RELEASE_VERIFICATION_MANIFEST_SAMPLE"
```

它明确是只读样本：

```json
"readOnly": true,
"executionAllowed": false
```

这点很重要：manifest 只是说明 release 前要怎么验证，不是一个执行器，也不是给 Node 触发 Java 构建或写接口的授权。

## releaseSubject

manifest 里先说明发布对象：

```json
"releaseSubject": {
  "project": "advanced-order-platform",
  "buildTool": "Maven",
  "javaVersion": "21",
  "artifact": "target/advanced-order-platform-0.1.0-SNAPSHOT.jar"
}
```

这里的 `javaVersion=21` 对应 `pom.xml` 的编译目标；实际本机运行 smoke 时可能由系统 `java` 命令选择更高版本 JDK，但项目编译目标仍然是 Java 21。

## verificationChecks

manifest 固定五个检查：

```json
"verificationChecks": [
  { "name": "focused-maven-tests" },
  { "name": "non-docker-regression-tests" },
  { "name": "maven-package" },
  { "name": "http-smoke" },
  { "name": "static-contract-json-validation" }
]
```

聚焦测试命令写成：

```text
mvn "-Dtest=JpaIdempotencyStoreTests,OrderApplicationServiceTests,OrderIdempotencyBoundaryIntegrationTests,OpsOverviewIntegrationTests,OpsEvidenceServiceTests" test
```

它覆盖：

```text
幂等存储抽象
订单创建 201/200/409
ops evidence
release verification manifest endpoint
静态样本结构
```

非 Docker 回归命令写成：

```text
mvn "-Dtest=!PostgresMigrationIntegrationTests,!RabbitMqNotificationConsumerIntegrationTests,!RabbitMqNotificationFailureIntegrationTests,!RabbitMqOutboxPublisherIntegrationTests" "-DargLine=-XX:TieredStopAtLevel=1 -Xmx512m" test
```

它保留本地 H2/Spring 回归覆盖，同时避开需要 Docker 的 Testcontainers/RabbitMQ 场景。

打包命令：

```text
mvn "-DskipTests" package
```

HTTP smoke 不写成自动执行脚本，而是写成检查语义：

```text
Start the packaged jar on a local temporary port,
then probe health, ops evidence, release manifest,
and idempotent order create 201/200/409.
```

这样 Node 后续消费 manifest 时知道要看什么证据，但不会替 Java 启动服务或执行写操作。

## staticContracts

manifest 列出随包静态契约清单：

```json
"staticContracts": [
  "/contracts/ops-read-only-evidence.sample.json",
  "/contracts/ops-evidence-field-guide.sample.json",
  "/contracts/order-idempotency-boundary.sample.json",
  "/contracts/order-idempotency-store-abstraction.sample.json",
  "/contracts/release-verification-manifest.sample.json"
]
```

每个契约都带：

```text
endpoint
source
versionField
expectedVersion
```

例如 release manifest 自己：

```json
{
  "endpoint": "/contracts/release-verification-manifest.sample.json",
  "source": "src/main/resources/static/contracts/release-verification-manifest.sample.json",
  "versionField": "manifestVersion",
  "expectedVersion": "java-release-verification-manifest.v1"
}
```

这让 release gate 能做两层检查：

```text
文件能解析
版本字段符合预期
```

## releaseGate

manifest 明确 Node 的消费边界：

```json
"releaseGate": {
  "intendedConsumer": "Node cross-project release verification intake gate",
  "nodeMayConsume": true,
  "nodeMayExecuteMaven": false,
  "nodeMayTriggerJavaWrites": false,
  "requiresLiveHttpSmoke": true,
  "requiresArchivedEvidence": true,
  "requiresProductionSecrets": false
}
```

这里最关键的是：

```text
nodeMayExecuteMaven=false
nodeMayTriggerJavaWrites=false
```

也就是说，Node v162 可以读取 Java v54 的 tag、manifest 和归档，但不能拿这份 manifest 当执行许可。

## boundaries

manifest 还固定说明本版没有改变业务：

```json
"boundaries": {
  "changesOrderCreateSemantics": false,
  "changesPaymentOrInventoryTransaction": false,
  "changesOutboxOrReplayExecution": false,
  "connectsMiniKv": false,
  "usesProductionDatabase": false
}
```

这与计划要求完全对齐：v54 是发布验证硬化，不是订单、支付、库存或 mini-kv 接入版本。

## ops evidence 接入

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java
```

`OpsEvidenceResponse` 新增：

```java
public record ReleaseVerification(
        String manifestVersion,
        String manifestEndpoint,
        String verificationMode,
        List<String> requiredChecks,
        List<String> staticContractEndpoints,
        boolean nodeMayExecuteBuild,
        boolean nodeMayTriggerWrites,
        boolean changesBusinessSemantics,
        boolean requiresProductionSecrets
) {
}
```

`OpsEvidenceService` 新增常量：

```java
static final String RELEASE_VERIFICATION_MANIFEST_VERSION = "java-release-verification-manifest.v1";

static final String RELEASE_VERIFICATION_MANIFEST_ENDPOINT =
        "/contracts/release-verification-manifest.sample.json";
```

动态 evidence 里返回：

```java
new OpsEvidenceResponse.ReleaseVerification(
        RELEASE_VERIFICATION_MANIFEST_VERSION,
        RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
        "LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS",
        List.of(
                "focused-maven-tests",
                "non-docker-regression-tests",
                "maven-package",
                "http-smoke",
                "static-contract-json-validation"
        ),
        ...
        false,
        false,
        false,
        false
)
```

四个 `false` 是安全边界：

```text
nodeMayExecuteBuild=false
nodeMayTriggerWrites=false
changesBusinessSemantics=false
requiresProductionSecrets=false
```

## 端点挂载

manifest endpoint 被加入：

```java
healthProbe.additionalProbeEndpoints
readOnlyWindow.allowedProbeEndpoints
evidenceEndpoints
```

所以动态 `/api/v1/ops/evidence` 会告诉 Node：

```text
你可以 GET /contracts/release-verification-manifest.sample.json
但仍不允许任何 POST 写操作
```

静态样本也同步更新：

```text
src/main/resources/static/contracts/ops-read-only-evidence.sample.json
src/main/resources/static/contracts/ops-evidence-field-guide.sample.json
```

field guide 新增 `releaseVerification` 字段组，解释 manifest 版本、端点、必跑检查、静态 contract endpoint 和 Node 不执行 Maven 的边界。

## 测试覆盖

`OpsEvidenceServiceTests` 新增断言：

```java
assertThat(evidence.releaseVerification().manifestVersion())
        .isEqualTo("java-release-verification-manifest.v1");
assertThat(evidence.releaseVerification().nodeMayExecuteBuild()).isFalse();
assertThat(evidence.releaseVerification().nodeMayTriggerWrites()).isFalse();
```

`OpsOverviewIntegrationTests` 新增静态 manifest 测试：

```java
mockMvc.perform(get("/contracts/release-verification-manifest.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.manifestVersion").value("java-release-verification-manifest.v1"))
        .andExpect(jsonPath("$.releaseGate.nodeMayExecuteMaven").value(false))
        .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
        .andExpect(jsonPath("$.archiveExpectation.runtimeArchiveRoot").value("c/<version>"));
```

这确保 manifest 不只是文件存在，而是关键边界字段真的稳定。

## 验证、归档和成熟度变化

运行调试归档写入：

```text
c/54/解释/说明.md
c/54/图片/
```

本版验证覆盖：

```text
静态 JSON 校验通过
结构聚焦测试 8 个通过
manifest 指定聚焦测试 33 个通过
非 Docker 回归 75 个通过
打包成功
HTTP smoke 成功，覆盖 health、ops evidence、release manifest、201 / 200 / 409
Docker 未启动
临时 Java 进程已停止
```

成熟度变化是：Java 项目开始具备 release gate 输入，不再只靠口头记录“跑过哪些命令”。后续 Node v162 可以读取 Java v54 的 tag 和 manifest，结合 mini-kv v63 的 manifest，形成三项目 release verification intake gate。

## 一句话总结

v54 把 Java 发布验证流程固化为随包 manifest，并通过 ops evidence 对外暴露只读 release gate 边界，为下一步跨项目 release verification 提供稳定输入。
