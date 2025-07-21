package com.paras_test_android.assignment_paras

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paras_test_android.assignment_paras.databinding.ShowExpensesActivityBinding
import kotlinx.coroutines.*

/**
 * Clase que muestra la actividad de gastos
 */
class ShowExpensesActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ShowExpensesActivityBinding
    private lateinit var expensesAdapter: ExpensesAdapter

    /**
     * OnCreate metodo para la actividad de mostrar estadísticas
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ShowExpensesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expensesAdapter = ExpensesAdapter(listOf())

        binding.expensesRecyclerView.adapter = expensesAdapter
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)

        loadExpenses()
        loadCategoriesAndSetupSpinner()

        // back button using BackButtonHelper
        BackButtonHelper.setupBackButton(this, R.id.backButton, MainActivity::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //** Load expenses method leveraging rounding
    //
    // */
    private fun loadExpenses() {
        CoroutineScope(Dispatchers.IO).launch {
            val expenses = AppDatabase.getDatabase(application).expenseDao().getAllExpenses()


            withContext(Dispatchers.Main) {
                expensesAdapter.updateExpenses(expenses.map {
                    it.apply { amount = amount.roundToTwoDecimalPlaces() }
                })
            }
        }
    }
    private fun loadCategoriesAndSetupSpinner() {
        CoroutineScope(Dispatchers.IO).launch {
            val categoriesFromDb = AppDatabase.getDatabase(application).expenseDao().getAllCategories()
            val categories = listOf("Todas") + categoriesFromDb.distinct().sorted()

            withContext(Dispatchers.Main) {
                val spinnerAdapter = ArrayAdapter(this@ShowExpensesActivity, android.R.layout.simple_spinner_item, categories)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.categoryFilterSpinner.adapter = spinnerAdapter

                binding.categoryFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val selectedCategory = parent.getItemAtPosition(position) as String
                        expensesAdapter.filterByCategory(selectedCategory)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }
}


