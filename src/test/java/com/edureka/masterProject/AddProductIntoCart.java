package com.edureka.masterProject;

import static org.testng.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AddProductIntoCart {

	//public AndroidDriver<AndroidElement> ad;
    public AndroidDriver<MobileElement> ad = null;
    public List<String> ls = null;

    @BeforeClass
    public void def() throws MalformedURLException {
        DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability("deviceName","9889d5383156535952");
        dc.setCapability("deviceName","6a52bef7");
        dc.setCapability("platformName", "ANDROID");
        dc.setCapability("appPackage","com.amazon.mShop.android.shopping");
        dc.setCapability("appActivity","com.amazon.mShop.splashscreen.StartupActivity");
        dc.setCapability("noReset",true);
        dc.setCapability("--session-override", "true");

        ad = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);

        ls = ad.getSupportedPerformanceDataTypes();
    }

    @Test
    public void login() throws InterruptedException {
        ad.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ad.findElementById("com.amazon.mShop.android.shopping:id/sign_in_button").click();
        Thread.sleep(4000);
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
    public void search() throws InterruptedException {
//        ad.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text").click();
        ad.findElementByXPath(".//android.widget.EditText[@text='What are you looking for?']").click();
        Thread.sleep(2000);
        ad.findElementByXPath(".//android.widget.EditText[@text='What are you looking for?']").sendKeys("samsung charger s8 plus");
//        ad.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text").sendKeys("samsung charger s8 plus");
        Thread.sleep(1000);
        ad.findElementByXPath(".//android.widget.TextView[@text='samsung charger s8 plus']").click();
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "search")
    public void selectFirstDisplayedProduct() throws InterruptedException {
        ad.findElementByXPath(".//android.view.View[contains(@text,'Samsung 15W Fast Charge 2.0 Wireless Charger Stand - Black')]").click(); 
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = "selectFirstDisplayedProduct")
    public void zoomImage() throws InterruptedException {
        Thread.sleep(1500);
        ad.findElementByXPath(".//android.view.View[@resource-id='image-block-row']").click();
        Thread.sleep(1500);
        zoomIn();
        Thread.sleep(1500);
        ad.navigate().back();
//        ad.findElementById("com.myntra.android:id/btn_pdp_fs_back").click();
    }

    @Test(dependsOnMethods = "zoomImage")
    public void addProductToBag() throws InterruptedException {
        ad.findElementByXPath(".//android.view.View[contains(@text,'Samsung 15W Fast Charge 2.0 Wireless Charger Stand - Black')]").click(); 
        Thread.sleep(2000);
        verticalSwipeByPercentages(0.95, 0.2, 0.5);
        Thread.sleep(1000);
    	ad.findElementByXPath(".//android.widget.Button[@resource-id='add-to-cart-button']").click();
    	Thread.sleep(3000);
        String cartCount = ad.findElementById("com.amazon.mShop.android.shopping:id/chrome_action_bar_cart_count").getText();
        assertEquals(cartCount, "1");
        Thread.sleep(4000);
    }

    @Test(dependsOnMethods = "addProductToBag")
    public void checkout() throws InterruptedException {
    	ad.findElementById("com.amazon.mShop.android.shopping:id/chrome_action_bar_cart_count").click();
    	Thread.sleep(1500);
        ad.findElementByXPath(".//android.widget.Button[contains(@text,'Proceed to checkout')]").click();
        Thread.sleep(3000);
//        String cartCount = ad.findElementById("com.amazon.mShop.android.shopping:id/chrome_action_bar_cart_count") .getText();
        String shippingPage = ad.findElementByXPath(".//android.view.View[contains(@text,'Enter a shipping address')]").getText();
        assertEquals(shippingPage,"Enter a shipping address");
        ad.navigate().back();
    }

    @Test(dependsOnMethods = "checkout")
    public void performance() throws InterruptedException {
        Thread.sleep(5000);
//        List<String> ls = ad.getSupportedPerformanceDataTypes();
        System.out.println(ls+"\n\n");
        Iterator<String> it = ls.iterator();
        while(it.hasNext()){
            String perfData = it.next();
            System.out.println(perfData);
            List<List<Object>> lo =
                    ad.getPerformanceData("com.amazon.mShop.android.shopping",perfData,5);
            System.out.println(lo+"\n\n");
        }
    }

    @Test (dependsOnMethods = "performance")
	public void logout() throws InterruptedException {
		Thread.sleep(2000);
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
/*        
        System.out.println("size:"+size);
        System.out.println("anchor:"+anchor);
        System.out.println("startPoint:"+startPoint);
        System.out.println("endPoint:"+endPoint);
*/ 
        new TouchAction(ad)
                .press(new PointOption().point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3L)))
                .moveTo(new PointOption().point(anchor, endPoint))
                .release().perform();
    }

    @SuppressWarnings("rawtypes")
	public void longPress(MobileElement element) {
        Dimension size = element.getSize();
        int starty = (int) (size.height * 0.85);
        int endy = (int) (size.height * 0.45);
        int width = size.width / 2;
        new TouchAction(ad)
                .longPress(new PointOption().withCoordinates(width, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3L)))
                .moveTo(new PointOption().withCoordinates(width, endy))
                .release()
                .perform();
    }

    public void zoomIn() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
        Dimension size = ad.manage().window().getSize();
        Point source = new Point(size.getWidth(), size.getHeight());
        Sequence pinchAndZoom1 = new Sequence(finger, 0);
        pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), source.x / 2, source.y / 2));
        pinchAndZoom1.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        pinchAndZoom1.addAction(new Pause(finger, Duration.ofMillis(100)));
        pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), source.x / 3, source.y / 3));
        pinchAndZoom1.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence pinchAndZoom2 = new Sequence(finger2, 0);
        pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), source.x / 2, source.y / 2));
        pinchAndZoom2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg())); pinchAndZoom2.addAction(new Pause(finger, Duration.ofMillis(100)));
        pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), source.x * 3 / 4, source.y * 3 / 4));
        pinchAndZoom2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ad.perform(Arrays.asList(pinchAndZoom1, pinchAndZoom2));
    }
}

