package pages.login;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    
    // Page elements
    private final SelenideElement usernameField = $("#username");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton = $("#doLogin");
    private final SelenideElement logoutButton = $(".btn-outline-primary");
    private final SelenideElement errorMessage = $(".alert-danger");
    private final SelenideElement adminPanel = $(".admin");
    
    @Step("Navigate to admin login")
    public LoginPage navigateToAdmin() {
        logger.info("Navigating to admin login");
        $("#navAdmin").click();
        return this;
    }
    
    @Step("Verify login page is displayed")
    public LoginPage verifyLoginPageDisplayed() {
        logger.info("Verifying login page is displayed");
        usernameField.shouldBe(visible);
        passwordField.shouldBe(visible);
        loginButton.shouldBe(visible);
        return this;
    }
    
    @Step("Login with username: {username} and password: {password}")
    public LoginPage login(String username, String password) {
        logger.info("Logging in with username: {}", username);
        usernameField.shouldBe(visible).setValue(username);
        passwordField.setValue(password);
        loginButton.click();
        return this;
    }
    
    @Step("Verify successful login")
    public LoginPage verifySuccessfulLogin() {
        logger.info("Verifying successful login");
        adminPanel.shouldBe(visible);
        return this;
    }
    
    @Step("Verify login error")
    public LoginPage verifyLoginError() {
        logger.info("Verifying login error is displayed");
        errorMessage.shouldBe(visible);
        return this;
    }
    
    @Step("Logout")
    public LoginPage logout() {
        logger.info("Logging out");
        logoutButton.click();
        return this;
    }
    
    @Step("Verify logout successful")
    public LoginPage verifyLogoutSuccessful() {
        logger.info("Verifying logout successful");
        usernameField.shouldBe(visible);
        return this;
    }
    
    public SelenideElement getUsernameField() {
        return usernameField;
    }
    
    public SelenideElement getPasswordField() {
        return passwordField;
    }
    
    public SelenideElement getLoginButton() {
        return loginButton;
    }
    
    public SelenideElement getErrorMessage() {
        return errorMessage;
    }
}
