package stepdefinitions;

import io.cucumber.java.en.*;
import pages.home.SauceDemoPage.*;
import steps.GenericSteps;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.testng.Assert.assertTrue;

public class LoginSteps {

    private final GenericSteps genericSteps = new GenericSteps();

    @Then("I should be on the products page")
    public void iShouldBeOnTheProductsPage() {
        genericSteps.shouldBeVisible(Home.productList);
        assertTrue(url().contains("/inventory.html"), "URL should contain /inventory.html after login");
    }
}
