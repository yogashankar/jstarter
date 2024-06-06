package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {
    private final Logger logger = LogManager.getLogger(this.getClass());
    ConfigReader cfg;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    DataContext context;

    final String cartDialogPath = "";
    final String cartIconPath = "//a[@class='action showcart']";
    final By cartDialog = By.xpath("//*[@class='block block-minicart ui-dialog-content ui-widget-content']");
    final By cartEmptyMessage = By.xpath("./descendant::strong[@class='subtitle empty']");
    final By cartCounter = By.xpath("./span[@class='counter qty']");
    final By cartQty = By.xpath("./span[@class='counter-number']");

    final String EmptyCartMessageText = "You have no items in your shopping cart.";

    public CartPage(WebDriver driver, DataContext ctx) {
        cfg = ConfigReader.getInstance();
        this.driver = driver;
        wait = new WebDriverWait(this.driver,
                Duration.ofSeconds(Integer.parseInt(
                        cfg.getProperty("pageSyncWait"))));
        actions = new Actions(this.driver);
        context = ctx;
    }

    public void waitForCartDialog() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartDialog));
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cartIconPath))).click();
        waitForCartDialog();
    }

    public String getCartEmptyMessage() {
        WebElement cartdlg = wait.until(ExpectedConditions.visibilityOfElementLocated(cartDialog));
        return cartdlg.findElement(cartEmptyMessage).getText();
    }

    public void verifyCartEmpty() {
        String ActualMessageText = getCartEmptyMessage();
        Assert.assertEquals(EmptyCartMessageText, ActualMessageText);
    }

    public int getCartQuantity() {
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cartIconPath)));
        int count = 0;
        try {
            String value = cartIcon.findElement(cartCounter).findElement(cartQty).getText();
            String formatted = value.replaceAll("\"", "");
            count = Integer.parseInt(formatted);
        } catch (NoSuchElementException ignored) {
        }
        return count;
    }


}
