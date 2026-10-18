package baseTest;

import driverFactory.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigHandler;
import utils.ExcelFileManager;
import utils.JSONFileManager;
import utils.ScreenShot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    public WebDriver driver;
    public ConfigHandler configHandler;
    public JSONFileManager jsonFileManager;
    public ExcelFileManager excelFileManager;
    @BeforeMethod
    public void setUp()
    {
        log.info("========== TEST SETUP START ==========");
        log.info("Loading test data from Excel, JSON, and properties files");
        excelFileManager = new ExcelFileManager("src/main/resources/Numbers.xlsx","Sheet1");
        configHandler = new ConfigHandler("src/main/resources/config.properties");
        jsonFileManager = new JSONFileManager("src/main/resources/searchingData.json");
        String browser = configHandler.getValue("browser");
        log.info("Configured browser: {}", browser);
        driver = DriverFactory.getWebDriver(browser);
        driver.manage().window().maximize();
        log.info("Browser window maximized");
        String baseUrl = configHandler.getValue("baseUrl");
        log.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
        log.info("Base URL loaded: {}", driver.getCurrentUrl());
        log.info("========== TEST SETUP COMPLETE ==========");
    }

    @AfterMethod
    public void failedTestCase(ITestResult result) throws FileNotFoundException {
        log.info("Test result received: {} (status={})", result.getName(), result.getStatus());
        if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test passed successfully: {}", result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("Test was skipped: {}", result.getName());
        }
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test failed: {}", result.getName(), result.getThrowable());
            File image = ScreenShot.takeScreenShot(driver);
            Assert.assertNotNull(image);
            FileInputStream file = new FileInputStream(image);
            Allure.addAttachment("Screenshot", "image/png", file, "png");
            log.info("Failure screenshot attached to Allure report");

        }
    }
    @AfterMethod
    public void tearDown()
    {
        log.info("========== TEST TEARDOWN START ==========");
        if (excelFileManager != null) {
            excelFileManager.closeWorkbook();
        }
        if (configHandler != null) {
            DriverFactory.quitDriver(configHandler.getValue("browser"));
        }
        log.info("========== TEST TEARDOWN COMPLETE ==========");
    }
}
