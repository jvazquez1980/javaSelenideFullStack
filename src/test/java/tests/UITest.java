package tests;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import steps.BookingSteps;

@Epic("UI Testing")
@Feature("Booking System Navigation")
public class UITest extends BaseTest {
    
    private BookingSteps bookingSteps;
    
    @Test(priority = 1)
    @Story("User can browse and interact with booking system")
    @Description("Test that verifies user can navigate through the booking system and submit contact form")
    @Severity(SeverityLevel.CRITICAL)
    public void testBookingSystemNavigation() {
        bookingSteps = new BookingSteps();
        
        // Navigate to the booking website
        navigateToUrl(UI_BASE_URL);
        
        // Perform booking flow with test data
        bookingSteps.completeBookingFlow(
                Users.TestUser.NAME,
                Users.TestUser.EMAIL,
                Users.TestUser.PHONE,
                Users.TestUser.SUBJECT,
                Users.TestUser.MESSAGE
        );
        
        logger.info("Booking system navigation test completed successfully");
    }

     
    @Test(priority = 3)
    @Story("Invalid login attempts are handled correctly")
    @Description("Test that verifies system handles invalid login attempts properly")
    @Severity(SeverityLevel.MEDIUM)
    public void testInvalidLogin() {
        loginSteps = new LoginSteps();
        
        // Navigate to the booking website
        navigateToUrl(UI_BASE_URL);
        
        // Attempt login with invalid credentials
        loginSteps.attemptInvalidLogin(Users.InvalidUser.USERNAME, Users.InvalidUser.PASSWORD);
        
        logger.info("Invalid login test completed successfully");
    }
}
