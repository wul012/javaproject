package com.codexdemo.orderplatform.ops.maintenance.readability;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadabilityUpkeepRegistryService {

    static final String RESPONSE_VERSION = "Java v1781";
    static final String ENDPOINT =
            ReadabilityUpkeepRoutePaths.BASE_PATH
                    + ReadabilityUpkeepRoutePaths.UPKEEP_REGISTRY;
    static final String PROFILE = "java-ops-readability-upkeep-registry.v1";

    @Transactional(readOnly = true)
    public ReadabilityUpkeepRegistryResponse registry() {
        var topics = ReadabilityTopicCatalog.topics();
        var packageRules = ReadabilityPackageRuleCatalog.packageRules();
        var templateRules = ReadabilityRegistryTemplateCatalog.templateRules();
        var classNameTrials = ReadabilityClassNameTrialCatalog.classNameTrials();
        var boundaryRules = ReadabilityBoundaryCatalog.boundaryRules();
        var verificationSteps = ReadabilityVerificationCatalog.verificationSteps();
        return ReadabilityUpkeepRegistrySupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                PROFILE,
                topics,
                packageRules,
                templateRules,
                classNameTrials,
                boundaryRules,
                verificationSteps,
                ReadabilityUpkeepRegistryRenderer.render(
                        topics,
                        packageRules,
                        templateRules,
                        classNameTrials,
                        boundaryRules,
                        verificationSteps
                )
        );
    }
}
