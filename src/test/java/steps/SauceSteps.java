package steps;

import data.Users;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.home.SauceDemoPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class SauceSteps {
    private static final Logger logger = LoggerFactory.getLogger(SauceSteps.class);

    @Step("Perform standard login with username: {username}")
    public SauceSteps standardLogin(String username, String password) {
        logger.info("Performing SauceDemo login with username: {}", username);

        $(SauceDemoPage.Login.userName).shouldBe(visible).shouldBe(enabled);
        $(SauceDemoPage.Login.userName).clear();
        $(SauceDemoPage.Login.userName).sendKeys(username);

        $(SauceDemoPage.Login.password).shouldBe(visible).shouldBe(enabled);
        $(SauceDemoPage.Login.password).clear();
        $(SauceDemoPage.Login.password).sendKeys(password);

        $(SauceDemoPage.Login.loginCta).shouldBe(enabled).click();

        logger.info("Login completed successfully");
        return this;
    }
}
