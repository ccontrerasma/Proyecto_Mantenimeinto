package com.paras_test_android.assignment_paras
import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.nio.file.Files.delete


/**
 * Data Access Object for the expenses table
 * DAO to me looks like the DTO objects in Java
 */
@Dao
interface ExpenseDAO {
    /**
     * adding a new expense in db
     */
    @Insert
    suspend fun insertExpense(expense: List<ExpenseTable>)

    //select statement for viewing all expenses
    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseTable>

    /**
     * inserts expenses in the db using list of expenses
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpenses(expenses: List<ExpenseTable>)

    /**
     * updates expenses in the db
     */
    @Update
    suspend fun updateExpenses(expenses: List<ExpenseTable>)

    /**
     * deletes expenses  in Room db
     */
    @Delete
    suspend fun deleteExpenses(expenses: List<ExpenseTable>)

    /**
     * deletes all expenses in Room db this is a helper function
     */
    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()

    /**
     * select statement for getting the most frequent title in show statistics
     */
    @Query("SELECT title, COUNT(title) as count FROM expenses GROUP BY title ORDER BY count DESC LIMIT 1")
    suspend fun getMostFrequentTitle(): ExpenseTitleFrequency

    /**
     * select statement in order to show the most frequent category in show statistics menu
     */
    @Query("SELECT category, COUNT(category) as count FROM expenses GROUP BY category ORDER BY count DESC LIMIT 1")
    suspend fun getMostFrequentCategory(): ExpenseCategoryFrequency

    /**
     * select statement for creating tghe average amount per category of expenses in statistics
     */
    @Query("SELECT category, AVG(amount) as averageAmount FROM expenses GROUP BY category")
    suspend fun getAverageAmountPerCategory(): List<CategoryAverageAmount>
    /**
     * select statement to show the most recent expense by date in the show statistics menu
     */
    @Query("SELECT * FROM expenses ORDER BY date DESC LIMIT 1")
    suspend fun getMostRecentExpense(): ExpenseTable

}