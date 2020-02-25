package model;

import exceptions.*;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Represents a ledger having a list of income and expense items
public class Ledger implements Saveable {
    private List<Item> myLedger;
    private Map<String, Double> expenseCategory;
    private Map<String, Double> incomeCategory;
    private double totalIncome;
    private double totalExpense;
    private double netIncome = totalIncome - totalExpense;

    // EFFECTS: constructs an empty ledger and initialize the category list
    public Ledger() {
        myLedger = new LinkedList<>();
        initCategory();
    }

    // MODIFIES: this
    // EFFECTS: add an item to the ledger, then update total income or expense and net income
    public void addItem(String type, String date, String entity, String description, String category, double amount)
            throws DuplicateItemException {
        if (isDuplicate(type, date, entity, amount)) {
            throw new DuplicateItemException();
        }
        boolean convertedType = (type.equals("income"));
        Item item = new Item(convertedType, date, entity, description, category, amount);
        item.setId(myLedger.size());
        myLedger.add(item);
        if (convertedType) {
            incomeCategory.computeIfPresent(category, (name, subtotal) -> subtotal + amount);
            totalIncome += amount;
        } else {
            expenseCategory.computeIfPresent(category, (name, subtotal) -> subtotal + amount);
            totalExpense += amount;
        }
        netIncome = totalIncome - totalExpense;
    }

    // MODIFIES: this
    // EFFECTS: overload the addItem method with item as parameter
    public void addItem(Item item) throws DuplicateItemException {
        if (isDuplicate(item.isIncome() ? "income" : "expense", item.getDate(), item.getEntity(), item.getAmount())) {
            throw new DuplicateItemException();
        }
        item.setId(myLedger.size());
        myLedger.add(item);
        if (item.isIncome()) {
            incomeCategory.computeIfPresent(item.getCategory(), (name, subtotal) -> subtotal + item.getAmount());
            totalIncome += item.getAmount();
        } else {
            expenseCategory.computeIfPresent(item.getCategory(), (name, subtotal) -> subtotal + item.getAmount());
            totalExpense += item.getAmount();
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
        Item itemToBeDeleted = myLedger.get(id - 1);
        if (itemToBeDeleted.isIncome()) {
            totalIncome -= itemToBeDeleted.getAmount();
            incomeCategory.computeIfPresent(itemToBeDeleted.getCategory(), (n, s) -> s - itemToBeDeleted.getAmount());

        } else {
            totalExpense -= itemToBeDeleted.getAmount();
            expenseCategory.computeIfPresent(itemToBeDeleted.getCategory(), (n, s) -> s - itemToBeDeleted.getAmount());
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
                    .append(item.getCategory()).append("    ").append(amount).append("\n");
        }
        return printLedger.toString();
    }

    // EFFECTS: initialize the income category list and expense category list
    private void initCategory() {
        incomeCategory = new HashMap<String, Double>();
        incomeCategory.put("salary", 0.00);
        incomeCategory.put("investment", 0.00);
        expenseCategory = new HashMap<String, Double>();
        expenseCategory.put("housing", 0.00);
        expenseCategory.put("transportation", 0.00);
        expenseCategory.put("food", 0.00);
        expenseCategory.put("clothing", 0.00);
        expenseCategory.put("utilities", 0.00);
        expenseCategory.put("entertainment", 0.00);
        expenseCategory.put("medical", 0.00);
        expenseCategory.put("miscellaneous", 0.00);
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

    // EFFECTS: returns the income category list
    public Map<String, Double> getIncomeCategory() {
        return incomeCategory;
    }

    // EFFECTS: returns the expense category list
    public Map<String, Double> getExpenseCategory() {
        return expenseCategory;
    }

    // EFFECTS: check if the given type is valid; if not, throw InvalidTypeException
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

    // EFFECTS: check if the given income category is valid; if not, throw InvalidCategoryException
    public void checkIncomeCategory(String inputCategory) throws InvalidCategoryException {
        if (!incomeCategory.containsKey(inputCategory)) {
            throw new InvalidCategoryException();
        }
    }

    // EFFECTS: check if the given expense category is valid; if not, throw InvalidCategoryException
    public void checkExpenseCategory(String inputCategory) throws InvalidCategoryException {
        if (!expenseCategory.containsKey(inputCategory)) {
            throw new InvalidCategoryException();
        }
    }

    // EFFECTS: check if the given amount is non-negative; if not, throw NegativeAmountException
    public void checkAmount(double inputAmount) throws NegativeAmountException {
        if (inputAmount < 0.00) {
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

    @Override
    public void save(PrintWriter printWriter) {
        for (Item item : myLedger) {
            printWriter.print(item.getId());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.isIncome());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.getDate());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.getEntity());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.getDescription());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.getCategory());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(item.getAmount());
            printWriter.print("\n");
        }
    }
}
