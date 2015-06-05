package vanbios;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class ResultParser {


    public static void main(String[] args) throws Exception {

        System.out.println("Type search key and push Enter:");

        Scanner in = new Scanner(System.in);
        String key = in.nextLine();

        System.out.println("Searching...");
        startWebDriver(key);
    }


    private static void startWebDriver(String key) throws Exception {

        WebDriver driver = new FirefoxDriver();

        String url = "https://www.google.com";

        driver.get(url + "/");

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(key + "\n");
        element.submit();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

        List<String> linkPages = new ArrayList<String>();


        for (WebElement webElement : findElements) {
            linkPages.add(webElement.getAttribute("href"));
        }

        Actions act = new Actions(driver);

        act.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).build().perform();


        for (String linkPage: linkPages) {

            driver.get(linkPage);

            System.out.println(driver.getTitle());
        }

        driver.close();
        driver.quit();

        System.out.println("Search completed. Results - " + linkPages.size() + ".");
    }
}
