package model;

import exceptions.InvalidCategoryException;

import java.util.HashMap;
import java.util.Map;

// Represents the category of an item
public class Category {

    private static final String space = "        ";
    private static final String newline = "\n";

    private Map<String, Double> expenseCategory;
    private Map<String, Double> incomeCategory;

    // EFFECTS: initialize the income category list and expense category list
    public Category() {
        incomeCategory = new HashMap<String, Double>();
        incomeCategory.put("Salary", 0.00);
        incomeCategory.put("Investment", 0.00);
        expenseCategory = new HashMap<String, Double>();
        expenseCategory.put("Housing", 0.00);
        expenseCategory.put("Transportation", 0.00);
        expenseCategory.put("Food", 0.00);
        expenseCategory.put("Clothing", 0.00);
        expenseCategory.put("Utilities", 0.00);
        expenseCategory.put("Entertainment", 0.00);
        expenseCategory.put("Medical", 0.00);
        expenseCategory.put("Miscellaneous", 0.00);
    }

    // MODIFIES: this
    // EFFECTS: updates the subtotal amount for the corresponding category name
    public void calculateSubtotal(String category, double amount) {
        incomeCategory.computeIfPresent(category, (name, subtotal) -> subtotal + amount);
        expenseCategory.computeIfPresent(category, (name, subtotal) -> subtotal + amount);
    }

    // EFFECTS: returns a string of income category name and subtotal
    public StringBuilder incomePercentageToString(Ledger myLedger) {
        StringBuilder incomePercentage = new StringBuilder();
        if (myLedger.getTotalIncome() != 0) {
            StringBuilder finalIncomePercentage = incomePercentage;
            myLedger.getCategory().getIncomeCategory().forEach((name, subtotal) ->
                    finalIncomePercentage.append(name).append(space).append(subtotal).append(space).append(roundPct(
                            subtotal / myLedger.getTotalIncome() * 100)).append("%").append(newline));
            return finalIncomePercentage;
        } else {
            incomePercentage.append("No income item in the ledger.").append(newline);
            return incomePercentage;
        }
    }

    // EFFECTS: returns a string of expense category name and subtotal
    public StringBuilder expensePercentageToString(Ledger myLedger) {
        StringBuilder expensePercentage = new StringBuilder();
        if (myLedger.getTotalExpense() != 0) {
            StringBuilder finalExpensePercentage = expensePercentage;
            myLedger.getCategory().getExpenseCategory().forEach((name, subtotal) ->
                    finalExpensePercentage.append(name).append(space).append(subtotal).append(space).append(roundPct(
                            subtotal / myLedger.getTotalExpense() * 100)).append("%").append(newline));
            return finalExpensePercentage;
        } else {
            expensePercentage.append("No expense item in the ledger.").append(newline);
            return expensePercentage;
        }
    }

    // EFFECTS: rounds up percentages in category percentage string
    public double roundPct(double amount) {
        long temp = Math.round(amount * (long) Math.pow(10, 2));
        amount = (double) temp / (long) Math.pow(10, 2);
        return amount;
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

    // EFFECTS: returns the income category list
    public Map<String, Double> getIncomeCategory() {
        return incomeCategory;
    }

    // EFFECTS: returns the expense category list
    public Map<String, Double> getExpenseCategory() {
        return expenseCategory;
    }

}