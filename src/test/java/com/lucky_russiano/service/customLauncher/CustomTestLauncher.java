package com.lucky_russiano.service.customLauncher;

import com.lucky_russiano.service.service.UserServiceTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class CustomTestLauncher {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();// show test result
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
                .filters(
                        TagFilter.includeTags("login")
                )
                //command in terminal ./mvnw clean test -Dgroups=login
//                .selectors(DiscoverySelectors.selectPackage("com.lucky_russiano.service"))
                .build();
        launcher.execute(request, summaryGeneratingListener);
        try (PrintWriter printWriter = new PrintWriter(System.out)){
            summaryGeneratingListener.getSummary().printTo(printWriter); // show test result
        }
    }
}
