package tests;

import core.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.HomePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

@Epic("Simple UI Testing")
@Feature("Basic Navigation")
public class SimpleUITest extends BaseTest {
    
    private HomePage homePage;
    
    @Test
    @Story("Browser opens and navigates correctly")
    @Description("Simple test to verify browser functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testBrowserNavigation() {
        homePage = new HomePage();
        
        // Navigate to the website
        navigateToUrl(UI_BASE_URL);
        
        // Verify page title or any basic element
        homePage.verifyPageLoaded()
                .verifyRoomsDisplayed()   
                     
        // Use HomePage methods
        homePage.scrollToContact()
                .fillContactForm("John Doe", "john.doe@example.com", "123456789", "Test Subject", "Test Message")
                .submitContactForm()
                .verifyContactSubmission();
        
        logger.info("Browser navigation test completed successfully");
    }
}
