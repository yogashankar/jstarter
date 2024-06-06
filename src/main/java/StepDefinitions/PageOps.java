package StepDefinitions;

import Pages.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class PageOps {
    DataContext context = DataContext.getInstance();
    HomePage homePage;
    JacketsPage jacketsPage;
    ProductPage productPage;
    CartPage cartPage;
    private WebDriver driver;
    private WebDriverWait wait;

    public PageOps() {
        this.driver = BrowserFactory.getBrowser("Chrome").getDiver();
    }

    @Given("I open the Store Website")
    public void open_store_website() {
        homePage = new HomePage(driver, context);
        homePage.navigateToHomePage();
        cartPage = new CartPage(driver, context);
        int cartCount = cartPage.getCartQuantity();
        context.setTestProperty("InitialCartCount", cartCount);
    }

    @Given("I choose the mens jacket section")
    public void i_choose_the_mens_jacket_section() {
        jacketsPage = homePage.navigateToJackets();
        jacketsPage.waitForPageLoad();
    }

    @When("I pick the cheapest jacket")
    public void i_pick_the_cheapest_jacket() {
        productPage = jacketsPage.chooseCheapestJacket();
    }

    @When("I pick the costliest jacket")
    public void i_pick_the_costliest_jacket() {
        productPage = jacketsPage.chooseCostliestJacket();
    }

    @When("Choose size as {string}")
    public void choose_size_as(String string) {
        context.setTestProperty("ChosenSize", string);
        productPage.chooseSize(string);
    }

    @When("Choose Color as {string}")
    public void choose_color_as(String string) {
        context.setTestProperty("ChosenColor", string);
        productPage.chooseColor(string);
    }

    @When("I choose Jacket options")
    public void choose_jacket_options(DataTable table) {
        Map<String, String> entries = table.asMap(String.class, String.class);
        System.out.println(entries);
        choose_color_as(entries.get("Color"));
        choose_size_as(entries.get("Size"));
    }

    @Then("I verify jacket details")
    public void verify_jacket_details() {
        productPage.verifyProductName();
        productPage.verifyColor();
        productPage.verifySize();
    }

    @When("I Add the jacket to the cart")
    public void i_add_the_jacket_to_the_cart() {
        productPage.addProductToCart();
    }

    @Then("I see success message on page")
    public void i_see_success_message() {
        productPage.verifySuccessMessage();
    }

    @Then("I should see the cart count increase")
    public void i_should_see_the_cart_count_increase() {

    }

    @Then("I quit")
    public void quit() {
        ((WebDriver) context.getTestProperty("driver")).close();
    }

    @After(value = "@WebTest")
    public void tearDown(Scenario scenario) {
        WebDriver d = driver;
        if (scenario.isFailed()) {
            File scr = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
            try {
                byte[] bytes = FileUtils.readFileToByteArray(scr);
                String img = Base64.getEncoder().encodeToString(bytes);
                scenario.attach(img, "image/png", "screenshot");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        d.close();
    }
}
