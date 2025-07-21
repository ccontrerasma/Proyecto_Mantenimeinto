package com.paras_test_android.assignment_paras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Clase adapter para la vista del reciclador de gastos
 */
class ExpensesAdapter(private var expenses: List<ExpenseTable>) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val categoryTextView: TextView = view.findViewById(R.id.categoryTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)

    }

    /**
     * Función que crea el contenedor de la vista al crearse
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    /**
     * Función que vincula el contenedor de la vista a los datos
     */
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.titleTextView.text = expense.title
        holder.categoryTextView.text = expense.category.toString()
        holder.amountTextView.text = "S/. ${expense.amount}"

    }

    override fun getItemCount() = expenses.size

    /**
     * Función de actualización de la lista de gastos
     */
    fun updateExpenses(newExpenses: List<ExpenseTable>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}





