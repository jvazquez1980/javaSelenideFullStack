package tests.checkout;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.SauceDemoPage.Home;
import pages.home.SauceDemoPage.Product;
import pages.home.SauceDemoPage;
import pages.home.SauceDemoPage.Checkout;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@Epic("SauceDemo E2E Testing")
@Feature("Checkout Flow")
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
        sauceSteps.navigateToCart();
        sauceSteps.verifyCartBadgeMatchesProductCount();
        // Verify cart badge matches product count (equivalent to Cypress code)
        genericSteps.shouldBeVisible(Product.productDescription);
        genericSteps.shouldBeVisible(Product.productTitle);

        genericSteps.clickElement(Checkout.checkoutButton);
        genericSteps.shouldHaveText(Checkout.succesMessage, "Checkout: Your Information");
        sauceSteps.fillCheckoutForm("Quality", "Assurance", "12345");
        genericSteps.clickElement(Checkout.continueButton);

        genericSteps.shouldHaveText(Checkout.paymentInfo, "Payment");
        genericSteps.shouldHaveText(Checkout.paymentValue, "SauceCard");
        genericSteps.shouldHaveText(Checkout.shippingInfo, "Shipping Information");
        genericSteps.shouldHaveText(Checkout.shippingValue, "Free");
        genericSteps.shouldHaveText(Checkout.totalInfo, "Total");
        sauceSteps.verifyTotalCalculation();

        genericSteps.clickElement(Checkout.finish);
        genericSteps.shouldHaveText(Checkout.succesMessage, "Checkout: Complete");
        genericSteps.shouldHaveText(Checkout.thankYouPage, "Thank you for your order");

        logger.info("Product details navigation test completed successfully");
    }
}
