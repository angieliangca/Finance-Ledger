package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Ledger class
class LedgerTest {
    private Ledger testLedger;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private Item testItem4;
    private Item testItem5;

    @BeforeEach
    void runBefore() {
        testLedger = new Ledger();
        testItem1 = new Item(true, "02-15-2020", "Research Lab", "Salary",
                "Salary", 1000.00);
        testItem2 = new Item(false, "02-10-2020", "UBC Housing", "Rent",
                "Housing", 800.00);
        testItem3 = new Item(false, "02-02-2020", "UBC Bookstore", "Textbook",
                "Miscellaneous", 50.00);
        testItem4 = new Item(true, "02-05-2020", "TD Bank", "Tesla stock",
                "Investment", 100.00);
        testItem5 = new Item(false,"02-07-2020", "Save on Food", "Grocery",
                "Food", 30.00);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(0, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getIncomeCategory().get("Salary")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Housing")));
    }

    @Test
    void testAddIncomeItemElement() {
        try {
            testLedger.addItem("income", "02-15-2020", "Research Lab", "Salary",
                    "Salary", 1000.00);
        } catch (DuplicateItemException e) {
            fail("No exception was expected");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(1000, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(1000, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(1000, (testLedger.getIncomeCategory().get("Salary")));
    }

    @Test
    void testAddIncomeItem() {
        try {
            testLedger.addItem(testItem1);
        } catch (DuplicateItemException e) {
            fail("No exception was expected");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(1000, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(1000, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(1000, (testLedger.getIncomeCategory().get("Salary")));
    }

    @Test
    void testAddExpenseItemElement() {
        try {
            testLedger.addItem("expense", "02-10-2020", "UBC Housing", "Rent",
                    "Housing", 800.00);
        } catch (DuplicateItemException e) {
            fail("No exception was expected");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(800, testLedger.getTotalExpense());
        assertEquals(-800, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(800, (testLedger.getExpenseCategory().get("Housing")));
    }

    @Test
    void testAddExpenseItem() {
        try {
            testLedger.addItem(testItem2);
        } catch (DuplicateItemException e) {
            fail("No exception was expected");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(800, testLedger.getTotalExpense());
        assertEquals(-800, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(800, (testLedger.getExpenseCategory().get("Housing")));
    }

    @Test
    void testAddDuplicateItem() {
        // test adding items that do not satisfy duplicate criteria
        try {
            testLedger.addItem(testItem3);
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "Miscellaneous", 100.00);
            testLedger.addItem("expense", "02-02-2020", "UBC", "Textbook", "Miscellaneous", 50.00);
            testLedger.addItem("expense", "02-01-2020", "UBC Bookstore", "Textbook", "Miscellaneous", 50.00);
            testLedger.addItem("income", "02-02-2020", "UBC Bookstore", "Textbook", "Salary", 50.00);
        } catch (DuplicateItemException e) {
            fail("No exception was expected");
        }
        assertEquals(5, testLedger.numItems());
        assertEquals(50, testLedger.getTotalIncome());
        assertEquals(250, testLedger.getTotalExpense());
        assertEquals(-200, testLedger.getNetIncome());
        assertEquals(5, testLedger.getMaxIncome());
        assertEquals(2, testLedger.getMaxExpense());
        assertEquals(250, (testLedger.getExpenseCategory().get("Miscellaneous")));
        assertEquals(50, (testLedger.getIncomeCategory().get("Salary")));

        // test adding item that throw DuplicateItemException
        try {
            testLedger.addItem(testItem3);
            fail("DuplicateItemException was expected.");
        } catch (DuplicateItemException e) {
            // expected
        }
        try {
            testLedger.addItem("income", "02-02-2020", "UBC Bookstore", "Textbook",
                    "Salary", 50.00);
            fail("DuplicateItemException was expected.");
        } catch (DuplicateItemException e) {
            // expected
        }
    }

    @Test
    void testDeleteIncomeItem() throws DuplicateItemException {
        try {
            testLedger.addItem(testItem1);
            assertTrue(testLedger.deleteItem(1));
        } catch (InvalidIDException e) {
            fail("There is no item with the given id");
        }
        assertEquals(0, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(0, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getIncomeCategory().get("Salary")));
    }

    @Test
    void testDeleteExpenseItem() throws DuplicateItemException {
        try {
            testLedger.addItem(testItem2);
            assertTrue(testLedger.deleteItem(1));
        } catch (InvalidIDException e) {
            fail("There is no item with the given id");
        }
        assertEquals(0, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(0, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("Housing")));
    }

    @Test
    void testDeleteEmptyItem() throws InvalidIDException {
        assertFalse(testLedger.deleteItem(1));
    }

    @Test
    void testDeleteInvalidIDItem() throws DuplicateItemException {
        testLedger.addItem(testItem3);

        try {
            testLedger.deleteItem(2);
            fail("InvalidIDException was expected");
        } catch (InvalidIDException e) {
            // expected
        }

        try {
            testLedger.deleteItem(0);
            fail("InvalidIDException was expected");
        } catch (InvalidIDException e) {
            // expected
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(50, testLedger.getTotalExpense());
        assertEquals(-50, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(50, (testLedger.getExpenseCategory().get("Miscellaneous")));
    }

    @Test
    void testAddMultipleItems() throws DuplicateItemException {
        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
        testLedger.addItem(testItem3);
        testLedger.addItem(testItem4);
        testLedger.addItem(testItem5);

        assertEquals(5, testLedger.numItems());
        assertEquals(1100, testLedger.getTotalIncome());
        assertEquals(880, testLedger.getTotalExpense());
        assertEquals(220, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(2, testLedger.getMaxExpense());
        assertEquals(800, (testLedger.getExpenseCategory().get("Housing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Transportation")));
        assertEquals(30, (testLedger.getExpenseCategory().get("Food")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Clothing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Utilities")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Entertainment")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Medical")));
        assertEquals(1000, (testLedger.getIncomeCategory().get("Salary")));
        assertEquals(100, (testLedger.getIncomeCategory().get("Investment")));
        assertEquals(50, (testLedger.getExpenseCategory().get("Miscellaneous")));
    }

    @Test
    void testDeleteMultipleItem() throws InvalidIDException, DuplicateItemException {
        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
        testLedger.addItem(testItem3);
        testLedger.addItem(testItem4);
        testLedger.addItem(testItem5);
        testLedger.deleteItem(2);
        assertEquals(4, testLedger.numItems());
        assertEquals(1100, testLedger.getTotalIncome());
        assertEquals(80, testLedger.getTotalExpense());
        assertEquals(1020, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(2, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("Housing")));

        testLedger.deleteItem(3);
        assertEquals(30, testLedger.getItem(3).getAmount());
        assertEquals(30, (testLedger.getExpenseCategory().get("Food")));
        testLedger.deleteItem(3);
        assertEquals(2, testLedger.numItems());
        assertEquals(1000, testLedger.getTotalIncome());
        assertEquals(50, testLedger.getTotalExpense());
        assertEquals(950, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(2, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("Housing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Transportation")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Food")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Clothing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Utilities")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Entertainment")));
        assertEquals(0, (testLedger.getExpenseCategory().get("Medical")));
        assertEquals(1000, (testLedger.getIncomeCategory().get("Salary")));
        assertEquals(0, (testLedger.getIncomeCategory().get("Investment")));
        assertEquals(50, (testLedger.getExpenseCategory().get("Miscellaneous")));
    }

    @Test
    void testToString() throws DuplicateItemException {
        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
        String printString = testLedger.toString();
        assertEquals("1    " + testLedger.getItem(1).toString() + "\n"
                + "2    " + testLedger.getItem(2).toString() + "\n", printString);
    }

    @Test
    void testInvalidTypeException() {
        try {
            testLedger.checkType("income");
        } catch (InvalidTypeException e) {
            fail("No exception was expected.");
        }

        try {
            testLedger.checkType("expense");
        } catch (InvalidTypeException e) {
            fail("No exception was expected.");
        }

        try {
            testLedger.checkType("spending");
            fail("InvalidTypeException was expected.");
        } catch (InvalidTypeException e) {
            // expected
        }
    }

    @Test
    void testInvalidDateException() {
        try {
            testLedger.checkDate("02-02-2020");
        } catch (InvalidDateException e) {
            fail("No exception was expected");
        }

        try {
            testLedger.checkDate("2020");
            fail("InvalidDateException was expected");
        } catch (InvalidDateException e) {
            // expected
        }

//        try {
//            testLedger.checkDate("13-02-2020");
//        } catch (InvalidDateException e) {
//            // expected
//        }
    }

    @Test
    void testInvalidCategoryException() {
        try {
            testLedger.checkExpenseCategory("Medical");
        } catch (InvalidCategoryException e) {
            fail("No exception was expected.");
        }

        try {
            testLedger.checkIncomeCategory("Investment");
        } catch (InvalidCategoryException e) {
            fail("No exception was expected.");
        }

        try
        {
            testLedger.checkIncomeCategory("Education");
            fail("InvalidCategoryException was expected.");
        } catch (InvalidCategoryException e) {
            //expected
        }

        try
        {
            testLedger.checkExpenseCategory("Education");
            fail("InvalidCategoryException was expected.");
        } catch (InvalidCategoryException e) {
            // expected
        }
    }

    @Test
    void testNegativeAmountException() {
        try {
            testLedger.checkAmount(1);
        } catch (NegativeAmountException e) {
            fail("No exception was expected.");
        }

        try {
            testLedger.checkAmount(0);
        } catch (NegativeAmountException e) {
            fail("No exception was expected.");
        }

        try {
            testLedger.checkAmount(-1);
            fail("NegativeAmountException was expected.");
        } catch (NegativeAmountException e) {
            // expected
        }
    }

    @Test
    void testGetMyLedger() {
        List<Item> itemList = new LinkedList<>();
        assertEquals(itemList, testLedger.getMyLedger());
    }
}