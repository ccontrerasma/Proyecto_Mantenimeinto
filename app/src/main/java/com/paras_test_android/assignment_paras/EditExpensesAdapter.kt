package com.paras_test_android.assignment_paras

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

/***
 * Adapter class for editing expenses this targets the  edit expense screen
 */
class EditExpensesAdapter(private var expenses: List<ExpenseTable>) : RecyclerView.Adapter<EditExpensesAdapter.ExpenseViewHolder>() {

    /***
     *
     * Method to create new views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_expense, parent, false)
        return ExpenseViewHolder(view)
    }


    /***
     *
     * Function updating the contents of the itemView  to represent the item.
     */
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    /***
     *
     * Function returning the size of the list of expenses
     */
    override fun getItemCount(): Int {
        return expenses.size
    }

    /***
     *
     * Method  to update the list of expenses
     */
    fun updateExpenses(newExpenses: List<ExpenseTable>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    /***
     *
     * Function to get all the expenses from db
     */
    fun getAllExpenses(): List<ExpenseTable> {
        return expenses
    }

    /***
     *
     * ExpenseViewHolder class that keeps the edit text fields for the title, amount, date and category
     * and binds the data to the edit text fields
     */
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private variables for the edit text fields
        private val titleEditText: EditText = itemView.findViewById(R.id.titleEditText)
        private val amountEditText: EditText = itemView.findViewById(R.id.amountEditText)
        private val dateEditText: EditText = itemView.findViewById(R.id.dateEditText)
        private val categoryEditText: EditText = itemView.findViewById(R.id.categoryEditText)

        /***
         *
         * Function that binds the data to the edit text fields
         */
        fun bind(expense: ExpenseTable) {
            titleEditText.setText(expense.title)
            amountEditText.setText(expense.amount.roundToTwoDecimalPlaces().toString())
            dateEditText.setText(expense.date)
            categoryEditText.setText(expense.category)

            /***
             *
             * Text listeners for the edit text fields to update the expense object correctly
             */
            titleEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    expense.title = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            amountEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    expense.amount = s.toString().toDoubleOrNull() ?: 0.0
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            dateEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    expense.date = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            categoryEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    expense.category = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
