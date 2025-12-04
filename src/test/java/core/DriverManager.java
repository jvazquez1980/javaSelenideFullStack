package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    private DriverManager() {
        // Private constructor to prevent instantiation
    }
    
    public static void initializeDriver() {
        if (driverThreadLocal.get() == null) {
            logger.info("Initializing Chrome driver...");
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            
            // For headless mode (uncomment if needed)
            // options.addArguments("--headless");
            
            WebDriver driver = new ChromeDriver(options);
            driverThreadLocal.set(driver);
            
            // Configure Selenide
            Configuration.browser = "chrome";
            Configuration.browserSize = "1920x1080";
            Configuration.timeout = 10000;
            Configuration.pageLoadTimeout = 30000;
            Configuration.headless = false;
            
            WebDriverRunner.setWebDriver(driver);
            
            logger.info("Chrome driver initialized successfully");
        }
    }
    
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Quitting driver...");
            driver.quit();
            driverThreadLocal.remove();
            logger.info("Driver quit successfully");
        }
    }
    
    public static void maximizeWindow() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }
}
