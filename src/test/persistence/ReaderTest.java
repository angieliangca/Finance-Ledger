package persistence;

import exceptions.DuplicateItemException;
import model.Item;
import model.Ledger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Reader class
public class ReaderTest {
    @Test
    void testParseAccountsFile1() {
        try {
            Ledger ledger1 = Reader.readLedger(new File("./data/testLedgerFile1.txt"));
            Item item1 = ledger1.getItem(1);
            assertEquals(1, item1.getId());
            assertTrue(item1.isIncome());
            assertEquals("02-15-2020", item1.getDate());
            assertEquals("Research Lab", item1.getEntity());
            assertEquals("Salary", item1.getDescription());
            assertEquals("salary", item1.getCategory());
            assertEquals(1000.00, item1.getAmount());

            Item item2 = ledger1.getItem(2);
            assertEquals(2, item2.getId());
            assertFalse(item2.isIncome());
            assertEquals("02-10-2020", item2.getDate());
            assertEquals("UBC Housing", item2.getEntity());
            assertEquals("Rent", item2.getDescription());
            assertEquals("housing", item2.getCategory());
            assertEquals(800.00, item2.getAmount());
        } catch (IOException | DuplicateItemException e) {
            fail("IOException or DuplicateItemException was not expected.");
        }
    }

    @Test
    void testParseAccountsFile2() {
        try {
            Ledger ledger2 = Reader.readLedger(new File("./data/testLedgerFile2.txt"));
            Item item1 = ledger2.getItem(1);
            assertEquals(1, item1.getId());
            assertFalse(item1.isIncome());
            assertEquals("02-02-2020", item1.getDate());
            assertEquals("UBC Bookstore", item1.getEntity());
            assertEquals("Textbook", item1.getDescription());
            assertEquals("miscellaneous", item1.getCategory());
            assertEquals(50.00, item1.getAmount());

            Item item2 = ledger2.getItem(2);
            assertEquals(2, item2.getId());
            assertTrue(item2.isIncome());
            assertEquals("02-05-2020", item2.getDate());
            assertEquals("TD Bank", item2.getEntity());
            assertEquals("Tesla stock", item2.getDescription());
            assertEquals("investment", item2.getCategory());
            assertEquals(100.00, item2.getAmount());
        } catch (IOException | DuplicateItemException e) {
            fail("IOException or DuplicateItemException was not expected.");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readLedger(new File("./path/does/not/exist/test.txt"));
            fail("IOException was expected.");
        } catch (IOException e) {
            // expected
        } catch (DuplicateItemException e) {
            fail("IOException was expected.");
        }
    }
}
