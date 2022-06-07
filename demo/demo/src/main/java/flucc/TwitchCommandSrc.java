package flucc;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import com.github.twitch4j.*;

import java.awt.AWTException;
import java.awt.Robot;

/**
 * This class takes commands from the twitch chat and creates a stack of
 * commands to do
 */

public class TwitchCommandSrc {
    public static Queue<Integer> commandQueue = new LinkedList<Integer>();

    // puts the commands into the queue depending on different mode eg. Every chat
    // to queue, set interval of time to queue, random interval of time to queue
    public static void commandsToQueue(int command) {
        System.out.println("12312");
        commandQueue.add(command);

    }

    public static void voteOnIt() {
        int voteInterval = GUI.getCommandInterval();
    }

    public static void executeKeyEvents(int command) throws AWTException {
        System.out.println("executeKeyEvents");
        Robot robot = new Robot();
        robot.keyPress(command);
        robot.delay(10);
        robot.keyRelease(command);

    }

    // THIS IS THE PROBLEM
    public static void executeQueue() throws InterruptedException, AWTException {
        System.out.println("executeQueue");
        while (GUI.onOffStatus()) {
            if (!commandQueue.isEmpty()) {
                executeKeyEvents(commandQueue.remove());
            }
            System.out.println("Relatively valid");
            Thread.sleep(GUI.getCommandDelay());
        }
    }
}
