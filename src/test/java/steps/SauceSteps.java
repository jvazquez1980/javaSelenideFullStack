package steps;

import data.Users;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.home.SauceDemoPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertTrue;

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

    @Step("Verify cart has products")
    public SauceSteps verifyCartHasProducts() {
        $(SauceDemoPage.Cart.cartWithProducts).shouldBe(visible);
        String badgeText = $(SauceDemoPage.Cart.cartWithProducts).getText().trim();
        int count = Integer.parseInt(badgeText);
        assertTrue(count > 0, "Cart should have at least 1 product");
        logger.info("Cart has {} products", count);
        return this;
    }

    @Step("Verify cart has {expectedCount} products")
    public SauceSteps verifyCartCount(int expectedCount) {
        $(SauceDemoPage.Cart.cartWithProducts).shouldHave(text(String.valueOf(expectedCount)));
        logger.info("Cart has {} products", expectedCount);
        return this;
    }

    @Step("Add last product to cart from products page")
    public SauceSteps addLastProductToCart() {
        $$(SauceDemoPage.Home.addToCartButton).last().click();
        logger.info("Added last product to cart");
        return this;
    }

    @Step("Click on first product title")
    public SauceSteps clickFirstProduct() {
        $$(SauceDemoPage.Product.productTitle).first().click();
        logger.info("Clicked on first product");
        return this;
    }
}
