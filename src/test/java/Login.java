import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Login {
    static WebDriver chromeDriver;
    static String env = "cloud";

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        chromeDriver = new ChromeDriver(options);

        if (env == "onPrem") {
            chromeDriver.get("https://uat.myhubgroup.com/GC3/glog.webserver.servlet.umt.Login");
            WebElement userName = chromeDriver.findElement(By.xpath("//input[@name='username']"));
            WebElement password = chromeDriver.findElement(By.xpath("//input[@name='userpassword']"));
            WebElement submitButton = chromeDriver.findElement(By.xpath("//input[@type='submit']"));

            userName.sendKeys("CORP/HUB.BISMATOV");
            password.sendKeys("otmBis293!");
            submitButton.click();

            chromeDriver.get("https://uat.myhubgroup.com/GC3/glog.webserver.process.walker.ProcessWalkerDiagServlet");

            olderThan("Hours", "1");

            WebElement scalabilityModeSelect = chromeDriver.findElement(By.xpath("//select[@name='scalMode']"));

            Select select = new Select(scalabilityModeSelect);
            select.selectByValue("network");

            PrintWriter writer = null;

            try {
                writer = new PrintWriter("src/test/resources/data/table_content.txt", "UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> description = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[1] | //tr[@class='rowEven']/td[1]"));
            List<WebElement> id = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[2] | //tr[@class='rowEven']/td[2]"));
            List<WebElement> logID = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[3] | //tr[@class='rowEven']/td[3]"));

            for (int i = 0; i < description.size(); i++) {
                writer.println(
                        "Description: " + description.get(i).getText() +
                                " :ID: " + id.get(i).getText() +
                                " :logid: " + logID.get(i).getText());
            }

            writer.close();

        } else if (env == "cloud") {
            chromeDriver.get("https://cloudua.myhubgroup.com");
            Thread.sleep(5000);

            WebElement userName = chromeDriver.findElement(By.xpath("//input[@id='idcs-signin-basic-signin-form-username']"));
            WebElement password = chromeDriver.findElement(By.xpath("//input[@id='idcs-signin-basic-signin-form-password|input']"));
            WebElement submitButton = chromeDriver.findElement(By.xpath("//*[@id='idcs-signin-basic-signin-form-submit']/button"));

            userName.sendKeys("bismatov@hubgroup.com");
            password.sendKeys("HubGroup2025");
            submitButton.click();

            Thread.sleep(5000);

            chromeDriver.get("https://otmgtm-test-hubgroupotm.otmgtm.us-ashburn-1.ocs.oraclecloud.com/GC3/glog.webserver.process.walker.ProcessWalkerDiagServlet");

            olderThan("Hours", "1");

//            WebElement scalabilityModeSelect = chromeDriver.findElement(By.xpath("//select[@name='scalMode']"));
//            Select select = new Select(scalabilityModeSelect);
//            select.selectByValue("network");

            PrintWriter writer = null;

            try {
                writer = new PrintWriter("src/test/resources/data/table_content.txt", "UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> description = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[1] | //tr[@class='rowEven']/td[1]"));
            List<WebElement> id = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[2] | //tr[@class='rowEven']/td[2]"));
            List<WebElement> logID = chromeDriver.findElements(By.xpath("//tr[@class='rowOdd']/td[3] | //tr[@class='rowEven']/td[3]"));

            for (int i = 0; i < description.size(); i++) {
                writer.println(
                        "Description: " + description.get(i).getText() +
                        " :ID: " + id.get(i).getText() +
                        " :logid: " + logID.get(i).getText());
            }

            writer.close();
        }
    }

    public static void olderThan(String parameter, String value) {
        WebElement olderThanDays = chromeDriver.findElement(By.xpath("//input[@name='uom_olderThan_days']"));
        WebElement olderThanHours = chromeDriver.findElement(By.xpath("//input[@name='uom_olderThan_hours']"));
        WebElement olderThanMinutes = chromeDriver.findElement(By.xpath("//input[@name='uom_olderThan_minutes']"));

        switch (parameter) {
            case "Days" :
                olderThanDays.clear();
                olderThanDays.sendKeys(value, Keys.ENTER);
                break;
            case "Hours" :
                olderThanHours.clear();
                olderThanHours.sendKeys(value, Keys.ENTER);
                break;
            case "Minutes" :
                olderThanMinutes.clear();
                olderThanMinutes.sendKeys(value, Keys.ENTER);
                break;
        }
    }
}
