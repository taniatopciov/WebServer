package selenium;// Generated by Selenium IDE

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class WebServerTestSiteTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    public void webServerTestSiteTest() {
        driver.get("http://localhost:8080/a.html");
        driver.manage().window().setSize(new Dimension(1196, 700));
        driver.findElement(By.linkText("do simple relative internal links work?")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do general relative links work")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do simple absolute links work")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do general absolute links work")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do URLs with spaces work")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do URLs with %20 work")).click();
        driver.findElement(By.linkText("back")).click();
        driver.findElement(By.linkText("do TXT files work")).click();
    }
}
