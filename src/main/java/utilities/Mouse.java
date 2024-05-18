package utilities;

import java.awt.*;
import java.awt.event.InputEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for controlling the mouse using the Robot class.
 */
public class Mouse {
    private static final Logger logger = LoggerFactory.getLogger(Mouse.class);
    private final Robot robot;

    /**
     * Constructs a Mouse object with the specified Robot instance.
     *
     * @param robot the Robot instance used to control the mouse
     * @throws AWTException if the platform configuration does not allow low-level input control
     */
    public Mouse(Robot robot) throws AWTException {
        this.robot = robot;
    }

    /**
     * Moves the mouse pointer to the specified screen coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public void move(int x, int y) {
        robot.mouseMove(x, y);
    }

    /**
     * Simulates a mouse click at the current mouse pointer location.
     */
    public void click() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * Main method for testing the Mouse class functionality.
     */
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            Mouse mouse = new Mouse(robot);
            mouse.move(100, 100);  // Example usage: Move mouse to coordinates (100, 100)
            mouse.click();          // Example usage: Perform a mouse click
        } catch (AWTException e) {
            logger.error("Error initializing Robot: ", e);
        }
    }
}
