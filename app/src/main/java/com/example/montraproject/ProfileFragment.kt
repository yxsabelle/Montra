package com.example.montraproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvProfBalance).text = pesoShort(Store.balance())
        view.findViewById<TextView>(R.id.tvProfSaved).text =
            pesoShort(Store.goals.sumOf { it.saved })
        view.findViewById<TextView>(R.id.tvProfGoals).text = Store.goals.size.toString()

        val llMenu = view.findViewById<LinearLayout>(R.id.llMenu)
        listOf(
            "Account Information",
            "Safety & Security",
            "Notification Settings",
            "Appearance",
            "Help & Support",
            "About Montra"
        ).forEach { title ->
            val row = layoutInflater.inflate(R.layout.item_menu_row, llMenu, false)
            row.findViewById<TextView>(R.id.tvMenu).text = title
            row.setOnClickListener {
                Toast.makeText(requireContext(), "$title coming soon", Toast.LENGTH_SHORT).show()
            }
            llMenu.addView(row)
        }

        view.findViewById<View>(R.id.btnLogout).setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}