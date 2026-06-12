package com.codexdemo.orderplatform.ops.maintenance.readability;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadabilityUpkeepAuditService {

    static final String RESPONSE_VERSION = "Java v1788";
    static final String ENDPOINT =
            ReadabilityUpkeepRoutePaths.BASE_PATH
                    + ReadabilityUpkeepRoutePaths.UPKEEP_AUDIT;
    static final String PROFILE = "java-ops-readability-upkeep-audit.v1";
    static final String SOURCE_REGISTRY_ENDPOINT =
            ReadabilityUpkeepRegistryService.ENDPOINT;

    @Transactional(readOnly = true)
    public ReadabilityUpkeepAuditResponse audit() {
        var topics = ReadabilityUpkeepAuditTopicCatalog.topics();
        var routeMaps = ReadabilityRouteServiceTestMapCatalog.routeMaps();
        var pressures = ReadabilityRootPackagePressureCatalog.pressures();
        var boundaryRules = ReadabilityUpkeepAuditBoundaryCatalog.boundaryRules();
        var verificationSteps = ReadabilityUpkeepAuditVerificationCatalog.verificationSteps();
        return ReadabilityUpkeepAuditSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                SOURCE_REGISTRY_ENDPOINT,
                topics,
                routeMaps,
                pressures,
                boundaryRules,
                verificationSteps,
                ReadabilityUpkeepAuditRenderer.render(
                        topics,
                        routeMaps,
                        pressures,
                        boundaryRules,
                        verificationSteps
                )
        );
    }
}
