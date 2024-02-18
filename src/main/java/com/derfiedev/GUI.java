package com.derfiedev;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private static DefaultTableModel model;
    private JSpinner threadsSpinner = new JSpinner();
    private JTextField portsField = new JTextField();
    private ScannerThreadManager scannerThreadManager;
    private JButton startButton;
    private JButton stopButton;
    private JButton copyIPButton;

    public GUI() {
        // Create a new JFrame
        JFrame frame = new JFrame("My Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create a new JPanel for the buttons
        JPanel buttonPanel = new JPanel();
        JLabel serverDiscoveryLabel = new JLabel("Server discovery");
        JTable table = new JTable();
        startButton = new JButton("Start");
        startButton.addActionListener(e -> startScannerThreads());
        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopScannerThreads());
        stopButton.setEnabled(false);
        final JTable finalTable = table;
        copyIPButton = new JButton("Copy IP:Port");
        copyIPButton.addActionListener(e -> {
            int selectedRow = finalTable.getSelectedRow();
            if (selectedRow != -1) {
                String ip = (String) model.getValueAt(selectedRow, 0);
                String port = (String) model.getValueAt(selectedRow, 1);
                String ipPort = ip + ":" + port;
                StringSelection stringSelection = new StringSelection(ipPort);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        buttonPanel.add(serverDiscoveryLabel);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(copyIPButton);

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