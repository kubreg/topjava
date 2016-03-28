package ru.javawebinar.topjava.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by kubreg on 29.03.2016.
 */
public class MethodExecutedTimeRule implements TestRule {
    private static final Logger log = getLogger(MethodExecutedTimeRule.class);

    @Override
    public Statement apply(Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.currentTimeMillis();
                statement.evaluate();
                log.info("{} executed in {} ms", description.getMethodName(), System.currentTimeMillis() - startTime);
            }
        };
    }
}
