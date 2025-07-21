package com.paras_test_android.assignment_paras
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Clase de datos para la tabla de gastos
 * contiene los datos de los gastos.
 */
@Entity(tableName = "expenses")
data class ExpenseTable(
    //primary key for the table
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    //title variable for the expense
    var title: String,
    //amount
    var amount: Double,
    //date
    var date: String,
    //category
    var category: String
)
