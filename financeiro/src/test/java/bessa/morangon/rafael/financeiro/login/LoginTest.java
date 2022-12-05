package bessa.morangon.rafael.financeiro.login;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class LoginTest {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\OneDrive\\Área de Trabalho\\Selenium Driver\\chromedriver.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void login() {
        driver.get("http://localhost:8080/login");
        driver.manage().window().setSize(new Dimension(1274, 744));
        driver.findElement(By.id("exampleInputEmail1")).click();
        driver.findElement(By.id("exampleInputEmail1")).sendKeys("rafael@teste.com");
        driver.findElement(By.id("exampleInputPassword1")).click();
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("abc");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector("p")).click();
        assertThat(driver.findElement(By.cssSelector("p > b")).getText(), is("rafael@teste.com"));
    }

    @Test
    public void login2() {
        driver.get("http://localhost:8080/login");
        driver.manage().window().setSize(new Dimension(1274, 744));
        driver.findElement(By.id("exampleInputEmail1")).click();
        driver.findElement(By.id("exampleInputEmail1")).sendKeys("bessa@email.com");
        driver.findElement(By.id("exampleInputPassword1")).click();
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("abc");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector("p")).click();
        assertThat(driver.findElement(By.cssSelector("p > b")).getText(), is("bessa@email.com"));
    }

    @Test
    public void login3() {
        driver.get("http://localhost:8080/formulario");
        driver.manage().window().setSize(new Dimension(1274, 744));
        driver.findElement(By.cssSelector("h1")).click();
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Bem vindo ao Sistema - Desenvolvido por Rafael Bessa"));
    }

    @Test
    public void login4() {
        driver.get("http://localhost:8080/login");
        driver.manage().window().setSize(new Dimension(1274, 744));
        driver.findElement(By.id("exampleInputEmail1")).click();
        driver.findElement(By.id("exampleInputEmail1")).sendKeys("naoexiste@teste.com");
        driver.findElement(By.id("exampleInputPassword1")).click();
        driver.findElement(By.id("exampleInputPassword1")).sendKeys("abc");
        driver.findElement(By.cssSelector(".btn")).click();
        driver.findElement(By.cssSelector("h1")).click();
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Bem vindo ao Sistema - Desenvolvido por Rafael Bessa"));
    }

}

