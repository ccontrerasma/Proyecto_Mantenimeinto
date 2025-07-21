package com.paras_test_android.assignment_paras

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Test class for ExpenseDAO that tests all the CRUD functionality
 */
@RunWith(AndroidJUnit4::class)
class ExpenseDAOTest {

    //create instances
    private lateinit var db: AppDatabase
    private lateinit var dao: ExpenseDAO

    /**
     * Create in-memory database same as in the other test
     */
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.expenseDao()
    }

    /**
     * Close the database after the test
     */
    @After
    fun closeDb() {
        db.close()
    }


    /**
     * Test insert expense
     */
    @Test
    fun testInsertExpense() = runBlocking {
        val expense = ExpenseTable(title = "Groceries", amount = 50.0, date = "12/06/2024", category = "Food")
        dao.insertExpense(listOf(expense))
        val retrievedExpenses = dao.getAllExpenses()
        assertEquals(1, retrievedExpenses.size)
        assertEquals("Groceries", retrievedExpenses[0].title)
    }

    /**
     * Test update expenses functionality
     */
    @Test
    fun testUpdateExpense() = runBlocking {
        val expense = ExpenseTable(title = "Groceries", amount = 50.0, date = "12/06/2024", category = "Food")
        dao.insertExpense(listOf(expense))
        val retrievedExpense = dao.getAllExpenses().first()

        val updatedExpense = retrievedExpense.copy(amount = 75.0)
        dao.updateExpenses(listOf(updatedExpense))
        val retrievedUpdatedExpense = dao.getAllExpenses().first()
        assertEquals(75.0, retrievedUpdatedExpense.amount, 0.01)
    }

    /**
     * Test delete expenses functionality
     */
    @Test
    fun testDeleteExpense() = runBlocking {
        val expense = ExpenseTable(title = "Groceries", amount = 50.0, date = "12/06/2024", category = "Food")
        dao.insertExpense(listOf(expense))
        val retrievedExpense = dao.getAllExpenses().first()
        dao.deleteExpenses(listOf(retrievedExpense))
        val retrievedExpenses = dao.getAllExpenses()
        assertTrue(retrievedExpenses.isEmpty())
    }


    /**
     * Test delete all expenses functionality in the editexpenses activity
     */
    @Test
    fun testDeleteAllExpenses() = runBlocking {
        val expense1 = ExpenseTable(title = "Groceries", amount = 50.0, date = "12/06/2024", category = "Food")
        val expense2 = ExpenseTable(title = "Rent", amount = 1200.0, date = "01/06/2024", category = "Housing")
        dao.insertExpense(listOf(expense1, expense2))

        // Check expenses are in the database
        val expensesBeforeDelete = dao.getAllExpenses()
        assertEquals(2, expensesBeforeDelete.size)

        // Delete all expenses with DAO function
        dao.deleteAllExpenses()

        // Verify all the expenses are deleted from the database
        val expensesAfterDelete = dao.getAllExpenses()
        assertTrue(expensesAfterDelete.isEmpty())
    }


    /**
     * Test get all expenses in app
     */
    @Test
    fun testGetAllExpenses() = runBlocking {
        val expense1 = ExpenseTable(title = "Groceries", amount = 50.0, date = "12/06/2024", category = "Food")
        val expense2 = ExpenseTable(title = "Rent", amount = 1200.0, date = "01/06/2024", category = "Housing")
        dao.insertExpense(listOf(expense1))
        dao.insertExpense(listOf(expense2))

        val expenses = dao.getAllExpenses()
        assertEquals(2, expenses.size)
        assertEquals("Groceries", expenses[0].title)
        assertEquals("Rent", expenses[1].title)
    }
}
