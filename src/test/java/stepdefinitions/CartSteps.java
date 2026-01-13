package stepdefinitions;

import io.cucumber.java.en.*;
import pages.home.SauceDemoPage.*;
import steps.GenericSteps;
import steps.SauceSteps;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CartSteps {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();

    @Then("I should see the cart badge with count {int}")
    public void iShouldSeeTheCartBadgeWithCount(int count) {
        sauceSteps.verifyCartCount(count);
    }

    @When("I navigate to the added product details")
    public void iNavigateToTheAddedProductDetails() {
        $$(Home.productItem)
                .findBy(text("Remove"))
                .$(Product.productTitle)
                .click();
    }

    @Then("I should see the remove button")
    public void iShouldSeeTheRemoveButton() {
        $(Product.remove).shouldBe(visible);
    }

    @When("I navigate to the cart page")
    public void iNavigateToTheCartPage() {
        genericSteps.clickElement(Cart.cartWithProducts);
    }

    @Then("I should see the checkout button")
    public void iShouldSeeTheCheckoutButton() {
        genericSteps.shouldBeVisible(Checkout.checkoutButton);
    }
}
