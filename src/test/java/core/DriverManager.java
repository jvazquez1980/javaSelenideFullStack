package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    
    private DriverManager() {
        // Private constructor to prevent instantiation
    }
    
    public static void initializeDriver() {
        logger.info("Configuring Selenide...");
        
        // Configure Selenide - let it handle WebDriver creation
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 30000;
        Configuration.headless = false;
        
        // Configure Chrome options properly for Selenide 6.x
        System.setProperty("chromeoptions.args", "--no-sandbox,--disable-dev-shm-usage,--disable-blink-features=AutomationControlled");
        
        logger.info("Selenide configured successfully");
    }
    
    public static void quitDriver() {
        logger.info("Closing browser...");
        WebDriverRunner.closeWebDriver();
        logger.info("Browser closed successfully");
    }
    
    public static void maximizeWindow() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.getWebDriver().manage().window().maximize();
        }
    }
}
