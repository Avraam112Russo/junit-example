package com.lucky_russiano.service.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalExtension implements BeforeAllCallback, BeforeEachCallback, AfterTestExecutionCallback { //beforeeach, aftereach, afterall and more
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("Before all callback...");
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println("Before each callback...");
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        System.out.println("After tests execution callback...");
    }
}
