package com.paras_test_android.assignment_paras

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.paras_test_android.assignment_paras.databinding.ActivityAddExpenseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Class para agregar Gastos.
 */
class AddExpensesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = listOf("GASTO", "INGRESO")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter


        binding.submitExpenseButton.setOnClickListener {
            addExpense()
        }

        // Set up the back button using BackButtonHelper
        BackButtonHelper.setupBackButton(this, R.id.backButtonAdd, MainActivity::class.java)
    }

    /**
     * Function Para agregar gastos a la base de datos. El registro está activado para depurar la función.
     */
    private fun addExpense() {
        val title = binding.titleInput.text.toString()
        val amount = binding.amountInput.text.toString().toDoubleOrNull() ?: 0.0
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yy")
        val date = formatter.format(time).toString()
        //val date = binding.dateInput.text.toString()
        val category = binding.categorySpinner.selectedItem.toString()

        if (title.isNotBlank() && date.isNotBlank() && category.isNotBlank()) {
            val expense = ExpenseTable(title = title, amount = amount, date = date, category = category)
            saveExpenseToDatabase(expense)
        } else {
            Log.d("AddExpensesActivity", "Invalid input: Title, Date, and Category must not be empty.")
        }
    }


    /**
     * Function Para controlar el botón de retroceso. Regresa a la actividad principal.
     * Obsoleto, pero creo que es bueno para la compatibilidad.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    /**
     * Function Para guardar el gasto en la base de datos al pulsar el botón Guardar
     * * por el usuario.
     */
    private fun saveExpenseToDatabase(expense: ExpenseTable) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expenseDao = AppDatabase.getDatabase(application).expenseDao()
                expenseDao.insertExpense(listOf(expense))
                Log.d("AddExpensesActivity", "Expense saved: $expense")
                finish()
            } catch (e: Exception) {
                Log.e("AddExpensesActivity", "Error saving expense", e)
            }
        }
    }
}
