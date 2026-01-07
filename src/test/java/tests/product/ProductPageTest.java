package tests.product;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;

import pages.home.SauceDemoPage.Home;
import pages.home.SauceDemoPage.Product;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Selenide.*;

@Epic("SauceDemo E2E Testing")
@Feature("Product Details")
public class ProductPageTest extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();

    @Test
    @Story("User can open the products detail page to get more information about the products")
    @Description("Navigate to Products page and access product details")
    @Severity(SeverityLevel.NORMAL)
    public void testProductDetailsNavigation() {
        open(UI_BASE_URL);

        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        genericSteps.shouldBeVisible(Home.productItem);

        $$(Product.productDetails)
                .filter(Condition.visible)
                .shouldHave(CollectionCondition.sizeGreaterThan(0));

        genericSteps.clickRandomElement(Product.productTitle);

        genericSteps.shouldBeVisible(Product.productPrice);
        genericSteps.clickElement(Home.addToCartButton);
        genericSteps.shouldBeVisible(Home.removeToCartButton);

        logger.info("Product details navigation test completed successfully");
    }
}
