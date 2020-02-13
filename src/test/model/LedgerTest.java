package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Ledger class
class LedgerTest {
    private Ledger testLedger;

    @BeforeEach
    void runBefore() {
        testLedger = new Ledger();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(0, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(0, (testLedger.getIncomeCategory().get("salary")));
    }

    @Test
    void testAddIncomeItem() {
        try {
            testLedger.addItem("income", "02-05-2020", "Research Lab", "salary", "salary", 1000.00);
        } catch (DuplicateItemException e) {
            fail("There is an existing item in the ledger");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(1000, testLedger.getTotalIncome());
        assertEquals(0, testLedger.getTotalExpense());
        assertEquals(1000, testLedger.getNetIncome());
        assertEquals(1, testLedger.getMaxIncome());
        assertEquals(0, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getIncomeCategory().get("investment")));
        assertEquals(1000, (testLedger.getIncomeCategory().get("salary")));
    }

    @Test
    void testAddExpenseItem() {
        try {
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        } catch (DuplicateItemException e) {
            fail("There is an existing item in the ledger");
        }
        assertEquals(1, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(50, testLedger.getTotalExpense());
        assertEquals(-50, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(50, (testLedger.getExpenseCategory().get("miscellaneous")));
    }

    @Test
    void testAddDuplicateItem() {
        try {
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 100.00);
            testLedger.addItem("expense", "02-02-2020", "UBC", "Textbook", "miscellaneous", 50.00);
            testLedger.addItem("expense", "02-01-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
            testLedger.addItem("income", "02-02-2020", "UBC Bookstore", "Textbook", "salary", 50.00);
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        } catch (DuplicateItemException e) {

        }
        assertEquals(5, testLedger.numItems());
        assertEquals(50, testLedger.getTotalIncome());
        assertEquals(250, testLedger.getTotalExpense());
        assertEquals(-200, testLedger.getNetIncome());
        assertEquals(5, testLedger.getMaxIncome());
        assertEquals(2, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(250, (testLedger.getExpenseCategory().get("miscellaneous")));
        assertEquals(50, (testLedger.getIncomeCategory().get("salary")));
    }

    @Test
    void testDeleteItem() throws DuplicateItemException {
        try {
            testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
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
        assertEquals(0, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("miscellaneous")));
    }

    @Test
    void testDeleteEmptyItem() throws InvalidIDException {
        assertFalse(testLedger.deleteItem(1));
    }

    @Test
    void testDeleteInvalidIDItem() throws DuplicateItemException {
        testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        try {
            testLedger.deleteItem(2);
        } catch (InvalidIDException e) {

        }
        try {
            testLedger.deleteItem(0);
        } catch (InvalidIDException e) {

        }
        assertEquals(1, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(50, testLedger.getTotalExpense());
        assertEquals(-50, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(50, (testLedger.getExpenseCategory().get("miscellaneous")));
    }

    @Test
    void testAddMultipleItem() throws DuplicateItemException {
        testLedger.addItem("expense", "02-01-2020", "UBC Housing", "Rent", "housing", 200.00);
        testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        testLedger.addItem("expense", "02-03-2020", "Save on Food", "Grocery", "food", 30.00);
        testLedger.addItem("income", "02-05-2020", "Research Lab", "Salary", "salary", 1000.00);
        testLedger.addItem("income", "02-10-2020", "Parent", "Reimbursement", "salary", 80.00);

        assertEquals(5, testLedger.numItems());
        assertEquals(1080, testLedger.getTotalIncome());
        assertEquals(280, testLedger.getTotalExpense());
        assertEquals(800, testLedger.getNetIncome());
        assertEquals(4, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(200, (testLedger.getExpenseCategory().get("housing")));
        assertEquals(50, (testLedger.getExpenseCategory().get("miscellaneous")));
        assertEquals(30, (testLedger.getExpenseCategory().get("food")));
        assertEquals(1080, (testLedger.getIncomeCategory().get("salary")));
        assertEquals(0, (testLedger.getExpenseCategory().get("transportation")));
        assertEquals(0, (testLedger.getExpenseCategory().get("clothing")));
        assertEquals(0, (testLedger.getExpenseCategory().get("utilities")));
        assertEquals(0, (testLedger.getExpenseCategory().get("entertainment")));
        assertEquals(0, (testLedger.getExpenseCategory().get("medical")));
        assertEquals(0, (testLedger.getIncomeCategory().get("investment")));
    }

    @Test
    void testDeleteMultipleItem() throws InvalidIDException, DuplicateItemException {
        testLedger.addItem("expense", "02-01-2020", "UBC Housing", "Rent", "housing", 200.00);
        testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        testLedger.addItem("expense", "02-03-2020", "Save on Food", "Grocery", "food", 30.00);
        testLedger.addItem("income", "02-05-2020", "Research Lab", "Salary", "salary", 1000.00);
        testLedger.addItem("income", "02-10-2020", "Parent", "Reimbursement", "salary", 80.00);
        testLedger.deleteItem(2);
        assertEquals(4, testLedger.numItems());
        assertEquals(1080, testLedger.getTotalIncome());
        assertEquals(230, testLedger.getTotalExpense());
        assertEquals(850, testLedger.getNetIncome());
        assertEquals(3, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getExpenseCategory().get("miscellaneous")));

        testLedger.deleteItem(3);
        assertEquals(80, testLedger.getItem(3).getAmount());
        assertEquals(80, (testLedger.getIncomeCategory().get("salary")));
        testLedger.deleteItem(3);
        assertEquals(2, testLedger.numItems());
        assertEquals(0, testLedger.getTotalIncome());
        assertEquals(230, testLedger.getTotalExpense());
        assertEquals(-230, testLedger.getNetIncome());
        assertEquals(0, testLedger.getMaxIncome());
        assertEquals(1, testLedger.getMaxExpense());
        assertEquals(0, (testLedger.getIncomeCategory().get("salary")));
    }

    @Test
    void testToString() throws DuplicateItemException {
        testLedger.addItem("expense", "02-02-2020", "UBC Bookstore", "Textbook", "miscellaneous", 50.00);
        testLedger.addItem("income", "02-05-2020", "Research Lab", "Salary", "salary", 1000.00);
        String printString = testLedger.toString();
        assertEquals(testLedger.getItem(1).toString() + "\n" + testLedger.getItem(2).toString() + "\n", printString);
    }

    @Test
    void testCheckType() {
        try {
            testLedger.checkType("income");
        } catch (InvalidTypeException e) {
            fail("Please enter valid type");
        }

        try {
            testLedger.checkType("expense");
        } catch (InvalidTypeException e) {
            fail("Please enter valid type");
        }

        try {
            testLedger.checkType("hello");
        } catch (InvalidTypeException e) {

        }
    }

    @Test
    void testCheckDate() {
        try {
            testLedger.checkDate("02-02-2020");
        } catch (InvalidDateException e) {
            fail("Please enter valid date");
        }

        try {
            testLedger.checkDate("13-02-2020");
        } catch (InvalidDateException e) {

        }

        try {
            testLedger.checkDate("2020");
        } catch (InvalidDateException e) {

        }
    }

    @Test
    void testCheckCategory() {
        try {
            testLedger.checkExpenseCategory("medical");
        } catch (InvalidCategoryException e) {
            fail("Please select from the provided options");
        }

        try {
            testLedger.checkIncomeCategory("investment");
        } catch (InvalidCategoryException e) {
            fail("Please select from the provided options");
        }

        try
        {
            testLedger.checkIncomeCategory("education");
        } catch (InvalidCategoryException e) {

        }

        try
        {
            testLedger.checkExpenseCategory("education");
        } catch (InvalidCategoryException e) {

        }
    }

    @Test
    void testCheckAmount() {
        try {
            testLedger.checkAmount(1);
        } catch (NegativeAmountException e) {
            fail("Please enter positive amount");
        }

        try {
            testLedger.checkAmount(0);
        } catch (NegativeAmountException e) {
            fail("Please enter positive amount");
        }

        try {
            testLedger.checkAmount(-1);
        } catch (NegativeAmountException e) {

        }
    }
}