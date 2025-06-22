package com.automation.stepdefinitions;

import com.automation.context.TestContext;
import com.automation.pages.IzertisHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class IzertisStepDefinitions {

    private final IzertisHomePage izertisHomePage;

    public IzertisStepDefinitions(TestContext context) {
        // PicoContainer nos provee el mismo 'context' para todos los Steps y Hooks
        this.izertisHomePage = context.getPageObjectManager().getIzertisHomePage();
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        izertisHomePage.navigateTo(url);
    }

    @Then("I should see the page title as {string}")
    public void i_should_see_the_page_title_as(String expectedTitle) {
        String actualTitle = izertisHomePage.getPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }
} 