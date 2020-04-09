package com.edureka.masterProject;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SearchUsingImage {

    public AndroidDriver<MobileElement> ad = null;
    public List<String> ls = null;

    @BeforeClass
    public void def() throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("deviceName","6a52bef7");
        dc.setCapability("platformName", "ANDROID");
        dc.setCapability("appPackage","com.amazon.mShop.android.shopping");
        dc.setCapability("appActivity","com.amazon.mShop.splashscreen.StartupActivity");
        dc.setCapability("noReset",true);
        dc.setCapability("--session-override", "true");
        dc.setCapability("newCommandTimeout", 1500);

        ad = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);

        ls = ad.getSupportedPerformanceDataTypes();
    }

    @Test
    public void login() throws InterruptedException {
    	ad.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ad.findElementById("com.amazon.mShop.android.shopping:id/sign_in_button").click();
        Thread.sleep(4500);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText").sendKeys("hernanoliva@outlook.com");
        Thread.sleep(2000);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.widget.Button").click();
        Thread.sleep(2000);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[7]/android.view.View[2]/android.widget.EditText").sendKeys("An163121!");
        Thread.sleep(2000);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[7]/android.view.View[6]/android.widget.Button").click();
        Thread.sleep(6000);
    }
    
    @Test(dependsOnMethods = "login")
    public void copyImage() throws IOException, InterruptedException {
    	ad.pushFile("/sdcard/Pictures/Screenshots/jacket.jpg",new File("C:\\Users\\Hernan\\Appium\\masterProject\\masterProject\\resources\\jacket.jpg"));
    	Thread.sleep(3000);
    }
    
    
    @Test(dependsOnMethods = "copyImage") 
    public void uploadJpgAndSearch() throws InterruptedException { 
    	ad.findElementByAccessibilityId("scan it").click(); 
    	ad.findElementById("com.amazon.mShop.android.shopping:id/hero_photo_upload_button").click();
    	Thread.sleep(6000);
//    	ad.findElementByXPath( ".//android.support.v7.widget.RecyclerView[@resource-id='com.android.documentsui:id/dir_list']/android.widget.LinearLayout[@index='0']") .click();
    	ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.GridView/android.widget.FrameLayout[1]/android.widget.ImageView[1]").click();
    }
    
    @Test(dependsOnMethods = "uploadJpgAndSearch")
    public void verifySearchResult() throws InterruptedException { 
    	Thread.sleep(2500);
    	ad.findElementById("com.amazon.mShop.android.shopping:id/londonCallingRecyclerView").click(); 
    	if (ad.findElementById("com.amazon.mShop.android.shopping:id/style_snap_result_pager").isDisplayed())
    		{
    			System.out.println("Some products have been found using an image!");
    		}
    	else
    		{
    			System.out.println("No products have been found using an image!");
    		};
    	
    	Thread.sleep(3500);
    	ad.navigate().back();
    	Thread.sleep(1500);
    	ad.navigate().back();
    	Thread.sleep(1500);
    	ad.navigate().back();
    	Thread.sleep(1500);
    }
    

	@Test (dependsOnMethods = "verifySearchResult")
	public void logout() throws InterruptedException {
		Thread.sleep(3500);
		ad.findElementById("com.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon").click();
		Thread.sleep(1500);
		verticalSwipeByPercentages(0.8, 0.2, 0.5);
		ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.support.v4.view.ViewPager/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[10]").click();
		Thread.sleep(1000);
		ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.support.v4.view.ViewPager/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[6]").click();
	    Thread.sleep(1500);
	    String ConfirmSignOut = ad.findElementById("android:id/alertTitle") .getText();
        assertEquals(ConfirmSignOut, "Confirm Sign Out");
	    ad.findElementById("android:id/button2").click();
		Thread.sleep(1500);
	}
	
    @AfterClass
    public void closeApp() {
        ad.quit();
    }
    
  //Vertical Swipe by percentages
    @SuppressWarnings({ "rawtypes", "static-access" })
	public void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = ad.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        
//        System.out.println("size:"+size);
//        System.out.println("anchor:"+anchor);
//        System.out.println("startPoint:"+startPoint);
//       System.out.println("endPoint:"+endPoint);
 
        new TouchAction(ad)
                .press(new PointOption().point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3L)))
                .moveTo(new PointOption().point(anchor, endPoint))
                .release().perform();
    }
    
}

