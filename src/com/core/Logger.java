package com.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
  
  private static String getTimestamp()
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
  }
  
  public static void toLog( String str )
  {
    System.out.println("[" + getTimestamp() + "] " + str );
  }
  
  public static void toWarning( String str )
  {
    System.out.println("[" + getTimestamp() + "] Warning: " + str );
  }
  
  public static void toError( String str )
  {
    System.err.println("[" + getTimestamp() + "] Error: " + str );
  }
}
