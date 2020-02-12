package model;

// Represents an  item in the ledger that has the following information: item number, income or expense type, date,
// payee or payer entity, description and amount
public class Item {
    private int id;
    private boolean type;
    private String date;
    private String entity;
    private String description;
    private Double amount;

    // EFFECTS: constructs an item for the given information
    public Item(boolean type, String date, String entity, String description, Double amount) {
        this.type = type;
        this.date = date;
        this.entity = entity;
        this.description = description;
        this.amount = amount;
    }

    // EFFECTS: set the item number of this item according its index in the ledger
    public void setId(int num) {
        id = num + 1;
    }

    // EFFECTS: returns the item number of this item
    public int getId() {
        return id;
    }

    // EFFECTS: returns the type of this item, true for income, false for expense
    public boolean isIncome() {
        return type;
    }

    // EFFECTS: returns the date of this item
    public String getDate() {
        return date;
    }

    // EFFECTS: returns the payee or payer of this item
    public String getEntity() {
        return entity;
    }

    // EFFECTS: returns the description of this item
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the amount of this item
    public Double getAmount() {
        return amount;
    }

    // EFFECTS: returns a string of information for this item, negative amount for expense item
    public String toString() {
        if (!isIncome()) {
            amount = -amount;
        }
        return getId() + "    " + getDate() + "    " + getEntity() + "    " + getDescription() + "    " + amount;
    }
}
