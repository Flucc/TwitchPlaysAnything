package flucc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListModel;

public class Logging {

    public static boolean logCommands() throws IOException {

        DefaultListModel<String> model = GUI.getListModel();
        File newFile = new File(createDirectory(System.getProperty("user.dir") + "/cmdLog/"),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss -  dd.MM.yyyy")) + ".txt");
        System.out.println(newFile.getPath());
        newFile.createNewFile();
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));
        for (Object str : model.toArray()) {
            System.out.println(str.toString());
            writer.append(str.toString() + '\n');
        }
        writer.close();
        GUI.clearModel();
        return true;
    }

    private static File createDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }
}
