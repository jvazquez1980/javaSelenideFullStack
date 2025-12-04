package steps;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.home.HomePage;

public class BookingSteps {
    private static final Logger logger = LoggerFactory.getLogger(BookingSteps.class);
    private final HomePage homePage;
    
    public BookingSteps() {
        this.homePage = new HomePage();
    }
    
    @Step("Navigate to booking page and verify rooms")
    public BookingSteps navigateToBooking() {
        logger.info("Navigating to booking page");
        homePage.verifyPageLoaded()
                .clickLetMeHack()
                .verifyRoomsDisplayed();
        return this;
    }
    
    @Step("Initiate room booking")
    public BookingSteps initiateRoomBooking() {
        logger.info("Initiating room booking");
        homePage.clickBookRoom();
        return this;
    }
    
    @Step("Submit contact inquiry")
    public BookingSteps submitContactInquiry(String name, String email, String phone, String subject, String message) {
        logger.info("Submitting contact inquiry for: {}", name);
        homePage.scrollToContact()
                .fillContactForm(name, email, phone, subject, message)
                .submitContactForm()
                .verifyContactSubmission();
        return this;
    }
    
    @Step("Verify booking page elements")
    public BookingSteps verifyBookingPageElements() {
        logger.info("Verifying booking page elements");
        homePage.verifyPageLoaded()
                .verifyRoomsDisplayed();
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
    
    public HomePage getHomePage() {
        return homePage;
    }
}
