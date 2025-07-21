package com.paras_test_android.assignment_paras

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paras_test_android.assignment_paras.databinding.ShowExpensesActivityBinding
import kotlinx.coroutines.*

/**
 * Class that Shows expenses activity
 */
class ShowExpensesActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ShowExpensesActivityBinding
    private lateinit var expensesAdapter: ExpensesAdapter

    /**
     * OnCreate method overriding for show statistics activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowExpensesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        expensesAdapter = ExpensesAdapter(listOf())
        binding.expensesRecyclerView.adapter = expensesAdapter
        binding.expensesRecyclerView.layoutManager = LinearLayoutManager(this)

        loadExpenses()

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
}


