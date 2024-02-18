package com.derfiedev;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.dilley.MineStat;

public class ScannerThread extends Thread {
    Logger logger = LogManager.getLogger(ScannerThread.class);
    private List<Integer> ports;

    public ScannerThread(List<Integer> ports) {
        this.ports = ports;
    }
    private volatile boolean running = true;

    public void stopThread() {
        running = false;
    }


    @Override
    public void run() {
        // logger.info("Starting scanner thread...");
        while (running) { // Loop and generate random IP addresses
            String ipAddress = IPAddressGenerator.generateRandomIPAddress();
            PortScanner portScanner = new PortScanner(ipAddress, ports);
            List<Integer> openPorts = portScanner.scanPorts();
            if (openPorts.size() == 0)
                continue;
            for (int port : openPorts) { // Loop through the open ports
                MineStat mineStat = new MineStat(ipAddress, port);
                if (!mineStat.isServerUp())
                    continue; // Skip if the server is not up
                logger.info("Found server at " + ipAddress + " on port: " + port + " with "
                        + mineStat.getCurrentPlayers() + " players online.");
                ServerEntry serverEntry = new ServerEntry( // Create a record of a server with all of its details...
                        ipAddress,
                        port,
                        portScanner.getTimeout(),
                        mineStat.getFavicon(),
                        mineStat.getStrippedMotd(),
                        mineStat.getVersion(),
                        mineStat.getCurrentPlayers(),
                        mineStat.getMaximumPlayers(),
                        mineStat.getProtocol(),
                        LocalDateTime.now(),
                        LocalDateTime.now());
                // Main.foundServers.add(serverEntry); // ...then add it to the list of found servers
                Main.addServerEntry(serverEntry); // ...and add it to the GUI
            }
        }
    }
}
