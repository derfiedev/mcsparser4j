package com.derfiedev;

import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static List<ServerEntry> foundServers = new ArrayList<>();
    public static final String VERSION = "1.0.0";

    public static GUI gui;
    public static void main(String[] args) {
        logger.info("Starting MCSParser...");

        // ScannerThreadManager scannerThreadManager = new ScannerThreadManager(ports, 10);
        // scannerThreadManager.startThreads();

        GUI gui = new GUI();
        foundServers.add(new ServerEntry("server1", 1234, 5000, "favicon1", "motd1", "version1", 10, 20, 1, LocalDateTime.now(), LocalDateTime.now()));
        foundServers.add(new ServerEntry("server2", 5678, 5000, "favicon2", "motd2", "version2", 30, 40, 1, LocalDateTime.now(), LocalDateTime.now()));
        foundServers.add(new ServerEntry("server3", 9012, 5000, "favicon3", "motd3", "version3", 50, 60, 1, LocalDateTime.now(), LocalDateTime.now()));
        // ListSaverToFile.saveListToFile("servers.txt", foundServers);
        for (ServerEntry serverEntry : foundServers) {
            gui.addRowToTable(serverEntry);
        }
    }

    public static void addServerEntry(ServerEntry serverEntry) {
        foundServers.add(serverEntry);
        gui.addRowToTable(serverEntry);
    }
    


    @Override
    protected void finalize() {
        logger.info("Closing MCSParser...");
    }

}