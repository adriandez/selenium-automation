package tests;

import factories.ChromeDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchSessionException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.BasePage;
import utilities.Constants;
import utilities.Key;
import utilities.Mouse;
import utilities.Printer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import static utilities.Util.getUuid;

public class BaseTest implements IRetryAnalyzer {

    private static final Logger printerLogger = Logger.getLogger(BaseTest.class.getName());
    private static final int MAX_RETRY_COUNT = 2;

    protected WebDriver driver;
    protected String aUuid;

    private int retryCount = 0;

    protected Robot robot;
    protected Mouse mouse;
    protected Key enterKey;
    protected Key command;
    protected Key t;

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public BaseTest() throws AWTException {
        this.robot = new Robot();
        this.mouse = new Mouse(robot);
        this.enterKey = new Key(robot, KeyEvent.VK_ENTER);
        this.command = new Key(robot, KeyEvent.VK_META);
        this.t = new Key(robot, KeyEvent.VK_T);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }

    @BeforeSuite
    public void beforeSuite() {
        Printer.printBeforeSuite(printerLogger);
    }

    @BeforeTest
    public void beforeTest(ITestContext context) {
        String execId = getUuid();
        context.setAttribute("ExecID", execId);
        Printer.printContextExecID(execId, printerLogger);
    }

    @BeforeClass
    public void beforeClass() {
        String className = this.getClass().getSimpleName();
        Printer.printClassName(className, printerLogger);
    }

    @BeforeMethod
    public void setup(Method method) {
        try {
            driver = ChromeDriverFactory.initializeChromeBrowser(Constants.WIN11);
            driverThreadLocal.set(driver);
            aUuid = getUuid();
            Printer.printMethodName(method, printerLogger);
            BasePage.logSetup(printerLogger);
        } catch (Exception e) {
            printerLogger.severe("Error in setup: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result, Method method) {
        String methodName = method.getName();
        Printer.printMethodNameTD(methodName, printerLogger);
        closeAndQuit();
        tearDownResult(result);
        BasePage.logTeardown();
    }

    @AfterClass
    public void afterClass() {
        String className = this.getClass().getSimpleName();
        Printer.printAfterClass(className, printerLogger);
    }

    @AfterTest
    public void afterTest(ITestContext context) {
        String execId = (String) context.getAttribute("ExecID");
        Printer.printContextExecID(execId, printerLogger);
    }

    @AfterSuite
    public void afterSuite() {
        Printer.printAfterSuite(printerLogger);
    }

    private void closeAndQuit() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.close();
            } catch (NoSuchSessionException e) {
                printerLogger.warning("Session already closed: " + e.getMessage());
            } catch (Exception e) {
                printerLogger.severe("Error closing WebDriver: " + e.getMessage());
            } finally {
                try {
                    driver.quit();
                } catch (NoSuchSessionException e) {
                    printerLogger.warning("Session already quit: " + e.getMessage());
                } catch (Exception e) {
                    printerLogger.severe("Error quitting WebDriver: " + e.getMessage());
                } finally {
                    driverThreadLocal.remove();
                }
            }
        }
    }

    private void tearDownResult(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.SUCCESS) {
                Printer.printPassed(printerLogger);
            } else if (result.getStatus() == ITestResult.FAILURE) {
                Printer.printFailed(printerLogger);
            } else if (result.getStatus() == ITestResult.SKIP) {
                Printer.printSkipped(printerLogger);
            }
        } catch (Exception e) {
            printerLogger.severe("Error in tearDownResult: " + e.getMessage());
        }
    }

    public static void setTestInfo(String aUuid, String aScenario, String aBrowser, String aPlatform, String pageTitle, String pageUrl, Logger logger) {
        Printer.printTestInfo(aUuid, aScenario, aBrowser, aPlatform, pageTitle, pageUrl, logger);
    }
}
