package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {
    private static WebDriver driver = null;
    private static String currentBrowserName = "Chrome";

    /**
     * <h1> Driver Management for Test Automation
     *     <div>
     *         <p>
     *             This Function will return a web driver instance pointing to an active session of the requested browser
     *             <b>The method replaces the driver reference to any existing session if a different browser is requested.
     *             So complete any tests before switching</b>
     *         </p>
     *     </div>
     * </h1>
     *
     * @param browserName <p>
     *                    Valid Values are:
     *                     <ul>
     *                     <li>Chrome</li>
     *                     <li>FireFox</li>
     *                     <li>Edge</li>
     *                     </ul>
     *                    </p>
     * @return driver
     * @see WebDriver
     */
    public static WebDriver getWebDriver(String browserName) {
        if (!currentBrowserName.equalsIgnoreCase(browserName) || driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--start-maximized");
                    options.addArguments("--disable-notifications");
                    options.addArguments("--disable-popup-blocking");
                    driver = new ChromeDriver(options);
                    currentBrowserName = browserName;
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    currentBrowserName = browserName;
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    currentBrowserName = browserName;
                    break;
                default:
                    driver = new ChromeDriver();
                    currentBrowserName = "Chrome";
            }
        }
        return driver;
    }

    public static WebDriver getCurrentDriver() {
        return driver;
    }

    public static void closeDriver() {
        getCurrentDriver().quit();
    }
}
