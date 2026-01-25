package stepdefinitions;

import data.Users;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pages.home.SauceDemoPage.*;
import steps.GenericSteps;
import steps.SauceSteps;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class CheckoutSteps {

    private final SauceSteps sauceSteps = new SauceSteps();
    private final GenericSteps genericSteps = new GenericSteps();
    private static final String UI_BASE_URL = "https://www.saucedemo.com/";

    @Given("I am on the SauceDemo homepage")
    public void iAmOnTheSauceDemoHomepage() {
        open(UI_BASE_URL);
    }

    @Given("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        sauceSteps.standardLogin(Users.SauceDemoUser.USERNAME, Users.SauceDemoUser.PASSWORD);
    }

    @Given("I am on the products page")
    public void iAmOnTheProductsPage() {
        genericSteps.shouldBeVisible(Home.productItem);
    }

    @When("I add a random product to cart")
    public void iAddARandomProductToCart() {
        genericSteps.clickRandomElement(Product.productTitle);
        genericSteps.shouldBeVisible(Product.productPrice);
        genericSteps.clickElement(Home.addToCartButton);
    }

    @When("I navigate to the cart")
    public void iNavigateToTheCart() {
        sauceSteps.navigateToCart();
    }

    @Then("the cart badge should match the product count")
    public void theCartBadgeShouldMatchTheProductCount() {
        SauceSteps.verifyCartBadgeMatchesProductCount();
        genericSteps.shouldBeVisible(Product.productDescription);
        genericSteps.shouldBeVisible(Product.productTitle);
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        genericSteps.clickElement(Checkout.checkoutButton);
        genericSteps.shouldHaveText(Checkout.succesMessage, "Checkout: Your Information");
    }

    @When("I fill the checkout form with:")
    public void iFillTheCheckoutFormWith(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> formData = data.get(0);

        String firstName = formData.get("firstName");
        String lastName = formData.get("lastName");
        String zipCode = formData.get("zipCode");

        sauceSteps.fillCheckoutForm(firstName, lastName, zipCode);
    }

    @When("I continue to the overview page")
    public void iContinueToTheOverviewPage() {
        genericSteps.clickElement(Checkout.continueButton);
        genericSteps.shouldHaveText(Checkout.paymentInfo, "Payment");
    }

    @Then("I should see payment information as {string}")
    public void iShouldSeePaymentInformationAs(String paymentMethod) {
        genericSteps.scrollToElement(Checkout.paymentInfo);
        genericSteps.shouldHaveText(Checkout.paymentValue, paymentMethod);
    }

    @Then("I should see shipping information as {string}")
    public void iShouldSeeShippingInformationAs(String shippingMethod) {
        genericSteps.shouldHaveText(Checkout.shippingInfo, "Shipping Information");
        genericSteps.shouldHaveText(Checkout.shippingValue, shippingMethod);
    }

    @Then("the total calculation should be correct")
    public void theTotalCalculationShouldBeCorrect() {
        genericSteps.shouldHaveText(Checkout.totalInfo, "Total");
        sauceSteps.verifyTotalCalculation();
    }

    @When("I finish the checkout")
    public void iFinishTheCheckout() {
        genericSteps.clickElement(Checkout.finish);
    }

    @Then("I should see the success message {string}")
    public void iShouldSeeTheSuccessMessage(String message) {
        genericSteps.shouldHaveText(Checkout.thankYouPage, message);
    }

    @Then("I should be on the complete page")
    public void iShouldBeOnTheCompletePage() {
        genericSteps.shouldHaveText(Checkout.succesMessage, "Checkout: Complete");
    }

}
