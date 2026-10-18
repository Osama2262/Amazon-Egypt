package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class GetFireFoxDriver {
    private static final Logger log = LogManager.getLogger(GetFireFoxDriver.class);
    private static WebDriver driver = null;
    public static WebDriver getFireFoxDriver()
    {
        if(driver==null)
        {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--incognito");
            try {
                driver = new FirefoxDriver(firefoxOptions);
                log.info("{} browser started successfully", "FireFox");
            } catch (RuntimeException e) {
                log.error("Failed to start {} browser", "FireFox", e);
                throw e;
            }
        }
        log.debug("Returning {} WebDriver instance", "FireFox");
        return driver;
    }
    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing {} browser", "FireFox");
            driver.quit();
            driver = null;
            log.info("{} browser closed", "FireFox");
        }
    }
}
