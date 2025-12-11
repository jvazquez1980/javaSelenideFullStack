package core;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

public abstract class ApiBaseTest {
    protected static final Logger logger = LoggerFactory.getLogger(ApiBaseTest.class);
    
    // API Test URL
    protected static final String API_BASE_URL = "https://jsonplaceholder.typicode.com";
    
    @BeforeSuite
    public void setupApiSuite() {
        logger.info("Setting up API test suite...");
        configureRestAssured();
        logger.info("API test suite setup completed");
    }
    
    private void configureRestAssured() {
        RestAssured.baseURI = API_BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        // Add logging filters for better debugging
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        
        logger.info("RestAssured configured with base URI: {}", API_BASE_URL);
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
    
    @Step("Log API test step: {message}")
    protected void logApiStep(String message) {
        logger.info("API Test Step: {}", message);
    }
}
