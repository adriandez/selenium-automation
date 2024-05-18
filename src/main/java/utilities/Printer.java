package utilities;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Printer extends Util {

    public static void printBeforeSuite(Logger logger) {
        String msg1 = Constants.COMPANY_EQUAL_SIGN_LINE;
        String msg2 = Constants.COMPANY_AUTO_TEST_SUITE + Util.getDate();
        String message = msg1 + "\n" + msg2;
        logAndPrint(logger, Level.FINEST, message);
    }

    public static void printContextExecID(String execId, Logger logger) {
        String message = Constants.COMPANY + execId;
        logAndPrint(logger, Level.FINEST, message);
    }

    public static void printClassName(String className, Logger logger) {
        String message = Constants.COMPANY_STARTING + className;
        logAndPrint(logger, Level.FINEST, message, Constants.COMPANY_EQUAL_SIGN_LINE, Constants.COMPANY);
    }

    public static void printMethodName(Method method, Logger logger) {
        String methodName = method.getName();
        String message = Constants.COMPANY_RUNNING + methodName;
        logAndPrint(logger, Level.FINEST, message, Constants.COMPANY_EQUAL_SIGN_LINE, Constants.COMPANY);
    }

    public static void printTestInfo(String uuid, String scenario, String browser, String platform, String pageTitle, String pageUrl, Logger logger) {
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_BROWSER + browser);
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_PLATFORM + platform);
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_TEST_ID + uuid);
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_SCENARIO + scenario);
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_PAGE_TILE + pageTitle);
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_PAGE_URL + pageUrl);

        System.out.println(Constants.COMPANY_PLUS_SIGN_LINE);
        System.out.println(Constants.COMPANY_BROWSER + browser);
        System.out.println(Constants.COMPANY_PLATFORM + platform);
        System.out.println(Constants.COMPANY_TEST_ID + uuid);
        System.out.println(Constants.COMPANY_SCENARIO + scenario);
        System.out.println(Constants.COMPANY_PAGE_TILE + pageTitle);
        System.out.println(Constants.COMPANY_PAGE_URL + pageUrl);
        System.out.println(Constants.COMPANY_PLUS_SIGN_LINE);
    }

    public static void printAfterSuite(Logger logger) {
        String message = Constants.COMPANY_AUTO_TEST_SUITE + getDate();
        logAndPrint(logger, Level.FINEST, message, Constants.COMPANY_EQUAL_SIGN_LINE);
    }

    public static void printAfterClass(String className, Logger logger) {
        String message = Constants.COMPANY_FINISHED + className;
        logAndPrint(logger, Level.FINEST, message, Constants.COMPANY_EQUAL_SIGN_LINE);
    }

    public static void printMethodNameTD(String methodName, Logger logger) {
        String message = Constants.COMPANY_TEARDOWN + methodName;
        logAndPrint(logger, Level.FINEST, message, Constants.COMPANY_MINUS_SIGN_LINE, Constants.COMPANY);
    }

    public static void printPassed(Logger logger) {
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_RESULT_PASSED, Constants.COMPANY_ASTERISK_SIGN_LINE, Constants.COMPANY);
    }

    public static void printFailed(Logger logger) {
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_RESULT_FAILED, Constants.COMPANY_ASTERISK_SIGN_LINE, Constants.COMPANY);
    }

    public static void printSkipped(Logger logger) {
        logAndPrint(logger, Level.FINEST, Constants.COMPANY_RESULT_SKIPPED, Constants.COMPANY_ASTERISK_SIGN_LINE, Constants.COMPANY);
    }

    public static void printThisOut(String string) {
        System.out.println(string);
    }

    private static void logAndPrint(Logger logger, Level level, String... messages) {
        for (String message : messages) {
            logger.log(level, message);
            System.out.println(message);
        }
    }
}
