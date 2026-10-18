package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class GetEdgeDriver {
    private static final Logger log = LogManager.getLogger(GetEdgeDriver.class);
    private static WebDriver driver =  null;
    public static WebDriver getEdgeDriver() {
        if (driver == null) {
            log.info("Starting {} browser in incognito/private mode", "Edge");
            EdgeOptions edgeDriver = new EdgeOptions();
            edgeDriver.addArguments("--incognito");
            try {
                driver = new EdgeDriver(edgeDriver);
                log.info("{} browser started successfully", "Edge");
            } catch (RuntimeException e) {
                log.error("Failed to start {} browser", "Edge", e);
                throw e;
            }
        }
        log.debug("Returning {} WebDriver instance", "Edge");
        return driver;

    }
    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing {} browser", "Edge");
            driver.quit();
            driver = null;
            log.info("{} browser closed", "Edge");
        }
    }

}
