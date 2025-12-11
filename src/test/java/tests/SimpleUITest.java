package tests;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.HomePageSteps;

@Epic("UI Testing")
@Feature("Home Page Navigation")
public class SimpleUITest extends BaseTest {

    private HomePageSteps homePageSteps;

    @BeforeMethod
    public void initializeSteps() {
        homePageSteps = new HomePageSteps();
    }

    @Test
    @Story("User can navigate and interact with home page")
    @Description("Verify user can load home page, view rooms, and submit contact form")
    @Severity(SeverityLevel.CRITICAL)
    public void testHomePageNavigation() {
        // Given: User navigates to the home page
        navigateToUrl(UI_BASE_URL);

        // When: User verifies page is loaded and rooms are displayed
        homePageSteps.verifyPageLoaded()
                .verifyRoomsDisplayed();

        // Then: User can interact with contact form
        homePageSteps.scrollToContact()
                .fillContactForm(
                        Users.TestUser.NAME,
                        Users.TestUser.EMAIL,
                        Users.TestUser.PHONE,
                        Users.TestUser.SUBJECT,
                        Users.TestUser.MESSAGE)
                .submitContactForm()
                .verifyContactSubmission();

        logger.info("Home page navigation test completed successfully");
    }

    @Test
    @Story("User can view available rooms")
    @Description("Verify user can load home page and view room availability")
    @Severity(SeverityLevel.NORMAL)
    public void testRoomDisplay() {
        // Given: User navigates to the home page
        navigateToUrl(UI_BASE_URL);

        // When & Then: User verifies rooms are displayed
        homePageSteps.verifyPageLoaded()
                .verifyRoomsDisplayed();

        logger.info("Room display test completed successfully");
    }
}
