package flucc;

/**
 * Hello world!
 *
 */
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.util.TimeUtils;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class App {
    // TODO: Parse config file to commands, Make each different mode for sampling
    // from chat
    // Future Plans: Add Press Length To Commands
    final static Integer[] VALID_COMMANDS = {
            3, 8, 9, 10, 12, 16, 17, 18, 19, 20, 21, 24, 25, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 59, 61, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76,
            77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 96, 97, 98, 99, 100, 101, 102, 103, 104,
            105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 127, 128,
            129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 150, 151, 152, 153,
            154, 155, 156, 157, 160, 161, 162, 192, 222, 224, 225, 226, 227, 240, 241, 242, 243, 244, 245, 256, 257,
            258, 259, 260, 261, 262, 263, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 61440,
            61441, 61442, 61443, 61444, 61445, 61446, 61447, 61448, 61449, 61450, 61451, 65312, 65368, 65406, 65480,
            65481, 65482, 65483, 65485, 65487, 65488, 65489
    };
    public static Random rand = new Random();
    private static Map<String, Integer> configMap = new HashMap<String, Integer>();

    public static void startBot() throws InterruptedException, AWTException {

        OAuth2Credential credential = new OAuth2Credential("twitch", "oauth:uvmb32l8q6iyr2f1lpbk4df6sowipz");
        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(credential)
                .build();

        twitchClient.getChat().joinChannel(GUI.getTwitchUser());

        configMap = HashMapFromTextFile();
        int mode = GUI.getMode();

        if (mode == 0)
            twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
                System.out.println(event.getMessage());
                handle_message(event);

            });
        if (mode == 1) {
            twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
                handle_message(event);
                try {
                    Thread.sleep(GUI.getMinInterval() + 1);
                    Thread.sleep(rand.nextInt(GUI.getMaxInterval() - GUI.getMinInterval() + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

        }
        if (mode == 2) {
            twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
                handle_message(event);
            });
            Thread.sleep(GUI.getCommandInterval());
        }
        TwitchCommandSrc.executeQueue();
    }

    private static void handle_message(ChannelMessageEvent e) {
        if (valid(e.getMessage())) {
            System.out.println("handle_message");
            TwitchCommandSrc.commandsToQueue(toCommand(e.getMessage()));
        }
    }

    public static int toCommand(String str) {
        System.out.println((int) configMap.get(str));
        return (int) configMap.get(str);
    }

    public static Map<String, Integer> HashMapFromTextFile() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        BufferedReader br = null;

        try {
            File file = new File("demo/demo/src/main/java/flucc/config.txt");

            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0].trim();
                String number = parts[1].trim();
                if (!name.equals("") && !number.equals(""))
                    map.put(name, Integer.valueOf(number));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }
        return map;
    }

    private static boolean valid(String message) {
        for (int x : VALID_COMMANDS) {
            if (x == toCommand(message)) {
                System.out.println("valid");
                return true;
            }
        }
        return false;
    }
}
