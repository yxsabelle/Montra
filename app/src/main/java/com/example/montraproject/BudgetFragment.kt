package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator

class BudgetFragment : Fragment(R.layout.fragment_budget) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val budget = Store.MONTHLY_BUDGET
        val spent = Store.expenses()
        val left = (budget - spent).coerceAtLeast(0.0)
        val pct = (spent / budget * 100).toInt().coerceIn(0, 100)

        view.findViewById<DonutView>(R.id.donutBudget).setData(
            listOf(spent.toFloat().coerceAtMost(budget.toFloat()), left.toFloat()),
            listOf(
                ContextCompat.getColor(requireContext(), R.color.red),
                ContextCompat.getColor(requireContext(), R.color.beige_dark)
            ),
            "$pct%"
        )
        view.findViewById<TextView>(R.id.tvBudgetTotal).text = peso(budget)
        view.findViewById<TextView>(R.id.tvBudgetSpent).text = "Spent ${peso(spent)}"
        view.findViewById<TextView>(R.id.tvBudgetLeft).text = "Left ${peso(left)}"

        val rows = view.findViewById<LinearLayout>(R.id.llBudgetRows)
        rows.removeAllViews()
        Store.budgetLimits.forEach { (category, limit) ->
            val s = Store.spentIn(category)
            val row = layoutInflater.inflate(R.layout.item_budget_row, rows, false)
            row.findViewById<TextView>(R.id.tvCat).text = category
            row.findViewById<TextView>(R.id.tvNums).text =
                "${pesoShort(s)} / ${pesoShort(limit)}"
            row.findViewById<LinearProgressIndicator>(R.id.progress).progress =
                (s / limit * 100).toInt().coerceIn(0, 100)
            rows.addView(row)
        }
    }
}