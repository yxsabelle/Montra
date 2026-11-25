package com.example.montraproject

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.util.Locale

data class Transaction(
    val title: String,
    val category: String,
    val date: String,
    val amount: Double,
    val isIncome: Boolean
)

data class Goal(val name: String, var saved: Double, val target: Double)

object Store {
    const val MONTHLY_BUDGET = 10000.0

    val transactions = mutableListOf(
        Transaction("Food", "Food", "Nov 24, 2026", 450.0, false),
        Transaction("Transportation", "Transportation", "Nov 23, 2026", 120.0, false),
        Transaction("Shopping", "Shopping", "Nov 20, 2026", 1850.0, false),
        Transaction("Electric Bill", "Bills", "Nov 18, 2026", 1500.0, false),
        Transaction("Salary", "Income", "Nov 15, 2026", 25000.0, true),
        Transaction("Gift", "Gift", "Nov 12, 2026", 500.0, false),
        Transaction("Allowance", "Income", "Nov 01, 2026", 3000.0, true)
    )

    val goals = mutableListOf(
        Goal("Emergency Fund", 6000.0, 15000.0),
        Goal("New Phone", 8500.0, 20000.0),
        Goal("Travel Fund", 2500.0, 10000.0)
    )

    val budgetLimits = linkedMapOf(
        "Food" to 3000.0,
        "Transportation" to 1500.0,
        "Shopping" to 2000.0,
        "Bills" to 2000.0,
        "Gift" to 500.0,
        "Others" to 1000.0
    )

    fun income() = transactions.filter { it.isIncome }.sumOf { it.amount }
    fun expenses() = transactions.filter { !it.isIncome }.sumOf { it.amount }
    fun balance() = income() - expenses()

    fun spentIn(category: String) =
        transactions.filter { !it.isIncome && it.category == category }.sumOf { it.amount }

    fun categoryTotals(): List<Pair<String, Double>> =
        transactions.filter { !it.isIncome }
            .groupBy { it.category }
            .map { entry -> Pair(entry.key, entry.value.sumOf { it.amount }) }
            .sortedByDescending { it.second }
}

fun peso(v: Double): String = "₱" + String.format(Locale.US, "%,.2f", v)
fun pesoShort(v: Double): String = "₱" + String.format(Locale.US, "%,.0f", v)

val chartColors = listOf(
    Color.parseColor("#C62828"),
    Color.parseColor("#8E1B1B"),
    Color.parseColor("#E2A68F"),
    Color.parseColor("#C9A66B"),
    Color.parseColor("#E2CBA3")
)

fun populateTransactions(
    container: LinearLayout,
    items: List<Transaction>,
    inflater: LayoutInflater
) {
    container.removeAllViews()
    if (items.isEmpty()) {
        val empty = TextView(container.context)
        empty.text = "No transactions found"
        empty.setTextColor(ContextCompat.getColor(container.context, R.color.text_gray))
        empty.setPadding(24, 24, 24, 24)
        container.addView(empty)
        return
    }
    items.forEach { t ->
        val row = inflater.inflate(R.layout.item_transaction, container, false)
        row.findViewById<TextView>(R.id.tvIcon).text = t.title.first().uppercase()
        row.findViewById<TextView>(R.id.tvTitle).text = t.title
        row.findViewById<TextView>(R.id.tvMeta).text = "${t.category} • ${t.date}"
        val amount = row.findViewById<TextView>(R.id.tvAmount)
        amount.text = (if (t.isIncome) "+" else "-") + peso(t.amount)
        amount.setTextColor(
            ContextCompat.getColor(
                container.context,
                if (t.isIncome) R.color.text_dark else R.color.red
            )
        )
        container.addView(row)
    }
}

fun bindCategoryChart(context: Context, donut: DonutView, legend: LinearLayout) {
    val totals = Store.categoryTotals()
    donut.setData(
        totals.map { it.second.toFloat() },
        chartColors,
        pesoShort(Store.expenses())
    )
    legend.removeAllViews()
    totals.take(5).forEachIndexed { i, pair ->
        val text = SpannableString("●  ${pair.first}  ${pesoShort(pair.second)}")
        text.setSpan(
            ForegroundColorSpan(chartColors[i % chartColors.size]),
            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val tv = TextView(context)
        tv.text = text
        tv.textSize = 12f
        tv.setTextColor(ContextCompat.getColor(context, R.color.text_dark))
        tv.setPadding(0, 4, 0, 4)
        legend.addView(tv)
    }
}

fun styleChip(tv: TextView, selected: Boolean) {
    tv.setBackgroundResource(if (selected) R.drawable.bg_chip_selected else R.drawable.bg_chip)
    tv.setTextColor(
        ContextCompat.getColor(tv.context, if (selected) R.color.cream else R.color.text_dark)
    )
}