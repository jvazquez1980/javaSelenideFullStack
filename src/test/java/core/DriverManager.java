package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    public static void initializeDriver() {
        logger.info("Configuring Selenide...");
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();

        // desactiva guardado de contraseñas
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        // desactiva alerta de contraseña comprometida
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        Configuration.browserCapabilities = options;
        // Configure Selenide - let it handle WebDriver creation
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;

        // Configure Selenide timeouts
        Configuration.timeout = 5000; // 5 seconds timeout for element finding
        Configuration.pollingInterval = 100; // Check every 100ms
        Configuration.assertionMode = com.codeborne.selenide.AssertionMode.STRICT;
        Configuration.fastSetValue = true;
        Configuration.clickViaJs = false;

        // Enable detailed logging for debugging (similar to Cypress)
        Configuration.reportsFolder = "build/reports/tests";
        Configuration.savePageSource = false;

        // Add Allure-Selenide listener for step-by-step logging
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
                        .includeSelenideSteps(true));

        // Configure Chrome options properly for Selenide 6.x
        System.setProperty("chromeoptions.args",
                "--no-sandbox,--disable-dev-shm-usage,--disable-blink-features=AutomationControlled");

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
