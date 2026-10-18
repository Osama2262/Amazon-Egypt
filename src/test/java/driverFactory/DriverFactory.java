package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
public class DriverFactory
{
    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    public static WebDriver getWebDriver(String browser)
    {
        log.info("Requesting WebDriver for browser: {}", browser);
        if (browser == null || browser.isBlank()) {
            throw new IllegalArgumentException("Browser must be configured in config.properties");
        }
        WebDriver driver = switch (browser.toLowerCase().trim()) {
            case "chrome" -> GetChromeDriver.getChromeDriver();
            case "firefox" -> GetFireFoxDriver.getFireFoxDriver();
            case "edge" -> GetEdgeDriver.getEdgeDriver();
            default -> {
                log.error("Unsupported browser configured: {}", browser);
                throw new IllegalArgumentException("Browser not recognized: " + browser);
            }
        };

        log.info("WebDriver ready for browser: {}", browser);
        return driver;
    }
    public static void quitDriver(String browser)
    {
        if (browser == null || browser.isBlank()) {
            log.warn("Cannot close browser because browser configuration is empty");
            return;
        }
        log.info("Requesting browser shutdown: {}", browser);
        switch (browser.toLowerCase().trim())
        {
            case "chrome":
                GetChromeDriver.quitDriver();
                break;
            case "firefox":
                    GetFireFoxDriver.quitDriver();
                    break;
            case "edge":
                    GetEdgeDriver.quitDriver();
                    break;
            default:
                throw new IllegalArgumentException("Browser not recognized: " + browser);

        }
    }
}
