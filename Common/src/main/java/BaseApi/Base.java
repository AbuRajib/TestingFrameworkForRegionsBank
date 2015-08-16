package BaseApi;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by HP on 8/15/2015.
 */
public class Base {

    public WebDriver driver=null;
    @Parameters({"SauceLab","UserName","Key","BrowserName","BrowserVersion","Os","url"})
    @BeforeMethod
    public void setUp(Boolean SauceLab,String UserName,String Key,String BrowserName,
                      String BrowserVersion,String Os,String url) throws IOException {

        if (SauceLab == true) {
            setupCloudEnvieonment(UserName,Key,BrowserName,BrowserVersion,Os,url);
        } else {
            localEnvironment(BrowserName,BrowserVersion,url);
        }

    }


    public void setupCloudEnvieonment(String UserName,String Key,String BrowserName,
                                      String BrowserVersion,String Os,String url) throws IOException {

        DesiredCapabilities cap=new DesiredCapabilities();
        cap.setBrowserName(BrowserName);
        cap.setCapability("platform",Os);
        cap.setCapability("version",BrowserVersion);
        this.driver=new RemoteWebDriver(new URL("http://"+UserName+":"+Key+"@ondemand.saucelabs.com:80/wd/hub"),cap);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.navigate().to(url);
        driver.manage().window().maximize();

    }

    public void localEnvironment(String BrowserName, String BrowserVersion,String url){
        if(BrowserName.equalsIgnoreCase("firefox")){
            driver=new FirefoxDriver();
        }
        else if(BrowserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver","Common\\selenium-server\\chromedriver_win32\\chromedriver.exe");
        }

        else if(BrowserName.equalsIgnoreCase("IE")){
            System.setProperty("webdriver.ie.driver","Common\\Selenium-Server\\IEDriverServer.exe");
        }


        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.navigate().to(url);
        driver.manage().window().maximize();
    }





    @AfterMethod
    public void cleanUp(){
        driver.quit();
    }


    public void clickByCss(String locator){
        driver.findElement(By.cssSelector(locator)).click();
    }

    public void clickByXpath(String locator){
        driver.findElement(By.xpath(locator)).click();
    }


    public void typeByCss(String locator,String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }

    public void typeByXpath(String locator,String value){
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }


    public void typeAndEnterByCss(String locator,String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }

    public void typeAndEnterByXpath(String locator,String value){
        driver.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
    }


    public void clickText(String locator){
        driver.findElement(By.linkText(locator)).click();
    }

    public void getTextByCss(String locator){
        driver.findElement(By.cssSelector(locator)).getText();
    }
    public void getTextByXpath(String locator){
        driver.findElement(By.xpath(locator)).getText();
    }




    public List<String> getListOfTextByCss(String locator){
        List<WebElement> lists=driver.findElements(By.cssSelector(locator));
        ArrayList<String> text=new ArrayList<String>();
        for(WebElement wb:lists){
            text.add(wb.getText());
        }
        return text;
    }


    public List<String> getListOfTextByXpath(String locator){
        List<WebElement> lists=driver.findElements(By.xpath(locator));
        ArrayList<String> texts=new ArrayList<String>();
        for(WebElement wb:lists){
            texts.add(wb.getText());
        }
        return texts;
    }


    public void displayText(List<String> texts){
        for(String st:texts){
            System.out.println(st);
        }

    }


    public List<WebElement> getWebElementsByCss(String locator){
        List<WebElement> elements=driver.findElements(By.cssSelector(locator));
        return elements;
    }

    public List<WebElement> getWebElementsByXpath(String locator){
        List<WebElement> elements=driver.findElements(By.xpath(locator));
        return elements;
    }


    public void sleepFor(int number) throws InterruptedException {
        Thread.sleep(number*1000);
    }

    public void navigateBack(){
        driver.navigate().back();
    }

    public void mouseOver(List<WebElement> lists){
        for(WebElement st:lists){
            Actions action=new Actions(driver);
            action.moveToElement(st).build().perform();
        }
    }


    public void takeScreenShoot(String locator) throws IOException {
        File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(locator),true);
    }




}
