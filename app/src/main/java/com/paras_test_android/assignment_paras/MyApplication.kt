package com.paras_test_android.assignment_paras

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Application class for the app that populates the database with some data
 * This will allow the user to see some data when they first open the app
 */
class MyApplication : Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//
//        CoroutineScope(Dispatchers.IO).launch {
//            initiateDatabase()
//        }
//    }
//
//    /**
//     * Function that I used before to populate the database with some data
//     *
//     */
//    private suspend fun initiateDatabase() {
//        val expenseDao = AppDatabase.getDatabase(this).expenseDao()
//
//        // Insert data  will be same as in the tests
//        val expense1 = ExpenseTable(title = "Cinema Ticket Other", amount = 899.0, date = "29/06/2024", category = "Relax")
//        val expense2 = ExpenseTable(title = "Croissant", amount = 10.0, date = "02/01/2024", category = "Groceries")
//        //val expense3 = ExpenseTable(title = "Chocolate", amount = 90.0, date = "03/01/2024", category = "Food")
//        //val expense4 = ExpenseTable(title = "Cinema Ticket Odeon", amount = 999.99, date = "04/01/2024", category = "Relax")
//
//        expenseDao.insertExpenses(listOf(expense1, expense2))
//    }
}