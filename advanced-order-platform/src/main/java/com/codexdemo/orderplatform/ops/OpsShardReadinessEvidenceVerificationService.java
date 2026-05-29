package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessEvidenceVerificationService {

    static final String ENDPOINT = "/api/v1/ops/shard-readiness/evidence-verification";
    static final String FIXTURE_ENDPOINT = "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json";
    static final String EVIDENCE_PATH = "e/156/evidence/java-shard-readiness-evidence-verification-v156.json";

    private final OpsShardReadinessEvidenceIndexService evidenceIndexService;

    public OpsShardReadinessEvidenceVerificationService(
            OpsShardReadinessEvidenceIndexService evidenceIndexService
    ) {
        this.evidenceIndexService = evidenceIndexService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessEvidenceVerificationResponse verification() {
        OpsShardReadinessEvidenceIndexResponse index = evidenceIndexService.evidenceIndex();

        return new OpsShardReadinessEvidenceVerificationResponse(
                "advanced-order-platform",
                "Java v156",
                true,
                false,
                index.version(),
                OpsShardReadinessEvidenceIndexService.ENDPOINT,
                OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT,
                OpsShardReadinessEvidenceIndexService.EVIDENCE_PATH,
                index.evidenceEntries().size(),
                verifiedEvidenceVersions(index),
                checks(index),
                index.fallbackPolicy(),
                EVIDENCE_PATH,
                verificationStatus(index)
        );
    }

    private List<String> verifiedEvidenceVersions(OpsShardReadinessEvidenceIndexResponse index) {
        return index.evidenceEntries().stream()
                .map(OpsShardReadinessEvidenceIndexResponse.EvidenceEntry::evidenceVersion)
                .toList();
    }

    private List<OpsShardReadinessEvidenceVerificationResponse.VerificationCheck> checks(
            OpsShardReadinessEvidenceIndexResponse index
    ) {
        return List.of(
                check(
                        "index-read-only-and-non-executable",
                        "Java v155 evidence index",
                        index.readOnly() && !index.executionAllowed(),
                        "Index evidence remains read-only and does not authorize execution."
                ),
                check(
                        "required-contract-fields-covered",
                        "Node shard readiness contract",
                        index.requiredContractFields().containsAll(requiredContractFields()),
                        "All nine regular-gate contract fields are present."
                ),
                check(
                        "source-entry-count",
                        "Java frozen evidence entries",
                        index.evidenceEntries().size() == 2,
                        "Java v153 core and Java v154 hardening are the frozen sources."
                ),
                check(
                        "all-sources-frozen",
                        "Java frozen evidence entries",
                        index.evidenceEntries().stream()
                                .allMatch(OpsShardReadinessEvidenceIndexResponse.EvidenceEntry::frozen),
                        "Every source entry is marked frozen."
                ),
                check(
                        "no-rolling-current-pointer",
                        "Historical fallback safety",
                        index.evidenceEntries().stream()
                                .noneMatch(OpsShardReadinessEvidenceIndexResponse.EvidenceEntry::rollingCurrentPointer),
                        "Historical baselines do not point at rolling current files."
                ),
                check(
                        "versioned-fixture-endpoints",
                        "Fixture fallback safety",
                        index.evidenceEntries().stream()
                                .allMatch(entry -> entry.fixtureEndpoint().matches(".+-v\\d+\\.fixture\\.json")),
                        "Every fixture endpoint carries an explicit Java version."
                ),
                check(
                        "versioned-archive-paths",
                        "Archive fallback safety",
                        index.evidenceEntries().stream()
                                .allMatch(entry -> entry.archivePath().matches("e/\\d+/evidence/.+-v\\d+\\.json")),
                        "Every archive path is versioned and release-directory scoped."
                ),
                check(
                        "node-archive-mutation-forbidden",
                        "Node v370-v376 archive chain",
                        index.compatibilityGuarantees().contains("no-node-v370-v376-archive-mutation"),
                        "The Java verifier does not rewrite Node historical archives."
                )
        );
    }

    private List<String> requiredContractFields() {
        return List.of(
                "project",
                "version",
                "readOnly",
                "executionAllowed",
                "shardEnabled",
                "shardCount",
                "slotCount",
                "routingMode",
                "status"
        );
    }

    private String verificationStatus(OpsShardReadinessEvidenceIndexResponse index) {
        boolean passed = checks(index).stream()
                .allMatch(OpsShardReadinessEvidenceVerificationResponse.VerificationCheck::passed);
        return passed ? "passed" : "blocked";
    }

    private OpsShardReadinessEvidenceVerificationResponse.VerificationCheck check(
            String checkId,
            String subject,
            boolean passed,
            String detail
    ) {
        return new OpsShardReadinessEvidenceVerificationResponse.VerificationCheck(
                checkId,
                subject,
                passed,
                detail
        );
    }
}
