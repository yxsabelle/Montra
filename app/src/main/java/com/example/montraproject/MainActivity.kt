package com.example.montraproject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private val tabIds = listOf(
        R.id.navHome, R.id.navReports, R.id.navAdd, R.id.navGoals, R.id.navProfile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabIds.forEachIndexed { index, id ->
            findViewById<View>(id).setOnClickListener { selectTab(index) }
        }
        if (savedInstanceState == null) selectTab(0) else highlight(0)
    }

    fun selectTab(index: Int) {
        val fragment: Fragment = when (index) {
            0 -> HomeFragment()
            1 -> ReportsFragment()
            2 -> AddExpenseFragment()
            3 -> GoalsFragment()
            else -> ProfileFragment()
        }
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        highlight(index)
    }

    fun openScreen(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun goHome() = selectTab(0)

    private fun highlight(index: Int) {
        val red = ContextCompat.getColor(this, R.color.red)
        val muted = ContextCompat.getColor(this, R.color.text_gray)
        tabIds.forEachIndexed { i, id ->
            if (i == 2) return@forEachIndexed
            val item = findViewById<LinearLayout>(id)
            val color = if (i == index) red else muted
            (item.getChildAt(0) as ImageView).setColorFilter(color)
            (item.getChildAt(1) as TextView).setTextColor(color)
        }
    }
}