package steps;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.login.LoginPage;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private final LoginPage loginPage;
    
    public LoginSteps() {
        this.loginPage = new LoginPage();
    }
    
    @Step("Perform admin login with credentials")
    public LoginSteps performAdminLogin(String username, String password) {
        logger.info("Performing admin login for user: {}", username);
        loginPage.navigateToAdmin()
                .verifyLoginPageDisplayed()
                .login(username, password);
        return this;
    }
    
    @Step("Verify login was successful")
    public LoginSteps verifyLoginSuccess() {
        logger.info("Verifying login success");
        loginPage.verifySuccessfulLogin();
        return this;
    }
    
    @Step("Verify login failed")
    public LoginSteps verifyLoginFailure() {
        logger.info("Verifying login failure");
        loginPage.verifyLoginError();
        return this;
    }
    
    @Step("Perform logout")
    public LoginSteps performLogout() {
        logger.info("Performing logout");
        loginPage.logout()
                .verifyLogoutSuccessful();
        return this;
    }
    
    @Step("Attempt login with invalid credentials")
    public LoginSteps attemptInvalidLogin(String username, String password) {
        logger.info("Attempting login with invalid credentials");
        loginPage.navigateToAdmin()
                .verifyLoginPageDisplayed()
                .login(username, password)
                .verifyLoginError();
        return this;
    }
    
    public LoginPage getLoginPage() {
        return loginPage;
    }
}
