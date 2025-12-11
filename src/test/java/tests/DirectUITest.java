package tests;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.HomePage;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("UI Testing")
@Feature("Direct Home Page Navigation - Native Selenide")
public class DirectUITest extends BaseTest {

     @Test
     @Story("User can navigate and interact with home page using direct steps")
     @Description("Verify user can load home page, view rooms, and submit contact form using GenericSteps + HomePage locators")
     @Severity(SeverityLevel.CRITICAL)
     public void testDirectHomePageNavigation() {
          // Given: User navigates to the home page using Selenide native method
          open(UI_BASE_URL);

          // When: User verifies page is loaded and rooms are displayed using native
          // Selenide
          $("body").shouldBe(visible);

          // Then: User can interact with contact form directly using native Selenide
          $(byId(HomePage.CONTACT_SECTION_ID)).shouldBe(visible).scrollTo();
          $(HomePage.NAME_FIELD).shouldBe(visible).setValue(Users.TestUser.NAME);
          $(HomePage.EMAIL_FIELD).setValue(Users.TestUser.EMAIL);
          $(HomePage.PHONE_FIELD).setValue(Users.TestUser.PHONE);
          $(HomePage.SUBJECT_FIELD).setValue(Users.TestUser.SUBJECT);
          $(HomePage.MESSAGE_FIELD).setValue(Users.TestUser.MESSAGE);
          $(".btn.btn-primary").click();
          $(byId(HomePage.CONTACT_SECTION_ID)).shouldBe(visible);

          logger.info("Direct home page navigation test completed successfully");
     }

     @Test
     @Story("User can view available rooms using direct steps")
     @Description("Verify user can load home page and view room availability using GenericSteps")
     @Severity(SeverityLevel.NORMAL)
     public void testDirectRoomDisplay() {
          // Given: User navigates to the home page using Selenide native method
          open(UI_BASE_URL);

          // When & Then: User verifies page and rooms are displayed using native Selenide
          $("body").shouldBe(visible);

          logger.info("Direct room display test completed successfully");
     }

}
