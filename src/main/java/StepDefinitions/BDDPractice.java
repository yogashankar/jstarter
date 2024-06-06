package StepDefinitions;

import com.practice.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BDDPractice {
    Account account;
    int prev_balance;

    @Given("I have a balance of ${int} in my account")
    public void i_have_account_with_$_balance(int balance) {
        account = new Account("Holder", balance);
    }

    @When("I request ${int}")
    public void i_request_$(int amount) {
        prev_balance = account.getBalance();
        boolean res = account.withdraw(amount);
    }

    @Then("${int} should be dispensed")
    public void check_dispense_amount(int amount) {
        int dispense = prev_balance - account.getBalance();
        Assert.assertEquals(amount, dispense);
        System.out.println(dispense == amount);
    }

}
