package com.example.montraproject

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment(R.layout.fragment_add_expense) {

    private var tvReceipt: TextView? = null

    private val pickReceipt =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) tvReceipt?.text = "Receipt attached ✓"
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val etDesc = view.findViewById<EditText>(R.id.etDesc)
        val spCategory = view.findViewById<Spinner>(R.id.spCategory)
        val spPayment = view.findViewById<Spinner>(R.id.spPayment)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        tvReceipt = view.findViewById(R.id.tvReceipt)

        fun fill(spinner: Spinner, items: List<String>) {
            val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, items)
            adapter.setDropDownViewResource(R.layout.item_spinner)
            spinner.adapter = adapter
        }
        fill(spCategory, Store.budgetLimits.keys.toList())
        fill(spPayment, listOf("Cash", "GCash", "Credit Card", "Debit Card"))

        val fmt = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val cal = Calendar.getInstance()
        tvDate.text = fmt.format(cal.time)
        tvDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    cal.set(y, m, d)
                    tvDate.text = fmt.format(cal.time)
                },
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        view.findViewById<View>(R.id.boxReceipt).setOnClickListener { pickReceipt.launch("image/*") }

        view.findViewById<View>(R.id.btnSave).setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull()
            if (amount == null || amount <= 0) {
                etAmount.error = "Enter a valid amount"
                return@setOnClickListener
            }
            val category = spCategory.selectedItem.toString()
            val desc = etDesc.text.toString().trim()
            Store.transactions.add(
                0,
                Transaction(
                    if (desc.isEmpty()) category else desc,
                    category,
                    tvDate.text.toString(),
                    amount,
                    false
                )
            )
            Toast.makeText(requireContext(), "Expense saved!", Toast.LENGTH_SHORT).show()
            (requireActivity() as MainActivity).goHome()
        }
    }
}