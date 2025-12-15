package steps;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.home.HomePage;

public class BookingSteps {
    private static final Logger logger = LoggerFactory.getLogger(BookingSteps.class);
    private final GenericSteps genericSteps;

    public BookingSteps() {
        this.genericSteps = new GenericSteps();
    }

    @Step("Navigate to booking page and verify rooms")
    public BookingSteps navigateToBooking() {
        logger.info("Navigating to booking page");
        genericSteps.shouldBeVisible(HomePage.PAGE_BANNER)
                .clickElementById(HomePage.booking)
                .shouldBeVisibleById(HomePage.ROOMS_SECTION_ID);
        return this;
    }

    @Step("Initiate room booking")
    public BookingSteps initiateRoomBooking() {
        logger.info("Initiating room booking");
        genericSteps.clickElement(HomePage.BOOK_ROOM_BUTTON_CSS);
        return this;
    }

    @Step("Submit contact inquiry")
    public BookingSteps submitContactInquiry(String name, String email, String phone, String subject, String message) {
        logger.info("Submitting contact inquiry for: {}", name);
        genericSteps.scrollToElementById(HomePage.CONTACT_SECTION_ID)
                .shouldBeVisible(HomePage.NAME_FIELD)
                .setValue(HomePage.NAME_FIELD, name)
                .setValue(HomePage.EMAIL_FIELD, email)
                .setValue(HomePage.PHONE_FIELD, phone)
                .setValue(HomePage.SUBJECT_FIELD, subject)
                .setValue(HomePage.MESSAGE_FIELD, message)
                .clickElement(HomePage.SUBMIT_BUTTON_CSS)
                .shouldBeVisibleById(HomePage.CONTACT_SECTION_ID);
        return this;
    }

    @Step("Verify booking page elements")
    public BookingSteps verifyBookingPageElements() {
        logger.info("Verifying booking page elements");
        genericSteps.shouldBeVisible(HomePage.PAGE_BANNER)
                    .shouldBeVisibleById(HomePage.ROOMS_SECTION_ID);
        return this;
    }

    @Step("Complete booking flow")
    public BookingSteps completeBookingFlow(String name, String email, String phone, String subject, String message) {
        logger.info("Completing full booking flow");
        navigateToBooking();
        initiateRoomBooking();
        submitContactInquiry(name, email, phone, subject, message);
        return this;
    }
}
