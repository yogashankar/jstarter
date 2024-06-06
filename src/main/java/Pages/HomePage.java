package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final Logger logger = LogManager.getLogger(this.getClass());
    ConfigReader cfg;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    DataContext context;
    final String logoXpath = "//a[@class='logo' and img[contains(@src,'logo.svg')]]";
    final String menuItemMenXpath = "//nav[@class='navigation']/descendant::li[a[span[text()='Men']]]"; //Direct Xpath on page
    final String subMenuItemMenTopsPath = "/ul[@role='menu']/li[a[span[text()='Tops']]]";// relative Xpath to be used on web element of Men menu item
    final String navigationItemJacketsPath = "/child::ul[@role='menu']/li[a[span[text()='Jackets']]]"; //relative xpath to be used on the Men->Tops menu item

    public HomePage(WebDriver driver, DataContext ctx) {
        logger.info("initialising the Home Page class");
        this.driver = driver;
        this.actions = new Actions(this.driver);
        this.cfg = ConfigReader.getInstance();
        int waitTime = Integer.parseInt(this.cfg.getProperty("pageSyncWait"));
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds((waitTime)));
        this.context = ctx;
    }

    public void navigateToHomePage() {
        logger.debug("navigating to the app url");
        this.driver.get((String) this.cfg.getProperty("baseUrl"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(this.logoXpath)));
        logger.debug("app home page loaded successfully");
    }

    public JacketsPage navigateToJackets() {
        logger.info("navigating to the mens jackets page menu item");
        logger.debug("waiting for the Top navigation bar Mens menu item to be visible");
        WebElement navigateMen = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(this.menuItemMenXpath)));
        logger.debug("found the Top navigation bar Mens menu item");
        logger.debug("doing mouse over action to the Top navigation bar Mens menu item");
        actions.moveToElement(driver.findElement(By.xpath(this.menuItemMenXpath)), 3, 3).pause(Duration.ofSeconds(5)).build().perform();
        logger.debug("waiting for the sub menu to be visible");
        WebElement subMenu = wait.until(ExpectedConditions.visibilityOf(navigateMen.findElement(By.xpath("//nav[@class='navigation']/ul[@role='menu']"))));
        actions.moveToElement(driver.findElement(By.xpath(String.join("", menuItemMenXpath, subMenuItemMenTopsPath)))).build().perform();
        WebElement navigateMenTopsJackets = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.join("", menuItemMenXpath, subMenuItemMenTopsPath, navigationItemJacketsPath))));
        actions.moveToElement(navigateMenTopsJackets).click().build().perform();
        JacketsPage jacketsPage = new JacketsPage(driver, context);
        jacketsPage.waitForPageLoad();
        return jacketsPage;
    }
}
