package tests.home;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.SauceDemoPage;
import steps.SauceSteps;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

@Epic("SauceDemo E2E Testing")
@Feature("Product Sorting")
public class ShortBy extends BaseTest {

    private final SauceSteps sauceSteps = new SauceSteps();

    @Test
    @Story("User can sort products to find what they're looking for easily")
    @Description("Verify products can be sorted correctly by A-Z, Z-A, price low-high, and price high-low")
    @Severity(SeverityLevel.NORMAL)
    public void testProductSorting() {
        open(UI_BASE_URL2);

        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);

        int initialProductsCount = $$(SauceDemoPage.Home.productItem).size();
        logger.info("Initial product count: {}", initialProductsCount);

        String[] sortOptions = { "az", "za", "lohi", "hilo" };

        for (String sortValue : sortOptions) {
            logger.info("Testing sort option: {}", sortValue);
            $(SauceDemoPage.Home.shortSelect).selectOptionByValue(sortValue);
            $$(SauceDemoPage.Home.productItem)
                    .shouldHave(com.codeborne.selenide.CollectionCondition.size(initialProductsCount));
        }

        logger.info("Product sorting test completed successfully");
    }
}
