package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.progressindicator.LinearProgressIndicator

class BuddyFragment : Fragment(R.layout.fragment_buddy) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val spent = Store.expenses()
        val pct = (spent / Store.MONTHLY_BUDGET * 100).toInt()

        view.findViewById<TextView>(R.id.tvAdvice).text = when {
            pct < 50 -> "You're on track! Keep managing your spending this month."
            pct < 80 -> "You've used $pct% of your budget. Keep an eye on your spending."
            else -> "Careful! You've used $pct% of your budget. Time to slow down."
        }
        view.findViewById<LinearProgressIndicator>(R.id.buddyProgress).progress =
            pct.coerceIn(0, 100)
        view.findViewById<TextView>(R.id.tvBuddySpent).text =
            "${peso(spent)} / ${peso(Store.MONTHLY_BUDGET)}"
        view.findViewById<TextView>(R.id.tvBuddyPct).text = "$pct%"
        view.findViewById<TextView>(R.id.tvReminder).text =
            if (pct >= 80) "You're approaching your budget limit!"
            else "You're within your budget. Nice work!"

        view.findViewById<MaterialSwitch>(R.id.swReminder)
            .setOnCheckedChangeListener { _, checked ->
                Toast.makeText(
                    requireContext(),
                    if (checked) "Reminders on" else "Reminders off",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}