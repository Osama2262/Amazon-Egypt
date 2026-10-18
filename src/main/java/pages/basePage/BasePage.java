package pages.basePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private static final Logger log = LogManager.getLogger(BasePage.class);
    public WebDriverWait wait;
    public WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        log.debug("Initialized {} with WebDriver: {}", getClass().getSimpleName(), driver == null ? "null" : driver.getClass().getSimpleName());
    }
    public WebElement findElement(By locator)
    {
        log.debug("Waiting for visible element: {}", locator);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            log.debug("Element is visible: {}", locator);
            return element;
        } catch (RuntimeException e) {
            log.error("Failed to find/wait for visible element: {}", locator, e);
            throw e;
        }
    }
    public List<WebElement> findElements(By locator)
    {
        log.debug("Waiting for visible elements: {}", locator);
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            List<WebElement> elements = driver.findElements(locator);
            log.debug("Located {} elements for {}", elements.size(), locator);
            return elements;
        } catch (RuntimeException e) {
            log.error("Failed to find/wait for elements: {}", locator, e);
            throw e;
        }
    }

    public void explicitWaitElementLocated(By locator)
    {
        log.debug("Explicit wait for visible element: {}", locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        log.debug("Explicit wait completed for: {}", locator);
    }
    public void navigationBackButton()
    {
        log.info("Navigating back from current page");
        driver.navigate().back();
    }
}
