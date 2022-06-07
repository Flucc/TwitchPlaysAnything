package flucc;

import java.awt.AWTException;
//Generated by GuiGenie - Copyright (c) 2004 Mario Awad.
//Home Page http://guigenie.cjb.net - Check often for new versions!
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import org.intellij.lang.annotations.JdkConstants.FlowLayoutAlignment;

public class GUI extends JPanel {
    private static String selectedWindow;
    private static String selectedMode;
    private JLabel twitchUserLabel;
    private static JToggleButton onOff;
    private static JTextField twitchUser;
    private JComboBox<String> chatMode;
    private JLabel chatModeLabel;
    private JLabel minIntervalLabel;
    private static JFormattedTextField minInterval;
    private JLabel maxIntervalLabel;
    private static JFormattedTextField maxInterval;
    private JLabel commandDelayLabel;
    private static JFormattedTextField commandDelay;
    private JLabel setInterval;
    private static JFormattedTextField commandInterval;
    private JComboBox<String> jcomp14;
    private JLabel windowExecLabel;
    private JLabel cmdConfigLabel;
    private static JFileChooser cfgFileSelect;
    private JCheckBox disableMouse;
    static boolean onOffStatus;

    public GUI() {
        // Number formatter for interval text fields
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        // construct preComponents
        String[] chatModeItems = { "Everybody Matters!", "Random Interval", "Set Interval", "Vote On It!" };
        // List<?> allWindows = WindowUtils.getAllWindows(true);
        String[] jcomp14Items = { "1" };
        // System.out.print(allWindows.toString());
        // selectedWindow = (String)jcomp14.getSelectedItem();
        // selectedMode = (String)chatMode.getSelectedItem();
        // construct components
        twitchUserLabel = new JLabel("Twitch Username:");
        onOff = new JToggleButton("ON/OFF", false);
        twitchUser = new JTextField(5);
        chatMode = new JComboBox<String>(chatModeItems);
        chatModeLabel = new JLabel("Command Mode: ");
        minIntervalLabel = new JLabel("Minimum Interval (ms):");
        minInterval = new JFormattedTextField(formatter);
        maxIntervalLabel = new JLabel("Maximum Interval (ms):");
        maxInterval = new JFormattedTextField(formatter);
        commandDelayLabel = new JLabel("Command Delay (ms):");
        commandDelay = new JFormattedTextField(formatter);
        setInterval = new JLabel("Command Interval(ms):");
        commandInterval = new JFormattedTextField(formatter);
        jcomp14 = new JComboBox<String>(jcomp14Items);
        windowExecLabel = new JLabel("Command Window:");
        cmdConfigLabel = new JLabel("Command Config:");
        cfgFileSelect = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("TXT files", "txt");
        cfgFileSelect.setFileFilter(filter);
        disableMouse = new JCheckBox("Disable Mouse?");

        cfgFileSelect.setCurrentDirectory(new File(System.getProperty("user.home")));

        // set components properties
        twitchUser.setToolTipText("Use the username of the chat you would like to listen to");
        chatMode.setToolTipText(
                "Everyone Matters: Everyone who types a valid command in the chat has their command executed \n Random Interval: Picks a single valid command at a random interval between two seperate delays \n Set Interval: Picks a single command at an interval set by you \n Vote On It: Takes the majority vote from the last designated interval");
        minIntervalLabel
                .setToolTipText("The minimum time that must pass before someone elses command is added to the queue");
        minInterval
                .setToolTipText("The minimum time that must pass before someone elses command is added to the queue");
        maxIntervalLabel
                .setToolTipText("The minimum time that must pass before someone elses command is added to the queue");
        maxInterval
                .setToolTipText("The maximum time that must pass before someone elses command is added to the queue");
        commandDelayLabel.setToolTipText("This is the delay in ms between each command");
        setInterval.setToolTipText("Interval in ms between each command");
        disableMouse.setToolTipText("Disables mouse movement through chat commands");

        // adjust size and set layout
        this.setSize(1000, 1000);

        // add components
        add(onOff);

        // add (chatModeLabel);
        add(twitchUserLabel);
        // add (minIntervalLabel);
        // add (cmdConfigLabel);
        // add (maxIntervalLabel);
        // add (commandDelayLabel);
        // add (windowExecLabel);

        add(twitchUser);
        add(chatMode);
        add(minInterval);
        add(maxInterval);
        add(commandDelay);
        add(setInterval);
        add(commandInterval);
        add(jcomp14);
        add(cfgFileSelect);
        add(disableMouse);

        // set component bounds (only needed by Absolute Positioning)
        twitchUserLabel.setBounds(5, 5, 110, 25);
        onOff.setBounds(290, 115, 100, 25);
        twitchUser.setBounds(120, 5, 200, 25);
        chatMode.setBounds(470, 5, 190, 25);
        chatModeLabel.setBounds(330, 5, 100, 25);
        minIntervalLabel.setBounds(330, 35, 135, 25);
        minInterval.setBounds(470, 35, 70, 25);
        maxIntervalLabel.setBounds(330, 60, 140, 25);
        maxInterval.setBounds(470, 60, 70, 25);
        commandDelayLabel.setBounds(330, 85, 140, 25);
        commandDelay.setBounds(470, 85, 70, 25);
        setInterval.setBounds(5, 80, 135, 25);
        commandInterval.setBounds(145, 80, 70, 25);
        jcomp14.setBounds(120, 30, 200, 25);
        windowExecLabel.setBounds(5, 30, 115, 25);
        cmdConfigLabel.setBounds(5, 55, 115, 20);
        cfgFileSelect.setBounds(120, 55, 200, 25);
        disableMouse.setBounds(545, 35, 115, 20);

        onOff.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    twitchUser.setEditable(false);
                    jcomp14.setEditable(false);
                    cfgFileSelect.setVisible(false);
                    commandInterval.setEditable(false);

                    chatMode.setEditable(false);
                    minInterval.setEditable(false);
                    maxInterval.setEditable(false);
                    commandDelay.setEditable(false);

                    disableMouse.setEnabled(false);
                    onOffStatus = true;

                    try {
                        App.startBot();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (AWTException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    twitchUser.setEditable(true);
                    jcomp14.setEditable(true);
                    cfgFileSelect.setVisible(true);
                    commandInterval.setEditable(true);

                    chatMode.setEditable(true);
                    minInterval.setEditable(true);
                    maxInterval.setEditable(true);
                    commandDelay.setEditable(true);

                    disableMouse.setEnabled(true);
                    onOffStatus = false;
                }
                // while(event.getStateChange() == ItemEvent.SELECTED){
                // focusProgram();

                // }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Twitch Plays Anything");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GUI());
        frame.pack();
        frame.setVisible(true);

    }

    public static void focusProgram() {
        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, selectedWindow);
        if (hwnd != User32.INSTANCE.GetForegroundWindow()) {
            User32.INSTANCE.SetFocus(hwnd);
            User32.INSTANCE.SetForegroundWindow(hwnd);
        } else {
            System.out.println("shits fucked yo");
        }
    }

    public static int getMode() {
        return 1;
    }

    public static int getMinInterval() {
        if (minInterval.getText().equals(""))
            return 1000;
        return Integer.valueOf(minInterval.getText());
    }

    public static int getMaxInterval() {
        if (maxInterval.getText().equals(""))
            return 10000;
        return Integer.valueOf(maxInterval.getText());
    }

    public static int getCommandInterval() {
        if (commandInterval.getText().equals(""))
            return 1000;
        return Integer.valueOf(commandInterval.getText());
    }

    public static int getCommandDelay() {
        if (commandDelay.getText().equals(""))
            return 1000;
        return Integer.valueOf(commandDelay.getText());
    }

    public static String getTwitchUser() {
        return twitchUser.getText();
    }

    public static File getConfigFileLocation() {
        return cfgFileSelect.getSelectedFile();
    }

    public static boolean onOffStatus() {
        return onOffStatus;
    }

}
