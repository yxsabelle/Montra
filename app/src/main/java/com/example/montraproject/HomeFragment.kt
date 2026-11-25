package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val main = requireActivity() as MainActivity

        view.findViewById<TextView>(R.id.tvBalance).text = peso(Store.balance())
        view.findViewById<TextView>(R.id.tvIncome).text = peso(Store.income())
        view.findViewById<TextView>(R.id.tvExpense).text = peso(Store.expenses())

        bindCategoryChart(
            requireContext(),
            view.findViewById<DonutView>(R.id.donutHome),
            view.findViewById<LinearLayout>(R.id.llLegend)
        )
        populateTransactions(
            view.findViewById<LinearLayout>(R.id.llRecent),
            Store.transactions.take(3),
            layoutInflater
        )

        view.findViewById<View>(R.id.tvViewAll).setOnClickListener { main.openScreen(HistoryFragment()) }
        view.findViewById<View>(R.id.btnHistory).setOnClickListener { main.openScreen(HistoryFragment()) }
        view.findViewById<View>(R.id.btnBudget).setOnClickListener { main.openScreen(BudgetFragment()) }
        view.findViewById<View>(R.id.btnBuddy).setOnClickListener { main.openScreen(BuddyFragment()) }
    }
}