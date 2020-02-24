package persistence;

import exceptions.DuplicateItemException;
import model.Item;
import model.Ledger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a ledger parsed from file;
    // throws IOException if an exception is raised when opening / reading from file
    public static Ledger readLedger(File file) throws IOException, DuplicateItemException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a ledger i.e. list of items parsed from list of strings
    // where each string contains data for one item
    private static Ledger parseContent(List<String> fileContent) throws DuplicateItemException {
        Ledger ledger = new Ledger();
        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            ledger.addItem(parseItem(lineComponents));
        }
        return ledger;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 7 where element 0 represents the id, element 1 represents the type, elements 2
    // represents the date, element 3 represents the entity, element 4 represents the description, element 5 represents
    // the category, element 6 represents the amount
    // EFFECTS: returns an item constructed from components
    private static Item parseItem(List<String> components) {
        int id = Integer.parseInt(components.get(0));
        boolean type = Boolean.parseBoolean(components.get(1));
        String date = components.get(2);
        String entity = components.get(3);
        String description = components.get(4);
        String category = components.get(5);
        double amount = Double.parseDouble(components.get(6));
        Item item = new Item(type, date, entity, description, category, amount);
        item.setId(id);
        return item;
    }
}
