import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private WebDriver driver;
    private static final String CREATE_ACCOUNT_URL = "https://sample-project.tech-stack.dev/signup";
    private static final String LOGIN = "tester@tester.test";
    private static final String PASSWORD = "12345678";
    private static final String LOGIN_URL = "https://sample-project.tech-stack.dev/login";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void loginInto(String login, String password) {
        driver.get(LOGIN_URL);
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//button[@class='col-md-3 btn btn-primary']")).click();
    }

    @Test
    public void loginValidDataTest() {
        loginInto(LOGIN, PASSWORD);
        Assert.assertEquals(driver.findElement(By.xpath("//a/span")).getText(), LOGIN.toUpperCase(Locale.ROOT));
    }

    @Test
    public void loginValidDataWithSpacesTest() {
        loginInto(String.valueOf(new StringBuilder(LOGIN).insert(0, "   ").append("   ")), PASSWORD);
        Assert.assertEquals(driver.findElement(By.xpath("//a/span")).getText(), LOGIN.toUpperCase(Locale.ROOT));
    }

    @Test
    public void loginInvalidLoginTest() {
        String login = String.valueOf(new StringBuilder(LOGIN).insert(1, "abc"));
        loginInto(login, PASSWORD);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-danger fade show']/div")).getText().contains(login));
    }
}
