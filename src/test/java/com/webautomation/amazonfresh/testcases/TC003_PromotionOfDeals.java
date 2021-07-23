package com.webautomation.amazonfresh.testcases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.zeroturnaround.zip.ZipUtil;

import com.webautomation.amazonfresh.base.BaseMethods;
import com.webautomation.amazonfresh.library.PropertyReader;

public class TC003_PromotionOfDeals extends BaseMethods {

	private static final String XPATH_STR = "//a[@class='a-link-normal aok-inline-block']/img";

	@Test
	public void getImageFromPromotionsSlideShow() {
		try {
			List<WebElement> slideShowItemsList = driver.findElements(By.xpath(XPATH_STR));
			WebDriver newDriver = new ChromeDriver();
			while (slideShowItemsList.isEmpty()) {
				System.out.println("-------------Items are not present, again trying -------------");
				newDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				newDriver.navigate().to(PropertyReader.configReader("URL"));
				slideShowItemsList = newDriver.findElements(By.xpath(XPATH_STR));
			}
			for (WebElement items : slideShowItemsList) {	
				String imgSRC = items.getAttribute("src");
				LocalDateTime time = LocalDateTime.now();
				String timeStr = time.toString().replace(":", "-").replace("T", "_");
				String imgAltName = items.getAttribute("alt").replace(" ", "_").replace("|", "").replace(",", "_").replace(":", "_");
				
				URL imgURL = new URL(imgSRC);
				BufferedImage imgSave = ImageIO.read(imgURL);
				ImageIO.write(imgSave, "jpg", new File("./SlideShowImages/dealImage_" + timeStr + "_" + imgAltName + ".jpg"));
			}
			ZipUtil.pack(new File("./SlideShowImages/"), new File("./ResultsData/HomePageDealsImages.zip"));
			newDriver.quit();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}
}
