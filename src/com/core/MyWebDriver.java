package com.core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.core.Logger;

public class MyWebDriver {

  public MyWebDriver() {}
  
  public static WebDriver SelectDriver(String webdriver)
  {
    Logger.toLog( "Use webdriver: " + webdriver );
    if ( webdriver.equals("chrome") )
      return new ChromeDriver();
    else if ( webdriver.equals("firefox") ) 
      return new FirefoxDriver();
    else
    {
      Logger.toError( "Setup webdriver!" );
      System.exit(0);
    }
    return null;
  }
}
