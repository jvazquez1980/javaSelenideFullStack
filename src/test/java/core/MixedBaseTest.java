package core;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * Base class for tests that need both UI (Selenide) and API (RestAssured) functionality
 * Combines BaseTest and ApiBaseTest capabilities
 */
public abstract class MixedBaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(MixedBaseTest.class);
    
    // Test URLs
    protected static final String UI_BASE_URL = "https://automationintesting.online/";
    protected static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";
    
    @BeforeSuite
    public void setupMixedSuite() {
        logger.info("Setting up mixed (UI + API) test suite...");
        configureRestAssured();
        logger.info("Mixed test suite setup completed");
    }
    
    @BeforeMethod
    @Step("Setting up mixed test environment")
    public void setUp() {
        logger.info("Setting up mixed test...");
        DriverManager.initializeDriver();
        DriverManager.maximizeWindow();
        logger.info("Mixed test setup completed");
    }
    
    @AfterMethod
    @Step("Cleaning up mixed test environment")
    public void tearDown() {
        logger.info("Tearing down mixed test...");
        DriverManager.quitDriver();
        logger.info("Mixed test teardown completed");
    }
    
    private void configureRestAssured() {
        RestAssured.baseURI = API_BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        // Add logging filters for better debugging
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        
        logger.info("RestAssured configured with base URI: {}", API_BASE_URL);
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
    
    @Step("Log mixed test step: {message}")
    protected void logTestStep(String message) {
        logger.info("Mixed Test Step: {}", message);
    }
}
