package com.grid;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GridTestClass {
	public WebDriver driver;
	public String URL, Node;

	@Parameters("browser")
	@BeforeTest
	public void launchapp(String browser) throws MalformedURLException {
		String URL = "file:///C:/Application/Offline%20Website/index.html";
		
		if (browser.equalsIgnoreCase("firefox")) {
			System.out.println(" Executing on FireFox");
			String Node = "http://localhost:4445/wd/hub";
			System.setProperty("webdriver.gecko.driver", "geckodriver64bit.exe");
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");

			driver = new RemoteWebDriver(new URL(Node), cap);
			// Puts an Implicit wait, Will wait for 10 seconds before throwing
			// exception
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Launch web site
			driver.navigate().to(URL);
			driver.manage().window().maximize();
		} else if (browser.equalsIgnoreCase("chrome")) {
			try {
				System.out.println(" Executing on CHROME");
				System.setProperty("webriver.chrome.driver", "chromedriver.exe");
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				String Node = "http://localhost:5557/wd/hub";
				driver = new RemoteWebDriver(new URL(Node), cap);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				// Launch website
				driver.navigate().to(URL);
				driver.manage().window().maximize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("The Browser Type is Undefined");
		}
	}

	@Test
	public void testUnameBlankMessage() {

		// Maximize window
		driver.manage().window().maximize();

		// locate email element
		WebElement userEle = driver.findElement(By.id("email"));
		// enter uname into text box
		userEle.sendKeys(" ");

		// locate password element
		WebElement passwordEle = driver.findElement(By.id("password"));
		// enter password into text box
		passwordEle.sendKeys(" ");

		// locate signin button
		WebElement signInbuttonEle = driver.findElement(By.xpath(".//*[@id='form']/div[3]/div/button"));
		// click signin button
		signInbuttonEle.click();

		String unameErrorMessage = driver.findElement(By.id("email_error")).getText();

		Assert.assertEquals("Please enter email.", unameErrorMessage);

	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}
