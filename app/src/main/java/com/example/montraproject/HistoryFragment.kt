package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var filter = 0 // 0 = all, 1 = income, 2 = expenses

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val llHistory = view.findViewById<LinearLayout>(R.id.llHistory)
        val chips = listOf(
            view.findViewById<TextView>(R.id.chipAll),
            view.findViewById<TextView>(R.id.chipIncome),
            view.findViewById<TextView>(R.id.chipExpense)
        )

        fun refresh() {
            chips.forEachIndexed { i, chip -> styleChip(chip, i == filter) }
            val q = etSearch.text.toString().trim().lowercase()
            val list = Store.transactions.filter { t ->
                val okType = when (filter) {
                    1 -> t.isIncome
                    2 -> !t.isIncome
                    else -> true
                }
                val okText = q.isEmpty() ||
                        t.title.lowercase().contains(q) ||
                        t.category.lowercase().contains(q)
                okType && okText
            }
            populateTransactions(llHistory, list, layoutInflater)
        }

        chips.forEachIndexed { i, chip ->
            chip.setOnClickListener {
                filter = i
                refresh()
            }
        }
        etSearch.doAfterTextChanged { refresh() }
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        refresh()
    }
}