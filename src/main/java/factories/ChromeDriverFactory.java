package factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ChromeDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(ChromeDriverFactory.class);

    private ChromeDriverFactory() {}

    public static WebDriver initializeChromeBrowser(String platformName) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.managed_default_content_settings.media_stream", 1);
        options.setExperimentalOption("prefs", prefs);

        options.addArguments("allow-http-screen-capture");
        options.addArguments("auto-select-desktop-capture-source=Entire screen");

        if (platformName.equalsIgnoreCase("WIN11") || platformName.equalsIgnoreCase("WINDOWS")) {
            options.setPlatformName("WINDOWS");
        } else if (platformName.equalsIgnoreCase("MAC")) {
            options.setPlatformName("MAC");
        } else {
            logger.error("Unsupported platform: " + platformName);
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().setPosition(new Point(0, 0));

        return driver;
    }
}
