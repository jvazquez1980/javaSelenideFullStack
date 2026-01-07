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

    @Step("Click product title from item with Remove button")
    public SauceSteps clickProductWithRemoveButton() {
        // Espera que el botón Remove sea visible, luego busca el producto y click en
        // título
        $(SauceDemoPage.Home.removeToCartButton).shouldBe(visible);
        $$(SauceDemoPage.Home.productItem)
                .findBy(text("Remove"))
                .$(SauceDemoPage.Product.productTitle).click();
        logger.info("Clicked on product that has Remove button");
        return this;
    }

    @Step("Navigate to cart")
    public SauceSteps navigateToCart() {
        $(SauceDemoPage.Cart.cartWithProducts).click();
        logger.info("Navigated to cart");
        return this;
    }

    @Step("Verify checkout button is visible")
    public SauceSteps verifyCheckoutVisible() {
        $(SauceDemoPage.Checkout.checkoutButton).shouldBe(visible);
        logger.info("Checkout button is visible");
        return this;
    }

    @Step("Verify cart badge matches product count on page")
    public static void verifyCartBadgeMatchesProductCount() {
        int productCount = $$(SauceDemoPage.Home.productItem).size();
        $(SauceDemoPage.Cart.cartWithProducts).shouldHave(text(String.valueOf(productCount)));
        logger.info("Cart badge matches product count: {}", productCount);
    }

    @Step("Fill checkout form with firstName: {firstName}, lastName: {lastName}, zipCode: {zipCode}")
    public SauceSteps fillCheckoutForm(String firstName, String lastName, String zipCode) {
        logger.info("Filling checkout form");
        $(SauceDemoPage.Checkout.firstName).shouldBe(visible).clear();
        $(SauceDemoPage.Checkout.firstName).sendKeys(firstName);
        $(SauceDemoPage.Checkout.firstName).shouldHave(value(firstName));
        $(SauceDemoPage.Checkout.lastName).shouldBe(visible).clear();
        $(SauceDemoPage.Checkout.lastName).sendKeys(lastName);
        $(SauceDemoPage.Checkout.lastName).shouldHave(value(lastName));
        $(SauceDemoPage.Checkout.zipCode).shouldBe(visible).clear();
        $(SauceDemoPage.Checkout.zipCode).sendKeys(zipCode);
        $(SauceDemoPage.Checkout.zipCode).shouldHave(value(zipCode));
        logger.info("Checkout form filled successfully");
        return this;
    }

    @Step("Verify total calculation (subtotal + tax = total)")
    public SauceSteps verifyTotalCalculation() {
        String subtotalText = $(SauceDemoPage.Checkout.subtotalValue).getText();
        String taxText = $(SauceDemoPage.Checkout.taxValue).getText();
        String totalText = $(SauceDemoPage.Checkout.totalValue).getText();

        double subtotal = Double.parseDouble(subtotalText.replaceAll("[^0-9.]", ""));
        double tax = Double.parseDouble(taxText.replaceAll("[^0-9.]", ""));
        double total = Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));

        double expectedTotal = subtotal + tax;
        String expectedTotalStr = String.format("%.2f", expectedTotal);
        String actualTotalStr = String.format("%.2f", total);

        assertTrue(expectedTotalStr.equals(actualTotalStr),
                String.format("Total mismatch: expected %s but got %s", expectedTotalStr, actualTotalStr));

        logger.info("Total calculation verified: {} + {} = {}", subtotal, tax, total);
        return this;
    }
}
