@echo off

SET rootPath=%~dp0
SET rootPath=%rootPath:~0,-4%
SET libPath=%rootPath%\lib
SET webdriverChromeDriver=%rootPath%\bin\chromedriver.exe

java -Dwebdriver.chrome.driver=%webdriverChromeDriver% -cp .;%libPath%\testng-6.9.9.jar;%libPath%\selenium-java-2.49.1.jar;%libPath%\selenium-server-standalone-2.49.1.jar org.testng.TestNG %rootPath%\testng.xml > testResults.txt 2>&1