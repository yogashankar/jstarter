package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserManager {
    ThreadLocal<WebDriver> driver;
    WebDriver wd;

    private BrowserManager() {
        driver = new ThreadLocal<>();
    }

    private static BrowserManager instance = null;

    public static BrowserManager getInstance() {
        if (instance == null) instance = new BrowserManager();
        return instance;
    }

    public WebDriver launchBrowser(String Name) {
        switch (Name) {
            case "Chrome":
            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                wd = new ChromeDriver(options);
                break;
            case "Edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--disable-popup-blocking");
                wd = new EdgeDriver(edgeOptions);
                break;
        }
        driver.set(wd);
        wd = null;
        return driver.get();
    }
}
