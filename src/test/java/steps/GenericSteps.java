package steps;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Step;

public class GenericSteps {
    private static final Logger logger = LoggerFactory.getLogger(GenericSteps.class);

    @Step("Click element: {locator}")
    public GenericSteps clickElement(String locator) {
        logger.info("Clicking element: {}", locator);
        $(locator).click();
        return this;
    }

    @Step("Set value '{value}' in field: {locator}")
    public GenericSteps setValue(String locator, String value) {
        logger.info("Setting value '{}' in field: {}", value, locator);
        $(locator).setValue(value);
        return this;
    }

    @Step("Verify element is visible: {locator}")
    public GenericSteps shouldBeVisible(String locator) {
        logger.info("Verifying element is visible: {}", locator);
        $(locator).shouldBe(visible);
        return this;
    }

    @Step("Verify element contains text '{expectedText}': {locator}")
    public GenericSteps shouldHaveText(String locator, String expectedText) {
        logger.info("Verifying element contains text '{}': {}", expectedText, locator);
        $(locator).shouldHave(text(expectedText));
        return this;
    }

    @Step("Scroll to element: {locator}")
    public GenericSteps scrollToElement(String locator) {
        logger.info("Scrolling to element: {}", locator);
        $(locator).scrollTo();
        return this;
    }

    @Step("Verify element exists: {locator}")
    public GenericSteps shouldExist(String locator) {
        logger.info("Verifying element exists: {}", locator);
        $(locator).should(exist);
        return this;
    }

    @Step("Debug: Check if element exists: {locator}")
    public GenericSteps debugElementExists(String locator) {
        boolean exists = $(locator).exists();
        logger.info("Element '{}' exists: {}", locator, exists);
        if (!exists) {
            logger.warn("Element '{}' NOT FOUND on page", locator);
            logger.info("Current URL: {}", com.codeborne.selenide.WebDriverRunner.url());
            logger.info("Page title: {}", com.codeborne.selenide.WebDriverRunner.getWebDriver().getTitle());
        }
        return this;
    }

    @Step("Click random element: {locator}")
    public GenericSteps clickRandomElement(String locator) {
        logger.info("Clicking random element: {}", locator);
        com.codeborne.selenide.ElementsCollection elements = com.codeborne.selenide.Selenide.$$(locator);
        int randomIndex = new java.util.Random().nextInt(elements.size());
        logger.info("Clicking element at random index: {} out of {}", randomIndex, elements.size());
        elements.get(randomIndex).click();
        return this;
    }
}
