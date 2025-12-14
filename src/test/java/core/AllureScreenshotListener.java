package core;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            takeScreenshot();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            takeScreenshot();
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html")
    public String savePageSource() {
        return WebDriverRunner.getWebDriver().getPageSource();
    }
}
