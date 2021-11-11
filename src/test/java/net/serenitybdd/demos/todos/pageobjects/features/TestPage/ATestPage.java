package net.serenitybdd.demos.todos.pageobjects.features.TestPage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class ATestPage extends PageObject {

    @FindBy(tagName = "input")
    private WebElement googleSearch;

    @FindBy(css = "html body div.L3eUgb div.o3j99.ikrT4e.om7nvf form div div.A8SBwf div.FPdoLc.lJ9FBc center input.gNO89b")
    private WebElement googleSearchButton;

    @FindBy(css = "div.hdtb-mitem:nth-child(2) > a:nth-child(1)")
    private WebElement googleImageButton;

    public void iLunchTheBrowsers() {
        this.open();
    }

    public void searchInGoogle(String text) {
        typeInto(googleSearch, text);
        googleSearchButton.click();
    }

    public void openGoogleImages() {
        googleImageButton.click();

    }
}
