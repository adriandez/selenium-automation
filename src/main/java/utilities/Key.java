package utilities;

import java.awt.AWTException;
import java.awt.Robot;

/**
 * Utility class for simulating keyboard actions using the Robot class.
 */
public class Key {
    private final Robot robot;
    private final int keyCode;

    /**
     * Constructor for creating a Key object.
     *
     * @param robot   the Robot instance used to simulate key events
     * @param keyCode the key code of the key to be simulated
     * @throws AWTException if the platform configuration does not allow low-level input control
     */
    public Key(Robot robot, int keyCode) {
        this.robot = robot;
        this.keyCode = keyCode;
    }

    /**
     * Simulates a key click (press and release).
     */
    public void click() {
        try {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid key code: " + keyCode);
        }
    }

    /**
     * Simulates pressing a key.
     */
    public void press() {
        try {
            robot.keyPress(keyCode);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid key code: " + keyCode);
        }
    }

    /**
     * Simulates releasing a key.
     */
    public void release() {
        try {
            robot.keyRelease(keyCode);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid key code: " + keyCode);
        }
    }
}
