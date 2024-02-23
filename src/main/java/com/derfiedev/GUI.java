package com.derfiedev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;


public class GUI {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static DefaultTableModel model;
    private JSpinner threadsSpinner = new JSpinner();
    private JTextField portsField = new JTextField();
    private ScannerThreadManager scannerThreadManager;
    private JButton startButton;
    private JButton stopButton;
    private JButton copyIPButton;
    private JButton saveToFileButton;

    public GUI() {
        // Create a new JFrame
        JFrame frame = new JFrame("MCSParser v" + Main.VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create a new JPanel for the buttons
        JPanel buttonPanel = new JPanel();
        JLabel serverDiscoveryLabel = new JLabel("Server discovery");
        JTable table = new JTable(); // нахуй тебе это тут?
        startButton = new JButton("Start");
        startButton.addActionListener(e -> startScannerThreads());
        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopScannerThreads());
        stopButton.setEnabled(false);
        final JTable finalTable = table; // https://avatars.dzeninfra.ru/get-zen_doc/9827869/pub_64b7a797e2f6aa6832016e60_64b7a7a30d50c9486b0e8ef1/smart_crop_516x290
        copyIPButton = new JButton("Copy IP:Port"); 
        saveToFileButton = new JButton("Save to file");
        saveToFileButton.addActionListener(e -> {
            ListSaverToFile.saveListToFile("servers.txt", Main.foundServers);
        });

        buttonPanel.add(serverDiscoveryLabel);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(copyIPButton);
        buttonPanel.add(saveToFileButton);

        // Create a new JTable with the specified columns
        String[] columns = { "host", "port", "timeout", "motd", "version", "players_online", "players_max",
                "last updated" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        final JTable finalTable1 = table;
        copyIPButton.addActionListener(e -> {
            try{
                int selectedRow = finalTable1.getSelectedRow();
                logger.info("Selected row: " + selectedRow);
                String ip = (String) model.getValueAt(selectedRow, 0) + ":" + model.getValueAt(selectedRow, 1);
                
                logger.info("IP: " + ip);
                copyIPButton(ip);
            }
            catch (Exception ex){
                System.out.println("No row selected");
            }
        }
        );

        table.setRowSelectionAllowed(true);

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create a new JPanel for the settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2, 2));
        settingsPanel.add(new JLabel("Threads:"));
        settingsPanel.add(threadsSpinner);
        threadsSpinner.setModel(new SpinnerNumberModel(10, 1, 1000, 1));
        
        settingsPanel.add(new JLabel("Ports:"));
        settingsPanel.add(portsField);

        // Add the panels to the frame
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(settingsPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    public void copyIPButton(String ipPort) {
        StringSelection stringSelection = new StringSelection(ipPort);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void addRowToTable(ServerEntry serverEntry) {
        model.addRow(new Object[] {
                serverEntry.host(),
                serverEntry.port(),
                serverEntry.timeout(),
                serverEntry.motd(),
                serverEntry.version(),
                serverEntry.players_online(),
                serverEntry.players_max(),
                serverEntry.last_updated()
        });
    }

    public Integer getThreads() {
        return (Integer) threadsSpinner.getValue();
    }

    public void startScannerThreads() {
        int amountOfThreads = getThreads();
        List<Integer> ports = new ArrayList<>();
        ports.add(25565);
        scannerThreadManager = new ScannerThreadManager(ports, amountOfThreads);
        scannerThreadManager.startThreads();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stopScannerThreads() {
        scannerThreadManager.closeAllThreads();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
}