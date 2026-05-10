# 第二十六版：Actuator RabbitMQ 健康检查对齐

## 本版目标

v25 冒烟时发现一个细节：默认本地启动下，`/actuator/health/liveness` 和 `/actuator/health/readiness` 都是 `UP`，页面和 API 也正常，但根 `/actuator/health` 返回 `DOWN`。

原因判断：
```text
项目引入了 spring-boot-starter-amqp
 -> Spring Boot 自动创建 RabbitMQ ConnectionFactory
 -> Actuator 默认启用 Rabbit health indicator
 -> 默认本地模式没有启用 RabbitMQ 业务能力，也没有启动 RabbitMQ
 -> 根 health 聚合 Rabbit health 后变成 DOWN
```

v26 的目标是把 health indicator 和业务 profile 对齐：
```text
默认 H2 本地模式
 -> outbox.rabbitmq.enabled=false
 -> notification.rabbitmq.enabled=false
 -> management.health.rabbit.enabled=false
 -> /actuator/health = UP

rabbitmq profile
 -> RabbitMQ 业务能力启用
 -> management.health.rabbit.enabled=true
 -> RabbitMQ 不可用时健康检查应该暴露问题
```

## 改动文件

```text
src/main/resources/application.yml
src/main/resources/application-rabbitmq.yml
src/test/java/com/codexdemo/orderplatform/ActuatorHealthIntegrationTests.java
README.md
代码讲解记录/README.md
a/26/解释/说明.md
```

## 默认配置：关闭未启用 RabbitMQ 的 health indicator

文件：`src/main/resources/application.yml`

默认配置新增：

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  health:
    rabbit:
      enabled: false
  endpoint:
    health:
      probes:
        enabled: true
```

这里不是“隐藏问题”，而是让健康检查只检查当前模式真正启用的能力。

默认模式里这两个业务开关本来就是关闭的：

```yaml
outbox:
  rabbitmq:
    enabled: false

notification:
  rabbitmq:
    enabled: false
```

所以默认根 health 不应该因为 RabbitMQ 没启动而 `DOWN`。

## RabbitMQ profile：真正启用 Rabbit health

文件：`src/main/resources/application-rabbitmq.yml`

RabbitMQ profile 新增：

```yaml
management:
  health:
    rabbit:
      enabled: true
```

这和 profile 里的业务开关是一致的：

```yaml
outbox:
  rabbitmq:
    enabled: true

notification:
  rabbitmq:
    enabled: ${NOTIFICATION_RABBITMQ_ENABLED:true}
```

也就是说：
```text
没有启用 RabbitMQ 功能
 -> 不检查 RabbitMQ

启用了 RabbitMQ 功能
 -> 必须检查 RabbitMQ
```

这个边界很重要。否则本地开发会误报，生产/联调又可能漏报。

## 测试：固定默认 health 行为

文件：`src/test/java/com/codexdemo/orderplatform/ActuatorHealthIntegrationTests.java`

新增随机端口集成测试：

```java
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "order.expiration.enabled=false",
                "outbox.publisher.enabled=false"
        }
)
class ActuatorHealthIntegrationTests {
```

这个测试真正启动 Web 容器，而不是只测配置类。原因是 health endpoint 是 HTTP 行为，应该用 HTTP 方式验证。

使用泛型响应读取 JSON：

```java
private static final ParameterizedTypeReference<Map<String, Object>> HEALTH_RESPONSE =
        new ParameterizedTypeReference<>() {
        };
```

核心断言：

```java
ResponseEntity<Map<String, Object>> health = health("/actuator/health");
ResponseEntity<Map<String, Object>> liveness = health("/actuator/health/liveness");
ResponseEntity<Map<String, Object>> readiness = health("/actuator/health/readiness");

assertThat(health.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(health.getBody()).containsEntry("status", "UP");
assertThat(liveness.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(liveness.getBody()).containsEntry("status", "UP");
assertThat(readiness.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(readiness.getBody()).containsEntry("status", "UP");
```

封装一个小 helper：

```java
private ResponseEntity<Map<String, Object>> health(String path) {
    return restTemplate.exchange(path, HttpMethod.GET, null, HEALTH_RESPONSE);
}
```

这样以后如果有人删掉 `management.health.rabbit.enabled=false`，默认根 health 会再次因为 RabbitMQ 探针失败而 `DOWN`，这个测试就会挡住回归。

## 运行验证

本版先跑了新增测试：

```powershell
mvn "-Dtest=ActuatorHealthIntegrationTests" test
```

结果：
```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

后续还会跑全量测试、打包和 jar HTTP 冒烟，确认根 `/actuator/health` 在本地默认模式下回到 `UP`。

## 本版总结

v26 没有新增业务功能，但它修掉了一个很真实的工程问题：健康检查不能脱离当前启用 profile。默认不启用 RabbitMQ 时，RabbitMQ 不应该拖垮根健康检查；启用 RabbitMQ profile 后，RabbitMQ 又必须成为健康检查的一部分。
