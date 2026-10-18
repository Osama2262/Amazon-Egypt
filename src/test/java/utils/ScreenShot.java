package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
public class ScreenShot {
        private static final Logger log = LogManager.getLogger(ScreenShot.class);

        public static File takeScreenShot(WebDriver driver) {
            log.info("Capturing browser screenshot");
            try {

                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                log.info("Browser screenshot captured successfully");
                return screenshot;

            } catch (Exception e) {
                log.error("Failed to capture browser screenshot", e);
                return null;
            }
        }
}
