package com.derfiedev;

import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;
import java.util.List;

public class ListSaverToFile {
    public static void saveListToFile(String filename, List<ServerEntry> list) {
        //save a list of ServerEntry objects to a file
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (ServerEntry serverEntry : list) {
                myWriter.write(serverEntry.toString() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
