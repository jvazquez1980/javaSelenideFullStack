package pages.home;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    
    // Page element selectors
    private static final String letMeHackButton = "#booking";
    private static final String roomsSection = "#rooms";
    private static final String bookRoomButton = "#booking";
    private static final String contactSection = "#contact";
    private static final String nameField = "[data-testid='ContactName']";
    private static final String emailField = "[data-testid='ContactEmail']";
    private static final String phoneField = "[data-testid='ContactPhone']";
    private static final String subjectField = "[data-testid='ContactSubject']";
    private static final String messageField = "[data-testid='ContactDescription']";
    // SUBMIT_BUTTON will use byText() in the method instead of CSS selector
    
    @Step("Verify home page is loaded")
    public HomePage verifyPageLoaded() {
        logger.info("Verifying home page is loaded");
        $(letMeHackButton).shouldBe(visible);
        return this;
    }
    
    @Step("Click 'Let me hack!' button")
    public HomePage clickLetMeHack() {
        logger.info("Clicking 'Let me hack!' button");
        $(letMeHackButton).click();
        return this;
    }
    
    @Step("Verify rooms are displayed")
    public HomePage verifyRoomsDisplayed() {
        logger.info("Verifying rooms are displayed");
        $(roomsSection).shouldBe(visible);
        return this;
    }
    
    @Step("Click book room button")
    public HomePage clickBookRoom() {
        logger.info("Clicking book room button");
        // Use Selenide's byText selector to find button containing "check availability"
        $(byText("check availability")).click();
        return this;
    }
    
    @Step("Scroll to contact section")
    public HomePage scrollToContact() {
        logger.info("Scrolling to contact section");
        $(contactSection).scrollTo();
        return this;
    }
    
    @Step("Fill contact form with name: {name}, email: {email}, phone: {phone}, subject: {subject}, message: {message}")
    public HomePage fillContactForm(String name, String email, String phone, String subject, String message) {
        logger.info("Filling contact form");
        $(nameField).shouldBe(visible).setValue(name);
        $(emailField).setValue(email);
        $(phoneField).setValue(phone);
        $(subjectField).setValue(subject);
        $(messageField).setValue(message);
        return this;
    }
    
    @Step("Submit contact form")
    public HomePage submitContactForm() {
        logger.info("Submitting contact form");
        // Use Selenide's byText selector to find button containing "Submit"
        $(byText("Submit")).click();
        return this;
    }
    
    @Step("Verify contact form submission")
    public HomePage verifyContactSubmission() {
        logger.info("Verifying contact form submission");
        // Add verification logic based on the actual response
        return this;
    }
    
    public SelenideElement getNameField() {
        return $(nameField);
    }
    
    public SelenideElement getEmailField() {
        return $(emailField);
    }
    
    public SelenideElement getPhoneField() {
        return $(phoneField);
    }
    
    public SelenideElement getSubjectField() {
        return $(subjectField);
    }
    
    public SelenideElement getMessageField() {
        return $(messageField);
    }
}
