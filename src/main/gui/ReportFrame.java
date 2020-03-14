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
    private static final String space = "        ";
    private static final String newline = "\n";
    private static final String newsection = "====================\n";

    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JLabel picture;

    private Ledger myLedger;

    // EFFECTS: constructs report frame
    public ReportFrame(Ledger myLedger) {
        super("Financial ReportFrame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        setLayout(new BorderLayout());

        this.myLedger = myLedger;

        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        picture = new JLabel();
        picture.setIcon(addImage(myLedger));

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
        String totalIncome = "Total income: " + myLedger.getTotalIncome();
        String totalExpense = "Total expense: " + myLedger.getTotalExpense();
        String netIncome = "Net income: " + myLedger.getNetIncome();
        return totalIncome + newline + totalExpense + newline + netIncome + newline;
    }

    // EFFECTS: constructs maximum items string
    private String maxString() {
        String header = newsection + "Maximum Single Items" + newline;
        String maxIncome;
        String maxExpense;
        if (myLedger.getMaxIncome() != 0) {
            Item maxSingleIncome = myLedger.getItem(myLedger.getMaxIncome());
            maxIncome = "You receive most on: " + newline + maxSingleIncome.toString().substring(10) + newline;
        } else {
            maxIncome = "No income item in the ledger." + newline;
        }
        if (myLedger.getMaxExpense() != 0) {
            Item maxSingleExpense = myLedger.getItem(myLedger.getMaxExpense());
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
        StringBuilder incomePercentage = new StringBuilder();
        String expenseSection = "Your expense consists of:" + newline;
        StringBuilder expensePercentage = new StringBuilder();
        if (myLedger.getTotalIncome() != 0) {
            StringBuilder finalIncomePercentage = incomePercentage;
            myLedger.getIncomeCategory().forEach((name, subtotal) ->
                    finalIncomePercentage.append(name).append(space).append(subtotal).append(space).append(roundPct(
                            subtotal / myLedger.getTotalIncome() * 100)).append("%").append(newline));
        } else {
            incomePercentage.append("No income item in the ledger.").append(newline);
        }

        if (myLedger.getTotalExpense() != 0) {
            StringBuilder finalExpensePercentage = expensePercentage;
            myLedger.getExpenseCategory().forEach((name, subtotal) ->
                    finalExpensePercentage.append(name).append(space).append(subtotal).append(space).append(roundPct(
                            subtotal / myLedger.getTotalExpense() * 100)).append("%").append(newline));
        } else {
            expensePercentage.append("No expense item in the ledger.").append(newline);
        }
        return header + incomeSection + incomePercentage + expenseSection + expensePercentage;
    }

    // EFFECTS: rounds up percentages in category percentage string
    private double roundPct(double amount) {
        long temp = Math.round(amount * (long) Math.pow(10, 2));
        amount = (double) temp / (long) Math.pow(10, 2);
        return amount;
    }

    // EFFECTS: adds an image based on net income
    private Icon addImage(Ledger myLedger) {
        if (myLedger.getNetIncome() >= 1000000) {
            ImageIcon imageIcon = new ImageIcon("pic/pic1.jpeg");
            return new ImageIcon(resizeImage(imageIcon));
        } else if (myLedger.getNetIncome() < 0) {
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