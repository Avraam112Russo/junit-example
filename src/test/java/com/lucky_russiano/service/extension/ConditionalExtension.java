package com.lucky_russiano.service.extension;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ConditionalExtension implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
        return System.getProperty("skip") != null // we added this props to vm options
                ? ConditionEvaluationResult.disabled("Skip all tests...")
                : ConditionEvaluationResult.enabled("All tests will be work");
    }
}
