package net.serenitybdd.demos.todos.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.demos.todos.pageobjects.features.TestPage.ATestPage;
import net.thucydides.core.annotations.Steps;

public class ATestSteps {

    @Steps
    ATestPage stepPage;

    @When("I lunch the browser")
    public void lunch_the_browser() {
        stepPage.iLunchTheBrowsers();
    }

    @Then("search {string} in google")
    public void searchInGoogle(String searchText) {
        stepPage.searchInGoogle(searchText);
    }

    @Then("open google images")
    public void openGoogleImages() {
        stepPage.openGoogleImages();
    }
}
