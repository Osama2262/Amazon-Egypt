# 🛒 Amazon Egypt UI Automation Project

![Java](https://img.shields.io/badge/Java-23-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-blue?logo=apachemaven&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.43.0-green?logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-lightgrey)
![Allure](https://img.shields.io/badge/Allure-2.35.5-purple)

A Java-based UI automation framework for testing selected shopping workflows on [Amazon Egypt](https://www.amazon.eg/). The project uses Selenium WebDriver, TestNG, Maven, the Page Object Model (POM), and Log4j2 for execution logging.

> ⚠️ **Note:** This is an automation practice project. Product availability, prices, shipping options, and page structure can change, so test results may vary over time.

## 🎬 Demo video

A short recording of the test suite running on Amazon Egypt:

[▶️ Watch the demo video](Amazon%20egypt%20video.mp4)

<!-- To show the video inline, drag "Amazon egypt video.mp4" into this README in the GitHub web editor. GitHub inserts a user-attachments link that plays directly on the page, and you can replace the link above with it. -->

## 🛠️ Tech Stack

- **Java Development Kit (JDK) 23**: compiles and runs the project.
- **Apache Maven**: dependency management and test execution.
- **Selenium WebDriver 4.43.0**: browser automation.
- **TestNG 7.12.0**: test framework and test lifecycle.
- **Log4j2 2.25.2**: console and file logging configured through `log4j2.properties`.
- **Allure TestNG 2.35.5**: Allure integration dependency (see 📊 Allure Reports below).
- **Gson 2.14.0**: JSON test-data handling.
- **Apache POI 5.5.1**: Excel test-data handling.

## 📁 Project Structure

```text
Amazon/
├── pom.xml
├── testng.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/pages/
│   │   │   ├── basePage/BasePage.java       # Shared page actions and waits
│   │   │   ├── productPage/ProductPage.java # Search, filters, and product details
│   │   │   └── cartPage/CartPage.java       # Cart counter, subtotal, and quantity controls
│   │   └── resources/
│   │       ├── config.properties            # Base URL and browser settings
│   │       ├── searchingData.json           # Search terms and expected values (JSON)
│   │       ├── Numbers.xlsx                 # Numeric test data (Excel)
│   │       └── log4j2.properties            # Logging configuration
│   └── test/
│       └── java/
│           ├── baseTest/BaseTest.java       # Driver setup and teardown for every test
│           ├── driverFactory/               # Chooses and creates the Chrome, Firefox, or Edge driver
│           ├── productTest/ProductTest.java # Product search, filter, and details tests
│           ├── cartTest/CartTest.java       # Cart quantity and total tests
│           └── utils/
│               └── ExcelFileManager.java    # Reads cell values from Numbers.xlsx
└── logs/                 # Generated locally when tests run; not committed
```

## 🚀 Run the Project from a Terminal

Follow these steps to set up and run the tests from the command line.

### ☕ Step 1: Install Java

Install a 64-bit **JDK 23** and configure `JAVA_HOME` if needed. Open a terminal and verify the installation:

```bash
java -version
javac -version
```

Both commands should report a Java 23 installation.

### 📦 Step 2: Install Maven

Install **Apache Maven** and make sure its `bin` directory is available through your system `PATH`. Verify the installation:

```bash
mvn -version
```

The output should show the Maven version and the Java version Maven is using.

### 📥 Step 3: Get the project files

Download or clone this repository, then open a terminal in the project root directory, the folder that contains `pom.xml`.

```bash
cd path/to/Amazon
```

Replace the path with the location of the project folder on your computer.

### ⚙️ Step 4: Check the configuration

Open `src/main/resources/config.properties` and confirm the base URL and browser are set correctly:

```properties
baseUrl=https://www.amazon.eg/
browser=chrome
```

Do not put quotation marks around values. Properties files keep them as part of the value, so `"chrome"` won't match.

Supported values for `browser` are:

- `chrome`
- `firefox`
- `edge`

Make sure the browser you choose is installed on your machine and that your computer can access Amazon Egypt.

### ▶️ Step 5: Run the test suite

From the project root, run:

```bash
mvn clean test
```

Maven will download the required dependencies, compile the project, and run the TestNG suite configured in `testng.xml`. The suite currently includes `productTest.ProductTest` and `cartTest.CartTest`.

#### 🙈 Run without opening a browser window (headless mode)

Add `-Dheadless=true` to run the browser in the background:

```bash
mvn clean test -Dheadless=true
```

To run a single test class in headless mode:

```bash
mvn clean test -Dheadless=true -Dtest=ProductTest
```

The default is `headless=false`, so leave the flag out to watch the browser while the tests run. Headless runs don't show the browser, so use a visible run when you need to see what a page looked like during a failure.

## 🧪 Current Test Scenarios

### 🛒 Product tests (`productTest.ProductTest`)

- Search for products and apply product filters, including brand and maximum price.
- Search with invalid or unusual input and validate the resulting page information.
- Verify product details such as name, availability, price, rating, image, and Add to Cart button visibility.
- Apply the free-shipping filter, add multiple products, verify the cart count, and check the subtotal against configured limits.

### 🛍️ Cart tests (`cartTest.CartTest`)

- Add multiple products to the cart and validate the cart counter and total-price behavior.
- Increase and decrease product quantity and validate the subtotal against the product price.

### 🗂️ Test data

- `searchingData.json` holds search terms and expected values used by the product tests.
- `Numbers.xlsx` holds numeric values that `ExcelFileManager` reads by row and column index. Cell values are returned as integers.

Test outcomes depend on live listings, prices, delivery options, and the current Amazon Egypt website structure.

## 📝 Logging with Log4j2

Log4j2 is configured in `src/main/resources/log4j2.properties`. It sends logs to the terminal and writes them to:

```text
logs/application.log
```

The log format includes the timestamp, log level, logger/class name, line number, and message. The root logging level is set to `DEBUG`.

### 🚫 Logs are not included in the project

Generated log files are intentionally excluded from the project archive and Git repository. Each user generates their own logs by running the tests:

```bash
mvn clean test
```

After execution, check `logs/application.log` for the recorded test activity. The `logs/` directory and `*.log` files are ignored by `.gitignore`, so generated logs will not be committed accidentally.

**To start with a fresh log file:** the Log4j2 `File` appender is configured with `append = true`, so new messages are added to the existing log. `mvn clean` removes only the `target/` build folder, not `logs/`. Delete `logs/application.log` manually before a run if you want a clean log. Log4j2 creates a new file during the next run.

## 📊 Allure Reports (optional)

The Allure TestNG dependency is included in `pom.xml`, but generating an HTML report also requires the Allure command-line tool and the Allure Maven plugin. If both are set up, run:

```bash
mvn allure:serve
```

Without them, the tests still run normally and this section can be skipped.

## 📌 Notes

- Do not commit generated logs, build output, or personal credentials.
- Keep test data and expected values aligned with the current test implementation.
- Amazon may change its page structure or product data, so locators and assertions may need maintenance.