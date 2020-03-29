package gui;

import exceptions.InvalidIDException;
import model.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the output panel in which user can view details
public class OutputPanel extends JPanel implements ActionListener {

    private static final int WIDTH = 440;
    private static final int HEIGHT = 540;
    private static final int BORDER = 5;

    private JList dataList;
    private JButton reviewButton;
    private JButton hideButton;
    private JButton deleteButton;
    private JButton reportButton;

    // EFFECTS: constructs output panel
    public OutputPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setLayout(new BorderLayout());

        initOutputPane();
        initControlPane();
    }

    // EFFECTS: initializes output pane in which user can view items list
    private void initOutputPane() {
        JPanel outputPane = new JPanel(new BorderLayout());
        outputPane.setBorder(BorderFactory.createTitledBorder("Display"));

        dataList = new JList();
        dataList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dataList.setVisibleRowCount(-1);

        JScrollPane scrollPane = new JScrollPane(dataList);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setViewportView(dataList);

        outputPane.add(scrollPane);

        add(outputPane, BorderLayout.CENTER);
    }

    // EFFECTS: initializes control pane in which user can view items list
    private void initControlPane() {
        JPanel buttonPane = new JPanel(new FlowLayout());
        buttonPane.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));

        reviewButton = new JButton("Review Details");
        hideButton = new JButton("Hide Details");
        deleteButton = new JButton("Delete Item");
        reportButton = new JButton("Generate Report");

        reviewButton.setActionCommand("review");
        reviewButton.addActionListener(this);
        hideButton.setActionCommand("hide");
        hideButton.addActionListener(this);
        hideButton.setVisible(false);
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(false);
        reportButton.setActionCommand("report");
        reportButton.addActionListener(this);

        buttonPane.add(reviewButton);
        buttonPane.add(hideButton);
        buttonPane.add(deleteButton);
        buttonPane.add(reportButton);

        add(buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("review")) {
            updateDataList();
            dataList.setVisible(true);
            disableBut();
        }

        if (e.getActionCommand().equals("delete")) {
            if (LedgerApp.myLedger.getMyLedger().size() == 0) {
                showEmptyMessage();
            }
            int index = dataList.getSelectedIndex();
            deleteAnItem(index);
            if (!reviewButton.isEnabled()) {
                updateDataList();
            }
        }

        if (e.getActionCommand().equals("report")) {
            new ReportFrame(LedgerApp.myLedger);
        }

        if (e.getActionCommand().equals("hide")) {
            dataList.setVisible(false);
            enableBut();
        }
    }

    // EFFECTS: updates item lists in real time
    private void updateDataList() {
        Item[] data = LedgerApp.myLedger.getMyLedger().toArray(new Item[0]);
        for (int i = 0; i < LedgerApp.myLedger.getMyLedger().size(); i++) {
            data[i] = (LedgerApp.myLedger.getMyLedger()).get(i);
        }
        dataList.setListData(data);
    }

    // EFFECTS: disables review button
    private void disableBut() {
        reviewButton.setVisible(false);
        reviewButton.setEnabled(false);
        hideButton.setVisible(true);
        deleteButton.setEnabled(true);
    }

    // EFFECTS: pops up message if the ledger is empty
    public void showEmptyMessage() {
        JOptionPane.showMessageDialog(new JFrame("Message"), "There is no item.",
                "Empty Ledger", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: deletes an item
    public void deleteAnItem(int index) {
        try {
            LedgerApp.myLedger.deleteItem(index + 1);
        } catch (InvalidIDException ex) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Please select an item.",
                    "Invalid Selection", JOptionPane.PLAIN_MESSAGE);
        }
    }

    // EFFECTS: enables review button
    private void enableBut() {
        reviewButton.setVisible(true);
        reviewButton.setEnabled(true);
        hideButton.setVisible(false);
        deleteButton.setEnabled(false);
    }

    public JButton getReviewButton() {
        return reviewButton;
    }

    public JList getDataList() {
        return dataList;
    }
}
