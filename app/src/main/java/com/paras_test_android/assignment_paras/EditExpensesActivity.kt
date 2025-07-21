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
 * Class Permite editar la actividad de gastos desde el menú principal en la esquina superior izquierda de la aplicación o en la pantalla principal.
 */
class EditExpensesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditExpensesBinding
    private lateinit var expensesAdapter: EditExpensesAdapter

    /**
     * Function que crea la actividad y configura el adaptador para la vista del reciclador.
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
     * Function que carga gastos de la base de datos y actualiza el adaptador.
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
     * Function que maneja la pulsación del botón Atrás y regresa a la actividad principal.
     *
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Function Para guardar gastos en la base de datos de la habitación. Separa los gastos vacíos de los que no están vacíos.
     * Elimina los gastos vacíos y actualiza los que no están vacíos.
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
     * Function que muestra un cuadro de diálogo de confirmación antes de eliminar todos los gastos en la actividad de edición de gastos.
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
     * Function que elimina todos los gastos de la base de datos de la habitación con el botón eliminar todo.
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
