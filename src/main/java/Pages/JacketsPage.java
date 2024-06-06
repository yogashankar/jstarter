package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class JacketsPage {
    private final Logger logger = LogManager.getLogger(this.getClass());
    ConfigReader cfg;
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    DataContext context;

    String pageUrl = "https://magento.softwaretestingboard.com/men/tops-men/jackets-men.html";
    String productsGridPath = "//div[@class='products wrapper grid products-grid']";
    String productCardPath = "//li[@class='item product product-item']";
    String productInfoPath = "/descendant::div[@class='product details product-item-details']";
    String productInfoNamePath = ".//strong/a";
    String productInfoPricePath = ".//span[@class='price']";

    public JacketsPage(WebDriver driver, DataContext ctx) {
        logger.info("Initialising the Jackets Page");
        this.driver = driver;
        cfg = ConfigReader.getInstance();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(cfg.getProperty("pageSyncWait"))));
        actions = new Actions(this.driver);
        context = ctx;
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.urlToBe(pageUrl));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productsGridPath)));
    }

    public WebElement getCheapestJacket() {
        List<WebElement> Products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String.join("", productCardPath, productInfoPath))));
        for (WebElement productInfo : Products) {
            WebElement name = productInfo.findElement(By.xpath(productInfoPricePath));
            //flashElement(name);
            String s = name.getText();
            System.out.println(s.substring(1));
        }

        Optional<WebElement> cj = Products.stream().reduce((p1, p2) -> {
            double firstValue, secondValue;
            firstValue = Double.parseDouble(p1.findElement(By.xpath(productInfoPricePath)).getText().substring(1));
            secondValue = Double.parseDouble(p2.findElement(By.xpath(productInfoPricePath)).getText().substring(1));
            return (firstValue <= secondValue) ? p1 : p2;
        });

        return cj.orElse(null);
    }

    public WebElement getCostliestJacket() {
        List<WebElement> Products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(String.join("", productCardPath, productInfoPath))));
        for (WebElement productInfo : Products) {
            WebElement name = productInfo.findElement(By.xpath(productInfoPricePath));
            //flashElement(name);
            String s = name.getText();
            System.out.println(s.substring(1));
        }

        Optional<WebElement> cj = Products.stream().reduce((p1, p2) -> {
            double firstValue, secondValue;
            firstValue = Double.parseDouble(p1.findElement(By.xpath(productInfoPricePath)).getText().substring(1));
            secondValue = Double.parseDouble(p2.findElement(By.xpath(productInfoPricePath)).getText().substring(1));
            return (firstValue >= secondValue) ? p1 : p2;
        });

        return cj.orElse(null);
    }

    public ProductPage chooseCheapestJacket() {
        logger.info("Selecting the cheapest jacket");
        WebElement product = getCheapestJacket();
        if (product != null) logger.info("jacket object returned");
        else logger.info("null returned for cheapest jacket product object");
        assert product != null;
        logger.info("setting test data 'SelectedJacket' as selected jacket product");
        context.setTestProperty("SelectedJacket", new Jacket(product));
        logger.info("SelectedJacket set in context {}", context.getTestProperty("SelectedJacket").toString());
        product.findElement(By.xpath("./preceding-sibling::a")).click();
        ProductPage productPage = new ProductPage(driver, context);
        productPage.waitForPageLoad();
        return productPage;
    }

    public ProductPage chooseCostliestJacket() {
        logger.info("Selecting the cheapest jacket");
        WebElement product = getCostliestJacket();
        if (product != null) logger.info("jacket object returned");
        else logger.info("null returned for cheapest jacket product object");
        assert product != null;
        logger.info("setting test data 'SelectedJacket' as selected jacket product");
        context.setTestProperty("SelectedJacket", new Jacket(product));
        logger.info("SelectedJacket set in context {}", context.getTestProperty("SelectedJacket").toString());
        product.findElement(By.xpath("./preceding-sibling::a")).click();
        ProductPage productPage = new ProductPage(driver, context);
        productPage.waitForPageLoad();
        return productPage;
    }

    public void flashElement(WebElement e) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String bgColor = e.getCssValue("backgroundColor");
        String color = "rgb(0,200,0)";
        js.executeScript("arguments[0].scrollIntoView();", e);
        long ets = System.currentTimeMillis();
        long cts = System.currentTimeMillis();
        while ((cts - ets) <= 2000) {
            js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", e);
            delay();
            js.executeScript("arguments[0].style.backgroundColor = '" + bgColor + "'", e);
            delay();
            cts = System.currentTimeMillis();
        }
    }

    public void delay() {
        long ets = System.currentTimeMillis();
        long cts = System.currentTimeMillis();
        double i = 0;
        while ((cts - ets) <= 500) {
            ++i;
            cts = System.currentTimeMillis();
        }
    }

    class Jacket {
        WebElement product;
        Double Price;
        String Name;
        WebElement addToCart;

        public Jacket(WebElement product) {
            this.product = product;
            this.Price = Double.parseDouble(this.product.findElement(By.xpath(productInfoPricePath)).getText().substring(1));
            this.Name = product.findElement(By.xpath(productInfoNamePath)).getText();
        }

        public Double getPrice() {
            return this.Price;
        }

        public String getName() {
            return this.Name;
        }
    }

}
