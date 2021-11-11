package net.serenitybdd.demos.todos.pageobjects.features.TestPage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePage extends PageObject {

    /*CsvUtil csvUtil = new CsvUtil();*/
    public JavascriptExecutor js = (JavascriptExecutor) getDriver();
    static Properties properties;
    @FindBy(tagName = "body")
    protected WebElement body;
    static {
        try (InputStream input = BasePage.class.getClassLoader().getResourceAsStream("general.properties")) {
            if (input == null) {
                log.info("Sorry, unable to find general.properties");
            }
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            log.error("Sorry, unable to load general.properties", ex);
        }
    }

    /*{
        csvUtil.initMap();
    }*/
    // Methods-WebElement
    protected WebElement getWebElementFromListByText(List<WebElement> elements, String text) {
        wait(elements);
        elements.removeAll(Collections.singleton(null));
        return elements.stream()
                .filter(t -> (t.getText() != null))
                .filter(e -> e.getText().contains(text))//equals(text))
                .findAny().orElse(null);
    }

    protected WebElement getWebElementFromListByAttribute(List<WebElement> elements, String attribute, String text) {
        return elements.stream()
                .filter(t -> (t.getAttribute(attribute) != null))
                .filter(e -> e.getAttribute(attribute).equals(text))//equals(text))
                .findAny().orElse(null);
    }

    protected WebElement getWebElementFromListById(List<WebElement> elements, String id) {
        WebElement result = null;
        for (int i = 0; i < elements.size(); i++) {
            boolean b = elements.get(i).getAttribute("id").equals(id);
            if (b) {
                result = elements.get(i);
            }

        }
        return result;
    }

    protected Boolean isWebElementDisabled(WebElement webElement) {
        if (js == null) {
            js = (JavascriptExecutor) getDriver();
        }
        return (Boolean) js.executeScript("return arguments[0].hasAttribute(\"disabled\");", webElement);
    }

    boolean areElementPresent(By locator) {
        return getDriver().findElements(locator).size() > 0;
    }

    protected WebElement getParent(WebElement webElement) {
        return webElement.findElement(By.xpath("./.."));
    }

    // Methods-Keyboard
    protected void pressEnter(WebElement webElement) {
        webElement.sendKeys(Keys.ENTER);
    }

    protected void pressArrowDown(WebElement webElement) {
        webElement.sendKeys(Keys.ARROW_DOWN);
    }

    // Methods-Mouse
    protected void hoverOverElement(WebElement element) {
        withAction().moveToElement(element).build().perform();
    }

    protected void moveToAndClick(WebElement element, int x, int y) {
        withAction().moveToElement(element, x, y).click().build().perform();
    }

    // Methods-scroll
    // scrollIntoView();
    protected void scrollIntoView(WebElement webElement) {
        if (js == null) {
            js = (JavascriptExecutor) getDriver();
        }
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

    public void scrollBy(int x, int y) {
        if (js == null) {
            js = (JavascriptExecutor) getDriver();
        }
        js.executeScript("window.scrollBy(" + x + "," + y + ")");
    }

    // Methods-wait
    /**
     * Waits for the WebElement to be available.
     *
     * @param element WebElement to wait for
     */
    protected void wait(WebElement element) {
        wait(parseSelector(element.toString()));
    }

    /**
     * Waits for the WebElement to be available.
     *
     * @param elements WebElement to wait for
     */
    protected void wait(List<WebElement> elements) {
        wait(parseSelector(elements.toString()));
    }

    /**
     * Waits for the WebElement to be available.
     *
     * @param selector Selector of the element
     */
    private void wait(String selector) {
        if (selector != null) {
            waitForRenderedElementsToBePresent(By.cssSelector(selector));
        } else {
            waitABit(1500);
        }
    }

    // Methods-Pause
    static void pause(Integer milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pauseNMilliseconds(String duration) {
        int d = Integer.parseInt(duration);
        waitABit(d);
    }

    // Methods-Selector
    /**
     * Parses the "toString" of a WebElement to a valid css selector.
     *
     * @param toStringSelector selector of a WebElement
     * @return css selector
     */
    private String parseSelector(String toStringSelector) {
        String[] splitSelector = toStringSelector.split("->");
        if (splitSelector.length < 2) {
            return null;
        }
        String selector = splitSelector[1].split("\\],\\s")[0];
        selector = selector.replaceFirst("\\].*?", "").trim();
        if (selector.endsWith("]")) {
            selector = selector.substring(0, selector.length() - 1);
        }

        String selectorType = selector.substring(0, 3);
        switch (selectorType) {
            case "id:":
                selector = selector.replaceFirst("id:\\s", "#");
                break;
            case "css":
                selector = selector.replaceFirst("css selector:\\s", "");
                break;
            default:
                String[] split = selector.split(":");
                if (split.length > 1) {
                    selector = split[1];
                }
                break;
        }

        return selector.trim();
    }

}
