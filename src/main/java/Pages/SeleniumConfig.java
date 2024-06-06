package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumConfig {
    private WebDriver driver;
    private WebDriverWait wait;

    public SeleniumConfig() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocker");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriverWait getWait() {
        return this.wait;
    }
}
