package pages.productPage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.basePage.BasePage;

import java.util.List;

   public class ProductPage extends BasePage {
       private static final Logger log = LogManager.getLogger(ProductPage.class);


       private final By searchIcon = By.cssSelector("[type='submit']");
       private final By searchBar = By.id("twotabsearchtextbox");
       private final By brandFilter = By.cssSelector("[aria-label='Apply the filter Dell to narrow results']");
       private final By maxPriceSlider = By.xpath("//input[@id='p_36/range-slider_slider-item_upper-bound-slider']");
       private final By targetP = By.xpath("//label[@for='p_36/range-slider_slider-item_upper-bound-slider']//span");
       private final By sortList = By.className("a-dropdown-container");
       private final By sortLowToHighPrice = By.xpath("//a[@class='a-dropdown-link' and contains (.,'Low to')]");
       private final By sortHighToLowPrice = By.xpath("//a[@class='a-dropdown-link' and contains (.,'High to')]");
       private final By priceForAllProduct = By.className("a-price-whole");
       private final By result_text = By.xpath("//h2[contains(.,'Results')]");
       private final By titleRecipe = By.xpath("//a[@class='a-link-normal s-line-clamp-3 s-link-style a-text-normal']//span");
       private final By numOfItem = By.xpath("//span[contains(text(),' results for')]");
       private final By addToCartButton_avoidOutOfStockItems = By.xpath("//div[@data-component-type='s-search-result' and not(.//span[contains(text(),'left in stock')])]//input[@aria-label=\"Add to cart\"]");
       private final By addToCartButton = By.xpath("//div[@role='listitem' and not(.//div[@class='a-stepper-controls'])]//input[@aria-label='Add to cart']");
       private final By productWithoutOutOfStock =By.xpath("//div[@data-component-type='s-search-result' and not(.//span[contains(text(),'left in stock')])]");
       private final By firstItem = By.xpath("//div[@data-cel-widget='search_result_1']");
       private final By productList = By.xpath("//div[@role='listitem']");
       private final By secondItem = By.cssSelector("[data-cel-widget='search_result_2']");
       private final By itemPricePDP = By.xpath("//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole']");
       private final By totalSubtotal  = By.xpath("//div[@data-name='Subtotals']//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']");
       private final By firstCategory =By.xpath("//ul[@class='a-unordered-list a-horizontal a-size-small']//span[@class='a-list-item']");
       private final By addToCartPDP = By.id("add-to-cart-button");
       private final By productTitle= By.id("productTitle");
       private final By priceWhole = By.xpath("//div[@id='corePriceDisplay_desktop_feature_div']//span[@class='a-price-whole']");
       private final By availabilityStatus = By.id("availability");
       private final By productImage = By.id("main-image-container");
       private final By ratingSection = By.id("averageCustomerReviews_feature_div");
       private final By freeShippingCheckBox = By.cssSelector("[aria-label='Apply the filter Free Shipping to narrow results']");
       private final By subTotal = By.xpath("//h2[@class='a-size-mini a-color-price a-text-bold']");
       private final By rightSideCartItems =By.xpath("//div[@data-cart-type='Retail_Cart']//li");
       private final By goToBasket = By.xpath("//span[@class='a-button-inner']//a[contains(text(),'Go to')]");
       private final By filterMenu =By.id("s-refinements");
       int firstItemPrice = 0;
       int secondItemPrice = 0;
       int totalPrice = 0;
       String firstCategoryName;
       String secondCategoryName;
       public ProductPage(WebDriver driver) {
           super(driver);
       }

       public ProductPage sendDataOnSearchBar(String searchValue) {
           log.info("Entering search term: {}", searchValue);
           findElement(searchBar).sendKeys(searchValue);
           return this;
       }
       public ProductPage clickSearchButton() {
           log.info("Submitting product search");
           findElement(searchIcon).click();
           explicitWaitElementLocated(filterMenu);
           return this;
       }
       public ProductPage clickOnBrandFilter() {
           log.info("Applying brand filter");
           findElement(brandFilter).click();
           explicitWaitElementLocated(result_text);
           return this;
       }
       public ProductPage waitForSearchResults() {
           log.info("Waiting for search results to be visible");
           explicitWaitElementLocated(productList);
           return this;
       }
       public ProductPage openFirstProduct() {
           log.info("Opening first product result");
           WebElement firstProduct = findElements(productList).getFirst();
           safeClick(firstProduct);
           explicitWaitElementLocated(productTitle);
           explicitWaitElementLocated(itemPricePDP);
           firstItemPrice = Integer.parseInt(findElement(itemPricePDP).getText().replace(",",""));
           return this;
       }
       public ProductPage openSecondProduct() {
           log.info("Opening second product result");
           WebElement secondProduct = findElements(productList).get(1);
           safeClick(secondProduct);
           explicitWaitElementLocated(productTitle);
           explicitWaitElementLocated(itemPricePDP);
           secondItemPrice = Integer.parseInt(findElement(itemPricePDP).getText().replace(",",""));
           return this;
       }
       public ProductPage clickOnFirstProduct() {
           log.info("Opening first search result");
           findElement(firstItem).click();
           return this;
       }
       public ProductPage clickOnSecondProduct() {
           log.info("Opening second search result");
           findElement(secondItem).click();
           return this;
       }
       public void goToBasket() {
           log.info("Opening Go to basket link");
           findElement(goToBasket).click();
       }
       public ProductPage clickAddToCartButton() {
           log.info("Clicking Add to Cart on product details page");
           findElement(addToCartPDP).click();
           return this;
       }
       public boolean collectTotalPrice() {
           log.info("Comparing cart subtotal with selected product prices");
           totalPrice = firstItemPrice + secondItemPrice;
           String temp = findElement(totalSubtotal).getText().replaceAll("[^0-9]", "");
           temp = temp.substring(0, temp.length() - 2);
           int subtotal = Integer.parseInt(temp);
           return subtotal == totalPrice;
       }
       public ProductPage setMaxPrice(int targetPrice) {
           log.info("Adjusting maximum price filter to: {}", targetPrice);
           explicitWaitElementLocated(result_text);
           WebElement maxSlider = findElement(maxPriceSlider);
           String temp = maxSlider.getAttribute("aria-valuetext");
           assert temp != null;
           int currentPrice = Integer.parseInt(temp.replaceAll("[^0-9]", ""));
           int steps = (currentPrice - targetPrice) / 100;
           for (int i = 0; i < steps; i++) {
               int tempPrice = Integer.parseInt(findElement(targetP).getText().replaceAll("[^0-9]", ""));
               if (tempPrice == targetPrice) {
                   break;
               }
               maxSlider.sendKeys(Keys.ARROW_LEFT);
           }
           String firstNumberOfItem = findElement(numOfItem).getText().split(" results")[0];
           By afterSlider = By.xpath( "//span[contains(text(),'"
                   + firstNumberOfItem + " results for')]");
           wait.until(ExpectedConditions.invisibilityOfElementLocated(afterSlider));

           return this;
       }
       public ProductPage sortLowToHighPrice() {
           log.info("Sorting products from low to high price");
           findElement(sortList).click();
           findElement(sortLowToHighPrice).click();
           explicitWaitElementLocated(result_text);
           return this;
       }
       public ProductPage sortHighToLowPrice() {
           log.info("Sorting products from high to low price");
           findElement(sortList).click();
           findElement(sortHighToLowPrice).click();
           explicitWaitElementLocated(result_text);
           return this;
       }
       public ProductPage matchingSelectedFilters() {
           log.info("Validating product titles against selected filters");
           List<WebElement> allTitle = findElements(titleRecipe);
           int listSize = allTitle.size();
           for (int i = 0; i < listSize - 1; i++) {
               if(!(allTitle.get(i).getText().contains("Dell")||allTitle.get(i).getText().contains("DELL")))
               {
                   log.warn("Product title does not match the selected Dell brand filter: {}", allTitle.get(i).getText());
               }
           }
           return this;
       }
       public void checkSortingOrderCurrently() throws RuntimeException {
           log.info("Validating ascending product price order");
           List<WebElement> allPrice = findElements(priceForAllProduct);
           int listSize = allPrice.size();

           for (int i = 0; i < listSize - 1; i++) {
               int currentProductPrice = Integer.parseInt(allPrice.get(i).getText().replace(",", ""));
               int nextProductPrice = Integer.parseInt(allPrice.get(i + 1).getText().replace(",", ""));
               if (!(currentProductPrice <= nextProductPrice)) {
                   log.error("Sorting validation failed: current price {} exceeds next price {}", currentProductPrice, nextProductPrice);
                   throw new RuntimeException("The Current product price is greater than the next product price.");
               }
           }
       }
       public WebElement findAddToCartWithoutOutOfStockSign() {
           return findElements(addToCartButton_avoidOutOfStockItems).getFirst();
       }
       public void clickOnFirstProduct_withoutOutOfStockSign() {
           log.info("Opening first in-stock product result");
           findElements(productWithoutOutOfStock).getFirst().click();
       }
       public ProductPage clickAddToCartForFirstItem() {
           log.info("Adding first in-stock result to cart");
           findAddToCartWithoutOutOfStockSign().click();
           wait.until(ExpectedConditions.visibilityOfElementLocated(goToBasket));
           return this;
       }
       public ProductPage getFirstCategory() {
           log.info("Reading first product category");
          firstCategoryName = findElements(firstCategory).getFirst().getText();
          return this;
       }
       public void getSecondCategory()
       {
           log.info("Reading second product category");
           secondCategoryName= findElements(firstCategory).getFirst().getText();
       }
        public String getFirstCategoryName()
    {
        return firstCategoryName;
    }
        public String getSecondCategoryName()
    {
        return secondCategoryName;
    }
        public boolean checkProductNameIsVisible()
    {
           log.info("Checking product title visibility");
        return findElement(productTitle).isDisplayed();
    }
        public boolean checkProductPriceIsVisible()
        {
           log.info("Checking product price visibility");
        return findElement(priceWhole).isDisplayed();
        }
        public boolean checkAvailabilityStatus()
        {
           log.info("Checking product availability visibility");
           return findElement(availabilityStatus).isDisplayed();
        }
        public boolean checkProductImageIsVisible()
        {
           log.info("Checking product image visibility");
            return findElement(productImage).isDisplayed();
        }
        public boolean checkRatingSection()
        {
           log.info("Checking customer rating section visibility");
            return findElement(ratingSection).isDisplayed();
        }
        public boolean checkAddToCartButtonIsVisibleAndDisplayed()
        {
           log.info("Checking Add to Cart button visibility");
            return findElement(addToCartPDP).isDisplayed();
        }
        public ProductPage clickOnFreeShippingCheckBox() {
           log.info("Applying Free Shipping filter");
           findElement(freeShippingCheckBox).click();
        return this;
       }
       public ProductPage addNumOfProduct(int num)
       {
           log.info("Adding requested number of products to cart: {}", num);
           while (num > 0) {
               String oldPrice;
               if (driver.findElements(subTotal).isEmpty()) {
                   oldPrice = "";
               } else {
                   oldPrice = findElement(subTotal).getText();
               }
               findElements(addToCartButton).getFirst().click();

               wait.until(ExpectedConditions.visibilityOfElementLocated(rightSideCartItems));

               if (!oldPrice.isEmpty()) {
                   wait.until(ExpectedConditions.not(
                           ExpectedConditions.textToBe(subTotal, oldPrice)));
               }

               log.debug("Product added; remaining additions: {}", num - 1);
               num--;
           }
           return this;
       }
       public int getSubTotal()
       {
           log.info("Reading cart subtotal");
           return Integer.parseInt(findElement(subTotal).getText().replaceAll("[^0-9]", ""))/100;
       }
       public int getCountOfItemOnCart()
       {
           log.info("Counting items in cart");
           return findElements(rightSideCartItems).size();
       }
   }