package flucc;

import java.awt.event.KeyEvent;
import java.util.*;

import com.github.twitch4j.*;

import java.awt.AWTException;
import java.awt.Robot;

/**
 * This class takes commands from the twitch chat and creates a stack of
 * commands to do
 */

public class TwitchCommandSrc {
    public static Queue<Integer> commandQueue = new LinkedList<Integer>();
    public static List<Integer> vote = new ArrayList<Integer>();
    public static List<Integer> randList = new ArrayList<Integer>();

    // puts the commands into the queue depending on different mode eg. Every chat
    // to queue, set interval of time to queue, random interval of time to queue
    public static void commandsToQueue(int command) {
        GUI.toJListEntry(command + "");
        commandQueue.add(command);

    }

    public static void commandsToVote(int command) {

        vote.add(command);

    }

    public static void commandsToRandom(int command) {

        randList.add(command);

    }

    public static void takeMode() {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int x : vote)
            map.put(x, map.getOrDefault(x, 0) + 1);
        int mode = -1;
        int max = 0;

        for (int x : map.keySet()) {
            max = Math.max(map.get(x), max);
            if (map.get(x) == max)
                mode = x;
        }
        System.out.print(mode);
        if (mode != -1)
            commandsToQueue(mode);
        vote.clear();
        map.clear();
    }

    public static void takeRandom() {
        Random rand = new Random();
        if (!randList.isEmpty()) {
            int randNum = rand.nextInt(randList.size() - 1);
            System.out.println("//// \\\\\\" + randList.size());
            commandsToQueue(randList.get(randNum));
            randList.clear();
        }
    }

    public static void executeKeyEvents(int command) throws AWTException {

        Robot robot = new Robot();
        robot.delay(GUI.getCommandDelay());
        robot.keyPress(command);

        robot.delay(10);
        robot.keyRelease(command);
        System.out.println(System.currentTimeMillis());

    }

    public static void executeQueue() throws InterruptedException, AWTException {
        while (GUI.getStartStopState()) {
            if (!commandQueue.isEmpty()) {
                System.out.println(System.currentTimeMillis());
                executeKeyEvents(commandQueue.poll());
            }
            Thread.sleep(10);
        }
    }
}
