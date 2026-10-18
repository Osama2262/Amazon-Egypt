package pages.cartPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.basePage.BasePage;

import java.math.BigDecimal;

public class CartPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CartPage.class);
    private final By cartIcon = By.id("nav-cart");
    private final By quantityOnCart =By.id("nav-cart-count");
    private final By quantityIncrementIcon = By.cssSelector("[data-a-selector='increment-icon']");
    private final By quantityDecrementIcon = By.cssSelector("[data-a-selector='decrement-icon']");
    private final By priceWhole = By.xpath("//div[@class=\"sc-item-price-block\"]//span[@class=\"a-price-whole\"]");
    private final By priceFraction = By.xpath("//div[@class=\"sc-item-price-block\"]//span[@class=\"a-price-fraction\"]");
    private final By totalSubtotal  = By.xpath("//div[@data-name='Subtotals']//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']");
    public int subTotalPrice;
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void openCart() {
        log.info("Opening shopping cart");
        log.debug("Clicking cart icon");
        findElement(cartIcon).click();
    }
    public void doubleBackButton() {
        log.info("Navigating back twice to return to search results");
        navigationBackButton();
        navigationBackButton();
    }

    public int checkCartCounter() {
        log.info("Reading cart item counter");
           return Integer.parseInt(findElement(quantityOnCart).getText());
    }


public void increaseQuantityBy(int quantity) {
        log.info("Increasing product quantity by: {}", quantity);
    while (quantity > 0) {
        int cartCounter = checkCartCounter();
            int expectedQuantity = cartCounter + 1;
            log.debug("Cart counter before increment: {}; expected after increment: {}", cartCounter, expectedQuantity);

        findElement(quantityIncrementIcon).click();

        wait.until(ExpectedConditions.textToBe(
                quantityOnCart,
                String.valueOf(expectedQuantity)
        ));
        quantity--;
        }
    }
    public void decreaseQuantity(int quantity) {
        log.info("Decreasing product quantity by: {}", quantity);
        while (quantity > 0) {
            int cartCounter = checkCartCounter();

            findElement(quantityDecrementIcon).click();

            int expectedQuantity = cartCounter - 1;
            log.debug("Cart counter before decrement: {}; expected after decrement: {}", cartCounter, expectedQuantity);

            wait.until(ExpectedConditions.textToBe(
                    quantityOnCart,
                    String.valueOf(expectedQuantity)
            ));

            quantity--;
        }
    }
    public int getProductPrice(int multiply) {
        log.info("Calculating product price in minor currency units");
        String whole = findElement(priceWhole)
                .getText()
                .replaceAll("[^0-9]", "");

        String fraction = findElement(priceFraction)
                .getText();
        BigDecimal price = new BigDecimal(whole + "." + fraction);

        int totalInMinorUnits = price.multiply(BigDecimal.valueOf(multiply)).movePointRight(2).intValueExact();
        log.debug("Calculated multiplied product price in minor units: {}", totalInMinorUnits);
        return totalInMinorUnits;
    }
    public int getProductPrice() {
        String whole = findElement(priceWhole)
                .getText()
                .replaceAll("[^0-9]", "");

        String fraction = findElement(priceFraction)
                .getText();
        BigDecimal price = new BigDecimal(whole + "." + fraction);

        int priceInMinorUnits = price.movePointRight(2).intValueExact();
        log.debug("Calculated product price in minor units: {}", priceInMinorUnits);
        return priceInMinorUnits;
    }

    public int getSubTotalPrice() {
        log.info("Reading cart subtotal in minor currency units");
        String temp = findElement(totalSubtotal)
                .getText()
                .replaceAll("[^0-9]", "");
        subTotalPrice = Integer.parseInt(temp);
        log.debug("Cart subtotal in minor currency units: {}", subTotalPrice);
        return subTotalPrice;
    }

}



