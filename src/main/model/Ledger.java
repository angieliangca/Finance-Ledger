package model;

import exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// Represents a ledger having a list of income and expense items
public class Ledger {
    private List<Item> myLedger;
    private double totalIncome;
    private double totalExpense;
    private double netIncome = totalIncome - totalExpense;

    // EFFECTS: constructs an empty ledger
    public Ledger() {
        myLedger = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: add an item to the ledger, then update total income or expense and net income
    public void addItem(String type, String date, String entity, String description, Double amount)
            throws DuplicateItemException {
        if (isDuplicate(type, date, entity, amount)) {
            throw new DuplicateItemException();
        }
        boolean convertedType = (type.equals("income"));
        Item item = new Item(convertedType, date, entity, description, amount);
        item.setId(myLedger.size());
        myLedger.add(item);
        if (convertedType) {
            totalIncome += amount;
        } else {
            totalExpense += amount;
        }
        netIncome = totalIncome - totalExpense;
    }

    // MODIFIES: this
    // EFFECTS: if the ledger is empty, return false;
    //          otherwise, delete an item to the ledger with the given id,
    //          then update total income or expense and net income
    public boolean deleteItem(int id) throws InvalidIDException {
        if (numItems() == 0) {
            System.out.println("There is no items to delete");
            return false;
        }
        if (id <= 0 || id > myLedger.size()) {
            throw new InvalidIDException();
        }
        if (myLedger.get(id - 1).isIncome()) {
            totalIncome -= myLedger.get(id - 1).getAmount();
        } else {
            totalExpense -= myLedger.get(id - 1).getAmount();
        }
        netIncome = totalIncome - totalExpense;
        myLedger.remove(id - 1);
        int i = 0;
        for (Item item : myLedger) {
            item.setId(i);
            i++;
        }
        return true;
    }

    // EFFECTS: returns a string representation of the ledger, negative amount for expense item
    public String toString() {
        StringBuilder printLedger = new StringBuilder();
        for (Item item : myLedger) {
            double amount = item.getAmount();
            if (!item.isIncome()) {
                amount = -item.getAmount();
            }
            printLedger.append(item.getId()).append("    ").append(item.getDate()).append("    ")
                    .append(item.getEntity()).append("    ").append(item.getDescription()).append("    ")
                    .append(amount).append("\n");
        }
        return printLedger.toString();
    }

    // EFFECTS: returns the total income amount on this ledger
    public double getTotalIncome() {
        return totalIncome;
    }

    // EFFECTS: returns the total expense amount on this ledger
    public double getTotalExpense() {
        return totalExpense;
    }

    // EFFECTS: returns the net income amount on this ledger
    public double getNetIncome() {
        return netIncome;
    }

    // EFFECTS: returns the number of items on this ledger
    public int numItems() {
        return myLedger.size();
    }

    // EFFECTS: check the type
    public void checkType(String inputType) throws InvalidTypeException {
        if (!inputType.equals("income") && !inputType.equals("expense")) {
            throw new InvalidTypeException();
        }
    }

    // EFFECTS: check if the given date is valid; if not, throw InvalidDateException
    public void checkDate(String inputDate) throws InvalidDateException {
        Date tryDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            tryDate = dateFormat.parse(inputDate);
        } catch (ParseException e) {
            throw new InvalidDateException();
        }
    }

    // EFFECTS: check if the given amount is non-negative; if not, throw NegativeAmountException
    public void checkAmount(double inputAmount) throws NegativeAmountException {
        if (inputAmount < 0) {
            throw new NegativeAmountException();
        }
    }

    // EFFECTS: returns true if the ledger has an existing item with the same type, date, entity and amount given
    public boolean isDuplicate(String type, String date, String entity, double amount) {
        boolean convertedType = (type.equals("income"));
        for (Item item : myLedger) {
            if (item.isIncome() == convertedType && item.getDate().equals(date)
                    && item.getEntity().equals(entity) && item.getAmount() == amount) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the item number of the maximum income item
    public int getMaxIncome() {
        double maxIncomeAmt = 0;
        int maxIncomeID = 0;
        for (Item item : myLedger) {
            if (item.isIncome() && item.getAmount() > maxIncomeAmt) {
                maxIncomeID = item.getId();
                maxIncomeAmt = item.getAmount();
            }
        }
        return maxIncomeID;
    }

    // EFFECTS: returns the item number of the maximum expense item
    public int getMaxExpense() {
        double maxExpenseAmt = 0;
        int maxExpenseID = 0;
        for (Item item : myLedger) {
            if (!item.isIncome() && item.getAmount() > maxExpenseAmt) {
                maxExpenseID = item.getId();
                maxExpenseAmt = item.getAmount();
            }
        }
        return maxExpenseID;
    }

    // REQUIRES: 0 < id <= myLedger.size()
    // EFFECTS: returns the item with the given item number
    public Item getItem(int id) {
        return myLedger.get(id - 1);
    }
}
