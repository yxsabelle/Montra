package com.example.montraproject

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.LinearProgressIndicator

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    private lateinit var llGoals: LinearLayout
    private lateinit var tvTotal: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llGoals = view.findViewById(R.id.llGoals)
        tvTotal = view.findViewById(R.id.tvTotalSaved)
        view.findViewById<View>(R.id.btnNewGoal).setOnClickListener { showNewGoalDialog() }
        render()
    }

    private fun render() {
        llGoals.removeAllViews()
        tvTotal.text = peso(Store.goals.sumOf { it.saved })
        Store.goals.forEach { goal ->
            val pct = if (goal.target > 0)
                (goal.saved / goal.target * 100).toInt().coerceIn(0, 100) else 0
            val row = layoutInflater.inflate(R.layout.item_goal, llGoals, false)
            row.findViewById<TextView>(R.id.tvGoalName).text = goal.name
            row.findViewById<TextView>(R.id.tvGoalPct).text = "$pct%"
            row.findViewById<TextView>(R.id.tvGoalNums).text =
                "${peso(goal.saved)} / ${peso(goal.target)}"
            row.findViewById<LinearProgressIndicator>(R.id.goalProgress).progress = pct
            row.findViewById<View>(R.id.btnAddSavings).setOnClickListener { showAddDialog(goal) }
            llGoals.addView(row)
        }
    }

    private fun showAddDialog(goal: Goal) {
        val input = EditText(requireContext())
        input.hint = "Amount"
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        AlertDialog.Builder(requireContext())
            .setTitle("Add savings to ${goal.name}")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val amount = input.text.toString().toDoubleOrNull()
                if (amount != null && amount > 0) {
                    goal.saved += amount
                    render()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showNewGoalDialog() {
        val box = LinearLayout(requireContext())
        box.orientation = LinearLayout.VERTICAL
        box.setPadding(48, 24, 48, 0)

        val etName = EditText(requireContext())
        etName.hint = "Goal name"
        val etTarget = EditText(requireContext())
        etTarget.hint = "Target amount"
        etTarget.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        box.addView(etName)
        box.addView(etTarget)

        AlertDialog.Builder(requireContext())
            .setTitle("New Savings Goal")
            .setView(box)
            .setPositiveButton("Create") { _, _ ->
                val name = etName.text.toString().trim()
                val target = etTarget.text.toString().toDoubleOrNull()
                if (name.isNotEmpty() && target != null && target > 0) {
                    Store.goals.add(Goal(name, 0.0, target))
                    render()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}