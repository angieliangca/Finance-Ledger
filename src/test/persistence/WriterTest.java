package persistence;

import exceptions.DuplicateItemException;
import model.Item;
import model.Ledger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Writer class
public class WriterTest {
    private static final String TEST_FILE = "./data/testLedger.txt";
    private Writer testWriter;
    private Ledger testLedger;
    private Item testItem1;
    private Item testItem2;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException, DuplicateItemException {
        testWriter = new Writer(new File(TEST_FILE));
        testLedger = new Ledger();
        testItem1 = new Item(true, "02-15-2020", "Research Lab", "Salary",
                "salary", 1000.00);
        testItem2 = new Item(false, "02-10-2020", "UBC Housing", "Rent",
                "housing", 800.00);
        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
    }

    @Test
    void testWriteAccounts() {
        testWriter.write(testLedger);
        testWriter.close();

        try {
            Ledger writtenLedger = Reader.readLedger(new File(TEST_FILE));
            Item writtenItem1 = writtenLedger.getItem(1);
            assertEquals(1, writtenItem1.getId());
            assertTrue(writtenItem1.isIncome());
            assertEquals("02-15-2020", writtenItem1.getDate());
            assertEquals("Research Lab", writtenItem1.getEntity());
            assertEquals("Salary", writtenItem1.getDescription());
            assertEquals("salary", writtenItem1.getCategory());
            assertEquals(1000.00, writtenItem1.getAmount());

            Item writtenItem2 = writtenLedger.getItem(2);
            assertEquals(2, writtenItem2.getId());
            assertFalse(writtenItem2.isIncome());
            assertEquals("02-10-2020", writtenItem2.getDate());
            assertEquals("UBC Housing", writtenItem2.getEntity());
            assertEquals("Rent", writtenItem2.getDescription());
            assertEquals("housing", writtenItem2.getCategory());
            assertEquals(800.00, writtenItem2.getAmount());

        } catch (IOException | DuplicateItemException e) {
            fail("IOException or DuplicateItemException was not expected.");
        }
    }
}