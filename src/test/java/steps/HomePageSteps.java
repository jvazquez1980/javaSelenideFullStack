package steps;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.home.HomePage;

/**
 * HomePageSteps - Combines GenericSteps with HomePage locators
 * This is the BDD approach: Steps + Page Objects
 */
public class HomePageSteps {
    private static final Logger logger = LoggerFactory.getLogger(HomePageSteps.class);
    private final GenericSteps genericSteps;

    public HomePageSteps() {
        this.genericSteps = new GenericSteps();
    }

    @Step("Verify home page is loaded")
    public HomePageSteps verifyPageLoaded() {
        logger.info("Verifying home page is loaded");
        genericSteps.shouldBeVisible(HomePage.PAGE_BANNER);
        return this;
    }

    @Step("Verify rooms are displayed")
    public HomePageSteps verifyRoomsDisplayed() {
        logger.info("Verifying rooms are displayed");
        genericSteps.shouldBeVisible(HomePage.ROOMS_SECTION_ID);
        return this;
    }

    @Step("Click book room button")
    public HomePageSteps clickBookRoom() {
        logger.info("Clicking book room button");
        genericSteps.clickElement(HomePage.BOOK_ROOM_BUTTON_CSS);
        return this;
    }

    @Step("Scroll to contact section")
    public HomePageSteps scrollToContact() {
        logger.info("Scrolling to contact section");
        genericSteps.scrollToElement(HomePage.CONTACT_SECTION_ID);
        return this;
    }

    @Step("Fill contact form with name: {name}, email: {email}, phone: {phone}, subject: {subject}, message: {message}")
    public HomePageSteps fillContactForm(String name, String email, String phone, String subject, String message) {
        logger.info("Filling contact form");
        genericSteps.shouldBeVisible(HomePage.NAME_FIELD);
        genericSteps.setValue(HomePage.NAME_FIELD, name);
        genericSteps.setValue(HomePage.EMAIL_FIELD, email);
        genericSteps.setValue(HomePage.PHONE_FIELD, phone);
        genericSteps.setValue(HomePage.SUBJECT_FIELD, subject);
        genericSteps.setValue(HomePage.MESSAGE_FIELD, message);
        return this;
    }

    @Step("Submit contact form")
    public HomePageSteps submitContactForm() {
        logger.info("Submitting contact form");
        genericSteps.clickElement(".btn.btn-primary");
        return this;
    }

    @Step("Verify contact form submission")
    public HomePageSteps verifyContactSubmission() {
        logger.info("Verifying contact form submission");
        genericSteps.shouldBeVisible(HomePage.CONTACT_SECTION_ID);
        return this;
    }
}
