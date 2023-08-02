package platform;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import platform.listeners.CustomListeners;
import platform.utils.PropertiesUtils;

public class InitTest {

    static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected BrowserContext context;

    protected static String userName = PropertiesUtils.getProperties("test.properties").getProperty("userName");
    protected static String password = PropertiesUtils.getProperties("test.properties").getProperty("password");

    @BeforeSuite
    protected static void launchBrowser(){
        CustomListeners.setAcceptedRate(40);
        playwright = Playwright.create();
        browser= playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(getHeadless()));
    }

    @BeforeMethod
    protected void createContextAndPage(){
        context = browser.newContext();
        page = browser.newPage();
        page.setViewportSize(1920,1080);
    }

    @AfterMethod
    protected void closeContext() {
        page.close();
        context.close();
    }

    @AfterSuite
    protected static void closeBrowser(){
        playwright.close();
    }


    private static boolean getHeadless(){
        return PropertiesUtils.getProperties("test.properties").getProperty("headless").equals("true");
    }
}

