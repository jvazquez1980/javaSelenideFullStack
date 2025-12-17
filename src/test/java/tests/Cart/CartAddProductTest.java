package tests.Cart;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import pages.home.SauceDemoPage.Home;
import pages.home.SauceDemoPage.Product;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("SauceDemo E2E Testing")
@Feature("Cart Management")
public class CartAddProductTest extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();

    @Test
    @Story("User can add products to cart")
    @Description("Verify user can add products to cart from Products page and Product details page")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddProductsToCart() {
        open(UI_BASE_URL2);
        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        // Verify products page is displayed
        genericSteps.shouldBeVisible(Home.productItem);

        // Add product from Products page
        sauceSteps.addLastProductToCart()
                .verifyCartHasProducts();

        // Navigate to Product details
        sauceSteps.clickFirstProduct();
        genericSteps.shouldBeVisible(Product.productImage);


        $(Product.add).click();

        // Add product from Product details page
        sauceSteps.verifyCartCount(2);

        // Verify Remove button is visible
        $(Product.remove).shouldBe(visible);

        logger.info("Cart add product test completed successfully");
    }
}
