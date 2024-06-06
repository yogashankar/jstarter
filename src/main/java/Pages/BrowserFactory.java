package Pages;

public class BrowserFactory {
    public static Browser getBrowser(String browser) {
        if (browser == null) {
            return null;
        } else if ("chrome".equalsIgnoreCase(browser)) {
            return new ChromeBrowser();
        } else if ("edge".equalsIgnoreCase(browser)) {
            return new EdgeBrowser();
        } else return null;
    }
}
