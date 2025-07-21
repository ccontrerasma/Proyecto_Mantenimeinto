package com.paras_test_android.assignment_paras

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

/***
 * Adapter class Para editar gastos, esto apunta a la pantalla de edición de gastos.
 */
class EditExpensesAdapter(private var expenses: List<ExpenseTable>) : RecyclerView.Adapter<EditExpensesAdapter.ExpenseViewHolder>() {

    /***
     *
     * Metodo para crear nuevas vistas
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_expense, parent, false)
        return ExpenseViewHolder(view)
    }


    /***
     *
     * Function actualizando el contenido de itemView para representar el elemento.
     */
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    /***
     *
     * Función que devuelve el tamaño de la lista de gastos
     */
    override fun getItemCount(): Int {
        return expenses.size
    }

    /***
     *
     * Metodo para actualizar la lista de gastos
     */
    fun updateExpenses(newExpenses: List<ExpenseTable>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    /***
     *
     * Función para obtener todos los gastos de la base de datos
     */
    fun getAllExpenses(): List<ExpenseTable> {
        return expenses
    }

    /***
     *
     * ExpenseViewHolder class Que conserva los campos de texto de edición para el título, el importe, la fecha y la categoría.
     *  Y vincula los datos a dichos campos.
     */
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private variables for the edit text fields
        private val titleEditText: EditText = itemView.findViewById(R.id.titleEditText)
        private val amountEditText: EditText = itemView.findViewById(R.id.amountEditText)
        private val dateEditText: EditText = itemView.findViewById(R.id.dateEditText)
        private val categoryEditText: EditText = itemView.findViewById(R.id.categoryEditText)

        /***
         *
         * Función que vincula los datos a los campos de texto de edición
         */
        fun bind(expense: ExpenseTable) {
            titleEditText.setText(expense.title)
            amountEditText.setText(expense.amount.roundToTwoDecimalPlaces().toString())
            dateEditText.setText(expense.date)
            categoryEditText.setText(expense.category)

            /***
             *
             * Text listeners para los campos de texto de edición para actualizar correctamente el objeto de gasto
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
