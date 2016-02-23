package com.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import com.core.StaticCommonMethods;
import com.core.Logger;
import com.core.MyWebDriver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

public class ProfileTestNegative {
  
  public static WebDriver driver;
  
  @BeforeSuite
  @Parameters( { "login", "password", "baseUrl", "webdriver" } )
  public void beforeSuite(String login, String password, String baseUrl, String webDriver) {    

    // Setup driver
    driver = MyWebDriver.SelectDriver(webDriver);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    // Open first page
    driver.get( baseUrl );
    
    // Login
    StaticCommonMethods.loginToProfile(driver, login, password);
    
    // Go to profile page
    driver.get( baseUrl + "/settings" );
  }
  
  @DataProvider
  public Object[][] incorrectFirstNameTestData() {
    return new Object[][] {
      // { Incorrect_First_Name }
      new Object[] { "", "Пожалуйста, укажите ваше имя." },
      new Object[] { "\"", "Пожалуйста, используйте только буквы." },
      new Object[] { "'", "Пожалуйста, используйте только буквы." },
    };
  }
  
  @Test(dataProvider = "incorrectFirstNameTestData", groups = { "profile" })
  public void testIncorrectFirstName( String incorrectFirstName, String errorMessage ) {
    // Tap edit on private data
    StaticCommonMethods.tapEditPrivateButton(driver);
    
    // Get First Name
    WebElement firstNameField = driver.findElement(By.id("field_name"));
    firstNameField.clear();
    firstNameField.sendKeys( incorrectFirstName );

    // Change City
    WebElement cityField = driver.findElement(By.id("field_citySugg_SearchInput"));
    cityField.clear();
    cityField.sendKeys("Санкт-Петербург");
    cityField.sendKeys(Keys.ARROW_DOWN);
    cityField.sendKeys(Keys.ENTER);
    
    // Press Save button
    WebElement saveProfileButton = driver.findElement(By.id("hook_FormButton_button_savePopLayerEditUserProfileNew"));
    saveProfileButton.click();
    
    // Check
    Logger.toLog("Check error message appear");
    WebElement firstNameErrorMessage = driver.findElement(By.xpath("//span[contains(text(),'"+errorMessage+"')]"));
    AssertJUnit.assertTrue("Error message isn't displayed", firstNameErrorMessage.isDisplayed());
    
    // Close window
    WebElement cancelButton = driver.findElement(By.cssSelector(".lp"));
    cancelButton.click();
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("popLayerBodyWrapper")));
  }
  
  @AfterSuite
  public void afterSuite() {
    driver.quit();
  }

}
