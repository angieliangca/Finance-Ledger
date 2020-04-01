package gui;

import model.Item;
import model.Ledger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents the report frame in which user can view summary
public class ReportFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private static final int BORDER = 10;
    private static final String header = "Your financial summary:";
    private static final String newline = "\n";
    private static final String newsection = "====================\n";

    // EFFECTS: constructs report frame
    public ReportFrame() {
        super("Financial Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JLabel picture = new JLabel();
        picture.setIcon(addImage(LedgerApp.myLedger));

        add(scrollPane, BorderLayout.CENTER);
        add(picture, BorderLayout.EAST);

        textArea.append(header + newline + summaryString() + newline + maxString() + newline + percentageString());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: constructs summary string
    private String summaryString() {
        String totalIncome = "Total income: " + LedgerApp.myLedger.getTotalIncome();
        String totalExpense = "Total expense: " + LedgerApp.myLedger.getTotalExpense();
        String netIncome = "Net income: " + LedgerApp.myLedger.getNetIncome();
        return totalIncome + newline + totalExpense + newline + netIncome + newline;
    }

    // EFFECTS: constructs maximum items string
    private String maxString() {
        String header = newsection + "Maximum Single Items" + newline;
        String maxIncome;
        String maxExpense;
        if (LedgerApp.myLedger.getMaxIncome() != 0) {
            Item maxSingleIncome = LedgerApp.myLedger.getItem(LedgerApp.myLedger.getMaxIncome());
            maxIncome = "You receive most on: " + newline + maxSingleIncome.toString().substring(10) + newline;
        } else {
            maxIncome = "No income item in the ledger." + newline;
        }
        if (LedgerApp.myLedger.getMaxExpense() != 0) {
            Item maxSingleExpense = LedgerApp.myLedger.getItem(LedgerApp.myLedger.getMaxExpense());
            maxExpense = "You spend most on: " + newline + maxSingleExpense.toString().substring(11) + newline;
        } else {
            maxExpense = "No expense item in the ledger." + newline;
        }
        return header + maxIncome + maxExpense;
    }

    // EFFECTS: constructs category percentage string
    private String percentageString() {
        String header = newsection + "Category Percentage" + newline;
        String incomeSection = "Your income consists of:" + newline;
        StringBuilder incomePercentage = LedgerApp.myLedger.getCategory()
                .incomePercentageToString(LedgerApp.myLedger);
        String expenseSection = "Your expense consists of:" + newline;
        StringBuilder expensePercentage = LedgerApp.myLedger.getCategory()
                .expensePercentageToString(LedgerApp.myLedger);
        return header + incomeSection + incomePercentage + expenseSection + expensePercentage;
    }

    // EFFECTS: adds an image based on net income
    private Icon addImage(Ledger myLedger) {
        if (myLedger.getNetIncome() >= 1000000) {
            ImageIcon imageIcon = new ImageIcon("pic/pic1.jpeg");
            return new ImageIcon(resizeImage(imageIcon));
        } else if (myLedger.getNetIncome() <= 0) {
            ImageIcon imageIcon = new ImageIcon("pic/pic2.jpg");
            return new ImageIcon(resizeImage(imageIcon));
        } else {
            ImageIcon imageIcon = new ImageIcon("pic/pic3.jpg");
            return new ImageIcon(resizeImage(imageIcon));
        }
    }

    // EFFECTS: resize the image
    private Image resizeImage(ImageIcon imageIcon) {
        Image origImg = imageIcon.getImage();
        return origImg.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
    }
}