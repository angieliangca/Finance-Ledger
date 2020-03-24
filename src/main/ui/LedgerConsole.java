package ui;

import exceptions.*;
import model.Item;
import model.Ledger;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

// Ledger application
public class LedgerConsole {
    private static final String LEDGER_FILE = "./data/myLedger.txt";

    private Scanner input;
    private Ledger myLedger;

    // EFFECTS: runs the ledger application
    public LedgerConsole() {
        runLedger();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runLedger() {
        boolean running = true;
        String command;
        input = new Scanner(System.in);

        loadLedger();

        System.out.println("Welcome to Ledger");

        while (running) {
            displayMenu();
            command = input.next();
            input.skip("\n");

            if (command.equals("5")) {
                processCommand(command);
                running = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you");
    }

    // MODIFIES: this
    // EFFECTS: loads ledger from LEDGER_FILE, if that file exists; otherwise creates new ledger
    private void loadLedger() {
        try {
            myLedger = Reader.readLedger(new File(LEDGER_FILE));
        } catch (IOException | DuplicateItemException e) {
            myLedger = new Ledger();
        }
    }

    // EFFECTS: saves state of ledger to LEDGER_FILE
    private void saveAccounts() {
        try {
            Writer writer = new Writer(new File(LEDGER_FILE));
            writer.write(myLedger);
            writer.close();
            System.out.println("Your ledger has been saved to file " + LEDGER_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, unable to save your ledger to " + LEDGER_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "1":
                addItem();
                break;
            case "2":
                deleteItem();
                break;
            case "3":
                displayDetail();
                break;
            case "4":
                createReport();
                break;
            case "5":
                saveAccounts();
                break;
            default:
                System.out.println("The option entered is invalid");
                break;
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nEnter your selection:");
        System.out.println("\t1 -> add item");
        System.out.println("\t2 -> delete item");
        System.out.println("\t3 -> review details");
        System.out.println("\t4 -> generate summary");
        System.out.println("\t5 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add an income or expense item
    private void addItem() {
        System.out.println("Enter Income or Expense:");
        String type = input.nextLine().toLowerCase();
        type = typeFormat(type);

        System.out.println("Enter date (MM-dd-yyyy):");
        String date = input.nextLine();
        date = dateFormat(date);

        System.out.println("Enter Payee or Payer:");
        String entity = input.nextLine();

        System.out.println("Enter Description:");
        String description = input.nextLine();

        System.out.println("Enter Category: \n"
                + "Select expense from: Housing, Transportation, Food, Clothing, Utilities, Entertainment, Medical, "
                + "Miscellaneous; \n" + "Select income from: Salary, Investment.");
        String category = input.nextLine().toLowerCase();
        category = (type.equals("income")) ? incomeCategoryFormat(category) : expenseCategoryFormat(category);

        System.out.println("Enter Amount:");
        double amount = input.nextDouble();
        amount = amountFormat(amount);

        try {
            myLedger.addItem(type, date, entity, description, category, amount);
        } catch (DuplicateItemException e) {
            System.out.println("This item exists in the ledger.");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete an income or expense item
    private void deleteItem() {
        displayDetail();
        System.out.println("Enter the item number you want to delete:");
        int id = input.nextInt();
        try {
            myLedger.deleteItem(id);
        } catch (InvalidIDException e) {
            System.out.println("No item with the given id in the ledger.");
        }
    }

    // EFFECTS: display every item in ledger
    private void displayDetail() {
        System.out.println(myLedger.toString());
    }

    // EFFECTS: generate summary for items in ledger
    private void createReport() {
        System.out.println("Here is your financial summary:");
        System.out.println("Total income: " + myLedger.getTotalIncome());
        System.out.println("Total expense: " + myLedger.getTotalExpense());
        System.out.println("Net income: " + myLedger.getNetIncome());
        maxItemAnalysis();
        percentageAnalysis();
    }

    // EFFECTS: pull out the income and expense items with the maximum amounts in ledger
    private void maxItemAnalysis() {
        System.out.println("====================\n" + "Maximum Single Items");
        if (myLedger.getMaxIncome() != 0) {
            Item maxSingleIncome = myLedger.getItem(myLedger.getMaxIncome());
            System.out.println("You receive most on: \n" + maxSingleIncome.toString());
        } else {
            System.out.println("No income item in the ledger.");
        }
        if (myLedger.getMaxExpense() != 0) {
            Item maxSingleExpense = myLedger.getItem(myLedger.getMaxExpense());
            System.out.println("You spend most on: \n" + maxSingleExpense.toString());
        } else {
            System.out.println("No expense item in the ledger.");
        }
    }

    // EFFECTS: pull out the subtotal and percentage for each income and expense category in ledger
    private void percentageAnalysis() {
        String header = "====================\n" + "Category Percentage" + "\n";
        String incomeSection = "Your income consists of:" + "\n";
        StringBuilder incomePercentage = myLedger.getCategory().incomePercentageToString(myLedger);
        String expenseSection = "Your expense consists of:" + "\n";
        StringBuilder expensePercentage = myLedger.getCategory().expensePercentageToString(myLedger);
        System.out.println(header + incomeSection + incomePercentage + expenseSection + expensePercentage);
    }

    // EFFECTS: check and returns the type with correct format
    private String typeFormat(String type) {
        try {
            myLedger.checkType(type);
        } catch (InvalidTypeException e) {
            System.out.println("Please enter valid type.");
            System.out.println("Enter Income or Expense:");
            type = input.nextLine();
            typeFormat(type);
        }
        return type;
    }

    // EFFECTS: check and returns the date with correct format
    private String dateFormat(String date) {
        try {
            myLedger.checkDate(date);
        } catch (InvalidDateException e) {
            System.out.println("Please enter valid date.");
            System.out.println("Enter date (MM-dd-yyyy):");
            date = input.nextLine();
            dateFormat(date);
        }
        return date;
    }

    // EFFECTS: check and returns the income category with correct format
    private String incomeCategoryFormat(String category) {
        try {
            myLedger.getCategory().checkIncomeCategory(category);
        } catch (InvalidCategoryException e) {
            System.out.println("Please select from the provided options.");
            System.out.println("Enter Category: \n"
                    + "Select expense from: Housing, Transportation, Food, Clothing, Utilities, Entertainment, Medical,"
                    + " Miscellaneous; \n" + "Select income from: Salary, Investment.");
            category = input.nextLine();
            category = category.toLowerCase();
            incomeCategoryFormat(category);
        }
        return category;
    }

    // EFFECTS: check and returns the expense category with correct format
    private String expenseCategoryFormat(String category) {
        try {
            myLedger.getCategory().checkExpenseCategory(category);
        } catch (InvalidCategoryException e) {
            System.out.println("Please select from the provided options.");
            System.out.println("Enter Category: \n"
                    + "Select expense from: Housing, Transportation, Food, Clothing, Utilities, Entertainment, Medical,"
                    + " Miscellaneous; \n" + "Select income from: Salary, Investment.");
            category = input.nextLine();
            category = category.toLowerCase();
            expenseCategoryFormat(category);
        }
        return category;
    }

    // EFFECTS: check and returns the amount with correct format
    private double amountFormat(double amount) {
        try {
            myLedger.checkAmount(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Please enter positive amount.");
            System.out.println("Enter Amount:");
            amount = input.nextDouble();
            amountFormat(amount);
        }
        return amount;
    }

}
