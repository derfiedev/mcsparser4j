package com.derfiedev;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PortScanner {
    private String host;
    private List<Integer> portsToScan;
    private int timeout = 200;
    private static final Logger logger = LogManager.getLogger(PortScanner.class);

    public PortScanner(String host, List<Integer> portsToScan) {
        this.host = host;
        this.portsToScan = portsToScan;

        // logger.info("PortScanner created with host: " + host + ", portsToScan: " + portsToScan);

    }

    public String getHost() {
        return host;
    }

    public List<Integer> getPortsToScan() {
        return portsToScan;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPortsToScan(List<Integer> portsToScan) {
        this.portsToScan = portsToScan;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isPortOpen(int port) {
        // logger.info("Checking port " + port + " on host " + host + "...");
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port), timeout);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Integer> scanPorts() {
        // logger.info("Scanning ports on host " + host + "...");
        List<Integer> openPorts = new ArrayList<>();
        for (int port : portsToScan) {
            if (isPortOpen(port)) {
                // logger.info("Port " + port + " on host " + host + " is open.");
                openPorts.add(port);
            } else {
                // logger.info("Port " + port + " on host " + host + " is closed.");
            }
        }
        return openPorts;
    }
}
