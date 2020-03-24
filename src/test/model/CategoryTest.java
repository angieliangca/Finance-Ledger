package model;

import exceptions.DuplicateItemException;
import exceptions.InvalidCategoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for the Category class
public class CategoryTest {
    private Category testCategory;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private Item testItem4;
    private Item testItem5;

    @BeforeEach
    void runBefore() {
        testCategory = new Category();
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
        assertEquals(2, testCategory.getIncomeCategory().size());
        assertEquals(8, testCategory.getExpenseCategory().size());
        assertEquals(0, (testCategory.getIncomeCategory().get("Salary")));
        assertEquals(0, (testCategory.getIncomeCategory().get("Investment")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Housing")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Transportation")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Food")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Clothing")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Utilities")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Entertainment")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Medical")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Miscellaneous")));
    }

    @Test
    void testUpdateIncomeTotal() {
        testCategory.calculateSubtotal(testItem1.getCategory(), testItem1.getAmount());
        assertEquals(1000, (testCategory.getIncomeCategory().get("Salary")));
    }

    @Test
    void testUpdateExpenseTotal() {
        testCategory.calculateSubtotal(testItem2.getCategory(), testItem2.getAmount());
        assertEquals(800, (testCategory.getExpenseCategory().get("Housing")));
    }

    @Test
    void testUpdateMultipleSubtotal() {
        testCategory.calculateSubtotal(testItem1.getCategory(), testItem1.getAmount());
        testCategory.calculateSubtotal(testItem2.getCategory(), testItem2.getAmount());
        testCategory.calculateSubtotal(testItem3.getCategory(), testItem3.getAmount());
        testCategory.calculateSubtotal(testItem4.getCategory(), testItem4.getAmount());
        testCategory.calculateSubtotal(testItem5.getCategory(), testItem5.getAmount());
        assertEquals(1000, (testCategory.getIncomeCategory().get("Salary")));
        assertEquals(100, (testCategory.getIncomeCategory().get("Investment")));
        assertEquals(800, (testCategory.getExpenseCategory().get("Housing")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Transportation")));
        assertEquals(30, (testCategory.getExpenseCategory().get("Food")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Clothing")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Utilities")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Entertainment")));
        assertEquals(0, (testCategory.getExpenseCategory().get("Medical")));
        assertEquals(50, (testCategory.getExpenseCategory().get("Miscellaneous")));
    }

    @Test
    void testIncomePercentageToString() throws DuplicateItemException {
        Ledger testLedger = new Ledger();
        assertEquals("No income item in the ledger.\n",
                testCategory.incomePercentageToString(testLedger).toString());

        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
        testLedger.addItem(testItem3);
        testLedger.addItem(testItem4);
        testLedger.addItem(testItem5);
        String printString = "Salary        1000.0        90.91%\n" + "Investment        100.0        9.09%\n";
        assertEquals(printString, testCategory.incomePercentageToString(testLedger).toString());

    }

    @Test
    void testExpensePercentageToString() throws DuplicateItemException {
        Ledger testLedger = new Ledger();
        assertEquals("No expense item in the ledger.\n",
                testCategory.expensePercentageToString(testLedger).toString());

        testLedger.addItem(testItem1);
        testLedger.addItem(testItem2);
        testLedger.addItem(testItem3);
        testLedger.addItem(testItem4);
        testLedger.addItem(testItem5);
        String printString = "Utilities        0.0        0.0%\n" + "Miscellaneous        50.0        5.68%\n" +
                "Entertainment        0.0        0.0%\n" + "Clothing        0.0        0.0%\n" +
                "Transportation        0.0        0.0%\n" + "Medical        0.0        0.0%\n" +
                "Housing        800.0        90.91%\n" + "Food        30.0        3.41%\n";
        assertEquals(printString, testCategory.expensePercentageToString(testLedger).toString());
    }

    @Test
    void testCheckIncomeCategory() {
        try {
            testCategory.checkIncomeCategory("Investment");
        } catch (InvalidCategoryException e) {
            fail("No exception was expected.");
        }
    }

    @Test
    void testCheckExpenseCategory() {
        try {
            testCategory.checkExpenseCategory("Medical");
        } catch (InvalidCategoryException e) {
            fail("No exception was expected.");
        }
    }

    @Test
    void testInvalidIncomeCategoryException() {
        try {
            testCategory.checkIncomeCategory("Education");
            fail("InvalidCategoryException was expected.");
        } catch (InvalidCategoryException e) {
            //expected
        }
    }

    @Test
    void testInvalidExpenseCategoryException() {
        try {
            testCategory.checkExpenseCategory("Education");
            fail("InvalidCategoryException was expected.");
        } catch (InvalidCategoryException e) {
            // expected
        }
    }

}
