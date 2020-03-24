package model;

import exceptions.DuplicateItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {
    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item(true, "02-15-2020", "Research Lab", "Salary",
                "Salary", 1000.00);
    }

    @Test
    void testToString() {
        String printString = testItem.toString();
        assertEquals("Income" + "    " + "02-15-2020" + "    " + "Research Lab" + "    " + "Salary"
                + "    " + "Salary" + "    " + "1000.0", printString);
    }
}
