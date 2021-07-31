package com.webautomation.amazonfresh.testcases;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
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
			String dateStr= "";
			List<WebElement> slideShowItemsList = getDriver().findElements(By.xpath(XPATH_STR));
			WebDriver driverPromotion = new ChromeDriver();
			while (slideShowItemsList.isEmpty()) {
				System.out.println("-------------Items are not present, again trying -------------");
				driverPromotion.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driverPromotion.navigate().to(PropertyReader.configReader("URL"));
				slideShowItemsList = driverPromotion.findElements(By.xpath(XPATH_STR));
			}
			for (WebElement items : slideShowItemsList) {	
				String imgSRC = items.getAttribute("src");
				LocalDate date = LocalDate.now();
				dateStr = date.toString().replace(":", "-").replace("T", "_");
				new File("./SlideShowImages/" + dateStr).mkdir();
				
				String imgAltName = items.getAttribute("alt").replace(" ", "_").replace("|", "").replace(",", "_").replace(":", "_");
				
				URL imgURL = new URL(imgSRC);
				BufferedImage imgSave = ImageIO.read(imgURL);
				ImageIO.write(imgSave, "jpg", new File("./SlideShowImages/" + dateStr + "/" + imgAltName + ".jpg"));
			}
			ZipUtil.pack(new File("./SlideShowImages/"), new File("./ResultsData/HomePageDealsImages.zip"));
			driverPromotion.quit();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------- Exception occurred -------" + e);
		}
	}
}
