package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DemoPage extends DemoAbstractPage {
    public DemoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void getGoogle(){
        String url = "https://www.google.com/";
        driver.get(url);
        waitUrlToBe(url);
    }
}
