package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    private final Logger logger = LogManager.getLogger(this.getClass());
    ConfigReader cfg;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    DataContext context;

    final String PageLoadXPath = "//div[@class='product-info-main']";
    final String ProductTitleXPath = "//h1[@class='page-title']";
    final String ProductPriceXPath = "//div[@class='product-info-price']/descendant::span[@class='price']";
    final String ProductSizeOptionsXPath = "//div[@class='swatch-attribute size']";
    final String ProductSizeOptionSelectorXPath = ".//div[@class='swatch-option text' and @aria-label='%s']";
    final String ProductSizeSelectedOptionPath = ".//span[@class='swatch-attribute-selected-option']";
    final String ProductColorOptionsXPath = "//div[@class='swatch-attribute color']";
    final String ProductColorOptionSelectorXPath = ".//div[@class='swatch-option color' and @aria-label='%s']";//format with requested color name
    final String ProductColorSelectedOptionPath = ".//span[@class='swatch-attribute-selected-option']";
    final String AddToCartSuccessMessagePath = "//div[@role='alert' and @class='messages']/descendant::div[@data-ui-id='message-success']";
    final String MessageTextPath = "./div";
    final By AddToCartButton = By.id("product-addtocart-button");

    public ProductPage(WebDriver driver, DataContext ctx) {
        logger.info("initialising Product ");
        this.driver = driver;
        cfg = ConfigReader.getInstance();
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(Integer.parseInt(cfg.getProperty("pageSyncWait"))));
        actions = new Actions(this.driver);
        context = ctx;
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PageLoadXPath)));
    }

    public String getProductName() {
        WebElement Name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductTitleXPath)));
        return Name.getText();
    }

    public Double getPrice() {
        WebElement Price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPriceXPath)));
        return Double.parseDouble(Price.getText().substring(1));
    }

    public void chooseColor(String Color) {
        WebElement ColorSelection = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(ProductColorOptionsXPath)));
        String selectorPath = String.format(ProductColorOptionSelectorXPath, Color);
        ColorSelection.findElement(By.xpath(selectorPath)).click();
    }

    public void chooseSize(String Size) {
        WebElement SizeSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ProductSizeOptionsXPath)
        ));
        String selectorPath = String.format(ProductSizeOptionSelectorXPath, Size);
        SizeSelection.findElement(By.xpath(selectorPath)).click();
    }

    public String getSelectedSize() {
        WebElement SizeSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ProductSizeOptionsXPath)
        ));
        return SizeSelection.findElement(By.xpath(ProductSizeSelectedOptionPath)).getText();
    }

    public String getSelectedColor() {
        WebElement ColorSelection = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.xpath(ProductColorOptionsXPath)));
        return ColorSelection.findElement(By.xpath(ProductColorSelectedOptionPath)).getText();
    }

    public void addProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(AddToCartButton)).click();
        isAddToCartSuccess();
    }

    public boolean isAddToCartSuccess() {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(AddToCartSuccessMessagePath)
        ));
        return message != null;
    }

    public String getNotificationMessageText() {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(AddToCartSuccessMessagePath)
        ));
        WebElement text = message.findElement(By.xpath("./div"));
        return text.getText();//+" "+
        //text.findElement(By.xpath("./a")).getText();
    }

    public void verifySize() {
        String expectedSize = (String) context.getTestProperty("ChosenSize");
        String actualSelectedSize = getSelectedSize();
        Assert.assertEquals(expectedSize, actualSelectedSize);
    }

    public void verifyColor() {
        String expectedColor = (String) context.getTestProperty("ChosenColor");
        String actualSelectedColor = getSelectedColor();
        Assert.assertEquals(expectedColor, actualSelectedColor);
    }

    public void verifyProductName() {
        JacketsPage.Jacket jacket = (JacketsPage.Jacket) context.getTestProperty("SelectedJacket");
        String expectedName = jacket.getName();
        String actualProductName = getProductName();
        Assert.assertEquals(expectedName, actualProductName);
    }

    public void verifySuccessMessage() {
        logger.info("Verifying the success message displayed on Product Page");
        JacketsPage.Jacket jacket = (JacketsPage.Jacket) context.getTestProperty("SelectedJacket");
        logger.info("SelectedJacket from context: {}", context.getTestProperty("SelectedJacket"));
        String productName = jacket.getName();
        String ExpectedMessageText = String.format("You added %s to your shopping cart.", productName);
        String ActualMessageText = getNotificationMessageText();
        logger.info("Expected Message: {}", ExpectedMessageText);
        logger.info("Actual message: {}", ActualMessageText);
        Assert.assertEquals(ExpectedMessageText, ActualMessageText);
    }

}
