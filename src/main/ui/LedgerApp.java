package ui;

import exceptions.*;
import model.Item;
import model.Ledger;

import java.util.Scanner;

// Ledger application
public class LedgerApp {
    private Scanner input;
    private Ledger myLedger = new Ledger();

    // EFFECTS: runs the ledger application
    public LedgerApp() {
        runLedger();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runLedger() {
        boolean running = true;
        String command;
        input = new Scanner(System.in);

        System.out.println("Welcome to Ledger");

        while (running) {
            displayMenu();
            command = input.next();
            input.skip("\n");

            if (command.equals("5")) {
                running = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you");
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
        System.out.println("\t4 -> generate report");
        System.out.println("\t5 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: add an income or expense item
    private void addItem() {
        System.out.println("Enter Income or Expense:");
        String type = input.nextLine();
        type = type.toLowerCase();
        type = typeFormat(type);

        System.out.println("Enter date");
        String date = input.nextLine();
        date = dateFormat(date);

        System.out.println("Enter Payee or Payer:");
        String entity = input.nextLine();

        System.out.println("Enter Description:");
        String description = input.nextLine();

        System.out.println("Enter Amount:");
        double amount = input.nextDouble();
        amount = amountFormat(amount);

        try {
            myLedger.addItem(type, date, entity, description, amount);
        } catch (DuplicateItemException e) {
            System.out.println("There is an existing item in the ledger");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete an income or expense item
    private void deleteItem() {
        displayDetail();
        System.out.println("Enter the item number you want to delete");
        int id = input.nextInt();
        try {
            myLedger.deleteItem(id);
        } catch (InvalidIDException e) {
            System.out.println("There is no item with the given id");
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
        if (myLedger.getMaxIncome() != 0) {
            Item maxSingleIncome = myLedger.getItem(myLedger.getMaxIncome());
            System.out.println("Largest single income: \n" + maxSingleIncome.toString());
        } else {
            System.out.println("There is no income item.");
        }
        if (myLedger.getMaxExpense() != 0) {
            Item maxSingleExpense = myLedger.getItem(myLedger.getMaxExpense());
            System.out.println("Largest single expense: \n" + maxSingleExpense.toString());
        } else {
            System.out.println("There is no expense item.");
        }

    }

    private String typeFormat(String type) {
        try {
            myLedger.checkType(type);
        } catch (InvalidTypeException e) {
            System.out.println("Please enter valid type");
            System.out.println("Enter Income or Expense:");
            type = input.nextLine();
            typeFormat(type);
        }
        return type;
    }

    private String dateFormat(String date) {
        try {
            myLedger.checkDate(date);
        } catch (InvalidDateException e) {
            System.out.println("Please enter valid date");
            System.out.println("Enter date");
            date = input.nextLine();
            dateFormat(date);
        }
        return date;
    }

    private double amountFormat(double amount) {
        try {
            myLedger.checkAmount(amount);
        } catch (NegativeAmountException e) {
            System.out.println("Please enter positive amount");
            System.out.println("Enter Amount:");
            amount = input.nextDouble();
            amountFormat(amount);
        }
        return amount;
    }

}
