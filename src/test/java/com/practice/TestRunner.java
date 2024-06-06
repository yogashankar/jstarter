package com.practice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"StepDefinitions"},
        features = "Features",
        tags = "@WebTest",
        dryRun = false,
        plugin = {"pretty", "html:target/cucumber-reports/Cucumber.html"}
)
public class TestRunner {
}
