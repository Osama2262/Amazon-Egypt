package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetChromeDriver {
    private static final Logger log = LogManager.getLogger(GetChromeDriver.class);
    private static WebDriver driver = null;

    public static WebDriver getChromeDriver() {
        if (driver == null) {
            log.info("Starting {} browser in incognito/private mode", "Chrome");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            try {
                driver = new ChromeDriver(chromeOptions);
                log.info("{} browser started successfully", "Chrome");
            } catch (RuntimeException e) {
                log.error("Failed to start {} browser", "Chrome", e);
                throw e;
            }
        }
        log.debug("Returning {} WebDriver instance", "Chrome");
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing {} browser", "Chrome");
            driver.quit();
            driver = null;
            log.info("{} browser closed", "Chrome");
        }
    }
}
