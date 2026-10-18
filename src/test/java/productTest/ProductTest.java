package productTest;

import baseTest.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.productPage.ProductPage;

public class ProductTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ProductTest.class);

    @Test
    public void browse_and_Filter_Products ()  {
        log.info("========== TEST START: browse_and_Filter_Products ==========");
     ProductPage productPage = new ProductPage(driver);
     productPage.sendDataOnSearchBar(jsonFileManager.getValue("search1").toString())
             .clickSearchButton()
             .clickOnBrandFilter()
             .setMaxPrice(excelFileManager.getCellValue(1,0))
             .sortLowToHighPrice()
             .matchingSelectedFilters()
             .checkSortingOrderCurrently();

    }
    @Test
    public void searchValidation_with_Invalid_Input()
    {
        log.info("========== TEST START: searchValidation_with_Invalid_Input ==========");
        ProductPage productPage = new ProductPage(driver);
        productPage.sendDataOnSearchBar(jsonFileManager.getValue("search2").toString())
                .clickSearchButton();
        productPage.clickOnFirstProduct()
                .getFirstCategory()
                .navigationBackButton();
        productPage.clickOnSecondProduct()
                .getSecondCategory();
        Assert.assertNotEquals(productPage.getFirstCategoryName(),productPage.getSecondCategoryName());
    }
    @Test
    public void productDetailsVerification()
    {
        log.info("========== TEST START: productDetailsVerification ==========");
        ProductPage productPage = new ProductPage(driver);
        productPage.sendDataOnSearchBar(jsonFileManager.getValue("search3").toString())
                .clickSearchButton()
                .clickOnFirstProduct_withoutOutOfStockSign();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(productPage.checkProductNameIsVisible());
        softAssert.assertTrue(productPage.checkAvailabilityStatus());
        softAssert.assertTrue(productPage.checkProductPriceIsVisible());
        softAssert.assertTrue(productPage.checkRatingSection());
        softAssert.assertTrue(productPage.checkProductImageIsVisible());
        softAssert.assertTrue(productPage.checkAddToCartButtonIsVisibleAndDisplayed());
        softAssert.assertAll();
    }
    @Test
    public void addMutiProductWithFreeShipping() {
        log.info("========== TEST START: addMutiProductWithFreeShipping ==========");
        ProductPage productPage = new ProductPage(driver);
        productPage.sendDataOnSearchBar(jsonFileManager.getValue("search4").toString())
                .clickSearchButton()
                .clickOnFreeShippingCheckBox()
                .setMaxPrice(excelFileManager.getCellValue(2,0))
                .sortHighToLowPrice()
                .addNumOfProduct(excelFileManager.getCellValue(2,1))
                .setMaxPrice(excelFileManager.getCellValue(3,0))
                .addNumOfProduct(excelFileManager.getCellValue(3,1));
        Assert.assertEquals(productPage.getCountOfItemOnCart(),4);
        Assert.assertTrue(
                productPage.getSubTotal() > excelFileManager.getCellValue(1,3) &&
                        productPage.getSubTotal() <= excelFileManager.getCellValue(1,2)
        );
    }
}
