package CertificateTest;

import com.ahmed.excelizer.ExcelReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class TestPublic {

    WebDriver driver;
    public int trials = 0;
    public String filepath = System.getProperty("user.dir")+"//Public_Excelfile//Publiccert.xlsx";

    @DataProvider(name = "userdata")
    public Object[][] UserData() {

        return ExcelReader.loadTestData(filepath, "Sheet1");
    }

    @BeforeClass
    public void Setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers//chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
    }
    @Test(dataProvider = "userdata")
    public void Vodafone(String fname, String lname , String email ,String remail, String filename) throws InterruptedException, AWTException {
        driver.navigate().to("https://www.digicert.com/secure/requests/ssl_certificate/ssl_plus?guest_key=vjtxx9p1z5m9nvh0");
        driver.findElement(By.name("guest_requester_first_name")).sendKeys(fname);
        driver.findElement(By.name("guest_requester_last_name")).sendKeys(lname);
        driver.findElement(By.name("guest_requester_email")).sendKeys(email);

        //handling cookie

        if (trials==0)
        {
            Thread.sleep(10000);
            WebElement cookie = driver.findElement(By.id("onetrust-accept-btn-handler"));
            cookie.click();
        }
        trials++;
        // handling CSR upload
        String csrpath = System.getProperty("user.dir")+"\\Public_CSR\\"+filename;
        WebElement Uploadcsr = driver.findElement(By.id("upload-csr-button"));
        Uploadcsr.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Uploadcsr);

        // code using Robotclass for file upload
        Robot robot = new Robot();
        // To copy csrpath
        StringSelection selection = new StringSelection(csrpath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);

        // click on crtl + v csrpath
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(3000);

        // ctrl + c csrname
        StringSelection selection2 = new StringSelection(filename);
        Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);

        // ctrl + v csrname
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(4000);

        //select organization


        WebElement organization =  driver.findElement(By.xpath("//*[@id=\"add_org_card\"]/div/h5/a"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        organization.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"existing_org\"]/div[2]/div[1]/div[2]/div/label/div/address/h5")).click();
        driver.findElement(By.id("add-org-btn")).click();

        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[1]/ul/li/input")).sendKeys(remail);
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[2]/ul/li/input")).sendKeys("amr.raafat@vodafone.com");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[3]/textarea")).sendKeys("Y");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[4]/textarea")).sendKeys("Y");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[5]/textarea")).sendKeys("Y");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[6]/textarea")).sendKeys("GROUP");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[7]/textarea")).sendKeys("certificate renewal");
        driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[8]/textarea")).sendKeys("Dev");



        WebElement DL = driver.findElement(By.xpath("//*[@id=\"request-form\"]/div[4]/div/div/div[9]/ul/li/input"));

        js.executeScript("arguments[0].scrollIntoView();", DL);
        DL.sendKeys("dl-tsse-enablers-gasf@vodafone.com");


        driver.findElement(By.id("tos")).click();
        Thread.sleep(10000);
        driver.findElement(By.id("submit-request-button")).click();

        // To get order number
        driver.getTitle();
        Thread.sleep(10000);
        String str = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div/div[1]/div/div[1]/div/div")).getText();
        System.out.println(str);


    }


    @AfterClass
    public void EndTest() {

        driver.quit();

    }
}
