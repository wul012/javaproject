# advanced-order-platform 运行调试归档

本目录从后续版本开始，作为新的运行调试归档入口使用，和旧目录 `a/` 同级：

```text
D:\javaproj\advanced-order-platform\a
D:\javaproj\advanced-order-platform\b
```

旧目录 `a/` 保留历史版本记录，不主动搬迁。

## 写入规则

后续每个 Java 版本的运行解释和截图写入：

```text
b/<版本>/解释/说明.md
b/<版本>/图片/*.png
```

例如：

```text
b/49/解释/说明.md
b/49/图片/01-focused-test.png
b/49/图片/02-full-test-docker.png
b/49/图片/03-package.png
b/49/图片/04-http-smoke.png
b/49/图片/05-cleanup.png
```

说明文档模式保持旧 `a/<版本>/解释/说明.md` 的写法：

```text
计划依据
合理性判断
本版目标
代码改动
验证记录
边界
清理记录
一句话总结
```

图片记录也保持旧模式，覆盖聚焦测试、全量测试、打包、HTTP smoke 和清理。
