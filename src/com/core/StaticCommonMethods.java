package com.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

public class StaticCommonMethods {
  
  public static void loginToProfile(WebDriver driver, String login, String password)
  {
    WebElement loginElement, passwordElement, loginButton;
    try {
      loginElement = driver.findElement(By.id("field_email"));
      passwordElement = driver.findElement(By.id("field_password"));
      loginButton = driver.findElement(By.cssSelector("form.form > div.form-actions > input.button-pro.form-actions_yes"));
      
      if ( driver.getTitle().contains("Odnoklassniki") && 
            loginElement.isDisplayed() && 
            passwordElement.isDisplayed() && 
            loginButton.isEnabled() )
      {
        Logger.toLog( "Login form was opened. Try to login." );
        loginElement.clear();
        loginElement.sendKeys( login );
        passwordElement.clear();
        passwordElement.sendKeys( password );
        loginButton.submit();
      }
      else
      {
        Logger.toLog( "Some elements have incorrect state" );
        AssertJUnit.assertTrue( "Login field doesn't show", loginElement.isDisplayed() );
        AssertJUnit.assertTrue( "Password field doesn't show", passwordElement.isDisplayed() );
        AssertJUnit.assertTrue( "Login button doesn't enable", loginButton.isEnabled() );
        AssertJUnit.assertTrue( "Incorrect Log in page title", driver.getTitle().contains("Odnoklassniki") );
      }
    } catch (NoSuchElementException e)
    {
      AssertJUnit.fail( "Login form was NOT opened" );
    }    
  }
  
  public static void tapEditPrivateButton( WebDriver driver )
  {
    // Tap edit on private data
    Logger.toLog("Tap on edit private data button");
    WebElement editPrivateData = driver.findElement(By.xpath("//div[@id='hook_Block_UserConfigMRB']/div/a/div[2]/div[2]"));
    JavascriptExecutor js = (JavascriptExecutor)driver;
    js.executeScript("arguments[0].click();", editPrivateData);
    
    // Check popup window was opened
    WebDriverWait waitEditProfileForm = new WebDriverWait(driver, 10); 
    waitEditProfileForm.until(ExpectedConditions.visibilityOfElementLocated(By.id("popLayerBodyWrapper")));
    Logger.toLog("Popup window with edit profile form was opened. Inserting datas...");
  }
}
