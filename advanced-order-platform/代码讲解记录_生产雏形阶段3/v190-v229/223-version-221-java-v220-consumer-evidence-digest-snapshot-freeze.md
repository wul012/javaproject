# 223. Java v221 v220 consumer evidence digest snapshot freeze

This version freezes the v220 consumer evidence digest into `OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot`.

The service now delegates to `v220Digest()`. This matches the existing v1 contract layering pattern and gives v222-v224 a stable digest baseline to compare against.

The snapshot test locks the v215 checklist endpoint/receipt, digest evidence list, digest checks, and v220 evidence path.
