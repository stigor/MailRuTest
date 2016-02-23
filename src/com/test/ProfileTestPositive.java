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

public class ProfileTestPositive {
  
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
  public Object[][] privateTestData() {
    return new Object[][] {
      // { TestNumber, First_name, Last_name, Sex, City }
      new Object[] { "������", "��������", 1, "�����-���������" },
      new Object[] { "��������", "�������", 2, "��������" },
    };
  }

  @Test(dataProvider = "privateTestData", groups = { "profile" })
  public void testProfileFirstLastNameSexCity(String firstName, 
                                              String lastName, 
                                              Integer sex, 
                                              String city) {
    
    // Tap edit on private data
    StaticCommonMethods.tapEditPrivateButton(driver);
    
    // Change first name
    WebElement firstNameField = driver.findElement(By.id("field_name"));
    firstNameField.clear();
    firstNameField.sendKeys(firstName);
    
    // Change last name
    WebElement lastNameField = driver.findElement(By.id("field_surname"));
    lastNameField.clear();
    lastNameField.sendKeys(lastName);
    
    // Change Sex
    WebElement sexField;
    if ( sex == 2 )
    {
      sexField = driver.findElement(By.id("field_gender_2"));
    }
    else
    {
      sexField = driver.findElement(By.id("field_gender_1"));
    }
    sexField.click();
    
    // Change City
    WebElement cityField = driver.findElement(By.id("field_citySugg_SearchInput"));
    cityField.clear();
    cityField.sendKeys(city);
    cityField.sendKeys(Keys.ARROW_DOWN);
    cityField.sendKeys(Keys.ENTER);
    
    // Press Save button
    WebElement saveProfileButton = driver.findElement(By.id("hook_FormButton_button_savePopLayerEditUserProfileNew"));
    saveProfileButton.click();
    
    // Check successful popup was displayed
    WebDriverWait waitProfileWasSavedPopup = new WebDriverWait(driver, 10); 
    waitProfileWasSavedPopup.until(ExpectedConditions.visibilityOfElementLocated(By.id("notifyPanel_msg")));
    Logger.toLog("ProfileWasSaved popup window was opened.");
    
    // Close popup
    driver.findElement(By.id("buttonId_button_close")).click();
    
    // Wait "add new app" form closed 
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("notifyPanel_msg")));
    
    // Check data saved
    Logger.toLog("Check data was saved");
    WebElement privateData = driver.findElement(By.cssSelector(".user-settings_i_tx,.textWrap"));
    String privateDataContent = privateData.getText();
    AssertJUnit.assertTrue("First name not found", privateDataContent.contains(firstName));
    AssertJUnit.assertTrue("Last name not found", privateDataContent.contains(lastName));
    if ( sex == 2 )
    {
      AssertJUnit.assertTrue("Incorrect sex", privateDataContent.contains("��������"));
    }
    else
    {
      AssertJUnit.assertTrue("Incorrect sex", privateDataContent.contains("�������"));
    }
    AssertJUnit.assertTrue("City not found", privateDataContent.contains(city));
  }

  @Test(groups = { "profile" })
  public void testDiscardChanges() {
    // Tap edit on private data
    StaticCommonMethods.tapEditPrivateButton(driver);
    
    // Get First Name
    WebElement firstNameField = driver.findElement(By.id("field_name"));
    String firstName = firstNameField.getAttribute("value");
    AssertJUnit.assertNotNull(firstName);
    firstNameField.clear();

    // Get Last Name
    WebElement lastNameField = driver.findElement(By.id("field_surname"));
    String lastName = lastNameField.getAttribute("value");
    AssertJUnit.assertNotNull(lastName);
    lastNameField.clear();
    
    // TODO: Also need to check sex and city
    
    // Discard changes
    // TODO: Also need to check by "X" button
    WebElement cancelButton = driver.findElement(By.cssSelector(".lp"));
    cancelButton.click();
    (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("popLayerBodyWrapper")));
    
    // Check
    Logger.toLog("Check data wasn't saved");
    WebElement privateData = driver.findElement(By.cssSelector(".user-settings_i_tx,.textWrap"));
    String privateDataContent = privateData.getText();
    AssertJUnit.assertTrue("First name was changed", privateDataContent.contains(firstName));
    AssertJUnit.assertTrue("Last name was changed", privateDataContent.contains(lastName));    
  }
  
  @AfterSuite
  public void afterSuite() {
    driver.quit();
  }

}
