package tests.Cart;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import pages.home.SauceDemoPage.*;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("SauceDemo E2E Testing")
@Feature("Cart Status")
public class CartStatusTest extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();

    @Test(groups = { "critical", "all" })
    @Story("User can view shopping cart status with correct product count")
    @Description("Verify cart status shows correct count across Products page, Product details, and Cart page")
    @Severity(SeverityLevel.CRITICAL)
    public void testCartStatusAcrossPages() {
        open(UI_BASE_URL);
        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        // Add product from Products page
        genericSteps.shouldBeVisible(Home.productItem);
        genericSteps.clickRandomElement(Home.addToCartButton);
        genericSteps.shouldBeVisible(Cart.cartWithProducts);

        // Navigate to Product details of added product and verify cart status
        // Busca el productItem que contiene "Remove" y click en su título
        $$(Home.productItem)
                .findBy(text("Remove"))
                .$(Product.productTitle)
                .click();
        $(Product.remove).shouldBe(visible);
        sauceSteps.verifyCartCount(1);

        // Navigate to Cart and verify status
        genericSteps.clickElement(Cart.cartWithProducts);
        genericSteps.shouldBeVisible(Checkout.checkoutButton);

        sauceSteps.verifyCartCount(1);

        logger.info("Cart status test completed successfully");
    }
}
