package cartTest;

import baseTest.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.cartPage.CartPage;
import pages.productPage.ProductPage;

public class CartTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(CartTest.class);
    @Test
    public void addMultipleProductsToCart()
    {
        log.info("========== TEST START: addMultipleProductsToCart ==========");
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        productPage.sendDataOnSearchBar(jsonFileManager.getValue("search5").toString())
                .clickSearchButton()
                .openFirstProduct()
                .clickAddToCartButton()
                .goBackTwice();
        productPage.waitForSearchResults()
                .openSecondProduct()
                .clickAddToCartButton();
        cartPage.doubleBackButton();
        productPage.openSecondProduct()
                .clickAddToCartButton();
        cartPage.openCart();
        Assert.assertTrue(productPage.collectTotalPrice());
        Assert.assertEquals(cartPage.checkCartCounter(),2);
    }
    @Test
    public void updateCartQuantity()  {
        log.info("========== TEST START: updateCartQuantity ==========");
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        productPage.sendDataOnSearchBar(jsonFileManager.getValue("search3").toString())
                .clickSearchButton()
                .clickAddToCartForFirstItem()
                .goToBasket();
        cartPage.increaseQuantityBy(2);
        Assert.assertEquals(cartPage.getSubTotalPrice(),cartPage.getProductPrice(3));
        cartPage.decreaseQuantity(2);
        Assert.assertEquals(cartPage.getSubTotalPrice(),cartPage.getProductPrice());
        System.out.println("GG,Go next ☺");
    }
}
