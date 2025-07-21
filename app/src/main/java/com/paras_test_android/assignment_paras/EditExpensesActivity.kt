package com.paras_test_android.assignment_paras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paras_test_android.assignment_paras.databinding.ActivityEditExpensesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.appcompat.app.AlertDialog



/**
 * Class that edits expenses activity when using the main menu on upper left corner of the app
 * or on the main screen of the app.
 */
class EditExpensesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditExpensesBinding
    private lateinit var expensesAdapter: EditExpensesAdapter

    /**
     * Function that creates the activity and sets up the adapter for the recycler view.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            expensesAdapter = EditExpensesAdapter(listOf())

            binding.editExpensesRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.editExpensesRecyclerView.adapter = expensesAdapter

            loadExpenses()

            // Save button listener
            binding.saveButton.setOnClickListener {
                saveExpenses()
            }

            // DeleteAll button listener for the edit expense activity
            binding.deleteAllButton.setOnClickListener {
                showDeleteAllConfirmationDialog()
            }

            // Set up the back button using BackButtonHelper class
            BackButtonHelper.setupBackButton(this, R.id.backButtonEdit, MainActivity::class.java)

        } catch (e: Exception) {
            Log.e("EditExpensesActivity", "Error while initializing activity in the app", e)
        }
    }

    /**
     * Function that loads expenses from the database and updates the adapter.
     */
    private fun loadExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expenses = AppDatabase.getDatabase(application).expenseDao().getAllExpenses()
                Log.d("EditExpensesActivity", "Expenses loaded: $expenses") // Log expenses

                withContext(Dispatchers.Main) {
                    if (expenses.isNotEmpty()) {
                        expensesAdapter.updateExpenses(expenses)
                        Log.d("EditExpensesActivity", "Expenses updated in adapter")
                    } else {
                        Log.d("EditExpensesActivity", "No expense to show")
                    }
                }
            } catch (e: Exception) {
                Log.e("EditExpensesActivity", "Error loading expenses in the app", e)
            }
        }
    }

    /**
     * Function that handles the back button press and goes back to the main activity.
     *
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Function to save expenses to Room database. separates empty and non-empty expenses
     * and deletes the empty ones and updates the non-empty ones.
     */
    private fun saveExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expenseDao = AppDatabase.getDatabase(application).expenseDao()

                // Get all the expenses
                val allExpenses = expensesAdapter.getAllExpenses()

                // Log the expenses for debugging
                Log.d("EditExpensesActivity", "All expenses: $allExpenses")

                // Identify empty and non-empty expenses to erase the right ones later
                val (emptyExpenses, nonEmptyExpenses) = allExpenses.partition { expense ->
                    Log.d("EditExpensesActivity", "Evaluating expense: $expense")
                    expense.title.trim().isBlank() && expense.amount == 0.0 && expense.date.trim().isBlank() && expense.category.trim().isBlank()
                }

                // Delete empty expenses
                if (emptyExpenses.isNotEmpty()) {
                    Log.d("EditExpensesActivity", "Deleting empty expenses: $emptyExpenses")
                    expenseDao.deleteExpenses(emptyExpenses)
                }

                // Update non-empty expenses
                if (nonEmptyExpenses.isNotEmpty()) {
                    Log.d("EditExpensesActivity", "Updating non-empty expenses: $nonEmptyExpenses")
                    expenseDao.updateExpenses(nonEmptyExpenses)
                }

                // Update the adapter
                withContext(Dispatchers.Main) {
                    val updatedExpenses = nonEmptyExpenses.toMutableList()
                    expensesAdapter.updateExpenses(updatedExpenses)
                }

                // Close the activity
                withContext(Dispatchers.Main) {
                    finish()
                }

            } catch (e: Exception) {
                Log.e("EditExpensesActivity", "Error saving expenses: ${e.message}")
            }
        }
    }


    /**
     * Function that shows a confirmation dialog before deleting all expenses in editexpenses activity.
     *
     */
    private fun showDeleteAllConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Expenses")
        builder.setMessage("REALLY delete ALL expenses in Room Database?")
        builder.setPositiveButton("Yes") { _, _ ->
            deleteAllExpenses()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }


    /**
     * Function that deletes all expenses from the Room database with delete all button.
     */
    private fun deleteAllExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase(application).expenseDao().deleteAllExpenses()
            withContext(Dispatchers.Main) {
                expensesAdapter.updateExpenses(emptyList())
                Toast.makeText(this@EditExpensesActivity, getString(R.string.toast_statistics_deleted), Toast.LENGTH_SHORT).show()
            }
        }
    }


}
