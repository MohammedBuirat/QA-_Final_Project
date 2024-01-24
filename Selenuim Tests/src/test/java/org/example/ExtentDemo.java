package org.example;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class ExtentDemo {
    static WebDriver driver;
    ExtentTest test;
    static ExtentReports report;

    @BeforeAll
    static void setupAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        String reportPath = "..\\..\\ExtentReports\\ExtentReportResults.html";
        report = new ExtentReports(reportPath);
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        File htmlFile = new File("..\\front\\index.html");
        driver.get(htmlFile.toURI().toString());
    }

    @Test
    public void testWhenSecondSelectIsNull() {
        test = report.startTest("Test when the second select is null");
        try {
            WebElement formContainer = driver.findElement(By.cssSelector(".form-container"));
            new Actions(driver).moveToElement(formContainer).clickAndHold().perform();

            WebElement amountField = driver.findElement(By.id("amount"));
            new Actions(driver).moveToElement(amountField).release().perform();

            driver.findElement(By.cssSelector(".form-container")).click();

            driver.findElement(By.id("amount")).click();
            driver.findElement(By.id("amount")).sendKeys("100");

            driver.findElement(By.id("fromCurrency")).click();
            WebElement fromCurrencyDropdown = driver.findElement(By.id("fromCurrency"));
            fromCurrencyDropdown.findElement(By.xpath("//option[. = 'AFN (Afghanistan)']")).click();
            driver.findElement(By.cssSelector(".button")).click();
            String alertMessage = driver.switchTo().alert().getText();
            assertEquals(alertMessage, "Please fill in all fields.");
            test.log(LogStatus.PASS, "Test passed successfully");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Test Failed" + e.getMessage());
        }
    }

    @Test
    public void testWhenFirstSelectIsNull() {
        test = report.startTest("Test when the first select is null");
        try {
            driver.findElement(By.id("fromCurrency")).click();
            driver.findElement(By.id("amount")).click();
            driver.findElement(By.id("amount")).sendKeys("100");
            driver.findElement(By.id("toCurrency")).click();
            {
                WebElement dropdown = driver.findElement(By.id("toCurrency"));
                dropdown.findElement(By.xpath("//option[. = 'AFN (Afghanistan)']")).click();
            }
            driver.findElement(By.cssSelector(".button")).click();
            assertThat(driver.switchTo().alert().getText(), is("Please fill in all fields."));
            test.log(LogStatus.PASS, "Test passed successfully");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Test Failed" + e.getMessage());
        }
    }

    @Test
    public void testWhenCurrenciesAreTheSame() {
        test = report.startTest("Test when currencies are the same");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement fromCurrencyDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("fromCurrency")));
            Select fromCurrencySelect = new Select(fromCurrencyDropdown);
            fromCurrencySelect.selectByIndex(2);

            WebElement toCurrencyDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("toCurrency")));
            Select toCurrencySelect = new Select(toCurrencyDropdown);
            toCurrencySelect.selectByIndex(2);

            WebElement amountInput = driver.findElement(By.id("amount"));
            amountInput.click();
            amountInput.sendKeys("100");

            driver.findElement(By.cssSelector(".button")).click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            assertThat(alert.getText(), is("Please select different currencies for conversion."));
            test.log(LogStatus.PASS, "Test passed successfully");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Test Failed" + e.getMessage());
        }
    }

    @Test
    public void testWhenAmountIsNull() {
        test = report.startTest("Test when amount is null");
        try {
            driver.findElement(By.id("fromCurrency")).click();
            {
                WebElement dropdown = driver.findElement(By.id("fromCurrency"));
                dropdown.findElement(By.xpath("//option[. = 'AMD (Armenia)']")).click();
            }
            driver.findElement(By.id("toCurrency")).click();
            {
                WebElement dropdown = driver.findElement(By.id("toCurrency"));
                dropdown.findElement(By.xpath("//option[. = 'AUD (Christmas Island)']")).click();
            }
            driver.findElement(By.cssSelector(".button")).click();
            assertThat(driver.switchTo().alert().getText(), is("Please fill in all fields."));
            test.log(LogStatus.PASS, "Test passed successfully");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Test Failed" + e.getMessage());
        }
    }

    @Test
    public void testNormalValidInteractions() {
        test = report.startTest("Test normal user interactions");
        try {
            WebElement amountInput = driver.findElement(By.id("amount"));
            amountInput.click();
            amountInput.sendKeys("100");
            amountInput.sendKeys(Keys.ENTER);

            WebElement fromCurrencyDropdown = driver.findElement(By.id("fromCurrency"));
            Select fromCurrencySelect = new Select(fromCurrencyDropdown);
            fromCurrencySelect.selectByIndex(10);

            WebElement toCurrencyDropdown = driver.findElement(By.id("toCurrency"));
            Select toCurrencySelect = new Select(toCurrencyDropdown);
            toCurrencySelect.selectByIndex(50);

            driver.findElement(By.cssSelector(".button")).click();

            toCurrencyDropdown = driver.findElement(By.id("toCurrency"));
            toCurrencySelect = new Select(toCurrencyDropdown);
            toCurrencySelect.selectByIndex(112);

            driver.findElement(By.cssSelector(".button")).click();

            driver.findElement(By.cssSelector(".form-container")).click();

            fromCurrencyDropdown = driver.findElement(By.id("fromCurrency"));
            fromCurrencySelect = new Select(fromCurrencyDropdown);
            fromCurrencySelect.selectByIndex(131);

            driver.findElement(By.cssSelector(".form-container")).click();

            driver.findElement(By.cssSelector(".button")).click();

            driver.findElement(By.cssSelector(".form-container")).click();

            driver.findElement(By.cssSelector(".button")).click();
            driver.findElement(By.cssSelector(".form-container")).click();
            test.log(LogStatus.PASS, "Test passed successfully");
        } catch (Exception e) {
            test.log(LogStatus.FAIL, "Test Failed" + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        report.endTest(test);
    }

    @AfterAll
    static void tearDownAll() {
        report.flush();
    }
}