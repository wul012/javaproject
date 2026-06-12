package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.ArrayList;
import java.util.List;

final class ReadabilityUpkeepAuditRenderer {

    private ReadabilityUpkeepAuditRenderer() {
    }

    static List<ReadabilityUpkeepAuditResponse.MarkdownSection> render(
            List<ReadabilityUpkeepAuditResponse.AuditTopic> topics,
            List<ReadabilityUpkeepAuditResponse.RouteServiceTestMap> routeMaps,
            List<ReadabilityUpkeepAuditResponse.RootPackagePressure> pressures,
            List<ReadabilityUpkeepAuditResponse.BoundaryRule> boundaryRules,
            List<ReadabilityUpkeepAuditResponse.VerificationStep> verificationSteps
    ) {
        return List.of(
                section("Audit Topics", renderTopics(topics)),
                section("Route Service Test Maps", renderRouteMaps(routeMaps)),
                section("Root Package Pressure", renderPressures(pressures)),
                section("Boundary Rules", renderBoundaries(boundaryRules)),
                section("Verification Steps", renderVerificationSteps(verificationSteps))
        );
    }

    private static List<String> renderTopics(
            List<ReadabilityUpkeepAuditResponse.AuditTopic> topics
    ) {
        List<String> lines = new ArrayList<>();
        for (var topic : topics) {
            lines.add("- " + topic.code()
                    + " docsPath=" + topic.docsPath()
                    + " evidence=" + topic.evidence()
                    + " required=" + topic.required());
        }
        return lines;
    }

    private static List<String> renderRouteMaps(
            List<ReadabilityUpkeepAuditResponse.RouteServiceTestMap> routeMaps
    ) {
        List<String> lines = new ArrayList<>();
        for (var routeMap : routeMaps) {
            lines.add("- " + routeMap.route()
                    + " controller=" + routeMap.controller()
                    + " service=" + routeMap.service()
                    + " response=" + routeMap.response()
                    + " tests=" + routeMap.tests().size()
                    + " readOnly=" + routeMap.readOnly());
        }
        return lines;
    }

    private static List<String> renderPressures(
            List<ReadabilityUpkeepAuditResponse.RootPackagePressure> pressures
    ) {
        List<String> lines = new ArrayList<>();
        for (var pressure : pressures) {
            lines.add("- " + pressure.area()
                    + " currentLocation=" + pressure.currentLocation()
                    + " migrationRequiredNow=" + pressure.migrationRequiredNow()
                    + " preferredDirection=" + pressure.preferredDirection());
        }
        return lines;
    }

    private static List<String> renderBoundaries(
            List<ReadabilityUpkeepAuditResponse.BoundaryRule> boundaryRules
    ) {
        List<String> lines = new ArrayList<>();
        for (var rule : boundaryRules) {
            lines.add("- " + rule.code()
                    + " forbiddenAction=" + rule.forbiddenAction()
                    + " allowed=" + rule.allowed());
        }
        return lines;
    }

    private static List<String> renderVerificationSteps(
            List<ReadabilityUpkeepAuditResponse.VerificationStep> verificationSteps
    ) {
        List<String> lines = new ArrayList<>();
        for (var step : verificationSteps) {
            lines.add("- " + step.name()
                    + " commandOrClass=" + step.commandOrClass()
                    + " required=" + step.required());
        }
        return lines;
    }

    private static ReadabilityUpkeepAuditResponse.MarkdownSection section(
            String heading,
            List<String> lines
    ) {
        return new ReadabilityUpkeepAuditResponse.MarkdownSection(heading, List.copyOf(lines));
    }
}
