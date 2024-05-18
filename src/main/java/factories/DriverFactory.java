package factories;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.Constants;
import utilities.Printer;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private DriverFactory() {}

    public static WebDriver initializeBrowser(String browserName, String platformName, String url) throws MalformedURLException {

        DesiredCapabilities dc = new DesiredCapabilities();

        try {
            switch (browserName) {
                case Constants.CHROME:
                    dc.setBrowserName(Constants.CHROME);
                    setPlatform(dc, platformName);
                    break;
                case Constants.FIREFOX:
                    dc.setBrowserName(Constants.FIREFOX);
                    setPlatform(dc, platformName);
                    break;
                case Constants.MICROSOFT_EDGE:
                    dc.setBrowserName(Constants.MICROSOFT_EDGE);
                    setPlatform(dc, platformName);
                    break;
                case Constants.INTERNET_EXPLORER:
                    dc.setBrowserName(Constants.INTERNET_EXPLORER);
                    break;
                case Constants.OPERA:
                    dc.setBrowserName(Constants.OPERA);
                    setPlatform(dc, platformName);
                    break;
                case Constants.SAFARI:
                    if (platformName.equals(Constants.MAC)) {
                        dc.setBrowserName(Constants.SAFARI);
                        dc.setPlatform(Platform.MAC);
                    } else {
                        Printer.printThisOut(Constants.DRIVER_FACTORY_ERROR);
                    }
                    break;
                default:
                    Printer.printThisOut(Constants.DRIVER_FACTORY_ERROR);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error setting browser or platform name capability", e);
        }

        WebDriver driver = new RemoteWebDriver(new URL(url), dc);

        Dimension dimension = new Dimension(1280, 720);
        driver.manage().window().setSize(dimension);

        Point point = new Point(0, 0);
        driver.manage().window().setPosition(point);

        driver.get(url);

        return driver;
    }

    private static void setPlatform(DesiredCapabilities dc, String platformName) {
        switch (platformName) {
            case Constants.WIN10:
                dc.setPlatform(Platform.WIN10);
                break;
            case Constants.MAC:
                dc.setPlatform(Platform.MAC);
                break;
            default:
                Printer.printThisOut(Constants.DRIVER_FACTORY_ERROR);
                break;
        }
    }
}
