package tests;

import core.BaseTest;
import data.Users;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.home.HomePage;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import steps.GenericSteps;

@Epic("UI Testing")
@Feature("Direct Home Page Navigation - Native Selenide")
public class DirectUITest extends BaseTest {

     private final GenericSteps genericSteps = new GenericSteps();

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
          $$(HomePage.SUBMIT_BUTTON_CSS).get(2)
                    .scrollTo()
                    .shouldHave(text("Submit"))
                    .click();
          $(byId(HomePage.CONTACT_SECTION_ID)).shouldBe(visible);

          logger.info("Direct home page navigation test completed successfully");
     }

}
