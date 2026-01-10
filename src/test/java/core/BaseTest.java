package core;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    // UI Test URL
    protected static final String UI_BASE_URL = "https://www.saucedemo.com/";

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        logger.info("Setting up UI test suite...");
        logger.info("UI test suite setup completed");
    }

    @BeforeMethod(alwaysRun = true)
    @Step("Setting up test environment")
    public void setUp() {
        logger.info("Setting up test...");
        DriverManager.initializeDriver();
        DriverManager.maximizeWindow();
        logger.info("Test setup completed");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Cleaning up test environment")
    public void tearDown() {
        logger.info("Tearing down test...");
        DriverManager.quitDriver();
        logger.info("Test teardown completed");
    }

    @Step("Navigate to URL: {url}")
    protected void navigateToUrl(String url) {
        logger.info("Navigating to URL: {}", url);
        com.codeborne.selenide.Selenide.open(url);
    }

    @Step("Wait for {seconds} seconds")
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Wait interrupted", e);
        }
    }
}
