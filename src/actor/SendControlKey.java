/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyokuto
 */
public class SendControlKey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mouse.mouseClick(Integer.parseInt("100"), Integer.parseInt("100"));
        try {
            Robot robot = new Robot();
            // robot.mouseMove(100, 100);
            // robot.mousePress(1);
            robot.keyPress(KeyEvent.VK_0);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_1);
        } catch (AWTException ex) {
            Logger.getLogger(SendControlKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testSendKey() {
        Mouse.mouseClick(Integer.parseInt("100"), Integer.parseInt("100"));
        try {
            Robot robot = new Robot();
            // robot.mouseMove(100, 100);
            // robot.mousePress(1);
            robot.keyPress(KeyEvent.VK_0);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_1);
        } catch (AWTException ex) {
            Logger.getLogger(SendControlKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendControlKey(String s) {
        try {
            Robot robot = new Robot();
            final int IT = 500;
            switch (s) {
                case "U":
                    robot.keyPress(KeyEvent.VK_UP);
                    //robot.delay(IT);
                    robot.keyRelease(KeyEvent.VK_UP);
                    robot.delay(IT);
                    break;
                case "D":
                    robot.keyPress(KeyEvent.VK_DOWN);
                    // robot.delay(IT);
                    robot.keyRelease(KeyEvent.VK_UP);
                    break;
                case "R":
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    //robot.delay(IT);
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    break;
                case "L":
                    robot.keyPress(KeyEvent.VK_LEFT);
                    //robot.delay(IT);
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    break;
                case "S":
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    //robot.delay(IT);
                    break;
                case "s":
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.delay(IT);
                    break;
                case "C":
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    //robot.delay(IT);
                    break;
                case "c":
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.delay(IT);
                    break;
                case "A":
                    robot.keyPress(KeyEvent.VK_ALT);
                    //robot.delay(IT);
                    break;
                case "a":
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.delay(IT);
                    break;
                default:
                    System.out.println("不正制御文字");
                    break;
            }
        } catch (AWTException ex) {
            Logger.getLogger(SendControlKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
