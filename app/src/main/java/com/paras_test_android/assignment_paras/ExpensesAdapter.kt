package com.paras_test_android.assignment_paras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter class for the expenses recycler view
 */
class ExpensesAdapter(private var expenses: List<ExpenseTable>) : RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)

    }

    /**
     * Function that creates the view holder on creation
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    /**
     * Function binding the view holder to the data
     */
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.titleTextView.text = expense.title
        holder.amountTextView.text = expense.amount.toString()

    }

    override fun getItemCount() = expenses.size

    /**
     * Function updating the expenses list
     */
    fun updateExpenses(newExpenses: List<ExpenseTable>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }
}





