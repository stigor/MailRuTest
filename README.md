MailRuTest
=============
Test task from Mail.ru company.
Need to create automatic tests (Selenium+Java) for changing user profile option 
(http://ok.ru/settings)

## Requirements

* Windows. Tested on Windows 10
* JRE 1.8
* Installed Chrome OR Firefox browser


## Usage
Edit \testng.xml file.
Set correct login (set_login) and password (set_password).

Then just launch runTests.cmd from bin folder:

```bash
runTests.cmd
```

Results will be in testResults.txt file near runTests.cmd and (html) in folder test-output (Just run test-output/index.html).


## ATTENTION
If you use Firefox browser do not change focus from browser window when script works.
