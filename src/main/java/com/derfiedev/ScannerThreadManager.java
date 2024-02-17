package com.derfiedev;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class ScannerThreadManager {
    private Logger logger = LogManager.getLogger(ScannerThreadManager.class);
    private List<ScannerThread> threads;
    private List<Integer> portsToScan;
    private int amountOfThreads;


    

    public ScannerThreadManager(List<Integer> portsToScan, int amountOfThreads) {
        this.portsToScan = portsToScan;
        this.amountOfThreads = amountOfThreads;
        this.threads = new ArrayList<>();
        logger.info("Creating a ScannerThreadManager...");
    }

    public void startThreads() {
        logger.info("Starting " + amountOfThreads + " scanner threads...");
        for (int i = 0; i < amountOfThreads; i++) {
            threads.add(new ScannerThread(portsToScan));
        }
        for (ScannerThread thread : threads) {
            thread.start();
        }
        logger.info("Started " + threads.size() + " scanner threads!");
    }

    public List<ScannerThread> getThreads() {
        return threads;
    }

    public List<Integer> getPortsToScan() {
        return portsToScan;
    }
}
