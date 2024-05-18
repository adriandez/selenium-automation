package tests;

import org.testng.annotations.Test;
import pages.DemoPage;

public class DemoTest extends BaseTest {

    public DemoTest() throws Exception {
    }

    @Test
    public void demoTest() {
        DemoPage demoPage = new DemoPage(driver);
        demoPage.getGoogle();
    }
}
