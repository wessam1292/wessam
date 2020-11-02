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


public class TestPrivate {

    WebDriver driver;
    public String filepath = System.getProperty("user.dir")+"//Private_Excelfile/privatecert.xlsx";


    @DataProvider(name = "newdata")
    public Object[][] newdata () {

        return ExcelReader.loadTestData(filepath, "Sheet1");
    }


    @BeforeClass
    public void start () throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//Drivers//chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

        driver.get("https://wessam.gamal1@vodafone.com:Wess@113355@enrol.pki.vodafone.com/aspx/certificate/CertificateIssuancePkcs10View.aspx");
        Thread.sleep(5000);
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();
    }

    @Test(dataProvider = "newdata")



        public void wessam(String fname, String lname , String email ,String remail, String filename) throws InterruptedException, AWTException {


        //String csrname = "cfs-portal-adm.vodafone.com.csr";
        String csrpath = System.getProperty("user.dir")+"\\Private_CSR\\"+filename;
        WebElement  Uploadcsr = driver.findElement(By.id("ctl00_ContentPlaceHolder2_AsyncFileUpload1"));
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
        StringSelection selection2 = new StringSelection(csrpath);
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

        WebElement ServiceDescription = driver.findElement(By.id("ctl00_ContentPlaceHolder2_CustomFieldGridView_ctl02_CustomFieldTextBox"));
        ServiceDescription.sendKeys("certificate renewal");
        WebElement LocalMarket = driver.findElement(By.id("ctl00_ContentPlaceHolder2_CustomFieldGridView_ctl03_CustomFieldTextBox"));
        ((WebElement) LocalMarket).sendKeys("GROUP");
        WebElement RequiredContacts  = driver.findElement(By.id("ctl00_ContentPlaceHolder2_TextBoxRecipientEmail"));
        RequiredContacts.sendKeys("wessam.gamal1@vodafone.com;amr.raafat@vodafone.com;dl-tsse-enablers-gasf@vodafone.com");
        WebElement Requestbutton  = driver.findElement(By.id("ctl00_ContentPlaceHolder2_RequestButton"));
        Requestbutton.click();
        driver.findElement(By.id("ctl00_ContentPlaceHolder2_FinishButton")).click();

    }
    @AfterClass
    public void EndTest() {

        driver.quit();

    }

    }


