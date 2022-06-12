package flucc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.text.NumberFormatter;

import com.formdev.flatlaf.FlatLightLaf;

import net.miginfocom.swing.MigLayout;

public class GUI extends JPanel {
    // Main Window Components
    static JPanel mainPanel;
    // Labels
    JLabel twitchUsernameLabel;
    JLabel selectionModeLabel;
    // Fields
    static JTextField twitchUsernameField;
    static JComboBox<String> selectionModeBox;
    // Buttons
    static JButton keyConfigButton;
    static JButton modeConfigButton;
    // JButton mouseConfigButton;
    // JButton chooseActiveWindow;
    static JToggleButton startStopButton;

    // Mode Config Window Components
    static JFrame modeFrame;
    static JPanel modeConfig;
    // Labels
    JLabel minIntLabel;
    JLabel maxIntLabel;
    JLabel setIntLabel;
    JLabel comDelayLabel;
    // Fields
    static JFormattedTextField minIntField;
    static JFormattedTextField maxIntField;
    static JFormattedTextField setIntField;
    static JFormattedTextField comDelayField;
    // Confirm Button, writies to txt config file on close
    static JButton closeModeConfig;

    // Mode Config Integers
    static int minInt;
    static int maxInt;
    static int setInt;
    static int comDelay;

    public GUI() {
        twitchUsernameLabel = new JLabel("Twitch Username:");
        selectionModeLabel = new JLabel("Select Chat Mode:");

        twitchUsernameField = new JTextField(12);

        String[] selectionModeItems = { "Everybody Matters!", "Vote On It!", "Random Interval", "Set Interval" };
        selectionModeBox = new JComboBox<String>(selectionModeItems);

        keyConfigButton = new JButton("Key Config File");

        modeConfigButton = new JButton("Mode Settings");
        startStopButton = new JToggleButton("Start!");

        // Add to Panel;
        mainPanel = new JPanel(new MigLayout("wrap 3, fill"));

        mainPanel.add(twitchUsernameLabel);
        mainPanel.add(twitchUsernameField, "cell 1 0, span 2, wrap, grow");

        mainPanel.add(selectionModeLabel);
        mainPanel.add(selectionModeBox, "cell 1 1, span 2, wrap, grow");

        mainPanel.add(keyConfigButton);
        // mainPanel.add(mouseConfigButton);
        // mainPanel.add(modeConfigButton);
        mainPanel.add(modeConfigButton, "cell 2 2");

        mainPanel.add(startStopButton, "cell 1 3");

        // mainPanel.add(startStopButton,"cell 0 2");
        // mainPanel.add(chooseActiveWindow, "cell 2 2");
        add(mainPanel);

        // Mode Settings Panel
        minIntLabel = new JLabel("Minimum Interval:");
        maxIntLabel = new JLabel("Maximum Interval:");
        setIntLabel = new JLabel("Set Interval:");
        comDelayLabel = new JLabel("Command Delay:");

        // Formatter for Fields

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);

        minIntField = new JFormattedTextField(formatter);
        maxIntField = new JFormattedTextField(formatter);
        setIntField = new JFormattedTextField(formatter);
        comDelayField = new JFormattedTextField(formatter);

        closeModeConfig = new JButton("Save and Close");

        modeConfig = new JPanel(new MigLayout("wrap 2, fill"));
        modeConfig.add(minIntLabel);
        modeConfig.add(minIntField);
        modeConfig.add(maxIntLabel);
        modeConfig.add(maxIntField);
        modeConfig.add(setIntLabel);
        modeConfig.add(setIntField);
        modeConfig.add(comDelayLabel);
        modeConfig.add(comDelayField);

        modeConfig.add(closeModeConfig, "cell 1 4");
        // Save and Close function
        closeModeConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minInt = Integer.parseInt(minIntField.getText());
                maxInt = Integer.parseInt(maxIntField.getText());
                setInt = Integer.parseInt(setIntField.getText());
                comDelay = Integer.parseInt(comDelayField.getText());
                modeFrame.setVisible(false);
            }

        });

        // Mode Settings Window
        modeConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeFrame = new JFrame("Selection Mode Settings");
                modeFrame.add(modeConfig);
                modeFrame.pack();
                modeFrame.setVisible(true);
                modeFrame.setAlwaysOnTop(true);
                modeFrame.setResizable(false);
            }

        });

        // Open Text Editor to edit KeyConfig
        keyConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("demo/demo/src/main/java/flucc/keyConfig.txt");
                try {
                    java.awt.Desktop.getDesktop().edit(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        startStopButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    twitchUsernameField.setEditable(false);
                    selectionModeBox.setEditable(false);
                    keyConfigButton.setEnabled(false);
                    modeConfigButton.setEnabled(false);
                    modeFrame.dispose();
                    try {
                        App.startBot();
                    } catch (Exception e1) {
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    twitchUsernameField.setEditable(true);
                    selectionModeBox.setEditable(true);
                    keyConfigButton.setEnabled(true);
                    modeConfigButton.setEnabled(true);
                }
            }
        });

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignore) {
        }
        JFrame frame = new JFrame("Twitch Plays Anything!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GUI());
        frame.pack();
        frame.setResizable(false);

        frame.setVisible(true);

    }

    // public static void focusProgram() {
    // WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, selectedWindow);
    // if (hwnd != User32.INSTANCE.GetForegroundWindow()) {
    // User32.INSTANCE.SetFocus(hwnd);
    // User32.INSTANCE.SetForegroundWindow(hwnd);
    // } else {
    // System.out.println("shits fucked yo");
    // }
    // }

    public static int getMode() {
        return 1;
    }

    public static int getMinInterval() {
        if (minIntField.getText().equals(""))
            return 1000;
        return minInt;
    }

    public static int getMaxInterval() {
        if (maxIntField.getText().equals(""))
            return 10000;
        return maxInt;
    }

    public static int getCommandInterval() {
        if (setIntField.getText().equals(""))
            return 1000;
        return setInt;
    }

    public static int getCommandDelay() {
        if (comDelayField.getText().equals(""))
            return 1000;
        return comDelay;
    }

    public static String getTwitchUser() {
        return twitchUsernameField.getText();
    }

    public static boolean getStartStopState() {
        return startStopButton.isSelected();
    }

}
