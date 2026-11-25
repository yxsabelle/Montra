package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator

class ReportsFragment : Fragment(R.layout.fragment_reports) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvRepIncome).text = pesoShort(Store.income())
        view.findViewById<TextView>(R.id.tvRepExpense).text = pesoShort(Store.expenses())
        view.findViewById<TextView>(R.id.tvRepNet).text = pesoShort(Store.balance())

        bindCategoryChart(
            requireContext(),
            view.findViewById<DonutView>(R.id.donutReports),
            view.findViewById<LinearLayout>(R.id.llRepLegend)
        )

        val totals = Store.categoryTotals()
        val total = Store.expenses()
        val llTop = view.findViewById<LinearLayout>(R.id.llTop)
        llTop.removeAllViews()
        totals.take(3).forEach { pair ->
            val row = layoutInflater.inflate(R.layout.item_budget_row, llTop, false)
            row.findViewById<TextView>(R.id.tvCat).text = pair.first
            row.findViewById<TextView>(R.id.tvNums).text = pesoShort(pair.second)
            row.findViewById<LinearProgressIndicator>(R.id.progress).progress =
                if (total > 0) (pair.second / total * 100).toInt() else 0
            llTop.addView(row)
        }

        view.findViewById<TextView>(R.id.tvTip).text =
            if (totals.isNotEmpty())
                "Your biggest spending is on ${totals[0].first}. Try lowering it next month to save more."
            else "Add your first expense to get personalized tips."

        val chips = listOf(
            view.findViewById<TextView>(R.id.chipWeekly),
            view.findViewById<TextView>(R.id.chipMonthly),
            view.findViewById<TextView>(R.id.chipYearly)
        )
        chips.forEachIndexed { i, chip ->
            styleChip(chip, i == 1)
            chip.setOnClickListener { chips.forEachIndexed { j, c -> styleChip(c, j == i) } }
        }
    }
}