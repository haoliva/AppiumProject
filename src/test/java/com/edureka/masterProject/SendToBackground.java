package com.edureka.masterProject;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;

public class SendToBackground {
	
	public AndroidDriver<MobileElement> ad = null;
	ApplicationState appState = null;

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
        ad.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @Test(priority = 1) 
    public void login() throws InterruptedException { 
       	ad.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ad.findElementById("com.amazon.mShop.android.shopping:id/sign_in_button").click();
        Thread.sleep(200);
    } 
    
    @Test(priority = 2) 
    public void sendToBackground() throws InterruptedException {
    	System.out.println("App is going to background for a few seconds"); 
    	ad.runAppInBackground(Duration.ofSeconds(10)); 
    } 
    
    @Test(priority = 3) 
    public void bringToFront() throws InterruptedException { 
    	ad.activateApp("com.amazon.mShop.android.shopping"); 
    	Thread.sleep(5000);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText").sendKeys("hernanoliva@outlook.com");
        Thread.sleep(2000);
        ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.widget.Button").click();
        Thread.sleep(2000);
    	appState = ad.queryAppState("com.amazon.mShop.android.shopping"); 
    	System.out.println("App Current State: " + appState); 
    } 
    
    @Test(priority = 4) 
    public void checkState() { 
    	appState = ad.queryAppState("com.amazon.mShop.android.shopping"); 
    	if(appState == ApplicationState.RUNNING_IN_FOREGROUND) 
    		{ 
    			System.out.println("App is in Foreground"); 
    		} 
    } 
    
    @Test(priority = 5) 
    public void terminate() throws InterruptedException { 
    	ad.terminateApp("10204");
    	Thread.sleep(5000);
    	appState = ad.queryAppState("com.amazon.mShop.android.shopping"); 
    	if(appState == ApplicationState.NOT_RUNNING) 
    		{ 
    			System.out.println("App is terminated"); 
    		}
    }

    @AfterClass 
	public void killApp() { 
		ad.quit(); 
	}
}
