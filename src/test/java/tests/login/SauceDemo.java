package tests.login;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.SauceSteps;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertTrue;

@Epic("SauceDemo E2E Testing")
@Feature("Login and Navigation")
public class SauceDemo extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();

    @Test
    @Story("User can login and navigate to inventory page")
    @Description("Verify user can login with valid credentials, land on inventory page, see cart icon, and open burger menu")
    @Severity(SeverityLevel.CRITICAL)
    public void testSauceDemoLogin() {
        open(UI_BASE_URL2);

        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        assertTrue(url().contains("/inventory.html"), "URL should contain /inventory.html after login");
        logger.info("SauceDemo login test completed successfully");
    }
}
