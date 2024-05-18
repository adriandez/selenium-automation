package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.DemoTest;
import utilities.Constants;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static utilities.Constants.COMPANY;
import static utilities.Util.*;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public static Logger logger = Logger.getLogger(BasePage.class.getName());
    public static Logger loggerLoginPage = Logger.getLogger(DemoTest.class.getName());
    public static FileHandler fileHandler;
    public static String date;

    public static void logSetup(Logger testLogger) {
        date = getDateLog();
        try {
            logger.setLevel(Level.SEVERE);
            loggerLoginPage.setLevel(Level.SEVERE);
            testLogger.setLevel(Level.SEVERE);
            fileHandler = new FileHandler("logs/Test" + date + ".log");
            logger.addHandler(fileHandler);
            loggerLoginPage.addHandler(fileHandler);
            testLogger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            logger.warning("FileHandler creation failed: " + e.getMessage());
        }
        logger.info("Log started");
    }

    public static void logTeardown() {
        logger.info("Log ended");
        if (fileHandler != null) {
            fileHandler.close();
            fileHandler = null;  // Reset to ensure no double-close issues
        } else {
            logger.warning("FileHandler is null, cannot close.");
        }
    }

    public void close() {
        driver.close();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public ArrayList<String> getPageWindowHandles() {
        return new ArrayList<>(driver.getWindowHandles());
    }

    public void goToTab(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    public void waitForVisibilityAndClickableOfWebElement(WebElement element, int duration) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitVisibilityOfWebElement(WebElement element, int duration) {
        new WebDriverWait(driver, Duration.ofSeconds(duration))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitVisibilityOfWebElement(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void longWaitVisibilityOfWebElement(WebElement element) {
        logAction("longWaitVisibilityOfWebElement", element);
        new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_VERY_LONG_SEC))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void longWaitInvisibilityOfWebElement(WebElement element) {
        logAction("longWaitInvisibilityOfElement", element);
        new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_VERY_LONG_SEC))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitVisibilityOfList(List<WebElement> elements, int duration) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(duration))
                    .until(ExpectedConditions.visibilityOfAllElements(elements));
        } catch (Exception error) {
            handleError(error, "Waiting Visibility of list error");
            sleepForHalfASecond();
            new WebDriverWait(driver, Duration.ofSeconds(duration))
                    .until(ExpectedConditions.visibilityOfAllElements(elements));
        }
    }

    public void waitVisibilityOfAllWebElementsIn(WebElement element) {
        logAction("waitVisibilityOfAllWebElementsIn", element);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_LONG_SEC))
                    .until(ExpectedConditions.visibilityOfAllElements(element));
        } catch (Exception e) {
            handleError(e, "Waiting Visibility of all WebElement in parent");
            sleepForHalfASecond();
            new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_LONG_SEC))
                    .until(ExpectedConditions.visibilityOfAllElements(element));
        }
    }

    public void waitInvisibilityOfWebElement(WebElement element, int duration) {
        new WebDriverWait(driver, Duration.ofSeconds(duration))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitInvisibilityOfWebElement(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForNumberOfWindowsToBe(int currentTabs, int sec) {
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.numberOfWindowsToBe(currentTabs + 1));
    }

    public void waitForVisibilityOfAllElementsBySelector(String selector, int sec) {
        logAction("waitForVisibilityOfAllElementsBySelector", selector);
        new WebDriverWait(driver, Duration.ofSeconds(sec))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(selector)));
    }

    public void goToDefaultContent() {
        System.out.println("goToDefaultContent");
        driver.switchTo().defaultContent();
    }

    public void clickWebElement(WebElement element) {
        try {
            waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
            logAction("Clicking", element);
            element.click();
        } catch (Exception e) {
            retryAction(e, "Clicking", element);
        }
    }

    public void checkWebElement(WebElement element) {
        logAction("checkWebElement", element);
        waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
        element.click();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC))
                    .until(ExpectedConditions.attributeContains(element, "class", "checked"));
        } catch (Exception e) {
            retryAction(e, "checkWebElement second try", element);
        }
    }

    public void setWebElement(WebElement element, String answer) {
        try {
            logAction("setWebElement", element, answer);
            waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
            element.click();
            element.sendKeys(answer);
        } catch (Exception e) {
            retryAction(e, "setWebElement second try", element, answer);
        }
    }

    public void setWebElement(WebElement element, String answer, Keys enter) {
        try {
            logAction("setWebElement", element, answer);
            waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
            element.click();
            element.sendKeys(answer + enter);
        } catch (Exception e) {
            retryAction(e, "setWebElement second try", element, answer + enter);
        }
    }

    public void setWebElement(WebElement element, String answer, boolean clear) {
        try {
            logAction("setWebElement", element, answer);
            waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
            element.click();
            if (clear) {
                element.clear();
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            }
            element.sendKeys(answer);
        } catch (Exception error) {
            retryAction(error, "setWebElement second try", element, answer);
        }
    }

    public void actionClickWebElement(WebElement element) {
        try {
            Actions action = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC));
            action.moveToElement(element).perform();
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logAction("Action clicking", element);
            action.moveToElement(element).click().perform();
        } catch (Exception e) {
            retryAction(e, "Action clicking", element);
        }
    }

    public void actionClickSus(WebElement element) {
        logAction("actionClickSus", element);
        Actions action = new Actions(driver);
        action.scrollByAmount(0, 10).perform();
        action.moveToElement(element).click().perform();
    }

    public void scrollToBottom(int deltaY) {
        logAction("scrollDown", String.valueOf(deltaY));
        Actions action = new Actions(driver);
        action.scrollByAmount(0, deltaY).build().perform();
    }

    public void scrollToTop(int deltaY) {
        logAction("scrollUp", String.valueOf(deltaY));
        Actions action = new Actions(driver);
        action.scrollByAmount(0, deltaY * -1).build().perform();
    }

    public void actionDragAndDrop(WebElement draggableElement, WebElement droppableElement) {
        logAction("actionDragAndDrop", draggableElement, String.valueOf(droppableElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(draggableElement).clickAndHold().moveByOffset(0, 200).build().perform();
        waitVisibilityOfWebElement(droppableElement, Constants.DEFAULT_SEC);
        actions.release(droppableElement).build().perform();
    }

    public void sleepForHalfASecond() {
        sleepFor(500);
    }

    public void sleepForASecond() {
        sleepFor(1000);
    }

    public void sleepForTwoSeconds() {
        sleepFor(2000);
    }

    public void sleepForSixSeconds() {
        sleepFor(6000);
    }

    public void sleepFor(int milliseconds) {
        logAction("sleepFor", String.valueOf(milliseconds));
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sleepFor(String text) {
        int milliseconds = getSpeechTime(text);
        sleepFor(milliseconds);
    }

    public void sleepFor(String text, int extraTime) {
        int milliseconds = getSpeechTime(text, extraTime);
        sleepFor(milliseconds);
    }

    public void goToIframeWebElement(WebElement element) {
        logAction("goToIframeWebElement", element);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*")));
        } catch (Exception e) {
            retryAction(e, "goToIframeWebElement second try", element);
        }
    }

    public void waitUrlToBe(String url) {
        waitUrlToBe(url, Constants.DEFAULT_SEC);
    }

    public void waitUrlToBe(String url, int seconds) {
        try {
            logAction("waitUrlToBe", url);
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.urlContains(url));
        } catch (Exception e) {
            retryAction(e, "waitUrlToBe", url, seconds);
        }
    }

    public void waitTextToBeInWebElement(WebElement element, String text) {
        waitTextToBeInWebElement(element, text, Constants.DEFAULT_SEC);
    }

    public void waitTextToBeInWebElement(WebElement element, String text, int seconds) {
        try {
            logAction("waitTextToBeInWebElement", element, text);
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception error) {
            retryAction(error, "waitTextToBeInWebElement second try", element, text, seconds);
        }
    }

    public void moveToWebElement(WebElement element) {
        logAction("moveToWebElement", element);
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void actionScrollToWebElement(WebElement element) {
        logAction("actionScrollToWebElement", element);
        Actions action = new Actions(driver);
        action.scrollToElement(element).perform();
    }

    public void deleteNoProdFromDOM() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(By.id("noProd"));
        logAction("deleteNoProdFromDOM", element);
        js.executeScript("arguments[0].remove();", element);
    }

    public String getWebElementAttribute(WebElement element, String attributeName) {
        logAction("getWebElementAttribute", element);
        return element.getAttribute(attributeName);
    }

    public void waitPresenceOfAllElementsLocatedBySelector(String selector) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_SEC));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
    }

    public void jsScrollToTop() {
        System.out.println("jsScrollToTop");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTo({top:90, left:100, behavior: 'smooth'});");
    }

    public void jsScrollToWebElement(WebElement target) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", target);
    }

    private void logAction(String action, WebElement element) {
        logger.finer(COMPANY + action + ": " + cleanAndSplitString(String.valueOf(element)));
        System.out.println(COMPANY + action + ": " + cleanAndSplitString(String.valueOf(element)));
    }

    private void logAction(String action, String detail) {
        logger.finer(COMPANY + action + ": " + detail);
        System.out.println(COMPANY + action + ": " + detail);
    }

    private void logAction(String action, WebElement element, String detail) {
        logger.finer(COMPANY + action + ": " + cleanAndSplitString(String.valueOf(element)) + " " + detail);
        System.out.println(COMPANY + action + ": " + cleanAndSplitString(String.valueOf(element)) + " " + detail);
    }

    private void handleError(Exception e, String errorMsg) {
        logger.severe(errorMsg + ": " + e.getMessage());
        e.printStackTrace();
    }

    private void retryAction(Exception e, String action, WebElement element) {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                handleError(e, action);
                logAction(action, element);
                waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
                element.click();
                return; // exit the method if successful
            } catch (Exception retryException) {
                handleError(retryException, "Retry " + action);
                sleepForHalfASecond();
                retryCount--;
            }
        }
        // If all retries fail, throw the exception
        throw new RuntimeException("Failed to perform action after retries: " + action, e);
    }

    private void retryAction(Exception e, String action, WebElement element, String detail) {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                handleError(e, action);
                logAction(action, element, detail);
                waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
                element.click();
                element.sendKeys(detail);
                return; // exit the method if successful
            } catch (Exception retryException) {
                handleError(retryException, "Retry " + action);
                sleepForHalfASecond();
                retryCount--;
            }
        }
        // If all retries fail, throw the exception
        throw new RuntimeException("Failed to perform action after retries: " + action, e);
    }

    private void retryAction(Exception e, String action, String detail, int seconds) {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                handleError(e, action);
                logAction(action, detail);
                new WebDriverWait(driver, Duration.ofSeconds(seconds))
                        .until(ExpectedConditions.urlContains(detail));
                return; // exit the method if successful
            } catch (Exception retryException) {
                handleError(retryException, "Retry " + action);
                sleepForHalfASecond();
                retryCount--;
            }
        }
        // If all retries fail, throw the exception
        throw new RuntimeException("Failed to perform action after retries: " + action, e);
    }

    private void retryAction(Exception e, String action, WebElement element, String detail, int seconds) {
        int retryCount = 3;
        while (retryCount > 0) {
            try {
                handleError(e, action);
                logAction(action, element, detail);
                waitForVisibilityAndClickableOfWebElement(element, Constants.DEFAULT_SEC);
                element.click();
                element.sendKeys(detail);
                new WebDriverWait(driver, Duration.ofSeconds(seconds))
                        .until(ExpectedConditions.textToBePresentInElement(element, detail));
                return; // exit the method if successful
            } catch (Exception retryException) {
                handleError(retryException, "Retry " + action);
                sleepForHalfASecond();
                retryCount--;
            }
        }
        // If all retries fail, throw the exception
        throw new RuntimeException("Failed to perform action after retries: " + action, e);
    }
}
