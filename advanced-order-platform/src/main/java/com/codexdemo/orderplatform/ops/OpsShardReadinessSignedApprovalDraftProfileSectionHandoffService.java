package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService {

    static final String RESPONSE_VERSION = "Java v1262";
    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF;
    static final String PROFILE =
            "java-shard-readiness-signed-approval-draft-profile-section-handoff.v1";

    private final OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService registryService;

    public OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService registryService
    ) {
        this.registryService = registryService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse handoff() {
        var registry = registryService.registry();
        var sectionHandoffs = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSectionCatalog
                .handoffs(registry.sections());
        return OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                registry,
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffModuleCatalog.modules(),
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSourceCatalog.sources(registry),
                sectionHandoffs,
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteContractCatalog
                        .routeContracts(registry.routeFieldLocks()),
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryCatalog.decisions(),
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRenderer.render(sectionHandoffs),
                OpsShardReadinessSignedApprovalDraftProfileSectionHandoffGateCatalog.gates(),
                List.of("signed-approval-draft-profile-section-handoff-consumes-v1237-registry-only"));
    }
}
