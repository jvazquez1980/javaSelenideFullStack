package tests.product;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import pages.home.SauceDemoPage.Home;
import pages.home.SauceDemoPage.Product;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

@Epic("SauceDemo E2E Testing")
@Feature("Product Validation")
public class ProductValidation extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();

    @Test
    @Story("User can see the products detail to get more information about the products")
    @Description("Verify product information on Products page and Product details page")
    @Severity(SeverityLevel.CRITICAL)
    public void testProductValidation() {
        open(UI_BASE_URL2);
        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        genericSteps.shouldBeVisible(Home.productList);

        ElementsCollection products = $$(Home.productItem);
        for (int i = 0; i < products.size(); i++) {
            products.get(i).shouldBe(Condition.visible);
            products.get(i).$(Product.productImage).shouldBe(Condition.visible);
            products.get(i).$(Product.productTitle).shouldBe(Condition.visible);
            products.get(i).$(Product.productDescription).shouldBe(Condition.visible);
            products.get(i)
                    .$(Product.productPrice)
                    .should(matchText("^\\$\\d+(\\.\\d{1,2})?$"));

            logger.info("Product {} validated successfully", i + 1);
        }
        genericSteps.clickRandomElement(Product.productTitle);
                sleep(1000);
        genericSteps.shouldBeVisible(Home.removeToCartButton);

        // Should verify product information on the Product details page
        $(Product.productImage).shouldBe(Condition.visible);
        $(Product.productTitle).shouldBe(Condition.visible);
        $(Product.productDescription).shouldBe(Condition.visible);

        String detailPrice = $(Product.productPrice).getText().trim();
        assertTrue(detailPrice.matches("^\\$\\d+(\\.\\d{1,2})?$"), "Price format should be $XX.X or $XX.XX");

        logger.info("Product validation test completed");
    }
}
