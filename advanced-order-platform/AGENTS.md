# advanced-order-platform Agent Notes

## Context Compression Guard

当上下文明显变长、即将进入压缩风险区时，停止当前推进节奏，不继续执行新的代码修改、文档修改、构建、测试、提交、tag 或 push。

先用中文明确提醒用户：当前上下文已经接近压缩风险，建议先让用户压缩上下文后再继续。

如果正在收尾已经完成的验证或清理，可以只做必要的安全清理；不要开启新的版本目标。

## Runtime Archive Folder

For future Java version work, put run/debug explanations and screenshots in `c/`, which is a sibling of the old `a/` and `b/` archive folders.

Use this shape:

```text
c/<version>/解释/说明.md
c/<version>/图片/*.png
```

Keep the old `a/` and `b/` folders as historical archives for earlier versions. Do not move old `a/<version>` or `b/<version>` records unless the user explicitly asks.

When finishing a version, the final report should mention the `c/<version>` archive path.

## Docker Shutdown Fast Path

When Docker was started only for this Java project validation, close it quickly during cleanup.

Use this order:

```powershell
$dockerCli = 'C:\Program Files\Docker\Docker\DockerCli.exe'
if (Test-Path $dockerCli) { & $dockerCli -Shutdown 2>$null }
Start-Sleep -Seconds 8
docker info *> $null
if ($LASTEXITCODE -eq 0) {
  Get-Process |
    Where-Object {
      $_.ProcessName -in @(
        'Docker Desktop',
        'DockerCli',
        'com.docker.backend',
        'com.docker.proxy',
        'com.docker.dev-envs',
        'com.docker.extensions'
      )
    } |
    Stop-Process -Force -ErrorAction SilentlyContinue
  Start-Sleep -Seconds 8
}
docker info *> $null
$dockerStopped = $LASTEXITCODE -ne 0
```

Do not spend minutes waiting on `Docker Desktop.exe -Shutdown` alone. It can leave Docker responsive for too long.

Before stopping Docker, confirm project Testcontainers are done and no containers need to remain running:

```powershell
docker ps -a --format 'table {{.Names}}\t{{.Image}}\t{{.Status}}'
```

Do not remove Docker volumes, images, or user containers unless the user explicitly asks.
