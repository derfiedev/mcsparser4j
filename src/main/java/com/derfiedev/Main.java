package com.derfiedev;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static List<ServerEntry> foundServers = new ArrayList<>();
    public static void main(String[] args) {
        logger.info("Starting MCSParser...");
        List<Integer> ports = new ArrayList<>();
        ports.add(25565);
        ScannerThreadManager scannerThreadManager = new ScannerThreadManager(ports, 1000);
        scannerThreadManager.startThreads();

    }
}