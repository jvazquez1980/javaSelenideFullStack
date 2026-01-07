package tests.checkout;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.SauceDemoPage.Home;
import pages.home.SauceDemoPage.Product;
import pages.home.SauceDemoPage.Checkout;
import pages.home.SauceDemoPage.Cart;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Selenide.*;

@Epic("SauceDemo E2E Testing")
@Feature("Product Details")
public class CheckoutTest extends BaseTest {

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

        genericSteps.clickRandomElement(Product.productTitle);

        genericSteps.shouldBeVisible(Product.productPrice);
        genericSteps.clickElement(Home.addToCartButton);
        genericSteps.clickElement(Cart.cartWithProducts);
        genericSteps.shouldBeVisible(Home.removeToCartButton);

        genericSteps.clickElement(Checkout.checkoutButton);

        // Count values
        SauceSteps.verifyCartCount(1);
        genericSteps.shouldBeVisible(Product.productDescription);
        genericSteps.shouldBeVisible(Product.productTitle);

        logger.info("Product details navigation test completed successfully");
    }
}
